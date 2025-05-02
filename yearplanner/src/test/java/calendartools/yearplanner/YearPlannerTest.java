package calendartools.yearplanner;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertThrows;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

import java.util.Calendar;

import calendartools.data.TestDataProvider;
import calendartools.map.DateMap;

/** Testing the YearPlanner Class.
 */
public final class YearPlannerTest {
    
    /** A Reference to the Data Provider, for Convenience.
     *  - Reference resets before every test.
     */
    private TestDataProvider provider;
    
    /** A Default DateFormat YearPlanner with TestDataProvider.CurrentYear.
     */
    private YearPlanner mInstance;
    
    /** An Alternative DateFormat YearPlanner, also CurrentYear.
     */
    private YearPlanner mAlternative;
    
    /** Compare the top 3 Date Attributes between 2 Calendars.
     * @return True if the Year, Month, and Day of Month attributes match.
     */
    public static boolean matchingCalendarDates(
        final Calendar a,
        final Calendar b
    ) {
        assertNotNull(a);
        assertNotNull(b);
        return a.get(Calendar.DAY_OF_MONTH) == b.get(Calendar.DAY_OF_MONTH) &&
            a.get(Calendar.MONTH) == b.get(Calendar.MONTH) &&
            a.get(Calendar.YEAR) == b.get(Calendar.YEAR);
    }
    
    @Before
    public void testSetup() {
        provider = TestDataProvider.getCurrentYearProvider();
        mInstance = new YearPlanner(TestDataProvider.CurrentYear);
        mAlternative = new YearPlanner(TestDataProvider.CurrentYear, DateMap.getMonthDayMap());
    }
    
    @Test
    public void test_InitialCondition() {
        assertEquals(TestDataProvider.CurrentYear, mInstance.mYear);
        assertEquals(DateMap.getDefaultMap(), mInstance.mDateMap);
    }
    
    @Test
    public void test_Alternative_InitialCondition() {
        assertEquals(TestDataProvider.CurrentYear, mAlternative.mYear);
        assertEquals(DateMap.getMonthDayMap(), mAlternative.mDateMap);
    }
    
    @Test
    public void test_Constructor_InvalidArg_Year_GreaterThanShortMax_ThrowsIllegalArgumentException() {
        assertThrows(IllegalArgumentException.class,
            () -> new YearPlanner(Short.MAX_VALUE + 1)
        );
    }

    @Test
    public void test_Constructor_InvalidArg_Year_LessThanShortMin_ThrowsIllegalArgumentException() {
        assertThrows(IllegalArgumentException.class,
            () -> new YearPlanner(Short.MIN_VALUE - 1)
        );
    }

    @Test
    public void test_Constructor2_InvalidArg_Year_GreaterThanShortMax_ThrowsIllegalArgumentException() {
        assertThrows(IllegalArgumentException.class,
            () -> new YearPlanner(Short.MAX_VALUE + 1, DateMap.getDefaultMap())
        );
    }

    @Test
    public void test_Constructor2_InvalidArg_Year_LessThanShortMin_ThrowsIllegalArgumentException() {
        assertThrows(IllegalArgumentException.class,
            () -> new YearPlanner(Short.MIN_VALUE - 1, DateMap.getDefaultMap())
        );
    }

    @Test
    public void test_Constructor2_InvalidArg2_Null_ThrowsIllegalArgumentException() {
        assertThrows(IllegalArgumentException.class,
            () -> new YearPlanner(TestDataProvider.CurrentYear, (DateMap) null)
        );
    }
    
    @Test
    public void test_ParseMonthDayString_Null_ThrowsIllegalArgumentException() {
        assertThrows(IllegalArgumentException.class,
            () -> mInstance.parseMonthDayString(null));
    }
    
    @Test
    public void test_ParseMonthDayString_SimpleDateStrings_ReturnsNull() {
        for (var x: provider.getSimpleDateStrings()) {
            assertNull(mInstance.parseMonthDayString(x));
        }
    }
    
    @Test
    public void test_ParseMonthDayString_SimpleReversedDateStrings_ReturnsIncorrectCalendar() {
        var index = 0;
        var calendars = provider.getCalendars();
        // The Reversed Dates can cause problems for Month-Day Parser
        for (var x: provider.getReversedDateStrings()) {
            var expectedCalendar = calendars.get(index);
            var day = expectedCalendar.get(Calendar.DAY_OF_MONTH);
            var month = 1 + expectedCalendar.get(Calendar.MONTH);
            //
            var result = mInstance.parseMonthDayString(x);
            if (day == month) { // If Day matches the Month, the Calendar will Match the Expected
                assertNotNull(result);
                assertTrue(
                    matchingCalendarDates(result, expectedCalendar)
                );
            } else if (day > 12) { // The Parser fails because the month value is greater than 12
                assertNull(result);
            } else {
                assertNotNull(result);
                assertFalse(
                    matchingCalendarDates(result, expectedCalendar)
                );
            }
            index++;
        }
    }
    
    @Test
    public void test_ParseMonthDayString_MonthDayStrings_ReturnsNull() {
        var calendars = provider.getCalendars();
        var index = 0;
        for (var x: provider.getMonthDayStrings()) {
            var result = mInstance.parseMonthDayString(x);
            assertNotNull(result);
            assertTrue(
                matchingCalendarDates(result, calendars.get(index++))
            );
        }
    }
    
