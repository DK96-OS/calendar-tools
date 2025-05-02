package calendartools.data;

import java.util.Arrays;
import java.util.Calendar;
import java.util.List;
import java.util.stream.Collectors;

/** Provider of Test Data.
 */
public class TestDataProvider {
    
    /** This year value is used repeatedly throughout tests.
     *  - If changed, expect that some tests may fail.
     */
    public static final short CurrentYear = 2025;
    
    public static final String FirstDayOf2025Str = "2025-01-01";
    
    public static final String LastDayOf2025Str = "2025-12-31";
    
    /** Another year value: used to test the leap year case.
     */
    public static final short LeapYear = 2024;
    
    public static final String LeapYearStr = "2024-02-29";
    
    public static final String LeapYearReverseStr = "29-02-2024";
    
    public static final Calendar FirstDayOf2025 = new Calendar.Builder()
        .setDate(2025, Calendar.JANUARY, 1)
        .build();
    
    public static final Calendar LastDayOf2025 = new Calendar.Builder()
        .setDate(2025, Calendar.DECEMBER, 31)
        .build();
    
    public static final Calendar LeapYearDay = new Calendar.Builder()
        .setDate(2024, Calendar.FEBRUARY, 29)
        .build();
    
    private static TestDataProvider mCurrentYearCachedProvider = null;
    
    public static TestDataProvider getCurrentYearProvider() {
        if (mCurrentYearCachedProvider == null)
            mCurrentYearCachedProvider = new TestDataProvider(CurrentYear);
        return mCurrentYearCachedProvider;
    }
    
    /** Create a new TestDataProvider for the LeapYear 2024.
     * @return New TestDataProvider Instance, containing multiple String Arrays, and a Calendar Array.
     */
    public static TestDataProvider getLeapYearProvider() {
        return new TestDataProvider(LeapYear);
    }
    
    /** An Array of Calendar objects, one for each Day of the Year.
     */
    protected final Calendar[] CalendarArray;
    
    /** A good subset of the Valid Possibilities for YYYY-MM-DD Format.
     *  - Array of Length 366, with Strings up to 10 char wide.
     */
    private final String[] SimpleDateStringArray;
    
    /** A good subset of the Valid Possibilities for MM-DD Format.
     *  - Array of Length 366, with Strings up to 5 char wide.
     */
    private final String[] MonthDayStringArray;
    
    /** The Year that the TestDataProvider was created for.
     */
    public final int mYear;
    
    /** Constructor.
     * @param year The Year that Calendars and DateStrings will be created for.
     */
    public TestDataProvider(
        final int year
    ) {
        mYear = year;
        Calendar cal = new Calendar.Builder()
            .setDate(mYear, Calendar.JANUARY, 1)
            .build();
        int dayCount = cal.getActualMaximum(Calendar.DAY_OF_YEAR);
        CalendarArray = new Calendar[dayCount];
        SimpleDateStringArray = new String[dayCount];
        MonthDayStringArray = new String[dayCount];
        //
        int index = 0;
        while (cal.get(Calendar.YEAR) == year) {
            CalendarArray[index] = (Calendar) cal.clone();
            SimpleDateStringArray[index] = String.format(
                "%d-%d-%d",
                year,
                cal.get(Calendar.MONTH) + 1,
                cal.get(Calendar.DAY_OF_MONTH)
            );
            MonthDayStringArray[index] = String.format(
                "%d-%d",
                cal.get(Calendar.MONTH) + 1,
                cal.get(Calendar.DAY_OF_MONTH)
            );
            cal.add(Calendar.DATE, 1);
            index++;
        }
        assert CalendarArray[dayCount - 1].get(Calendar.DAY_OF_YEAR) == dayCount;
        assert !SimpleDateStringArray[dayCount - 1].isEmpty();
        assert !MonthDayStringArray[dayCount - 1].isEmpty();
    }
    
    /** One Calendar for each Day of the Year.
     * @return A List of Calendar objects.
     */
    public List<Calendar> getCalendars() {
        return Arrays.asList(CalendarArray);
    }
    
    /** A String for each Day of the Year, in DateFormat: YYYY-MM-DD
     * @return A List of Strings.
     */
    public final List<String> getSimpleDateStrings() {
        return Arrays.asList(SimpleDateStringArray);
    }
    
    /** A String for each Day of the Year, in DateFormat: MM-DD
     * @return A List of Strings.
     */
    public final List<String> getMonthDayStrings() {
        return Arrays.asList(MonthDayStringArray);
    }
    
    /** A String for each Day of the Year, in DateFormat: DD-MM-YYYY
     *  - Note: This List is generated on every call.
     *  - Strings are derived from the private CalendarArray member.
     * @return A List of Strings.
     */
    public List<String> getReversedDateStrings() {
        final String str = '-' + Integer.toString(mYear);
        return Arrays.stream(CalendarArray).map(
            (c) -> String.format("%d-%d%s", c.get(Calendar.DAY_OF_MONTH), c.get(Calendar.MONTH) + 1, str)
        ).collect(Collectors.toList());
    }
    
}
