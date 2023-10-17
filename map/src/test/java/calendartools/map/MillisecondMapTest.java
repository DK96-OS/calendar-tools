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
	public void testMapArray_TargetWeek1_Start0Count0_ReturnsEmpty() {
		var targetWeek1 = TestDataProvider.getTargetWeek1();
		var result = mInstance.map(targetWeek1, 0, 0);
		assertEquals(
			0, result.size()
		);
	}

	@Test
	public void testMapArray_TargetWeek1_Start0Count1_ReturnsMonday() {
		var targetWeek1 = TestDataProvider.getTargetWeek1();
		var result = mInstance.map(targetWeek1, 0, 1);
		assertEquals(
			1, result.size()
		);
		assertEquals(
			(byte) Calendar.MONDAY,
			result.get(0).byteValue()
		);
	}

	@Test
	public void testMapArray_TargetWeek1_Start1Count1_ReturnsTuesday() {
		var targetWeek1 = TestDataProvider.getTargetWeek1();
		var result = mInstance.map(targetWeek1, 1, 1);
		assertEquals(
			1, result.size()
		);
		assertEquals(
			(byte) Calendar.TUESDAY,
			result.get(0).byteValue()
		);
	}

	@Test
	public void testMapArray_TargetWeek1_StartNegative1Count1_ReturnsSunday() {
		var targetWeek1 = TestDataProvider.getTargetWeek1();
		var result = mInstance.map(targetWeek1, -1, 1);
		assertEquals(
			1, result.size()
		);
		assertEquals(
			(byte) Calendar.SUNDAY,
			result.get(0).byteValue()
		);
	}

	@Test
	public void testMapArray_TargetWeek1_StartNegative2Count2_ReturnsWeekend() {
		var targetWeek1 = TestDataProvider.getTargetWeek1();
		var result = mInstance.map(targetWeek1, -2, 2);
		assertEquals(
			2, result.size()
		);
		assertEquals(
			(byte) Calendar.SATURDAY,
			result.get(0).byteValue()
		);
		assertEquals(
			(byte) Calendar.SUNDAY,
			result.get(1).byteValue()
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