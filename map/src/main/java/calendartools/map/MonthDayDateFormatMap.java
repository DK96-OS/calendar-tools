package calendartools.map;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/** A DateFormat Map designed for use with MonthDay Strings, where the Year is known ahead of time.
 *  Construct a new instance with the Year, which must be a Short range 2^15 integer.
 */
public class MonthDayDateFormatMap extends DateFormatMap {
    
    /** A Simple DateFormat for the Month and DayOfMonth (MM-DD).
     */
    public static SimpleDateFormat MONTH_DAY_FORMAT = new SimpleDateFormat("MM-dd");
    
    static {
        MONTH_DAY_FORMAT.setLenient(false);
    }
    
    /** Map the given Information into a Calendar using the MonthDay DateFormat.
     * @param year The Year to add to the Month-Day pair.
     * @param dateString The DateString to parse for Month-Day information.
     * @return The java.util.Calendar object created from the given arguments.
     * @throws IllegalArgumentException The DateString was null.
     */
    public static Calendar map(
        final short year,
        final String dateString
    ) throws IllegalArgumentException {
        if (dateString == null) throw new IllegalArgumentException();
        Date parsedDate = null;
        try {
            parsedDate = MONTH_DAY_FORMAT.parse(dateString);
        } catch (ParseException ignored) {}
        if (null == parsedDate)
            return null;
        parsedDate.setYear(year - 1900);  // Normalized Year
        return convert(parsedDate);
    }
    
    /** The Year that will be added to every Date that is Parsed.
     */
    public final short year;
    
    /** Constructor for a given Year.
     * @param year The year that valid Month-Day pairs will be mapped into. Must be within Short range.
     */
    public MonthDayDateFormatMap(
        final int year
    ) {
        super(MONTH_DAY_FORMAT);
        if (year < Short.MIN_VALUE || year > Short.MAX_VALUE)
            throw new IllegalArgumentException();
        this.year = (short) year;
    }
    
    /** Parse a Month-Day String into a Calendar object.
     * @param dateString The String containing the Month-Day (MM-DD) Formatted Date.
     * @return A new Calendar object, or null if it failed to parse.
     */
    @Override
    public Calendar map(
        final String dateString
    ) throws IllegalArgumentException {
        return map(year, dateString);
    }
    
    @Override
    public final boolean equals(final Object other) {
        if (this == other)  // Identity Check
            return true;
        if (!(other instanceof MonthDayDateFormatMap))  // Type Check
            return false;
        return year == ((MonthDayDateFormatMap) other).year;
    }
    
    @Override
    public int hashCode() {
        return year;
    }
    
}
