package calendartools.data;

/** A Weekly Checklist.
 * The checklist utilizes a compact representation for optimal memory efficiency.
 * The Constants used are related to DAY_OF_WEEK in Java Util Calendar.
 */
public final class WeeklyChecklist {

	/** The compact representation of the checklist.
	 */
	final byte mData;

	/** Initialize a Checklist with the given state for each day of the week.
	 * @param sunday The checked state for Sunday.
	 * @param monday The checked state for Monday.
	 * @param tuesday The checked state for Tuesday.
	 * @param wednesday The checked state for Wednesday.
	 * @param thursday The checked state for Thursday.
	 * @param friday The checked state for Friday.
	 * @param saturday The checked state for Saturday.
	 */
	public WeeklyChecklist(
		final boolean sunday,
		final boolean monday,
		final boolean tuesday,
		final boolean wednesday,
		final boolean thursday,
		final boolean friday,
		final boolean saturday
	) {
		mData = (byte)(
			(sunday ? 1 : 0) |
				(monday ? 2 : 0) |
				(tuesday ? 4 : 0) |
				(wednesday ? 8 : 0) |
				(thursday ? 16 : 0) |
				(friday ? 32 : 0) |
				(saturday ? 64 : 0)
		);
	}

	/** Initialize a Checklist with the same state for all days of the week.
	 * @param allDays The checked state for all days of the week.
	 */
	public WeeklyChecklist(
		final boolean allDays
	) {
		mData = (byte)(
			allDays ? 127 : 0
		);
	}

	/** Only used internally to avoid unnecessary checks.
	 * @param data The compact representation of the checklist.
	 */
	private WeeklyChecklist(
		final byte data
	) {
		mData = data;
	}

	/** Determine the status on the given day of the week.
	 * @param dayOfWeek The given Day Of Week, from Calendar.DAY_OF_WEEK.
	 * @return True if day of week is selected. Undefined, if input is invalid.
	 */
	public boolean getDayOfWeek(
		final int dayOfWeek
	) {
		return (
			mData & (1 << dayOfWeek - 1)
		) != 0;
	}

	/** Update the status on the given day of the week.
	 * Instantiates a new WeeklyChecklist when the status is changed.
	 * If the status is not changed, returns the same WeeklyChecklist.
	 * @param dayOfWeek The given Day Of Week, from Calendar.DAY_OF_WEEK.
	 * @return A WeeklyChecklist with the updated status.
	 */
	public WeeklyChecklist updateDayOfWeek(
		final int dayOfWeek
	) {
		// Check for invalid input.
		if (dayOfWeek < java.util.Calendar.SUNDAY ||
			dayOfWeek > java.util.Calendar.SATURDAY
		) return this;
		// Toggle the bit.
		final byte newData = (byte)(
			mData ^ (1 << dayOfWeek - 1)
		);
		// Use a private constructor to avoid unnecessary checks.
		return new WeeklyChecklist(newData);
	}

}