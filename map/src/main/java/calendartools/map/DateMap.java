package calendartools.map;

import java.util.Calendar;
import java.util.Date;

/** Mapping java.util.Date Objects.
 */
public final class DateMap {
    
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
    
    private DateMap() {}
    
}
