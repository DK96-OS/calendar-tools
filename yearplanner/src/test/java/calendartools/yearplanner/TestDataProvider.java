package calendartools.yearplanner;

import java.util.Calendar;

/** Provider of Test Data.
 */
public class TestDataProvider {

    public static final short CurrentYear = 2025;

    public static final String FirstDayOf2025Str = "2025-01-01";

    public static final String LastDayOf2025Str = "2025-12-31";

    public static final Calendar FirstDayOf2025 = new Calendar.Builder()
            .setDate(2025, Calendar.JANUARY, 1)
            .build();

    public static final Calendar LastDayOf2025 = new Calendar.Builder()
            .setDate(2025, Calendar.DECEMBER, 31)
            .build();

}
