package AppDataStructures;

import java.util.Calendar;
import java.util.Locale;
import java.util.TimeZone;


// Superclass for the other "session" sub-classes
// Done, but i still need to test each function
public class Session
{
    public Calendar startTime; // Var representing the start time of a session
    Calendar endTime; // Var representing the end time of a session
    public Boolean isFinished; // Boolean representing whether the session is finished or still ongoing
    String extraComments; // String var which will hold extra comments that the user said about the session
    public SessionType sessionType; // Enum variable which will hold what kind of session we are dealing with

    // Constructor for the session class
    // enum var "ST" represents the session type that you want the session to be
    public Session(SessionType ST)
    {
        // Denote the session type
        sessionType = ST;

        // set locale variable
        Locale locale = new Locale("ENGLISH", "US");

        // Get current time into the start time variable
        setTimeToCurrentTime(TimeVars.START, "CST", locale);

        // Get current time into the endTime variable (to initialize it with a calendar object)
        setTimeToCurrentTime(TimeVars.END, "CST", locale);

        // Set isFinished bool to false
        isFinished = false;
    }

    // Get the "startTime" variable
    public Calendar getStartTime()
    {
        return startTime;
    }

    // Function which will set the startTime or endTime variable with a user inputted year, month, day, hour, and minute. This function can be used when the user is
    // inputting a custom start or end time into a session
    // var "whatTime" represents the time variable in this class that the user would like to edit. "END" = endTime variable, and "START" = startTime variable.
    // var "year" represents the four digit year for the inputted date
    // var "month" represents the month for the inputted date
    // var "day" represents the day for the inputted date
    // var "hour" represents the hour for the inputted date
    // var "minute" represents the minute for the inputted date
    // var "AM_PM" denotes whether the time we want is in the AM or PM
    public void setTimeToUserInputtedTime(TimeVars whatTime, int year, int month, int day, int hour, int minute, TimeVars AM_PM)
    {
        // If time variable to set is the startTime, then set its variables to the user inputted time variables
        if(whatTime == TimeVars.START)
        {
            // If AM time, set that in calendar
            if(AM_PM == TimeVars.AM)
            {
                startTime.set(Calendar.AM_PM, Calendar.AM);
            }

            // If PM time, set that in calendar
            else if(AM_PM == TimeVars.PM)
            {
                startTime.set(Calendar.AM_PM, Calendar.PM);
            }

            // Set year, month, day, hour, minute, etc. for the selected time variable

            startTime.set(year, month, day, hour, minute);

            startTime.set(Calendar.SECOND, 0);

            startTime.set(Calendar.MILLISECOND, 0);
        }

        // If time variable to set is the endTime, then set its variables to the user inputted time variables
        else if(whatTime == TimeVars.END)
        {
            // If AM time, set that in calendar
            if(AM_PM == TimeVars.AM)
            {
                endTime.set(Calendar.AM_PM, Calendar.AM);
            }

            // If PM time, set that in calendar
            else if(AM_PM == TimeVars.PM)
            {
                endTime.set(Calendar.AM_PM, Calendar.PM);
            }

            // Set year, month, day, hour, minute, etc. for the selected time variable
            endTime.set(year, month, day, hour, minute);

            endTime.set(Calendar.SECOND, 0);

            endTime.set(Calendar.MILLISECOND, 0);
        }
    }

    // Function which will set the startTime or endTime variable to the current time. This function can be used when the session is just being created (with the startTime
    // var) or when the session is ending (with the endTime var).
    // var "whatTime" represents the time variable in this class that the user would like to edit. "end" = endTime variable, and "start" = startTime variable.
    // var "timeZone" represents the time zone code for the current date
    // var "locale" represents the language and location code for the current date
    public void setTimeToCurrentTime(TimeVars whatTime, String timeZone, Locale locale)
    {
        TimeZone TZ = TimeZone.getTimeZone(timeZone);

        // If time variable we want to affect is startTime, then get time for startTime
        if(whatTime == TimeVars.START)
        {
            //startTime = Calendar.getInstance(TZ, locale);
            startTime = Calendar.getInstance();
        }

        // If time variable we want to affect is endTime, then get time for endTime
        else if(whatTime == TimeVars.END)
        {
            //endTime = Calendar.getInstance(TZ, locale);
            endTime = Calendar.getInstance();
        }
    }

    // Function which returns the endTime calendar variable
    public Calendar getEndTime()
    {
        return endTime;
    }

    // Function which returns the user's extra comments
    public String getComment()
    {
        return extraComments;
    }

    // Function which sets the user's extra comment string
    public void setComment(String comment)
    {
        extraComments = comment;
    }

    // Function for testing
    public static void main(String[] args)
    {
        // Make a mock feeding session, which also tests setTimeToCurrentTime for startTime
        Session testSession = new Session(SessionType.FEEDING_SESSION);

        Calendar startCal, endCal;

        // Test enum
        if(testSession.sessionType == SessionType.FEEDING_SESSION)
        {
            System.out.println("Session is a feeding session");
        }

        startCal = testSession.getStartTime();
        endCal = testSession.getEndTime();

        // Test getStartTime() and setTimeToCurrentTime for startTime
        System.out.println("Current Start Time: " + startCal.getTime());

        System.out.println("Start Time TZ: " + startCal.getTimeZone().getDisplayName());

        System.out.println();

        // Test getEndTime() and setTimeToCurrentTime for EndTime
        System.out.println("Current End Time: " + endCal.getTime());

        System.out.println("End Time TZ: " + endCal.getTimeZone().getDisplayName());

        System.out.println();

        // Test setTimeUserInputtedTime for startTime
        testSession.setTimeToUserInputtedTime(TimeVars.START, 1980, 11, 25, 12, 12, TimeVars.PM);

        // Test setTimeToUserInputtedTime for endTime
        testSession.setTimeToUserInputtedTime(TimeVars.END, 1980, 11, 25, 12, 12, TimeVars.PM);

        startCal = testSession.getStartTime();
        endCal = testSession.getEndTime();

        // Test setTimeToUSerInputtedTime for startTime
        System.out.println("New Start Time: " + startCal.getTime());

        System.out.println("Start Time TZ: " + startCal.getTimeZone().getDisplayName());

        System.out.println();

        // Test setTimeToUSerInputtedTime for EndTime
        System.out.println("New End Time: " + endCal.getTime());

        System.out.println("End Time TZ: " + endCal.getTimeZone().getDisplayName());

        System.out.println();

        testSession.setComment("POOP");

        System.out.println(testSession.getComment());
    }
}
