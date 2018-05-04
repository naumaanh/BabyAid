package myaaronbatch.unt.edu.babyapp;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.TimePicker;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import AppDataStructures.Child;
import AppDataStructures.Settings;
import AppDataStructures.SleepingSession;

public class SleepingSessionInfoMenu extends AppCompatActivity implements DatePickerDialog.OnDateSetListener, TimePickerDialog.OnTimeSetListener
{
    TextView currentStartDateTV; // Textview var representing the box that will hold the current start date
    TextView currentStartTimeTV; // Textview var representing the box that will hold the current start time
    TextView currentEndDateTV; // Textview var representing the box that will hold the current end date
    TextView currentEndTimeTV; // Textview var representing the box that will hold the current end time

    Button changeStartDateBtn; // Button var representing the button that says "Change Start Date" (or something along those lines)
    Button changeStartTimeBtn; // Button var representing the button that says "Change Start Time" (or something along those lines)
    Button changeEndDateBtn; // Button var representing the button that says "Change End Date" (or something along those lines)
    Button changeEndTimeBtn; // Button var representing the button that says "Change End Time" (or something along those lines)
    Button submitSessionBtn; // Button var representing the button that says "Save Session" (or something along those lines)
    Button exitSessionBtn; // Button var representing the button that says "Exit Session" (or something along those lines)

    Calendar possibleNewEndTime; // Calendar var that will hold the date and time of the user edited end time (whose values will be given to the
    // actual end time once the user saves the session

    Calendar possibleNewStartTime; // Calendar var that will hold the date and time of the user edited start time (whose values will be given to the
        // actual start time once the user saves the session

    Calendar currentStartTime; // Calendar var representing the session's current start time and date
    Calendar currentEndTime; // Calendar var representing the session's current end time and date

    String MenuName; // String var representing the name of the menu that user came here from
    int index; // Integer var representing the current session's index value (when looking at the current child's session array)

    Child child; // The current child
    SleepingSession currentSession; // The current sleeping session
    Settings tempSettings; // An instance of the settings singleton

    String tempDate; // String variable used for holding a formatted date
    SimpleDateFormat tempTime; // SimpleDateformat var used for outputting a formatted time

    String currentBtnPressed; // String var representing the button that was just pressed (it will hold the name of the button inside of it)

