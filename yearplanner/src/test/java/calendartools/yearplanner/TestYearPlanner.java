package calendartools.yearplanner;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;

import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;

/** Testing the YearPlanner Class.
 */
public final class TestYearPlanner {

    private YearPlanner mInstance;

    @Before
    public void testSetup() {
        mInstance = new YearPlanner(TestDataProvider.CurrentYear);
    }

    @Test
    public void test_InitialCondition() {
        assertEquals(TestDataProvider.CurrentYear, mInstance.mYear);
        assertEquals(YearPlanner.SIMPLE_DATE_FORMAT, mInstance.mDateFormat);
    }

    @Test
    public void test_InvalidArgument1() {
        assertThrows(IllegalArgumentException.class,
            () -> new YearPlanner(Short.MAX_VALUE + 1)
        );
    }

    @Test
    public void test_InvalidArgument2() {
        assertThrows(IllegalArgumentException.class,
            () -> new YearPlanner(Short.MIN_VALUE - 1)
        );
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
    public void test_GetDayArray_PrintWeeks1To10() {
        System.out.println(Arrays.toString(mInstance.getDayArray((byte) 1)));
        System.out.println(Arrays.toString(mInstance.getDayArray((byte) 2)));
        System.out.println(Arrays.toString(mInstance.getDayArray((byte) 4)));
        System.out.println(Arrays.toString(mInstance.getDayArray((byte) 5)));
        System.out.println(Arrays.toString(mInstance.getDayArray((byte) 6)));
        System.out.println(Arrays.toString(mInstance.getDayArray((byte) 7)));
        System.out.println(Arrays.toString(mInstance.getDayArray((byte) 8)));
        System.out.println(Arrays.toString(mInstance.getDayArray((byte) 9)));
        System.out.println(Arrays.toString(mInstance.getDayArray((byte) 10)));
    }

    @Test
    public void test_GetDayArray_EndOfYear_Prints() {
        System.out.println(Arrays.toString(mInstance.getDayArray((byte) 51)));
        System.out.println(Arrays.toString(mInstance.getDayArray((byte) 52)));
        System.out.println(Arrays.toString(mInstance.getDayArray((byte) 53)));
    }

}
