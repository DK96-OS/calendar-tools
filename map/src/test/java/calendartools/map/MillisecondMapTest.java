package calendartools.map;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

import java.util.Calendar;
import java.util.List;

/** Testing Millisecond Map.
 */
public final class MillisecondMapTest {

	private MillisecondMap<Byte> mInstance;

	private List<Byte> expectedDaysOfWeek;

	@Before
	public void testSetup() {
		// Map the Timestamp to the Day of the Week.
		mInstance = new MillisecondMap<Byte>(
			(Calendar c) -> (byte) c.get(Calendar.DAY_OF_WEEK)
		);
		expectedDaysOfWeek = List.of(
			(byte) Calendar.MONDAY,
			(byte) Calendar.TUESDAY,
			(byte) Calendar.WEDNESDAY,
			(byte) Calendar.THURSDAY,
			(byte) Calendar.FRIDAY,
			(byte) Calendar.SATURDAY,
			(byte) Calendar.SUNDAY
		);
	}

	@Test
	public void testMapArray_TargetWeek1_ReturnsDaysOfWeek() {
		var targetWeek1 = TestDataProvider.getTargetWeek1();
		assertEquals(
			expectedDaysOfWeek,
			mInstance.map(targetWeek1)
		);
	}

	@Test
	public void testMapList_TargetWeek1_ReturnsDaysOfWeek() {
		var targetWeek1 = TestDataProvider.getTargetWeek1List();
		assertEquals(
			expectedDaysOfWeek,
			mInstance.map(targetWeek1)
		);
	}

	@Test
	public void testMapValue_TargetWeek1_ReturnsDaysOfWeek() {
		var targetWeek1 = TestDataProvider.getTargetWeek1();
		for (int index = 0; index < targetWeek1.length; ++index) {
			final Byte expectDay = expectedDaysOfWeek.get(index);
			assertEquals(
				expectDay,
				mInstance.map(targetWeek1[index])
			);
		}
	}

}