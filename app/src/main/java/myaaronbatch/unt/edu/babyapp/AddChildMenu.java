package myaaronbatch.unt.edu.babyapp;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.res.AssetManager;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;

import com.thoughtworks.xstream.XStream;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.text.DateFormat;
import java.util.Calendar;

import AppDataStructures.Child;
import AppDataStructures.FeedingSession;
import AppDataStructures.MedicalSession;
import AppDataStructures.Session;
import AppDataStructures.Settings;
import AppDataStructures.SleepingSession;
import AppDataStructures.WasteSession;

public class AddChildMenu extends BaseActivity implements DatePickerDialog.OnDateSetListener
{
    TextView currentFirstNameTV; // Textview for displaying the current first name of the child
    EditText firstNameEditText; // Edittext var for inputting the name for the first name of the child
    Button saveFirstNameBtn; // Button which if pressed will save the user inputted first name

    TextView currentLastNameTV; // Textview for displaying the current last name of the child
    EditText lastNameEditText; // Edit text var for inputting the name for the last name of the child
    Button saveLastNameBtn; // Button which if pressed will save the user inputted last name

    TextView currentBirthDateTV; // Textview for displaying the child's current birth date
    Button changeBirthDateBtn; // Button which if pressed will bring up a date picker for changing the child's current birth date

    Button submitChildBtn; // Button which if pressed will save the child's newly inputted info and (if coming from the choose child menu) will add the new child to the
    // child array list

    Button exitChildMenuBtn; // Button which if pressed will take the user back to the menu that they came from (without saving or adding a child)

    String possibleFirstName = "FirstName"; // Var which will hold the child's inputted first name
    String possibleLastName = "LastName"; // Var which will hold the child's inputted last name
    Calendar possibleBirthDate; // Var which will hold the child's inputted birth date

    String MenuName; // Var which will hold the name of the menu that the user came from previously

    Child currentChild; // Var which will hold the current child that the user has selected
    Settings tempSettings; // Var which will hold an instance of the settings singleton

