package calendartools.yearplanner;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import calendartools.map.DateFormatMap;

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
    
    /** Determine the offset for the start of the given year.
     *  - Value Range: 1 - 7, corresponding with Calendar.SUNDAY ... Calendar.SATURDAY
     * @param year The Year to calculate for.
     * @return The Day of Week that the year starts on. See Calendar.SUNDAY, etc.
     */
    public static byte getWeekOffset(final int year) {
        Calendar cal = new Calendar.Builder()
            .setFields(Calendar.YEAR, year, Calendar.DAY_OF_YEAR, 1)
            .build();
        return (byte) cal.get(Calendar.DAY_OF_WEEK);
    }
    
    /** The Year that this Class will be used for.
     */
    public final short mYear;
    
    /** The Mapping that will be used to Parse DateStrings.
     */
    public final DateFormatMap mDateMap;
    
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
        mDateMap = DateFormatMap.getDefaultMap();
    }

    /** Constructor with custom DateFormat to apply to DateStrings before the defaults.
     * @param year The year that this Planner will be used for.
     * @param dateFormatMap The DateMap to use to parse Strings.
     */
    public YearPlanner(
        final int year,
        final DateFormatMap dateFormatMap
    ) {
        if (dateFormatMap == null) throw new IllegalArgumentException();
        // Validate the Year will fit in a Short integer.
        if (year > Short.MAX_VALUE || year < Short.MIN_VALUE) {
            throw new IllegalArgumentException(String.format("Invalid Year: $1%d", year));
        }
        mYear = (short) year;
        mDateMap = dateFormatMap;
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
        return DateFormatMap.convert(initialDate);
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
        if (!validateMonthDayPair(month, day))
            throw new IllegalArgumentException();
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
        return getDayNumber(mDateMap.map(dateString));
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
        return getWeekNumber(mDateMap.map(dateString));
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

    public short getWeekNumber(
        final int dayOfYear
    ) throws IllegalArgumentException {
        var cal = new Calendar.Builder()
            .setFields(Calendar.YEAR, mYear, Calendar.DAY_OF_YEAR, dayOfYear)
            .build();
        return getWeekNumber(cal);
    }
    
    /** Obtain an Array containing the Days of the Month for a given Week Number.
     * @param weekNumber The number of the Week.
     * @return A Byte Array containing 7 numbers, from Monday to Sunday.
     */
    public byte[] getDayArray(
        final byte weekNumber
    ) throws IllegalArgumentException {
        var cal = new Calendar.Builder()
                .setFields(Calendar.YEAR, mYear, Calendar.WEEK_OF_YEAR, weekNumber)
                .build();
        byte[] dayArray = new byte[7];
        dayArray[0] = (byte) cal.get(Calendar.DAY_OF_MONTH);
        var diff = 0;
        for (byte i = 1; i < 7; i++) {
            byte inc = (byte) (dayArray[i - 1] + 1);
            if (inc <= 28) {
                dayArray[i] = inc;
                diff++;
            } else {
                cal.add(Calendar.DAY_OF_YEAR, diff + 1);
                diff = 0;
                dayArray[i] = (byte) cal.get(Calendar.DAY_OF_MONTH);
            }
        }
        return dayArray;
    }

    /** Determine whether the Month-Day Integer Pair is Valid.
     * @param month The Month Integer: Min 1, Max 12.
     * @param day The Day Integer: Min 1, Max 31.
     * @return True if both values are within their respective valid ranges.
     */
    boolean validateMonthDayPair(
        final int month,
        final int day
    ) {
        final boolean isMonthValid = !(1 > month || month > 12);
        final boolean isDayValid = !(1 > day || day > 31);
        return isMonthValid && isDayValid;
    }
    
}
