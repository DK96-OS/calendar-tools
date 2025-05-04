package calendartools.data;

import org.junit.Test;

import java.util.Calendar;

/** Testing Data Provider Methods.
 *  Builder Methods: Regular and Alternate
 *   - Regular uses the Object Clone method.
 *   - Alternate uses Calendar Builder.
 */
public class DataProviderTests {

    @Test
    public void testAlternateBuilderMethod() {
        final int year = 2025;
        Calendar.Builder calb = new Calendar.Builder()
            .setDate(year, Calendar.JANUARY, 1);
        //
        var cal = calb.build();
        int dayCount = cal.getActualMaximum(Calendar.DAY_OF_YEAR);
        var CalendarArray = new Calendar[dayCount];
        //
        int index = 0;
        while (cal.get(Calendar.YEAR) == year) {
            calb.setDate(
                year,
                cal.get(Calendar.MONTH),
                cal.get(Calendar.DAY_OF_MONTH)
            );
            CalendarArray[index] = calb.build();
            cal.add(Calendar.DATE, 1);
            index++;
        }
    }

    @Test
    public void testRegularBuilderMethod() {
        final int year = 2025;
        Calendar.Builder calb = new Calendar.Builder()
            .setDate(year, Calendar.JANUARY, 1);
        //
        var cal = calb.build();
        int dayCount = cal.getActualMaximum(Calendar.DAY_OF_YEAR);
        var CalendarArray = new Calendar[dayCount];
        //
        int index = 0;
        while (cal.get(Calendar.YEAR) == year) {
            CalendarArray[index] = (Calendar) cal.clone();
            cal.add(Calendar.DATE, 1);
            index++;
        }
    }
    
    @Test
    public void testAlt100Times() {
        for (int i = 0; i < 100; i++) {
            testAlternateBuilderMethod();
        }
    }
    
    @Test
    public void testRegular100Times() {
        for (int i = 0; i < 100; i++) {
            testRegularBuilderMethod();
        }
    }
    
    @Test
    public void testAlt2000Times() {
        for (int i = 0; i < 2000; i++) {
            testAlternateBuilderMethod();
        }
    }
    
    @Test
    public void testRegular2000Times() {
        for (int i = 0; i < 2000; i++) {
            testRegularBuilderMethod();
        }
    }
    
    @Test
    public void testAlt4000Times() {
        for (int i = 0; i < 4000; i++) {
            testAlternateBuilderMethod();
        }
    }
    
    @Test
    public void testRegular4000Times() {
        for (int i = 0; i < 4000; i++) {
            testRegularBuilderMethod();
        }
    }
    
}
