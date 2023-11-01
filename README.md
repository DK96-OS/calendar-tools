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
