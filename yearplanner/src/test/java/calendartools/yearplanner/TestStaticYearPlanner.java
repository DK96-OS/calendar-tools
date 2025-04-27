package calendartools.yearplanner;

import static org.junit.Assert.assertEquals;
import static calendartools.yearplanner.YearPlanner.getDayNumber;
import static calendartools.yearplanner.YearPlanner.getWeekNumber;

import org.junit.Test;

/** Testing The Static Methods in the YearPlanner Class File.
 *  - Static Methods in YearPlanner accept the java.util.Calendar object as a parameter.
 *  - DateFormat Strings and Month-Day pairs are handled by YearPlanner Instance Methods.
 */
public final class TestStaticYearPlanner {

    @Test
    public void test_GetDayNumber_FirstDayOf2025_Returns1() {
        assertEquals(1, getDayNumber(TestDataProvider.FirstDayOf2025));
    }

    @Test
    public void test_GetDayNumber_LastDayOf2025_Returns1() {
        assertEquals(365, getDayNumber(TestDataProvider.LastDayOf2025));
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

}
