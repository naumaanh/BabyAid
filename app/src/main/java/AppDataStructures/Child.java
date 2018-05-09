package AppDataStructures;

import android.content.Context;
import android.content.res.AssetManager;
import android.os.Environment;

import com.dropbox.core.DbxException;
import com.dropbox.core.v2.DbxClientV2;
import com.dropbox.core.v2.files.WriteMode;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.annotations.XStreamAlias;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Scanner;

import myaaronbatch.unt.edu.babyapp.ChooseChildMenu;

/**
 * Created by aaronbatch on 3/5/18.
 */

// Class which represents a child (with a name, sessions that belong to them, etc.)
@XStreamAlias("CHILD")
public class Child
{
    @XStreamAlias("FNAME")
    public String firstName; // String var representing the child's first name
    @XStreamAlias("LNAME")
    public String lastName; // String var representing the child's last name
    @XStreamAlias("DOB")
    public Calendar dateOfBirth; // String var representing the child's date of birth
    @XStreamAlias("SARR")
    public ArrayList<Session> sessionArray; // Array list which will hold all of the sessions for a child

    /*

       SINGLETON CODE (BELOW)

     */

    private static ArrayList<Child> children; // Static child array list

    // Function which returns the current child object from the static array list
    public static Child getChild()
    {
        Settings tempSettings = Settings.getInstance();

        Child tempChild = new Child();

        // If the array list has not been initialized yet, then initialize the array list, and add a mock child to that array list
        if(children == null)
        {
            children = new ArrayList<Child>();

            children.add(tempChild);

            tempSettings.childIndex = 0;
            tempSettings.numberOfChildren = 1;
        }

        // If the array is not null, but empty, fill the array with one mock child and return that child
        else if(children.isEmpty())
        {
            children.add(tempChild);

            tempSettings.childIndex = 0;
            tempSettings.numberOfChildren = 1;
        }

        // return current child
        return children.get(tempSettings.childIndex);
    }

    public static Child getChild(Context context)
    {
        Settings tempSettings = Settings.getInstance();

        getChildren(context);

        Child tempChild = new Child();

        // If the array list has not been initialized yet, then initialize the array list, and add a mock child to that array list
        if(children == null)
        {
            children = new ArrayList<Child>();

            children.add(tempChild);

            tempSettings.childIndex = 0;
            tempSettings.numberOfChildren = 1;
        }

        // If the array is not null, but empty, fill the array with one mock child and return that child
        else if(children.isEmpty())
        {
            children.add(tempChild);

            tempSettings.childIndex = 0;
            tempSettings.numberOfChildren = 1;
        }

        // return current child
        return children.get(tempSettings.childIndex);
    }

