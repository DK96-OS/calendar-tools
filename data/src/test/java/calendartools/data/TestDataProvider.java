package calendartools.data;

import java.util.ArrayList;
import java.util.Calendar;

/** Provider for Testing data.
 */
public class TestDataProvider {

	/**
	 * @return An Array containing the Days of the Week.
	 */
	public static ArrayList<Integer> getDaysOfWeek() {
		var list =  new ArrayList<Integer>(7);
		for (int i = Calendar.SUNDAY; i <= Calendar.SATURDAY; ++i) {
			list.add(i);
		}
		return list;
	}

}