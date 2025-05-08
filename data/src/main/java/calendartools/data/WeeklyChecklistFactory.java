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
	
	/** Translate the days of the week by a given offset.
	 *  - Normalizes the Offset before applying any shift on the data (divides by 7).
	 *  - An Offset of 1 shifts Monday into first day in the checklist data.
	 *  - Negative 1 Offset shifts Saturday into the first day.
	 * @param offset The offset to be applied to the weekly checklist data.
	 */
	public void offset(int offset) {
		boolean reverse = offset < 0;
		if (reverse) {
			offset = (offset == Integer.MIN_VALUE) ? Integer.MAX_VALUE : -offset;
		}
		// Normalize the Offset
		if (offset >= 7) {offset %= 7;}
		// Zero Offset has no effect
		if (offset == 0) return;
		// A Reverse direction shift is the same as this
		if (reverse)
			offset = 7 - offset;
		// Copy Array segments before overwriting
		var seg0 = Arrays.copyOfRange(array, 0, offset);
		var seg6 = Arrays.copyOfRange(array, offset, 7);
		// Overwrite the array from the two segments
		for (byte i = 0; i < array.length; i++) {
			array[i] = (i < seg6.length) ? seg6[i]: seg0[i - seg6.length];
		}
	}
	
}