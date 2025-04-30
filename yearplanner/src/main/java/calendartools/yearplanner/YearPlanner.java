package calendartools.yearplanner;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/** The class that helps plan for a given year.
 *  - Date Strings are parsed using a DateFormat instance, optionally provided to constructor.
 *  - Default SimpleDateFormat: YYYY-MM-DD
 *  - Month-Day Integer Pairs are supported by Method overloads.
 */
public class YearPlanner {

    /** The Recommended DateFormat for Strings.
     */
    public static SimpleDateFormat SIMPLE_DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd");
    
    /** The reversed Simple DateFormat for Strings.
     */
    public static SimpleDateFormat SIMPLE_DATE_REVERSED_FORMAT = new SimpleDateFormat("dd-MM-yyyy");
    
    /** Another DateFormat usable by YearPlanner.
     */
    public static SimpleDateFormat MONTH_DAY_FORMAT = new SimpleDateFormat("MM-dd");
    
    static {
        SIMPLE_DATE_FORMAT.setLenient(false);
        SIMPLE_DATE_REVERSED_FORMAT.setLenient(false);
        MONTH_DAY_FORMAT.setLenient(false);
    }
    
    /** Determine the Day of the Year from the given Calendar Object.
     *  - Note: If Your Calendar object is from a different year, this will work for that year.
     * @param cal The java.util.Calendar object to obtain the Date information from.
     * @return The Day of the Year.
     */
    public static short getDayNumber(final Calendar cal) throws IllegalArgumentException {
        if (cal == null) throw new IllegalArgumentException();
        return (short) cal.get(Calendar.DAY_OF_YEAR);
    }

    /** Determine the Number of the Week of the Year from a Calendar object.
     *  - Note: If Your Calendar object is from a different year, this will work for that year.
     * @param cal The java.util.Calendar object containing Date information.
     * @return The Week of the Year.
     */
    public static byte getWeekNumber(final Calendar cal) throws IllegalArgumentException {
        if (cal == null) throw new IllegalArgumentException();
        return (byte) cal.get(Calendar.WEEK_OF_YEAR);
    }

    /** The Year that this Class will be used for.
     */
    public final short mYear;

    /** The DateFormat that the Class will use to parse DateString Arguments.
     */
    public final List<DateFormat> mDateFormats;

    /** Constructor.
     * @param year The year that this Planner will be used for.
     */
    public YearPlanner(
        final int year
    ) {
        // Validate the Year will fit in a Short integer.
        if (year > Short.MAX_VALUE || year < Short.MIN_VALUE) {
            throw new IllegalArgumentException(String.format("Invalid Year: $1%d", year));
        }
        mYear = (short) year;
        mDateFormats = null;
    }

    /** Constructor with custom DateFormat to apply to DateStrings before the defaults.
     * @param year The year that this Planner will be used for.
     * @param dateFormat The DateFormat that will be used first, fallback to YYYY-MM-DD, MM-DD.
     */
    public YearPlanner(
            final int year,
            final DateFormat dateFormat
    ) {
        if (dateFormat == null) throw new IllegalArgumentException();
        // Validate the Year will fit in a Short integer.
        if (year > Short.MAX_VALUE || year < Short.MIN_VALUE) {
            throw new IllegalArgumentException(String.format("Invalid Year: $1%d", year));
        }
        mYear = (short) year;
        mDateFormats = List.of(dateFormat);
    }

    /** Constructor with custom DateFormat List Interface for specifying exactly which DateFormats you want to support.
     * @param year The year that this Planner will be used for.
     * @param dateFormat The DateFormat List that will be used when trying to parse a DateString.
     */
    public YearPlanner(
            final int year,
            final List<DateFormat> dateFormat
    ) {
        if (dateFormat == null) throw new IllegalArgumentException();
        // Validate the Year will fit in a Short integer.
        if (year > Short.MAX_VALUE || year < Short.MIN_VALUE) {
            throw new IllegalArgumentException(String.format("Invalid Year: $1%d", year));
        }
        mYear = (short) year;
        mDateFormats = dateFormat;
    }
    
    /** Convert a Date object into a Calendar object, using the Instant as an intermediary.
     * @param date The Date object to derive information from.
     * @return A new Calendar object.
     */
    public static Calendar convert(final Date date) throws IllegalArgumentException {
        if (date == null) throw new IllegalArgumentException();
        return new Calendar.Builder()
                .setInstant(date)
                .build();
    }

