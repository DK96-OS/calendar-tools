package calendartools.map;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/** Mapping java.util.Date Objects.
 */
public class DateMap {
    
    /** Convert a Date object into a Calendar object, using Instant as an intermediary.
     * @param date The Date object to derive information from.
     * @return A new Calendar object.
     */
    public static Calendar convert(
        final Date date
    ) throws IllegalArgumentException {
        if (date == null)
            throw new IllegalArgumentException("Argument Must Not Be Null");
        return new Calendar.Builder().setInstant(date).build();
    }
    
    /** Convert a Calendar object into a Date object, using Instant as an intermediary.
     * @param calendar The Calendar object to derive information from.
     * @return A new Date object.
     */
    public static Date convert(
        final Calendar calendar
    ) throws IllegalArgumentException {
        if (calendar == null)
            throw new IllegalArgumentException("Argument Must Not Be Null");
        return Date.from(calendar.toInstant());
    }
    
    /** The Recommended DateFormat for Strings.
     */
    public static SimpleDateFormat SIMPLE_DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd");
    
    /** The reversed Simple DateFormat for Strings.
     */
    public static SimpleDateFormat SIMPLE_REVERSED_DATE_FORMAT = new SimpleDateFormat("dd-MM-yyyy");
    
    /** .
     */
    public static SimpleDateFormat MONTH_DAY_FORMAT = new SimpleDateFormat("MM-dd");
    
    static {
        SIMPLE_DATE_FORMAT.setLenient(false);
        SIMPLE_REVERSED_DATE_FORMAT.setLenient(false);
        MONTH_DAY_FORMAT.setLenient(false);
    }
    
    private static DateMap DEFAULT_DATE_MAP = null;
    
    /** The Default Map checks SimpleDateFormat (YYYY-MM-DD) then, it's Reverse (DD-MM-YYYY).
     * @return The new DateMap object.
     */
    public static DateMap getDefaultMap() {
        if (DEFAULT_DATE_MAP == null) {
            DEFAULT_DATE_MAP = new DateMap(
                List.of(SIMPLE_DATE_FORMAT, SIMPLE_REVERSED_DATE_FORMAT)
            );
        }
        return DEFAULT_DATE_MAP;
    }
    
    private static DateMap MONTH_DAY_DATE_MAP = null;
    
    /** The MonthDay Map checks MonthDay DateFormat (MM-DD).
     * @return The new DateMap object.
     */
    public static DateMap getMonthDayMap() {
        if (MONTH_DAY_DATE_MAP == null) {
            MONTH_DAY_DATE_MAP = new DateMap(
                List.of(MONTH_DAY_FORMAT)
            );
        }
        return MONTH_DAY_DATE_MAP;
    }
    
    /** The DateFormats that the Class will use to parse DateString Arguments.
     */
    public final List<DateFormat> mDateFormats;
    
    public DateMap(
        final DateFormat dateFormat
    ) {
        mDateFormats = List.of(dateFormat);
    }
    
    public DateMap(
        final List<DateFormat> dateFormatList
    ) {
        mDateFormats = dateFormatList;
    }
    
    /** Try to parse the Date string using the Simple DateFormat first, and then Reversed DateFormat.
     * @param dateString The Date String to be parsed.
     * @return The Date object, or null if the method failed to parse.
     */
    public static Date tryParseSimpleDateFormats(
        final String dateString
    ) throws IllegalArgumentException {
        if (dateString == null) throw new IllegalArgumentException();
        try {
            return SIMPLE_DATE_FORMAT.parse(dateString);
        } catch (ParseException ignored) {}
        try {
            return SIMPLE_REVERSED_DATE_FORMAT.parse(dateString);
        } catch (ParseException ignored) {}
        return null;
    }
    
    /** Parse a String containing a Date, using the YearPlanner's DateFormat member.
     * @param dateString The String containing the Date.
     * @return A new Calendar Instance corresponding to the parsed DateString.
     */
    public Calendar map(
        final String dateString
    ) throws IllegalArgumentException {
        if (dateString == null) throw new IllegalArgumentException();
        Date result = null;
        for (var x : mDateFormats) {
            try {
                result = x.parse(dateString);
                break;
            } catch (ParseException ignored) {}
        }
        if (result == null) return null;
        return convert(result);
    }
    
    public List<Calendar> map(
        final List<String> inputDateStrings
    ) {
        return inputDateStrings.stream()
            .map(this::map)
            .collect(Collectors.toList());
    }
    
    public List<Calendar> map(
        final String[] inputDateStrings
    ) {
        return Arrays.stream(inputDateStrings)
            .map(this::map)
            .collect(Collectors.toList());
    }
    
    /** Parse a Month-Day String into a Calendar object.
     * @param dateString The String containing the Month-Day (MM-DD) Formatted Date.
     * @return A new Calendar object, or null if it failed to parse.
     */
    public Calendar parseMonthDayString(
        final short year,
        final String dateString
    ) throws IllegalArgumentException {
        if (dateString == null) throw new IllegalArgumentException();
        Date initialDate = null;
        try {
            initialDate = MONTH_DAY_FORMAT.parse(dateString);
        } catch (ParseException ignored) {}
        if (null == initialDate)
            return null;
        initialDate.setYear(year - 1900);  // Normalized Year
        return convert(initialDate);
    }
    
}
