## Calendar Tools
Solves a selection of Java Calendar problems.

### Map
The Map module contains only the `Millisecond Map` class.
This class is designed for processing `Long` Millisecond time values using the Java Calendar.

It requires a function to be passed in the constructor, that controls how Calendar data is extracted from the input.
The function returns a Generic type, which can be anything but should not reference the Calendar instance passed into the function.

Internally, when processing a collection of Millisecond values, the Calendar instance is reused for all Function calls. Concurrent mapping not included.
If processing a single Millisecond value at a time, a new Calendar instance is created.

### Data
The Data module contains only the `Weekly Checklist` class.
This class is designed for compact representation of 7 boolean values, one for each day of the week.

The days are accessed and updated using the Java Calendar constants, Calendar.SUNDAY (1) to Calendar.SATURDAY (7).
Instances of `Weekly Checklist` are immutable. The update method therefore returns a new instance.
Note: When the update argument is invalid (outside of range 1 -> 7), the method returns the same instance.

### Year Planner
This is the newest Java Gradle Module to be added to the project, providing the `YearPlanner` Class.

**Design Highlights:**
- Static Methods provide quick Calendar Calculations, no Constructor needed!
- Customizable String DateFormat Parsing with Default: YYYY-MM-DD

**Key Quantities:**
- Weeks of the Year assigns a numerical value to every 7 day group in the year.
    - There are 52 weeks in a year.
    - The Calendar API will wrap around, back to week 1 instead of 53.
- Days of the Year ... a value to each consecutive day in the year.
    - There are 365 days in a year.
    - Leap years contain 366 days; an extra day in February (29).
    - The most recent leap year was 2024, the next is 2028.

These quantities may be useful in planning, and by creating this easy-to-use interface, we may readily explore them.

**Compatibility and Conversions:**
 - java.util.Calendar objects are fully supported.
    - The methods that process them are static, which means you don't need to use YearPlanner.
 - Date Strings are read by customizable DateFormat object within YearPlanner.
    - Default Parsers for DateFormats: YYYY-MM-DD, MM-DD
    - Provide an optional DateFormat object to the YearPlanner secondary Constructor overload.
 - Month-Day Integer Pairs can be provided as inputs
    - Construct any YearPlanner instance. It will provide the year.

**Additional Functionality:**
- Creates a Byte Array of length 7, containing the DayOfMonth values for a given Week of the Year.
    - Week 1 probably contains dates from the previous year.
    - Week 53 definitely contains dates for the next year.
    - The Parameter is not limited, it can go further in either direction, as far as Calendar API allows.