    /** Parse a String containing a Date, using the YearPlanner's DateFormat member.
     * @param dateString The String containing the Date.
     * @return A new Calendar Instance corresponding to the parsed DateString.
     */
    public Calendar parseDateString(final String dateString) throws IllegalArgumentException {
        if (dateString == null) throw new IllegalArgumentException();
        Date result = null;
        if (mDateFormats == null) {
            result = tryParseSimpleDateFormats(dateString);
        } else for (var x : mDateFormats) {
            try {
                result = x.parse(dateString);
                break;
            } catch (ParseException ignored) {}
        }
        if (result == null) return null;
        return convert(result);
    }
    
    /** Parse a Month-Day String into a Calendar object.
     * @param dateString The String containing the Month-Day (MM-DD) Formatted Date.
     * @return A new Calendar object, or null if it failed to parse.
     */
    public Calendar parseMonthDayString(final String dateString) throws IllegalArgumentException {
        if (dateString == null) throw new IllegalArgumentException();
        Date initialDate = null;
        try {
            initialDate = MONTH_DAY_FORMAT.parse(dateString);
        } catch (ParseException ignored) {}
        if (null == initialDate)
            return null;
        initialDate.setYear(mYear - 1900);  // Normalized Year
        return convert(initialDate);
    }
    
    /** Create a new Calendar Instance for the given Month-Day Pair.
     * @param month The Month: Min 1, Max 12.
     * @param day The Day: Min 1, Max 31
     * @return A new Calendar Instance at the Requested Date.
     */
    public Calendar getCalendar(
            final int month,
            final int day
    ) {
        if (!validate_month_day_pair(month, day)) throw new IllegalArgumentException();
        return new Calendar.Builder()
                // Need to Convert to Calendar Months, which start at zero.
                .setDate(mYear, month - 1, day)
                .build();
    }

    /** Determine the Day of the Year from the given Date-Formatted String.
     * @param dateString The String containing the Formatted Date.
     * @return The Day of the Year.
     */
    public short getDayNumber(final String dateString) throws IllegalArgumentException {
        if (dateString == null) throw new IllegalArgumentException();
        return getDayNumber(parseDateString(dateString));
    }

    /** Determine the Day of the Year from month and day.
     * @param month The Month. Min 1, Max 12.
     * @param day The day of the month. Min 1, Max 31.
     * @return The Day of the Year.
     */
    public short getDayNumber(
            final int month,
            final int day
    ) throws IllegalArgumentException {
        return getDayNumber(getCalendar(month, day));
    }

    /** Determine the Number of the Week of the Year from a Date-Formatted String.
     * @param dateString A String containing a Formatted Date.
     * @return The Week of the Year.
     */
    public byte getWeekNumber(final String dateString) throws IllegalArgumentException {
        if (dateString == null) throw new IllegalArgumentException();
        return getWeekNumber(parseDateString(dateString));
    }

    /** Determine the Number of the Week of the Year.
     * @param month The Month. Min 1, Max 12.
     * @param day The day of the month. Min 1, Max 31.
     * @return The Week of the Year.
     */
    public short getWeekNumber(
            final int month,
            final int day
    ) throws IllegalArgumentException {
        return getWeekNumber(getCalendar(month, day));
    }

    /** Obtain an Array containing the Days of the Month for a given Week Number.
     * @param weekNumber The number of the Week.
     * @return A Byte Array containing 7 numbers, from Monday to Sunday.
     */
    public byte[] getDayArray(final byte weekNumber) throws IllegalArgumentException {
        var cal = new Calendar.Builder()
                .setFields(Calendar.YEAR, mYear, Calendar.WEEK_OF_YEAR, weekNumber)
                .build();
        byte[] dayArray = new byte[7];
        dayArray[0] = (byte) cal.get(Calendar.DAY_OF_MONTH);
        for (byte i = 1; i < 7; i++) {
            cal.roll(Calendar.DAY_OF_YEAR, 1);
            dayArray[i] = (byte) cal.get(Calendar.DAY_OF_MONTH);
        }
        return dayArray;
    }

    /** Determine whether the Month-Day Integer Pair is Valid.
     * @param month The Month Integer: Min 1, Max 12.
     * @param day The Day Integer: Min 1, Max 31.
     * @return True if both values are within their respective valid ranges.
     */
    boolean validate_month_day_pair(
            final int month,
            final int day
    ) {
        return !(1 > month || month > 12) && !(1 > day || day > 31);
    }
    
    /** Try to parse the Date string using the Simple DateFormat first, and then Reversed DateFormat.
     * @param dateString The Date String to be parsed.
     * @return The Date object, or null if the method failed to parse.
     */
    Date tryParseSimpleDateFormats(final String dateString) {
        try {
            return SIMPLE_DATE_FORMAT.parse(dateString);
        } catch (ParseException ignored) {}
        try {
            return SIMPLE_DATE_REVERSED_FORMAT.parse(dateString);
        } catch (ParseException ignored) {}
        return null;
    }
    
}
