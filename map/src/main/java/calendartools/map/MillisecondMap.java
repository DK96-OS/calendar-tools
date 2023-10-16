package calendartools.map;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.function.Function;

/** A Calendar-assisted map that takes Milliseconds since epoch as input.
 */
public class MillisecondMap<T> {

	/** The Mapping Function.
	 */
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
	 * @param startIndex The first index in the array that will be mapped.
	 * @param count The number of elements from the array that will be mapped.
	 * @return An ArrayList of Output Data.
	 */
	public ArrayList<T> map(
		final long[] millisecondValues,
		int startIndex,
		final int count
	) {
		final int lastIndex;
		// Flip If Start Index is negative.
		if (startIndex < 0) {
			startIndex = millisecondValues.length + startIndex;
			lastIndex = Integer.min(
				startIndex + count - 1,
				millisecondValues.length - 1
			);
		} else {
			// Determine the last index
		    lastIndex = startIndex + count - 1;
		}
		var result = new ArrayList<T>();
		var calendar = Calendar.getInstance();
		//
		for (int i = startIndex; i <= lastIndex; ++i) {
			calendar.setTimeInMillis(millisecondValues[i]);
			result.add(mMap.apply(calendar));
		}
		return result;
	}

	/** Map an Array of Millisecond Time values.
	 * @param millisecondValues The Array of Time values in milliseconds.
	 * @return An ArrayList of Output Data.
	 */
	public ArrayList<T> map(
		final Collection<Long> millisecondValues
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