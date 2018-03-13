package AppDataStructures;

import java.util.ArrayList;
import java.util.Calendar;

/**
 * Created by aaronbatch on 3/5/18.
 */

// Class which represents a child (with a name, sessions that belong to them, etc.)
public class Child
{
    String firstName; // String var representing the child's first name
    String lastName; // String var representing the child's last name
    Calendar dateOfBirth; // String var representing the child's date of birth
    ArrayList<Session> sessionArray; // Array list which will hold all of the sessions for a child

    /*

       SINGLETON CODE (BELOW)

     */

    private static Child child; // Static child object (this will be the only child object to be created)

    // Function which returns the static child object (uses constructor thst initializes the child to default values)
    public static Child getChild()
    {
        if(child == null)
        {
            child = new Child();
        }

        return child;
    }

    /*

        NORMAL OBJECT CODE (BELOW)

     */

    // Constructor for child class; This class is private in order to stop someone from creating a second instance of this object
    // var "fName" represents the first name of the child
    // var "lName" represents the last name of the child
    // var "yearOfBirth" represents the four digit year that the child was born
    // var "monthOfBirth" represents the integer month that the child was born in (starts from zero)
    // var "dayOfBirth" represents the integer day that the child was born on
    private Child(String fName, String lName, Integer yearOfBirth, Integer monthOfBirth, Integer dayOfBirth)
    {
        // Set name variables
        firstName = fName;
        lastName = lName;

        // Set the date of birth variable
        setDateOfBirth(yearOfBirth, monthOfBirth, dayOfBirth);
    }

    // Constructor for child class; This class takes no arguments and fills the variables of the child with mock data to be filled in later
    // This class is private in order to stop someone from creating a second instance of this object
    private Child()
    {
        // Give a default name (to be filled out later)
        firstName = "John";
        lastName = "Doe";

        // Set the birth date variable to a default date (to be filled out later)
        setDateOfBirth(2002, 2, 22);
    }

    // Function which returns the first name of child
    public String getFirstName()
    {
        return firstName;
    }

    // Function which sets the first name of child
    // var "fName" represents the name that will go into the firstName variable
    public void setFirstName(String fName)
    {
        firstName = fName;
    }

    // Function which returns the last name of child
    public String getLastName()
    {
        return lastName;
    }

    // Function which sets the last name of the child
    // var "lName" represents the name that will go into the lastName variable
    public void setLastName(String lName)
    {
        lastName = lName;
    }

    // Function which returns the calendar dateOfBirth variable
    public Calendar getDateOfBirth()
    {
        return dateOfBirth;
    }

    // Function which sets the dateOfBirth variable to a user inputted date
    // var "year" represents the four digit year of the child's birth
    // var "month" represents the month of the child's birth (starts from zero)
    // var "day" represents the day of the child's birth
    public void setDateOfBirth(int year, int month, int day)
    {
        // Get a calendar object into the dateOfBirth variable
        dateOfBirth = Calendar.getInstance();

        // Set the date
        dateOfBirth.set(year, month, day);
    }

    // Main function created for testing purposes
    public static void main(String[] args)
    {
        //Child mockChild = new Child("Aaron", "Batch", 1996, 7, 26);

        Child mockChild = Child.getChild();

        System.out.println("First name before change: " + mockChild.getFirstName());

        mockChild.setFirstName("Paul");

        System.out.println("First name after change: " + mockChild.getFirstName());

        System.out.println();

        System.out.println("Last name before change: " + mockChild.getLastName());

        mockChild.setLastName("Mannafort");

        System.out.println("Last name after change: " + mockChild.getLastName());

        System.out.println();

        Calendar cal = mockChild.getDateOfBirth();

        System.out.println("DOB before change: " + cal.getTime());

        System.out.println();

        mockChild.setDateOfBirth(1900, 11, 25);

        cal = mockChild.getDateOfBirth();

        System.out.println("DOB after change: " + cal.getTime());

        Child mockChild2 = Child.getChild();

        System.out.println("Name in child 2: " + mockChild2.getFirstName());
    }
}