    String tempDate; // String variable used for holding a formatted date

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_child_menu);
        final Context ctx = this;

        // Connect var to UI elements

        currentFirstNameTV = (TextView) findViewById(R.id.currentFirstNameTV);
        firstNameEditText = (EditText) findViewById(R.id.firstNameEditText);
        saveFirstNameBtn = (Button) findViewById(R.id.saveFirstNameBtn);

        currentLastNameTV = (TextView) findViewById(R.id.currentLastNameTV);
        lastNameEditText = (EditText) findViewById(R.id.lastNameEditText);
        saveLastNameBtn = (Button) findViewById(R.id.saveLastNameBtn);

        currentBirthDateTV = (TextView) findViewById(R.id.currentBirthDateTV);
        changeBirthDateBtn = (Button) findViewById(R.id.changeBirthDateBtn);

        submitChildBtn = (Button) findViewById(R.id.submitChildBtn);
        exitChildMenuBtn = (Button) findViewById(R.id.exitChildMenuBtn);

        // Get name of previous menu that the user came here from
        if(getIntent().hasExtra("MenuName"))
        {
            MenuName = getIntent().getStringExtra("MenuName");

            System.out.println("Menu NAme: " + MenuName);
        }

        // Hide keyboard initially
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

        // Get settings singleton
        tempSettings = Settings.getInstance();

        // If user came from choosing child menu, then set up necessary vars
        if(MenuName.equals("ChoosingChildMenu"))
        {
            // Set temp birth date var
            possibleBirthDate = Calendar.getInstance();

            // Set first and last name textviews
            currentFirstNameTV.setText(possibleFirstName);

            currentLastNameTV.setText(possibleLastName);

            // Set currentBirthDateTV
            tempDate = DateFormat.getDateInstance(DateFormat.FULL).format(possibleBirthDate.getTime());

            currentBirthDateTV.setText(tempDate);
        }


        // If user came from options menu, then set necessary vars
        else if(MenuName.equals("OptionsMenu"))
        {
            // Get current child user is on
            currentChild = Child.getChild(this);

            // Init. temp birth date variable with child's current birth date
            possibleBirthDate = (Calendar) currentChild.getDateOfBirth().clone();

            // Init. both temp first and last name vars to the current child's
            possibleFirstName = currentChild.getFirstName();
            possibleLastName = currentChild.getLastName();

            // Initialize UI elements with child information

            // Set first and last name textviews
            currentFirstNameTV.setText(currentChild.getFirstName());

            currentLastNameTV.setText(currentChild.getLastName());

            // Initialize currentBirthDateTV
            tempDate = DateFormat.getDateInstance(DateFormat.FULL).format(currentChild.getDateOfBirth().getTime());

            currentBirthDateTV.setText(tempDate);
        }


        // When save first name button is clicked, init. the possible first name var and first name textview to the name entered into the first name edit text
        saveFirstNameBtn.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                possibleFirstName = firstNameEditText.getText().toString();

                currentFirstNameTV.setText(possibleFirstName);
            }
        });

        // When save last name button is clicked, init. the possible last name var and last name textview to the name entered into the last name edit text
        saveLastNameBtn.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                possibleLastName = lastNameEditText.getText().toString();

                currentLastNameTV.setText(possibleLastName);
            }
        });

        // When changeBirthDateBtn is pressed, bring up the date picker fragment
        changeBirthDateBtn.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                // temporarily change style of datepicker
                tempSettings.styleInt = android.R.style.Theme_Material_Light_Dialog_Alert;

                DialogFragment DP = new DatePickerFragment();

                DP.show(getSupportFragmentManager(), "date picker");

                // Change style back to default
                tempSettings.styleInt = android.R.style.Theme_DeviceDefault_Light_Dialog_Alert;
            }
        });

        // When save child button is pressed, save the child's information and/or add the child to the child arraylist singleton
        submitChildBtn.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                // Get year, month, and day values from possible birth date var
                int year = possibleBirthDate.get(Calendar.YEAR);
                int month = possibleBirthDate.get(Calendar.MONTH);
                int day = possibleBirthDate.get(Calendar.DAY_OF_MONTH);

                // If user came from choosing child menu, then add a child with the user inputted vars to the child arraylist singleton
                if(MenuName.equals("ChoosingChildMenu"))
                {
                    Child.addChild(possibleFirstName, possibleLastName, year, month, day);

                    ////// TEST CODE BEGIN
                    //Child c = Child.getChildren(ctx).get(0);

                    //System.out.println("First NAme: " + c.getFirstName());
                    //System.out.println("Last Name: " + c.getLastName());
                    //System.out.println("DOB: " + c.getDateOfBirth().getTime());


                    /////// TEST CODE END
                    Child.save(ctx);
                    // Make intent that is set to take the user back to the choosing child ,enu
                    Intent goToChoosingChildMenuIntent = new Intent(getApplicationContext(), ChooseChildMenu.class);

                    // send user to choosing child menu
                    startActivity(goToChoosingChildMenuIntent);
                }


                // If the user came from the options menu, then save the user inputted vars to the current child
                else if(MenuName.equals("OptionsMenu"))
                {
                    currentChild.setFirstName(possibleFirstName);

                    currentChild.setLastName(possibleLastName);

                    currentChild.setDateOfBirth(year, month, day);

                    // Send user back to options menu
                    Intent intent = new Intent(getApplicationContext(), OptionsMenu.class);

                    Child.save(ctx);

                    startActivity(intent);
                }


                Child.save(ctx);
            }
        });

        // If the user presses the exit child menu button, then send them back to whatever menu they came from without saving any inputted information
        exitChildMenuBtn.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                // If user came from choosing child menu, then go back to that menu
                if(MenuName.equals("ChoosingChildMenu"))
                {
                    // Make intent that is set to take the user back to the choosing child ,enu
                    Intent goToChoosingChildMenuIntent = new Intent(getApplicationContext(), ChooseChildMenu.class);

                    // send user to choosing child menu
                    startActivity(goToChoosingChildMenuIntent);
                }

                /*
                else if(MenuName.equals("OptionsMenu"))
                {
                    // Go to options menu
                }
                */
            }
        });
    }

    // Function for the date picker which will run once the user presses the "Ok" button to submit their custom inputted date into the date picker
    @Override
    public void onDateSet(DatePicker datePicker, int year, int month, int dayOfMonth)
    {
        // Set possible birth date var to inputted vars
        possibleBirthDate.set(year, month, dayOfMonth);

        // Initialize currentBirthDateTV
        tempDate = DateFormat.getDateInstance(DateFormat.FULL).format(possibleBirthDate.getTime());

        currentBirthDateTV.setText(tempDate);
    }
}
