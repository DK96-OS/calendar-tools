package calendartools.data;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertThrows;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

import java.util.Calendar;

public class WeeklyChecklistFactoryTest {
    
    /** A WeeklyChecklist Factory with all values set to False.
     */
    WeeklyChecklistFactory mInstance;
    
    /** A WeeklyChecklist Factory with all values set to True.
     */
    WeeklyChecklistFactory allTrueInstance;
    
    /** A WeeklyChecklist with all values set to True.
     */
    WeeklyChecklist allTrue;
    
    /** A WeeklyChecklist Factory with only Wednesday set to True.
     */
    WeeklyChecklistFactory mWednesday;
    
    @Before
    public void testSetup() {
        mInstance = new WeeklyChecklistFactory();
        allTrue = new WeeklyChecklist(true);
        allTrueInstance = new WeeklyChecklistFactory();
        allTrueInstance.fromChecklist(allTrue);
        mWednesday = new WeeklyChecklistFactory();
        mWednesday.toggle(Calendar.WEDNESDAY);
    }
    
    @Test
    public void testToggle_7Days_ReturnsTrueAll7() {
        for (byte i = 1; i < 8; i++) {
            assertTrue(mInstance.toggle(i));
        }
    }
    
    @Test
    public void testToggle_AllTrue_7Days_ReturnsFalseAll7() {
        for (byte i = 1; i < 8; i++) {
            assertFalse(allTrueInstance.toggle(i));
        }
    }
    
    @Test
    public void testToggle_Wednesday_7Days_ReturnsFalseOnWednesday() {
        for (byte i = 1; i < 8; i++) {
            if (i == Calendar.WEDNESDAY) {
                assertFalse(mWednesday.toggle(i));
            } else {
                assertTrue(mWednesday.toggle(i));
            }
        }
    }
    
    @Test
    public void testToggle_Zero_ThrowsIllegalArgumentException() {
        assertThrows(IllegalArgumentException.class,
            () -> mInstance.toggle(0));
    }
    
    @Test
    public void testToggle_8_ThrowsIllegalArgumentException() {
        assertThrows(IllegalArgumentException.class,
            () -> mInstance.toggle(8));
    }
    
    @Test
    public void testClear() {
        mInstance.clear();
        for (byte i = 0; i < 7; i++) {
            assertFalse(mInstance.array[i]);
        }
    }
    
    @Test
    public void testClear_Wednesday() {
        mWednesday.clear();
        for (byte i = 0; i < 7; i++) {
            assertFalse(mInstance.array[i]);
        }
    }
    
    @Test
    public void testClear_AllTrue() {
        allTrueInstance.clear();
        for (byte i = 0; i < 7; i++) {
            assertFalse(allTrueInstance.array[i]);
        }
    }
    
    @Test
    public void testOffset_Wednesday_1_ShiftsToTuesday() {
        mWednesday.offset(1);
        assertFalse(mWednesday.toggle(Calendar.TUESDAY));
        assertEquals((byte) 0, mWednesday.get().mData);
    }
    
    @Test
    public void testOffset_Wednesday_2_ShiftsToMonday() {
        mWednesday.offset(2);
        assertFalse(mWednesday.toggle(Calendar.MONDAY));
        assertEquals((byte) 0, mWednesday.get().mData);
    }
    
    @Test
    public void testOffset_Wednesday_10_ShiftsToSunday() {
        mWednesday.offset(10);
        assertFalse(mWednesday.toggle(Calendar.SUNDAY));
        assertEquals((byte) 0, mWednesday.get().mData);
    }
    
    @Test
    public void testOffset_Wednesday_Negative1_ShiftsToThursday() {
        mWednesday.offset(-1);
        assertFalse(mWednesday.toggle(Calendar.THURSDAY));
        assertEquals((byte) 0, mWednesday.get().mData);
    }
    
    @Test
    public void testOffset_Wednesday_Negative2_ShiftsToFriday() {
        mWednesday.offset(-2);
        assertFalse(mWednesday.toggle(Calendar.FRIDAY));
        assertEquals((byte) 0, mWednesday.get().mData);
    }
    
    @Test
    public void testOffset_Wednesday_6_ShiftsToThursday() {
        mWednesday.offset(6);
        assertFalse(mWednesday.toggle(Calendar.THURSDAY));
        assertEquals((byte) 0, mWednesday.get().mData);
    }
    
    @Test
    public void testOffset_Wednesday_7_DoesNothing() {
        mWednesday.offset(7);
        assertFalse(mWednesday.toggle(Calendar.WEDNESDAY));
        assertEquals((byte) 0, mWednesday.get().mData);
    }
    
    @Test
    public void testOffset_Wednesday_0_DoesNothing() {
        mWednesday.offset(0);
        assertFalse(mWednesday.toggle(Calendar.WEDNESDAY));
        assertEquals((byte) 0, mWednesday.get().mData);
    }
    
    @Test
    public void testOffset_Wednesday_MinValue_ShiftsToMonday() {
        mWednesday.offset(Integer.MIN_VALUE);
        assertFalse(mWednesday.toggle(Calendar.MONDAY));
        assertEquals((byte) 0, mWednesday.get().mData);
    }
    
    @Test
    public void testOffset_Wednesday_MaxValue_ShiftsToTuesday() {
        mWednesday.offset(Integer.MAX_VALUE);
        assertFalse(mWednesday.toggle(Calendar.TUESDAY));
        assertEquals((byte) 0, mWednesday.get().mData);
    }
    
}
