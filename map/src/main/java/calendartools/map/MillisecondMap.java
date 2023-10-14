package calendartools.map;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.function.Function;

/**
 */
public class MillisecondMap<T> {

	final Function<Calendar, T> mMap;

	/** Create a new Map.
	 * @param mapping The Function that maps a Calendar to another Type.
	 */
	public MillisecondMap(
		final Function<Calendar, T> mapping
	) {
		mMap = mapping;
	}

	/** Map an Array of Millisecond Time values.
	 * @param millisecondValues The Array of Time values in milliseconds.
	 * @return An ArrayList of Output Data.
	 */
	public ArrayList<T> map(
		final long[] millisecondValues
	) {
		var result = new ArrayList<T>();
		var calendar = Calendar.getInstance();
		//
		for (long l : millisecondValues) {
			calendar.setTimeInMillis(l);
			result.add(mMap.apply(calendar));
		}
		return result;
	}

	/** Map an Array of Millisecond Time values.
	 * @param millisecondValues The Array of Time values in milliseconds.
	 * @return An ArrayList of Output Data.
	 */
	public ArrayList<T> map(
		final List<Long> millisecondValues
	) {
		var result = new ArrayList<T>();
		var calendar = Calendar.getInstance();
		//
		for (long l : millisecondValues) {
			calendar.setTimeInMillis(l);
			result.add(mMap.apply(calendar));
		}
		return result;
	}

	/** Map a single Millisecond Time value.
	 * @param millisecondValue The Time in Milliseconds.
	 * @return The Map's Output Data.
	 */
	public T map(
		final long millisecondValue
	) {
		var cal = Calendar.getInstance();
		cal.setTimeInMillis(millisecondValue);
		return mMap.apply(cal);
	}

}