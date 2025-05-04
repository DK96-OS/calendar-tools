package calendartools.map;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertThrows;
import static org.junit.Assert.assertTrue;
import static calendartools.map.DateFormatMapTest.matchingCalendarDates;

import org.junit.Before;
import org.junit.Test;

import java.util.Calendar;

/** Testing the DateMap class.
 */
public class MonthDayDateFormatMapStaticTest {
    
    private calendartools.data.TestDataProvider provider;
    
    @Before
    public void testSetup() {
        provider = calendartools.data.TestDataProvider.getCurrentYearProvider();
    }
    
    @Test
    public void test_map_String_Null_ThrowsIllegalArgumentException() {
        assertThrows(IllegalArgumentException.class,
            () -> MonthDayDateFormatMap.map(TestDataProvider.CurrentYear, (String) null));
    }
    
    @Test
    public void test_ParseDateString_SimpleDateStrings_ReturnsCalendar() {
        for (var x: TestDataProvider.getCurrentYearProvider().getSimpleDateStrings()) {
            assertNull(MonthDayDateFormatMap.map(TestDataProvider.CurrentYear, x));
        }
    }
    
    @Test
    public void test_map_SimpleMonthDayStrings_ReturnsNull() {
        var calendars = provider.getCalendars();
        int index = 0;
        for (var x: provider.getMonthDayStrings()) {
            var result = MonthDayDateFormatMap.map(TestDataProvider.CurrentYear, x);
            assertNotNull(result);
            assertTrue(
                matchingCalendarDates(result, calendars.get(index++))
            );
        }
    }
    
    @Test
    public void test_map_full_simple_date_string_returns_null() {
        var result = MonthDayDateFormatMap.map(TestDataProvider.CurrentYear, "2044-10-5");
        assertNull(result);
    }
    
    @Test
    public void test_map_MonthDay_Returns() {
        var result = MonthDayDateFormatMap.map(TestDataProvider.CurrentYear, "10-5");
        assertNotNull(result);
        assertEquals(TestDataProvider.CurrentYear, result.get(Calendar.YEAR));
        assertEquals(Calendar.OCTOBER, result.get(Calendar.MONTH));
        assertEquals(5, result.get(Calendar.DAY_OF_MONTH));
    }
    
    @Test
    public void test_map_alt_year_MonthDay_Returns() {
        short altYear = 2044;
        var result = MonthDayDateFormatMap.map(altYear, "10-5");
        assertNotNull(result);
        assertEquals(altYear, result.get(Calendar.YEAR));
        assertEquals(Calendar.OCTOBER, result.get(Calendar.MONTH));
        assertEquals(5, result.get(Calendar.DAY_OF_MONTH));
    }
    
}
