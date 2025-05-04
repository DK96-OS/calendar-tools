package calendartools.data;

import java.util.Arrays;
import java.util.Calendar;

/** A Builder for WeeklyChecklist data.
 */
public class WeeklyChecklistFactory {

	/** Represents the Weekly Checklist with mutable array.
	 */
	public final boolean[] array = new boolean[7];

	/** Flip the State of the given DAY_OF_WEEK.
	 * @param dayOfWeek The DAY_OF_WEEK.
	 * @return The new value of the toggled DAY_OF_WEEK.
	 * @throws IllegalArgumentException When the given DAY_OF_WEEK is invalid.
	 */
	public final boolean toggle(
		final int dayOfWeek
	) throws IllegalArgumentException {
		if (dayOfWeek < Calendar.SUNDAY || dayOfWeek > Calendar.SATURDAY)
			throw new IllegalArgumentException("Invalid Day of Week: " + dayOfWeek);
		final byte index = (byte) (dayOfWeek - 1); // Convert Argument to Array Index
		final boolean updatedValue = !array[index];
		array[index] = updatedValue;
		return updatedValue;
	}

	/** Obtain a WeeklyChecklist instance matching the builder's current state.
	 * @return A new WeeklyChecklist.
	 */
	public final WeeklyChecklist get() {
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
	public final void clear() {
		Arrays.fill(array, false);
	}
	
	/** Copy the Values from a WeeklyChecklist
	 * @param checklist The
	 */
	public final void fromChecklist(
		final WeeklyChecklist checklist
	) {
		for (byte i = 0; i < 7;) {
			array[i++] = checklist.getDayOfWeek(i);
		}
	}
	
}