    @Test
    public void testGetCalendar_ZeroMonth_ThrowsIllegalArgumentException() {
        assertThrows(IllegalArgumentException.class,
            () -> mInstance.getCalendar(0, 1));
    }
    
    @Test
    public void testGetCalendar_ZeroDay_ThrowsIllegalArgumentException() {
        assertThrows(IllegalArgumentException.class,
            () -> mInstance.getCalendar(1, 0));
    }
    
    @Test
    public void testGetCalendar_Day1_ReturnsCalendar() {
        var result = mInstance.getCalendar(1, 1);
        assertNotNull(result);
        assertTrue(
            matchingCalendarDates(result, TestDataProvider.FirstDayOf2025))
        ;
    }
    
    @Test
    public void test_GetDayNumber_FromString_Null_ThrowsIllegalArgumentException() {
        assertThrows(IllegalArgumentException.class,
            () -> mInstance.getDayNumber((String) null));
    }
    
    @Test
    public void test_GetDayNumber_FromString_FirstDayOf2025_Returns1() {
        assertEquals(1, mInstance.getDayNumber(TestDataProvider.FirstDayOf2025Str));
    }

    @Test
    public void test_GetDayNumber_FromString_LastDayOf2025_Returns365() {
        assertEquals(365, mInstance.getDayNumber(TestDataProvider.LastDayOf2025Str));
    }

    @Test
    public void test_GetDayNumber_FromPair_FirstDayOf2025_Returns1() {
        assertEquals(1, mInstance.getDayNumber(1, 1));
    }

    @Test
    public void test_GetDayNumber_FromPair_LastDayOf2025_Returns365() {
        assertEquals(365, mInstance.getDayNumber(12, 31));
    }
    
    @Test
    public void test_GetWeekNumber_FromString_Null_ThrowsIllegalArgumentException() {
        assertThrows(IllegalArgumentException.class,
            () -> mInstance.getWeekNumber((String) null));
    }
    
    @Test
    public void test_GetWeekNumber_FromString_FirstDayOf2025_Returns1() {
        assertEquals(1, mInstance.getWeekNumber(TestDataProvider.FirstDayOf2025Str));
    }

    @Test
    public void test_GetWeekNumber_FromString_LastDayOf2025_Returns1() {
        assertEquals(1, mInstance.getWeekNumber(TestDataProvider.LastDayOf2025Str));
    }

    @Test
    public void test_GetWeekNumber_FromString_Week1_Returns1() {
        assertEquals(1, mInstance.getWeekNumber("2025-01-02"));
        assertEquals(1, mInstance.getWeekNumber("2025-01-03"));
        assertEquals(1, mInstance.getWeekNumber("2025-01-04"));
    }

    @Test
    public void test_GetWeekNumber_FromString_Week2_Returns2() {
        assertEquals(2, mInstance.getWeekNumber("2025-01-05"));
        assertEquals(2, mInstance.getWeekNumber("2025-01-06"));
        assertEquals(2, mInstance.getWeekNumber("2025-01-07"));
        assertEquals(2, mInstance.getWeekNumber("2025-01-08"));
        assertEquals(2, mInstance.getWeekNumber("2025-01-09"));
        assertEquals(2, mInstance.getWeekNumber("2025-01-10"));
        assertEquals(2, mInstance.getWeekNumber("2025-01-11"));
    }

    @Test
    public void test_GetWeekNumber_FromString_Week3_Returns3() {
        assertEquals(3, mInstance.getWeekNumber("2025-01-12"));
        assertEquals(3, mInstance.getWeekNumber("2025-01-13"));
        assertEquals(3, mInstance.getWeekNumber("2025-01-14"));
        assertEquals(3, mInstance.getWeekNumber("2025-01-15"));
        assertEquals(3, mInstance.getWeekNumber("2025-01-16"));
        assertEquals(3, mInstance.getWeekNumber("2025-01-17"));
        assertEquals(3, mInstance.getWeekNumber("2025-01-18"));
    }
    
    @Test
    public void test_GetDayArray_CurrentYear2025_Week1() {
        var result = mInstance.getDayArray((byte) 1);
        assertArrayEquals(
            new byte[]{29, 30, 31, 1, 2, 3, 4},
            result
        );
    }
    
    @Test
    public void test_GetDayArray_CurrentYear2025_Week18() {
        var result = mInstance.getDayArray((byte) 18);
        assertArrayEquals(
            new byte[]{27, 28, 29, 30, 1, 2, 3},
            result
        );
    }
    
    @Test
    public void test_GetDayArray_CurrentYear2025_Week52() {
        var result = mInstance.getDayArray((byte) 52);
        assertArrayEquals(
            new byte[]{21, 22, 23, 24, 25, 26, 27},
            result
        );
    }
    
    @Test
    public void test_GetDayArray_CurrentYear2025_Week53() {
        var result = mInstance.getDayArray((byte) 53);
        assertArrayEquals(
            new byte[]{28, 29, 30, 31, 1, 2, 3},
            result
        );
    }
    
}
