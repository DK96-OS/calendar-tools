package calendartools.map;

import java.util.Calendar;

/** Provider of Test Data.
 */
public class TestDataProvider {

	/** Target Week 1 is October 16 to 22, 2023.
	 * @return An Array of Time values.
	 */
	public static long[] getTargetWeek1() {
		// Setup the Calendar
		var cal = Calendar.getInstance();
		cal.set(2023, Calendar.OCTOBER, 16);
		cal.set(Calendar.HOUR_OF_DAY, 12);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.MILLISECOND, 0);
		// Setup the Array
		var result = new long[7];
		result[0] = cal.getTimeInMillis();
		for (int i = 1; i < 7; ++i) {
			cal.set(Calendar.DAY_OF_MONTH, 16 + i);
			result[i] = cal.getTimeInMillis();
		}
		return result;
	}

}