    public static ArrayList<Child> getChildren(Context context)
    {
        XStream xstream = null;
        String xml = "";
        java.util.Scanner s;
        Settings opt = Settings.getInstance();
        try {
            AssetManager assetManager = context.getAssets();
            File f = new File(context.getFilesDir() + File.separator + "children.xml");
            DbxClientV2 dbxCli = DropboxClient.getClient(opt.accesskey);
            OutputStream os = new FileOutputStream(f);
            dbxCli.files().download("/children.xml").download(os);
            if (f.exists())
            {
                s = new Scanner(f).useDelimiter("\\A");
            }
            else
            {
                f.createNewFile();
                s = new Scanner(f).useDelimiter("\\A");
            }
            xml = s.hasNext() ? s.next() : "";
            xstream = new XStream();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (DbxException e) {
            e.printStackTrace();
        }
        xstream.processAnnotations(Child.class);
        xstream.processAnnotations(MedicalSession.class);
        xstream.processAnnotations(WasteSession.class);
        xstream.processAnnotations(SleepingSession.class);
        xstream.processAnnotations(FeedingSession.class);
        xstream.processAnnotations(Session.class);
        if (xml.isEmpty())
        {
            children = new ArrayList<Child>();
        }
        else
        {
            CArr arr = (CArr) xstream.fromXML(xml);
            children = arr.cArr;
        }
        return children;
    }

    public static void save(Context context)
    {
        File file = new File(context.getFilesDir() + File.separator + "children.xml");
        XStream xstream = null;
        PrintWriter pw = null;
        Settings opt = Settings.getInstance();
        try {
            pw = new PrintWriter(new FileOutputStream(file));
            xstream = new XStream();
            xstream.processAnnotations(Child.class);
            xstream.processAnnotations(MedicalSession.class);
            xstream.processAnnotations(WasteSession.class);
            xstream.processAnnotations(SleepingSession.class);
            xstream.processAnnotations(FeedingSession.class);
            xstream.processAnnotations(Session.class);
            CArr arr = new CArr(children);
            pw.print(xstream.toXML(arr));
            pw.close();
            InputStream is = new FileInputStream(file);
            DbxClientV2 dbxCli = DropboxClient.getClient(opt.accesskey);
            dbxCli.files().uploadBuilder("/children.xml").uploadAndFinish(is);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (DbxException e) {
            e.printStackTrace();
        }
    }

    // Function which returns the current child object from the static array list (with a child as an input argument to serve as the first child to add to the array list)
    // if there are currently no kids in the array list
    private static void getChild(Child tempChild)
    {
        Settings tempSettings = Settings.getInstance();

        // If the array list has not been initialized yet, then initialize the array list, and add the inputted child to that array list
        if(children == null)
        {
            children = new ArrayList<Child>();

            children.add(tempChild);

            tempSettings.childIndex = 0;
            tempSettings.numberOfChildren = 1;
        }
    }

    // Function for adding a child to the children array list (with a child object variables as arguments)
    public static void addChild(String fName, String lName, int yearOfBirth, int monthOfBirth, int dayOfBirth)
    {
        Settings tempSettings = Settings.getInstance();
        Child tempChild = new Child();

        tempChild.setFirstName(fName);

        tempChild.setLastName(lName);

        tempChild.setDateOfBirth(yearOfBirth, monthOfBirth, dayOfBirth);

        // If children array has not even been init. yet, then init. it with the inputted child as the first child in the array list of children
        if(children == null)
        {
            getChild(tempChild);
        }

        // Else, just add the child to the array list
        else
        {
            children.add(tempChild);
            tempSettings.numberOfChildren += 1;
        }
    }

    // Function for removing a child at a certain index of the array list
    public static void removeChild(int index)
    {
        Settings tempSettings = Settings.getInstance();

        Integer childrenArrayLength = children.size();

        if(childrenArrayLength != 0)
        {
            if( (childrenArrayLength - 1) >= index)
            {
                children.remove(index);
                tempSettings.numberOfChildren -= 1;
            }
        }
    }

    // Function which returns the actual array list of children
    public static ArrayList<Child> getChildren()
    {
        // If children is a null object, then set it up
        if(children == null)
        {
            setUpChildren();
        }

        return children;
    }

    // Function which sets up an empty array of children
    public static void setUpChildren()
    {
        // If children array is null, give it an empty array
        if(children == null)
        {
            children = new ArrayList<Child>();
        }
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

        // Initialize an empty array list
        sessionArray = new ArrayList<Session>();
    }

    // Constructor for child class; This class takes no arguments and fills the variables of the child with mock data to be filled in later
    // This class is private in order to stop someone from creating a second instance of this object
    private Child()
    {
        // Give a default name (to be filled out later)
        firstName = "FirstName";
        lastName = "LastName";

        // Set the birth date variable to a default date (to be filled out later)
        setDateOfBirth(2002, 2, 22);

        // Initialize an empty array list
        sessionArray = new ArrayList<Session>();
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

        ArrayList<Child> tempChildren = Child.getChildren();

        System.out.println("Size: " + tempChildren.size());
        /*
        Settings mockSettings = Settings.getInstance();

        /////////////////

        //Child q = new Child("Whoa", "Mao", 1990, 5, 22);

        Child.addChild("Whoa", "Mao", 1990, 5, 22);

        /////////////////

        Child mockChild = Child.getChild();

        System.out.println("First Name: " + mockChild.getFirstName());

        System.out.println("Last Name: " + mockChild.getLastName());

        Calendar cal = mockChild.getDateOfBirth();

        System.out.println("DOB: " + cal.getTime());

        //////////////////

        //Child p = new Child("Poop", "Mck", 1990, 5, 22);

        Child.addChild("poop", "mck", 1990, 5, 23);

        mockSettings.childIndex = 1;

        mockChild = Child.getChild();

        System.out.println("First Name: " + mockChild.getFirstName());

        System.out.println("Last Name: " + mockChild.getLastName());

        Calendar cal2 = mockChild.getDateOfBirth();

        System.out.println("DOB: " + cal2.getTime());
        */

        ///////////////////

        /*
        Child.removeChild(4);

        ///////////////////

        ArrayList<Child> childs = Child.getChildren();

        System.out.println("Size: " + childs.size());

        mockSettings.childIndex = 0;

        mockChild = childs.get(0);

        System.out.println("First Name: " + mockChild.getFirstName());

        System.out.println("Last Name: " + mockChild.getLastName());

        Calendar cal3 = mockChild.getDateOfBirth();

        System.out.println("DOB: " + cal3.getTime());
        */

        //////////////////

        // Individual child test code
        /*
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
        */
    }
}