    // Create the activity

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sleeping_session_info_menu);

        // Connect variables to their UI counterparts

        currentStartDateTV = (TextView) findViewById(R.id.currentStartDateTV);
        currentStartTimeTV = (TextView) findViewById(R.id.currentStartTimeTV);
        currentEndDateTV = (TextView) findViewById(R.id.currentEndDateTV);
        currentEndTimeTV = (TextView) findViewById(R.id.currentEndTimeTV);

        changeStartDateBtn = (Button) findViewById(R.id.changeStartDateBtn);
        changeStartTimeBtn = (Button) findViewById(R.id.changeStartTimeBtn);
        changeEndDateBtn = (Button) findViewById(R.id.changeEndDateBtn);
        changeEndTimeBtn = (Button) findViewById(R.id.changeEndTimeBtn);
        submitSessionBtn = (Button) findViewById(R.id.submitSessionBtn);
        exitSessionBtn = (Button) findViewById(R.id.exitSessionBtn);

        // Get menu name and index of current session from activity that the user came here from

        MenuName = getIntent().getStringExtra("MenuName");
        index = getIntent().getIntExtra("index", 0);

        // Test code
        System.out.println("Menu Name: " + MenuName + ", Index: " + index);

        // If the menu that the user just came here from was the start or end session menu, then hide the exit session button (since we want to force them to save the session
        if(MenuName.equals("StartOrEndSessionMenu"))
        {
            exitSessionBtn.setVisibility(View.GONE);
        }

        // Get current child and settings objects

        child = Child.getChild(this);
        tempSettings = Settings.getInstance(this);

        // Get current session from child
        currentSession = (SleepingSession) child.sessionArray.get(index);

        // Get start and end times from current session
        currentStartTime = currentSession.getStartTime();
        currentEndTime = currentSession.getEndTime();

        // Fill the possible start and end time variables to match the current start and end time variables

        possibleNewStartTime = (Calendar) currentStartTime.clone();

        possibleNewEndTime = (Calendar) currentEndTime.clone();

        //possibleNewStartTime.set(currentStartTime.get(Calendar.YEAR), currentStartTime.get(Calendar.MONTH), currentStartTime.get(Calendar.DAY_OF_MONTH), currentStartTime.get(Calendar.HOUR_OF_DAY), currentStartTime.get(Calendar.MINUTE), 0);

        //possibleNewEndTime.set(currentEndTime.get(Calendar.YEAR), currentEndTime.get(Calendar.MONTH), currentEndTime.get(Calendar.DAY_OF_MONTH), currentEndTime.get(Calendar.HOUR_OF_DAY), currentEndTime.get(Calendar.MINUTE), 0);

        // If setting is ticked to 24 hour time, then set up the tempTime variable to formate time in 24 hr time
        if(tempSettings.is24HTime)
        {
            tempTime = new SimpleDateFormat("HH:mm");
        }

        // Else, set up tempTime to output 12 hr time
        else
        {
            tempTime = new SimpleDateFormat("hh:mm a");
        }

        // Initialize  date and time textview variables to match current start and end times

        // Initialize currentStartDateTV
        tempDate = DateFormat.getDateInstance(DateFormat.FULL).format(currentSession.getStartTime().getTime());

        currentStartDateTV.setText(tempDate);

        // Initialize currentStartTimeTV
        currentStartTimeTV.setText(tempTime.format(currentSession.getStartTime().getTime()));

        // Initialize currentEndDateTV
        tempDate = DateFormat.getDateInstance(DateFormat.FULL).format(currentSession.getEndTime().getTime());

        currentEndDateTV.setText(tempDate);

        // Initialize currentEndTimeTV
        currentEndTimeTV.setText(tempTime.format(currentSession.getEndTime().getTime()));

        // When changeStartDateBtn is pressed, bring up the date picker fragment
        changeStartDateBtn.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                currentBtnPressed = "ChangeStartDate";

                DialogFragment DP = new DatePickerFragment();

                DP.show(getSupportFragmentManager(), "date picker");
            }
        });

        // When changeStartTimeBtn is pressed, bring up the time picker fragment
        changeStartTimeBtn.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                // Bring up time picker fragment

                currentBtnPressed = "ChangeStartTime";

                DialogFragment TP = new TimePickerFragment();

                TP.show(getSupportFragmentManager(), "time picker");
            }
        });

        // When changeEndDateBtn is pressed, bring up the date picker fragment
        changeEndDateBtn.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                currentBtnPressed = "ChangeEndDate";

                DialogFragment DP = new DatePickerFragment();

                DP.show(getSupportFragmentManager(), "date picker");
            }
        });

        // When changeEndTimeBtn is pressed, bring up the time picker fragment
        changeEndTimeBtn.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                // Bring up time picker fragment

                currentBtnPressed = "ChangeEndTime";

                DialogFragment TP = new TimePickerFragment();

                TP.show(getSupportFragmentManager(), "time picker");
            }
        });

        // When submitSessionBtn is pressed, save the values of the possible start and end time variables into the current start and end time variables
        // and then send the user to the main menu
        submitSessionBtn.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                // Variables representing input values for the year, month, day of month, hour of day (24 hr time), and minute of a calendar object
                int year, month, dayOfMonth, hourOfDay, minute;

                // Set year, month, etc. to the possible start time values
                year = possibleNewStartTime.get(Calendar.YEAR);
                month = possibleNewStartTime.get(Calendar.MONTH);
                dayOfMonth = possibleNewStartTime.get(Calendar.DAY_OF_MONTH);
                hourOfDay = possibleNewStartTime.get(Calendar.HOUR_OF_DAY);
                minute = possibleNewStartTime.get(Calendar.MINUTE);

                // Set current start time to new start time values
                currentStartTime.set(year, month, dayOfMonth, hourOfDay, minute, 0);

                // Set year, month, etc. to the possible end time values
                year = possibleNewEndTime.get(Calendar.YEAR);
                month = possibleNewEndTime.get(Calendar.MONTH);
                dayOfMonth = possibleNewEndTime.get(Calendar.DAY_OF_MONTH);
                hourOfDay = possibleNewEndTime.get(Calendar.HOUR_OF_DAY);
                minute = possibleNewEndTime.get(Calendar.MINUTE);

                // Set current end time to new end time values
                currentEndTime.set(year, month, dayOfMonth, hourOfDay, minute, 0);

                // Make intent that is set to take the user back to the main menu for the child
                Intent goToMainMenuIntent = new Intent(getApplicationContext(), MainActivity.class);

                /////////////// START OF TEST CODE

                Calendar tCal = currentSession.getStartTime();

                System.out.println("Start Time: " + tCal.getTime());

                tCal = currentSession.getEndTime();

                System.out.println("End Time: " + tCal.getTime());

                /////////////// END OF TEST CODE
                Child.save();
                // Take the user to the current child's main menu
                startActivity(goToMainMenuIntent);
            }
        });

        // If the exit session button is pressed, take the user back to the main menu without saving the new start and end time vars to the current start and end time vars
        exitSessionBtn.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                // Make an intent that is set to go to the current child's main menu
                Intent goToMainMenuIntent = new Intent(getApplicationContext(), MainActivity.class);

                /////////////// START OF TEST CODE

                Calendar tCal = currentSession.getStartTime();

                System.out.println("Start Time: " + tCal.getTime());

                tCal = currentSession.getEndTime();

                System.out.println("End Time: " + tCal.getTime());

                /////////////// END OF TEST CODE

                // Take the user back to the current child's main menu
                startActivity(goToMainMenuIntent);
            }
        });
    }

    // Function for the date picker which will run once the user presses the "Ok" button to submit their custom inputted date into the date picker
    @Override
    public void onDateSet(DatePicker datePicker, int year, int month, int dayOfMonth)
    {
        // If the Change start date button was pressed in order to bring up this date picker, then give the inputted date values to the possible start date var
        if(currentBtnPressed.equals("ChangeStartDate"))
        {
            possibleNewStartTime.set(Calendar.YEAR, year);
            possibleNewStartTime.set(Calendar.MONTH, month);
            possibleNewStartTime.set(Calendar.DAY_OF_MONTH, dayOfMonth);

            // Initialize currentStartDateTV
            tempDate = DateFormat.getDateInstance(DateFormat.FULL).format(possibleNewStartTime.getTime());

            currentStartDateTV.setText(tempDate);
        }

        // If the Change end date button was pressed in order to bring up this date picker, then give the inputted date values to the possible end date var
        else if(currentBtnPressed.equals("ChangeEndDate"))
        {
            possibleNewEndTime.set(Calendar.YEAR, year);
            possibleNewEndTime.set(Calendar.MONTH, month);
            possibleNewEndTime.set(Calendar.DAY_OF_MONTH, dayOfMonth);

            // Initialize currentEndDateTV
            tempDate = DateFormat.getDateInstance(DateFormat.FULL).format(possibleNewEndTime.getTime());

            currentEndDateTV.setText(tempDate);
        }
    }

    // Function for the time picker which will run once the user presses the "Ok" button to submit their custom inputted time into the time picker
    @Override
    public void onTimeSet(TimePicker timePicker, int hourOfDay, int minute)
    {
        // If the Change start time button was pressed in order to bring up this time picker, then give the inputted time values to the possible start time var
        if(currentBtnPressed.equals("ChangeStartTime"))
        {
            possibleNewStartTime.set(Calendar.HOUR_OF_DAY, hourOfDay);
            possibleNewStartTime.set(Calendar.MINUTE, minute);

            //currentStartTime.set(Calendar.HOUR_OF_DAY, hourOfDay);
            //currentStartTime.set(Calendar.MINUTE, minute);

            // Initialize currentStartTimeTV
            currentStartTimeTV.setText(tempTime.format(possibleNewStartTime.getTime()));
            //currentStartTimeTV.setText(tempTime.format(currentStartTime.getTime()));
        }

        // If the Change end time button was pressed in order to bring up this time picker, then give the inputted time values to the possible end time var
        else if(currentBtnPressed.equals("ChangeEndTime"))
        {
            possibleNewEndTime.set(Calendar.HOUR_OF_DAY, hourOfDay);
            possibleNewEndTime.set(Calendar.MINUTE, minute);

            // Initialize currentEndTimeTV
            currentEndTimeTV.setText(tempTime.format(possibleNewEndTime.getTime()));
        }
    }
}
