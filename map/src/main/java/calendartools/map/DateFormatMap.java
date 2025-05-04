package calendartools.map;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/** Mapping java.util.Date Objects.
 */
public class DateFormatMap {
    
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
    
    static {
        SIMPLE_DATE_FORMAT.setLenient(false);
        SIMPLE_REVERSED_DATE_FORMAT.setLenient(false);
    }
    
    private static DateFormatMap DEFAULT_DATE_MAP = null;
    
    /** The Default Map checks SimpleDateFormat (YYYY-MM-DD) then, it's Reverse (DD-MM-YYYY).
     * @return The new DateMap object.
     */
    public static DateFormatMap getDefaultMap() {
        if (DEFAULT_DATE_MAP == null) {
            DEFAULT_DATE_MAP = new DateFormatMap(
                List.of(SIMPLE_DATE_FORMAT, SIMPLE_REVERSED_DATE_FORMAT)
            );
        }
        return DEFAULT_DATE_MAP;
    }
    
    /** The DateFormats that the Class will use to parse DateString Arguments.
     */
    public final List<DateFormat> mDateFormats;
    
    /** Constructor for a single DateFormat.
     * @param dateFormat The object that is used to parse strings containing Date information.
     */
    public DateFormatMap(
        final DateFormat dateFormat
    ) {
        mDateFormats = List.of(dateFormat);
    }
    
    /** Constructor for a list of DateFormat.
     * @param dateFormatList The list of DateFormat parsers to try on each Date string in the given order.
     */
    public DateFormatMap(
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
    
    /** Map a DateFormat-compatible String into a Calendar.
     * @param dateString The String containing the Date.
     * @return A new Calendar Instance created from the parsed DateString.
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
    
    /** Map a List of DateFormat-compatible Strings into a List of Calendar objects.
     * @param inputDateStrings The Strings containing the Date information.
     * @return A List of Calendars, which may include null values if the String could not be parsed.
     */
    public List<Calendar> map(
        final List<String> inputDateStrings
    ) {
        if (inputDateStrings == null) return Collections.emptyList();
        return inputDateStrings.stream()
            .map(this::map)
            .collect(Collectors.toList());
    }
    
    /** Map an Array of DateFormat-compatible Strings into a List of Calendar objects.
     * @param inputDateStrings The Strings containing the Date information.
     * @return A List of Calendars, which may include null values for Strings that failed to parse.
     */
    public List<Calendar> map(
        final String[] inputDateStrings
    ) {
        if (inputDateStrings == null) return Collections.emptyList();
        return Arrays.stream(inputDateStrings)
            .map(this::map)
            .collect(Collectors.toList());
    }
    
}
