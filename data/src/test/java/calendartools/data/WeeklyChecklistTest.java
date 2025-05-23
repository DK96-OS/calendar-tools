package calendartools.data;

import static junit.framework.TestCase.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Calendar;

/** Testing Weekly Checklist class.
 */
public final class WeeklyChecklistTest {

	WeeklyChecklist checklistAllTrue;
	WeeklyChecklist checklistAllFalse;

	ArrayList<Integer> daysOfWeek;

	@Before
	public void testSetup() {
		checklistAllTrue = new WeeklyChecklist(true);
		checklistAllFalse = new WeeklyChecklist(false);
		daysOfWeek = new ArrayList<>(7);
		for (int i = Calendar.SUNDAY; i <= Calendar.SATURDAY; ++i) {
			daysOfWeek.add(i);
		}
	}

	@Test
	public void testInitialCondition_AllTrue() {
		for (int dayOfWeek : daysOfWeek) {
			assertTrue(
				checklistAllTrue.getDayOfWeek(dayOfWeek)
			);
		}
	}

	@Test
	public void testInitialCondition_AllFalse() {
		for (int dayOfWeek : daysOfWeek) {
			assertFalse(
				checklistAllFalse.getDayOfWeek(dayOfWeek)
			);
		}
	}

	@Test
	public void testConstructor_EachDayChecked() {
		var builder = new WeeklyChecklistFactory();
		for (int day : daysOfWeek) {
			builder.toggle(day);
		}
		var instance = builder.get();
		assertEquals(
			127, instance.mData
		);
	}

	@Test
	public void testConstructor_NoDayChecked() {
		var builder = new WeeklyChecklistFactory();
		var instance = builder.get();
		assertEquals(
			0, instance.mData
		);
	}

	@Test
	public void testConstructor_Only3DaysChecked() {
		final int[] checkedDays = new int[]{
			Calendar.TUESDAY, Calendar.WEDNESDAY, Calendar.SATURDAY
		};
		var builder = new WeeklyChecklistFactory();
		for (int day : checkedDays) {
			builder.toggle(day);
		}
		var instance = builder.get();
		for (int day : checkedDays) {
			assertTrue(
				instance.getDayOfWeek(day)
			);
		}
	}

	@Test
	public void testGetDayOfWeek_InvalidDayOfWeek_ReturnsFalse() {
		assertFalse(
			checklistAllTrue.getDayOfWeek(8)
		);
		assertFalse(
			checklistAllFalse.getDayOfWeek(8)
		);
	}

	@Test
	public void testUpdateDayOfWeek_InvalidDayOfWeek_ReturnsThis() {
		assertEquals(
			checklistAllTrue,
			checklistAllTrue.updateDayOfWeek(0)
		);
		assertEquals(
			checklistAllTrue,
			checklistAllTrue.updateDayOfWeek(8)
		);
	}

	@Test
	public void testUpdateDayOfWeek_Monday() {
		var result = checklistAllTrue.updateDayOfWeek(
			Calendar.MONDAY
		);
		assertNotEquals(
			checklistAllTrue,
			result
		);
		assertFalse(
			result.getDayOfWeek(Calendar.MONDAY)
		);
		boolean removed = daysOfWeek.remove(
			Integer.valueOf(Calendar.MONDAY)
		);
		for (int i : daysOfWeek) {
			assertTrue(
				result.getDayOfWeek(i)
			);
		}
	}

	@Test
	public void testEquals_AllTrue_SameInstance_ReturnsTrue() {
		assertEquals(
			checklistAllTrue,
			checklistAllTrue
		);
	}

	@Test
	public void testEquals_AllTrue_NewInstance_ReturnsTrue() {
		assertEquals(
			checklistAllTrue,
			new WeeklyChecklist(true)
		);
	}

	@Test
	public void testEquals_AllFalse_SameInstance_ReturnsTrue() {
		assertEquals(
			checklistAllFalse,
			checklistAllFalse
		);
	}

	@Test
	public void testEquals_AllFalse_NewInstance_ReturnsTrue() {
		assertEquals(
			checklistAllFalse,
			new WeeklyChecklist(false)
		);
	}

	@Test
	public void testEquals_AllTrue_AllFalse_ReturnsFalse() {
		assertNotEquals(
			checklistAllTrue,
			checklistAllFalse
		);
	}

	@Test
	public void testEquals_AllTrue_Modified_ReturnsFalse() {
		// Modify one day at a time from the AllTrue checklist
		for (int day = Calendar.SUNDAY; day <= Calendar.SATURDAY; ++day) {
			assertNotEquals(
				checklistAllTrue,
				checklistAllTrue.updateDayOfWeek(day)
			);
		}
	}

	@Test
	public void testEquals_AllFalse_Modified_ReturnsFalse() {
		// Modify one day at a time from the AllTrue checklist
		for (int day = Calendar.SUNDAY; day <= Calendar.SATURDAY; ++day) {
			assertNotEquals(
				checklistAllFalse,
				checklistAllFalse.updateDayOfWeek(day)
			);
		}
	}

	@Test
	public void testEquals_Null_ReturnsFalse() {
		assertNotEquals(
			checklistAllTrue, null
		);
	}

}