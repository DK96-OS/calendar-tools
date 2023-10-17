package calendartools.data;

import java.util.Arrays;
import java.util.Calendar;

/** A Builder for WeeklyChecklist data.
 */
public final class WeeklyChecklistBuilder {

	/** Represents the Weekly Checklist with mutable array.
	 */
	final boolean[] array = new boolean[7];

	/** Flip the State of the given DAY_OF_WEEK.
	 * @param dayOfWeek The DAY_OF_WEEK.
	 * @throws IllegalArgumentException When the given DAY_OF_WEEK is invalid.
	 */
	public void toggle(
		int dayOfWeek
	) throws IllegalArgumentException {
		if (dayOfWeek < Calendar.SUNDAY ||
			dayOfWeek > Calendar.SATURDAY
		) throw new IllegalArgumentException();
		// Change to Array Index
		--dayOfWeek;
		array[dayOfWeek] = !array[dayOfWeek];
	}

	/** Obtain a WeeklyChecklist instance matching the builder's current state.
	 * @return A new WeeklyChecklist.
	 */
	public WeeklyChecklist get() {
		return new WeeklyChecklist(
			array[0],
			array[1],
			array[2],
			array[3],
			array[4],
			array[5],
			array[6]
		);
	}

	/** Clear the Builder's internal representation of the checklist.
	 * Sets all values to false.
	 */
	public void clear() {
		Arrays.fill(array, false);
	}

}