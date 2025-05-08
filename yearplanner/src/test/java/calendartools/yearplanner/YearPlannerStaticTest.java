package calendartools.yearplanner;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;
import static calendartools.yearplanner.YearPlanner.getDayNumber;
import static calendartools.yearplanner.YearPlanner.getWeekNumber;
import static calendartools.yearplanner.YearPlanner.getWeekOffset;

import org.junit.Test;

import java.util.Calendar;

import calendartools.data.TestDataProvider;

/** Testing The Static Methods in the YearPlanner Class File.
 *  - Static Methods in YearPlanner accept the java.util.Calendar object as a parameter.
 *  - DateFormat Strings and Month-Day pairs are handled by YearPlanner Instance Methods.
 */
public final class YearPlannerStaticTest {
    
    @Test
    public void test_GetDayNumber_Null_ThrowsIllegalArgumentException() {
        assertThrows(IllegalArgumentException.class,
            () -> getDayNumber(null)
        );
    }

    @Test
    public void test_GetDayNumber_FirstDayOf2025_Returns1() {
        assertEquals(1, getDayNumber(TestDataProvider.FirstDayOf2025));
    }

    @Test
    public void test_GetDayNumber_LastDayOf2025_Returns1() {
        assertEquals(365, getDayNumber(TestDataProvider.LastDayOf2025));
    }
    
    @Test
    public void test_GetWeekNumber_Null_ThrowsIllegalArgumentException() {
        assertThrows(IllegalArgumentException.class,
            () -> getWeekNumber(null)
        );
    }
    
    @Test
    public void test_GetWeekNumber_FirstDayOf2025_Returns1() {
        assertEquals(1, getWeekNumber(TestDataProvider.FirstDayOf2025));
    }

    @Test
    public void test_GetWeekNumber_LastDayOf2025_Returns1() {
        // Would be 53, but it wraps to 1.
        assertEquals(1, getWeekNumber(TestDataProvider.LastDayOf2025));
    }
    
    @Test
    public void test_GetWeekOffset_2034_ReturnsSunday() {
        assertEquals(Calendar.SUNDAY - 1, getWeekOffset(2034));
    }
    
    @Test
    public void test_GetWeekOffset_2024_ReturnsMonday() {
        assertEquals(Calendar.MONDAY - 1, getWeekOffset(2024));
    }
    
    @Test
    public void test_GetWeekOffset_2024_ReturnsTuesday() {
        assertEquals(Calendar.TUESDAY - 1, getWeekOffset(2030));
    }
    
    @Test
    public void test_GetWeekOffset_2025_ReturnsWednesday() {
        assertEquals(Calendar.WEDNESDAY - 1, getWeekOffset(2025));
    }
    
    @Test
    public void test_GetWeekOffset_2026_ReturnsThursday() {
        assertEquals(Calendar.THURSDAY - 1, getWeekOffset(2026));
    }
    
    @Test
    public void test_GetWeekOffset_2026_ReturnsFriday() {
        assertEquals(Calendar.FRIDAY - 1, getWeekOffset(2027));
    }
    
    @Test
    public void test_GetWeekOffset_2028_ReturnsSaturday() {
        assertEquals(Calendar.SATURDAY - 1, getWeekOffset(2028));
    }
    
}
