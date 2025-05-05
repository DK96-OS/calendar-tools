package calendartools.data;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertThrows;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

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
    
    @Before
    public void testSetup() {
        mInstance = new WeeklyChecklistFactory();
        allTrue = new WeeklyChecklist(true);
        allTrueInstance = new WeeklyChecklistFactory();
        allTrueInstance.fromChecklist(allTrue);
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
    public void testClear_AllTrue() {
        allTrueInstance.clear();
        for (byte i = 0; i < 7; i++) {
            assertFalse(allTrueInstance.array[i]);
        }
    }
    
}
