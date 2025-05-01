package calendartools.map;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThrows;
import static org.junit.Assert.assertTrue;
import static calendartools.map.DateMap.convert;

import org.junit.Test;

import java.time.Instant;
import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.stream.Collectors;

/** Testing the DateMap class.
 */
public class DateMapTest {
    
    /** Compare the top 3 Date Attributes between 2 Calendars.
     * @return True if the Year, Month, and Day of Month attributes match.
     */
    public static boolean matchingCalendarDates(
        final Calendar a,
        final Calendar b
    ) {
        assertNotNull(a); assertNotNull(b);
        return a.get(Calendar.DAY_OF_MONTH) == b.get(Calendar.DAY_OF_MONTH) &&
            a.get(Calendar.MONTH) == b.get(Calendar.MONTH) &&
            a.get(Calendar.YEAR) == b.get(Calendar.YEAR);
    }
    
    @Test
    public void test_ConvertDate_Null_ThrowsIllegalArgumentException() {
        assertThrows(IllegalArgumentException.class, () -> convert((Date) null));
    }
    
    @Test
    public void test_ConvertCalendar_Null_ThrowsIllegalArgumentException() {
        assertThrows(IllegalArgumentException.class, () -> convert((Calendar) null));
    }
    
    @Test
    public void test_ConvertDate_Today_ReturnsTodayCalendar() {
        assertTrue(
            matchingCalendarDates(
                Calendar.getInstance(),
                convert(
                    Date.from(Instant.now())
                )
            )
        );
    }
    
    @Test
    public void test_ConvertDate_TargetWeek1_ReturnsTargetWeek() {
        var inputWeekDates = Arrays.stream(TestDataProvider.getTargetWeek1())
            .mapToObj(Date::new)
            .collect(Collectors.toList());
        var expectedCalendarQueue = new ArrayDeque<>(
            TestDataProvider.getTargetWeek1_Calendars()
        );
        for (var d: inputWeekDates) {
            assertTrue(
                matchingCalendarDates(
                    expectedCalendarQueue.pop(),
                    convert(d)
                )
            );
        }
    }
    
}
