# RYANAIR TEST

I started the test using Cucumber and POM. The idea was to create multiple page classes for each of the views from the test case (search, dates and seats). Also, I planned to make everything
completely reusable based on the gherkin/feature file content, meaning:

1. Any combination of from, to, dates, and passengers is possible
2. Any dates can be updated
3. Any seats can be selected
4. Any number of pax is possible with any given names defined

The more I started to interact with the page I quickly realized this is not a 3 hours test for me using this approach.

What was I able to achieve with the time given?
I started working on the test Saturday around 4pm COL time and ended late 11pm.

1. Any combination of from, to, dates, and passengers is possible based on the gherkin content
2. Any date can be updated. For this one, I added a logic that calculates the difference in days between the initial date and the new one, based on this result
the automation script can click an x amount of times to the right or left and select the new date.
3. Select value fare and add names. At this point, time was already running out so I only added the necessary logic to enter the given names from the test and not making the step reusable.
4. Seat view is displayed and validated at this point, an assertion is made and the test ends.

Known Issues
- POM was not implemented the way I wanted due to time constraints. Also due to the dynamic values of the page, most elements were not located using the FindBy annotation since this approach takes static strings only although there's plenty of other elements that could be located this way.
- There's plenty of code that can be separated into smaller more reusable functions. There are a few code duplicates that can easily be avoided.
- I don't like to use thread.sleep and much rather use explicit waits but due to the time limitation, I did it to code some steps faster.
- The steps to select steps and continue to payment were not implemented due to time limitations.

How to run
1. Run the TestRunner class
2. Current chromedriver executable works only for Mac. Please download the Windows executable and overwrite the existing one if needed.
