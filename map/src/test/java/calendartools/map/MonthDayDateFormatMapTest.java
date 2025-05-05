package calendartools.map;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertThrows;
import static org.junit.Assert.assertTrue;
import static calendartools.map.DateFormatMapTest.matchingCalendarDates;

import org.junit.Before;
import org.junit.Test;

import java.util.Calendar;
import java.util.Collections;
import java.util.List;

/** Testing the DateMap class.
 */
public class MonthDayDateFormatMapTest {
    
    private calendartools.data.TestDataProvider provider;
    
    private MonthDayDateFormatMap mInstance;
    
    @Before
    public void testSetup() {
        provider = calendartools.data.TestDataProvider.getCurrentYearProvider();
        mInstance = new MonthDayDateFormatMap(TestDataProvider.CurrentYear);
    }
    
    @Test
    public void testConstructor_InvalidYear_TooLow_ThrowsIllegalArgumentException() {
        var invalid_year = Short.MIN_VALUE - 1;
        assertThrows(IllegalArgumentException.class,
            () -> new MonthDayDateFormatMap(invalid_year));
    }
    
    @Test
    public void testConstructor_InvalidYear_TooHigh_ThrowsIllegalArgumentException() {
        var invalid_year = Short.MAX_VALUE + 1;
        assertThrows(IllegalArgumentException.class,
            () -> new MonthDayDateFormatMap(invalid_year));
    }
    
    @Test
    public void test_map_String_Null_ThrowsIllegalArgumentException() {
        assertThrows(IllegalArgumentException.class,
            () -> mInstance.map((String) null));
    }
    
    @Test
    public void test_map_List_Null_ReturnsEmptyList() {
        assertEquals(
            Collections.emptyList(),
            mInstance.map((List<String>) null)
        );
    }
    
    @Test
    public void test_map_Array_Null_ReturnsEmptyList() {
        assertEquals(
            Collections.emptyList(),
            mInstance.map((String[]) null)
        );
    }
    
    @Test
    public void test_map_SimpleMonthDayStrings_ReturnsNull() {
        var calendars = provider.getCalendars();
        var index = 0;
        for (var x: provider.getMonthDayStrings()) {
            var result = mInstance.map(x);
            assertNotNull(result);
            assertTrue(
                matchingCalendarDates(result, calendars.get(index++))
            );
        }
    }
    
    @Test
    public void test_map_full_simple_date_string_returns_null() {
        var result = mInstance.map("2044-10-5");
        assertNull(result);
    }
    
    @Test
    public void test_map_MonthDay_Returns() {
        var result = mInstance.map("10-5");
        assertNotNull(result);
        assertEquals(TestDataProvider.CurrentYear, result.get(Calendar.YEAR));
        assertEquals(Calendar.OCTOBER, result.get(Calendar.MONTH));
        assertEquals(5, result.get(Calendar.DAY_OF_MONTH));
    }
    
    @Test
    public void test_map_alt_year_MonthDay_Returns() {
        short altYear = 2044;
        mInstance = new MonthDayDateFormatMap(altYear);
        var result = mInstance.map("10-5");
        assertNotNull(result);
        assertEquals(altYear, result.get(Calendar.YEAR));
        assertEquals(Calendar.OCTOBER, result.get(Calendar.MONTH));
        assertEquals(5, result.get(Calendar.DAY_OF_MONTH));
    }
    
    @Test
    public void testEquals_Instance_Self_ReturnsTrue() {
        assertTrue(mInstance.equals(mInstance));
    }
    
    @Test
    public void testEquals_New_SameYear_ReturnsTrue() {
        assertTrue(mInstance.equals(new MonthDayDateFormatMap(mInstance.year)));
    }
    
    @Test
    public void testEquals_StringType_ReturnsFalse() {
        assertFalse(mInstance.equals("IsEqual?"));
    }
    
    @Test
    public void testEquals_DifferentYear_ReturnsFalse() {
        assertFalse(mInstance.equals(new MonthDayDateFormatMap(mInstance.year + 1)));
    }
    
    @Test
    public void testHashCode_ReturnsYear() {
        assertEquals(TestDataProvider.CurrentYear, mInstance.hashCode());
    }
    
}
