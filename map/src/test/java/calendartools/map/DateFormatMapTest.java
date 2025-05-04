package calendartools.map;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertThrows;
import static org.junit.Assert.assertTrue;
import static calendartools.map.DateFormatMap.convert;

import org.junit.Before;
import org.junit.Test;

import java.time.Instant;
import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/** Testing the DateMap class.
 */
public class DateFormatMapTest {
    
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
    
    /** Compare the Time value for each Date.
     * @return True if the Time values match.
     */
    public static boolean matchingDates(
        final Date a,
        final Date b
    ) {
        assertNotNull(a);
        assertNotNull(b);
        return a.getTime() == b.getTime();
    }
    
    private calendartools.data.TestDataProvider provider;
    
    private DateFormatMap mInstance;
    
    @Before
    public void testSetup() {
        provider = calendartools.data.TestDataProvider.getCurrentYearProvider();
        mInstance = DateFormatMap.getDefaultMap();
    }
    
    @Test
    public void test_ConvertDate_Null_ThrowsIllegalArgumentException() {
        assertThrows(IllegalArgumentException.class,
            () -> convert((Date) null));
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
    
    @Test
    public void test_ConvertCalendar_Null_ThrowsIllegalArgumentException() {
        assertThrows(IllegalArgumentException.class, () -> convert((Calendar) null));
    }
    
    @Test
    public void test_ConvertCalendar_CurrentTimeInstant_ReturnsDate() {
        var currentTime = Instant.now();
        var testCalendarInput = new Calendar.Builder()
            .setInstant(currentTime.toEpochMilli())
            .build();
        assertTrue(
            matchingDates(
                convert(testCalendarInput),
                Date.from(currentTime)
            )
        );
    }
    
    @Test
    public void test_ConvertCalendar_TargetWeek1_ReturnsDates() {
        var index = 0;
        for (var testCalendarInput: TestDataProvider.getTargetWeek1_Calendars()) {
            matchingDates(
                convert(testCalendarInput),
                new Date(TestDataProvider.getTargetWeek1()[index++])
            );
        }
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
    public void test_ParseDateString_SimpleDateStrings_ReturnsCalendar() {
        for (var x: TestDataProvider.getCurrentYearProvider().getSimpleDateStrings()) {
            assertNotNull(mInstance.map(x));
        }
    }
    
    @Test
    public void test_ParseDateString_ReversedDateStrings_ReturnsCalendars() {
        var calendars = provider.getCalendars();
        var index = 0;
        for (var x: provider.getReversedDateStrings()) {
            var result = mInstance.map(x);
            assertNotNull(result);
            assertTrue(
                matchingCalendarDates(result, calendars.get(index++))
            );
        }
    }
    
    @Test
    public void test_map_SimpleMonthDayStrings_ReturnsNull() {
        for (var x: provider.getMonthDayStrings()) {
            assertNull(mInstance.map(x));
        }
    }
    
    @Test
    public void test_map() {
        var result = mInstance.map("2044-10-5");
        assertEquals(2044, result.get(Calendar.YEAR));
        assertEquals(Calendar.OCTOBER, result.get(Calendar.MONTH));
        assertEquals(5, result.get(Calendar.DAY_OF_MONTH));
    }
    
    @Test
    public void test_map_MonthDay_ReturnsNull() {
        assertNull(mInstance.map("10-5"));
    }
    
    @Test
    public void test_TrySimpleDateFormats_Parse_SimpleDateStrings_ReturnsCorrectCalendarDate() {
        for (var x: provider.getSimpleDateStrings()) {
            var result = DateFormatMap.tryParseSimpleDateFormats(x);
            assertNotNull(result);
        }
    }
    
    @Test
    public void test_TrySimpleDateFormats_Parse_SimpleMonthDayStrings_ReturnsNull() {
        for (var x : provider.getMonthDayStrings()) {
            assertNull(DateFormatMap.tryParseSimpleDateFormats(x));
        }
    }
    
    @Test
    public void test_TryParseSimpleDateFormats_MonthDay_ReturnsNull() {
        assertNull(DateFormatMap.tryParseSimpleDateFormats("1-4"));
    }
    
    @Test
    public void test_TryParseSimpleDateFormats_ReversedDateStrings_ReturnsCalendars() {
        var expect = provider.getCalendars();
        int index = 0;
        for (var x: provider.getReversedDateStrings()) {
            assertTrue(matchingCalendarDates(
                expect.get(index++),
                convert(DateFormatMap.tryParseSimpleDateFormats(x))
            ));
        }
    }
    
}
