package myaaronbatch.unt.edu.babyapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.Locale;

import AppDataStructures.Child;
import AppDataStructures.MedicalSession;
import AppDataStructures.Session;
import AppDataStructures.TimeVars;

// Class which represents the "Start/End Session Menu"
public class StartOrEndSessionMenu extends AppCompatActivity
{
    Button backBtn_SOESM; // Button which will take the user to the previous screen
    Button startOrEndBtn; // Button which will start a session or end a session (depending on what it is displaying at that time)
    TextView titleTextView; // Textview which represents the title of the activity
    TextView testTextView; // Textview which exists for testing purposes
    String sessionType; // String representing what kind of session the user would like to potentially start or end
    // ("Waste" = waste session, "Medication" = medication session, "Sleeping" = sleeping session, and "Feeding" = feeding session)
    Boolean startPushed = false; // Boolean representing whether the user has pressed the startOrEndBtn yet
    String menuName; // String representing the name if the menu that the user came from before they got to this menu
    // ("SessionRecordingMenu" = session recording menu, "SessionViewingMenu" = session viewing menu)

    Child child; // Child class variable which will hold and edit the child singleton
    Session session; // Session class variable which will hold the session that the user is starting or ending
    Locale locale = new Locale("en", "US"); // Default locale variable

    Integer index; // integer variable which will hold the index of the session that the user wants to start or end (index for the child's sessionArray)

    String endSessionTxt = "End Session";
    String startSessionTxt = "Start Session";

    // On activity creation, set up all of the buttons, textviews, and other vital elements of the activity
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start_or_end_session_menu);

        // Get references to the UI elements on the activity
        backBtn_SOESM = (Button) findViewById(R.id.backBtn_SOESM);
        startOrEndBtn = (Button) findViewById(R.id.startOrEndBtn);
        testTextView = (TextView) findViewById(R.id.testTextView);

        // Get name of previous menu that the user came here from
        if(getIntent().hasExtra("MenuName"))
        {
            menuName = getIntent().getStringExtra("MenuName");
        }

        // If the user came here from the SessionRecordingMenu, then get necessary variables
        if(menuName.equals("SessionRecordingMenu"))
        {
            // Set startPushed to false
            startPushed = false;

            // If the previous view has sent us some information under the data-title "SessionType", then use that info to find out what type of session the user
            // wanted to start
            if (getIntent().hasExtra("SessionType"))
            {
                // Get the session type that was sent in the string message
                sessionType = getIntent().getStringExtra("SessionType");

                testTextView.setText(sessionType);
            }

        }

        // If the user came here from the SessionViewingMenu, then get the necessary variables
        else if(menuName.equals("SessionViewingMenu"))
        {
            // Set startPushed to true
            startPushed = true;

            // Get the index of the current session that the user would like to end (the index is pointing to the location of the session in the child singleton's
            // sessionArray)
            if(getIntent().hasExtra("index"))
            {
                index = getIntent().getIntExtra("index", 0);
            }

            // Get the type of session that the user wants to end
            if(getIntent().hasExtra("SessionType"))
            {
                sessionType = getIntent().getStringExtra("SessionType");
            }
        }

        // If the startOrEndBtn was pushed already, change the text on the button to "End Session"
        if(startPushed)
        {
            startOrEndBtn.setText(endSessionTxt);
        }

        // If the startOrEndBtn was not pushed yet, change the text on the button to "Start Session"
        else
        {
            startOrEndBtn.setText(startSessionTxt);
        }

        // When the startOrEndBtn is pushed, go into this function
        startOrEndBtn.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                // Get the child singleton
                child = Child.getChild();

                // If the startOrEndBtn has not been pressed before now, and if the user came from the SessionRecordingMenu, then this means that the user wants to start
                // the session
                if( (!startPushed) && (menuName.equals("SessionRecordingMenu")) )
                {
                    // Set button text
                    startOrEndBtn.setText(endSessionTxt);

                    // Add the specific type of session that the user chose to the child singleton's session array

                    if(sessionType.equals("Waste"))
                    {
                        // add waste session to child's session array list
                    }

                    else if(sessionType.equals("Medication"))
                    {
                        child.sessionArray.add(new MedicalSession());
                    }

                    else if(sessionType.equals("Sleeping"))
                    {
                        // add sleeping session to child's session array list
                    }

                    else if(sessionType.equals("Feeding"))
                    {
                        // add feeding session to child's session array list
                    }

                    // Show that the button has now been pressed
                    startPushed = true;

                    // Test Code
                    /*
                    Integer i = child.sessionArray.size() - 1;

                    Session sess = child.sessionArray.get(i);

                    System.out.println();

                    System.out.println("Start Time: " + sess.getStartTime().getTime());

                    if(sess.sessionType.equals(SessionType.MEDICATION_SESSION)){
                        System.out.println("Med Session");
                    }

                    System.out.println(child.sessionArray.size());
                    System.out.println();
                    */
                }

                // If the button has already been pressed before
                else if(startPushed)
                {
                    // Get an intent that is ready to transition to the SessionInformationMenu, and so that we can send the necessary messages
                    Intent goToSessionInformationMenuIntent = new Intent(getApplicationContext(), SessionInformationMenu.class);

                    // If the user is coming from the SessionRecordingMenu, then this means that they want to end the session that they started previously
                    if(menuName.equals("SessionRecordingMenu"))
                    {
                        // Get the most recent session made (which also happens to be the session that the user is ending)
                        index = child.sessionArray.size() - 1;

                        // Get the current session using the index
                        session = child.sessionArray.get(index);

                        // set the end time of the current session to the current time
                        session.setTimeToCurrentTime(TimeVars.END, "CST", locale);

                        // Set the current session to finished
                        session.isFinished = true;

                        /*
                        System.out.println();
                        // Test code
                        Session sess = child.sessionArray.get(index);

                        System.out.println("End Time: " + sess.getEndTime().getTime());

                        System.out.println();
                        */
                    }

                    // If the user is coming from the SessionViewingMenu, then this means that they want to end the ongoing session that they selected from the
                    // SessionViewingMenu
                    else if(menuName.equals("SessionViewingMenu"))
                    {
                        // Get the current session from the child singleton's session array
                        session = child.sessionArray.get(index);

                        // set the end time of the current session to the current time
                        session.setTimeToCurrentTime(TimeVars.END, "CST", locale);

                        // set the current session to finished
                        session.isFinished = true;
                    }

                    // Place the name of this menu in the the "MenuName" putExtra
                    goToSessionInformationMenuIntent.putExtra("MenuName", "StartOrEndSessionMenu");

                    // Place the type of session that the user is ending into the "SessionType" putExtra

                    if(sessionType.equals("Waste"))
                    {
                        goToSessionInformationMenuIntent.putExtra("SessionType", "Waste");
                    }

                    else if(sessionType.equals("Medication"))
                    {
                        goToSessionInformationMenuIntent.putExtra("SessionType", "Medication");
                    }

                    else if(sessionType.equals("Sleeping"))
                    {
                        goToSessionInformationMenuIntent.putExtra("SessionType", "Sleeping");
                    }

                    else if(sessionType.equals("Feeding"))
                    {
                        goToSessionInformationMenuIntent.putExtra("SessionType", "Feeding");
                    }

                    // Place the index of the user's current session into the "index" putExtra
                    goToSessionInformationMenuIntent.putExtra("index", index);

                    // Go to the SessionInformationMenu
                    startActivity(goToSessionInformationMenuIntent);
                }
            }
        });

        // If the back button is selected, then go to the previous view that the user was on
        backBtn_SOESM.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                finish();
            }
        });
    }
}
