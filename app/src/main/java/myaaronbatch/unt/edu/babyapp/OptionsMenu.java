package myaaronbatch.unt.edu.babyapp;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import AppDataStructures.Settings;

public class OptionsMenu extends BaseActivity
{
     RadioGroup timeSystemRadioGroup; // Radiogroup of radio buttons for "time system"
    RadioGroup colorThemeRadioGroup; // Radio group of radio buttons for "color theme"
    RadioButton radioButton; // radio button variable which will represent the radio button that was just pressed

    boolean is24HrTime; // boolean representing the user's 24 or 12 hour time preference
    boolean isDarkTheme; // boolean representing the user's dark or light theme preference

    Button changeChildInfoBtn; // Button which the user will press if they would like to change their current child's information
    Button saveAndExitBtn; // Button which the user will press if they would like to save their choices and exit the options menu
    Button exitWithoutSavingBtn; // Button which the user will press if they would like to exit the options menu without saving their choices

    String menuName = "OptionsMenu"; // Variable which will carry the name of the current menu

    Settings settings; // Variable which will hold the settings singleton

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_options_menu);

        final Context ctx = this;

        // Initialize variables with their UI counterparts

        timeSystemRadioGroup = (RadioGroup) findViewById(R.id.timeSystemRadioGroup);
        colorThemeRadioGroup = (RadioGroup) findViewById(R.id.colorThemeRadioGroup);

        changeChildInfoBtn = (Button) findViewById(R.id.changeChildInfoBtn);
        saveAndExitBtn = (Button) findViewById(R.id.saveAndExitBtn);
        exitWithoutSavingBtn = (Button) findViewById(R.id.exitWithoutSavingBtn);

        // Get settings singleton
        settings = Settings.getInstance();

        // Get user's current settings
        is24HrTime = settings.is24HTime;
        isDarkTheme = settings.isDarkTheme;

        // Initialize radio group choices to user's current settings

        if(settings.is24HTime)
        {
            timeSystemRadioGroup.check(R.id.twoFourHrTime);
        }
        else
        {
            timeSystemRadioGroup.check(R.id.oneTwoHrTime);
        }

        if(settings.isDarkTheme)
        {
            colorThemeRadioGroup.check(R.id.darkTheme);
        }
        else
        {
            colorThemeRadioGroup.check(R.id.lightTheme);
        }

        // When the changeChildInfoBtn is tapped, send the user to the add child menu
        changeChildInfoBtn.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                // Make intent to go to the add child menu
                Intent goToChildInfoMenuIntent = new Intent(getApplicationContext(), AddChildMenu.class);

                // send the name of the menu that the user is on to the add child menu
                goToChildInfoMenuIntent.putExtra("MenuName", menuName);

                // Go to the menu the user chose
                startActivity(goToChildInfoMenuIntent);
            }
        });

        // When the saveAndExitBtn is tapped, save the user's current settings and send the user to the child main menu
        saveAndExitBtn.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                // Make intent that goes to the child main menu
                Intent goToChildMainMenuIntent = new Intent(getApplicationContext(), MainActivity.class);

                // Save user's selected settings
                settings.is24HTime = is24HrTime;
                settings.isDarkTheme = isDarkTheme;

                settings.save(ctx);

                // TEST CODE

                if(settings.is24HTime){
                    System.out.println("24 Hour Time Enabled");
                }
                else{
                    System.out.println("12 Hour Time Enabled");
                }

                if (settings.isDarkTheme) {

                    System.out.println("Dark Theme Enabled");
                }
                else{
                    System.out.println("Light Theme Enabled");
                }

                // Go to the menu the user chose
                startActivity(goToChildMainMenuIntent);
            }
        });

        // When the exitWithoutSavingBtn is tapped, send the user to the child main menu without saving their settings
        exitWithoutSavingBtn.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                // Make intent that goes to the child main menu
                Intent goToChildMainMenuIntent = new Intent(getApplicationContext(), MainActivity.class);

                // Go to the menu the user chose
                startActivity(goToChildMainMenuIntent);
            }
        });
    }

    // If a radio button on the "time system" radio group is selected, save the user's choice into the temporary is24HrTime bool variable
    public void tsClick(View v)
    {
        // get id of selected radio button
        int rbID = timeSystemRadioGroup.getCheckedRadioButtonId();

        // With id of selected radio button, get selected radio button object
        radioButton = (RadioButton) findViewById(rbID);

        // If user chose 24 Hr time radio button, save that choice into temp bools
        if(radioButton.getId() == R.id.twoFourHrTime)
        {
            is24HrTime = true;
            System.out.println("24 HR Time Set");
        }

        // If user chose 12 Hr time radio button, save that choice into temp bools
        else
        {
            is24HrTime = false;
            System.out.println("12 HR Time Set");
        }

        // Send notification to screen about user's choice
        //Toast.makeText(getBaseContext(), radioButton.getText(), Toast.LENGTH_LONG).show();
    }

    // If a radio button on the "color theme" radio group is selected, save the user's choice into the temporary isDarkTheme bool variable
    public void ctClick(View v)
    {
        // get id of selected radio button
        int rbID = colorThemeRadioGroup.getCheckedRadioButtonId();

        // With id of selected radio button, get selected radio button object
        radioButton = (RadioButton) findViewById(rbID);

        // If user chose dark theme radio button, save that choice into temp bools
        if(radioButton.getId() == R.id.darkTheme)
        {
            isDarkTheme = true;
            System.out.println("Dark Theme Set");
        }

        // If user chose light theme radio button, save that choice into temp bools
        else
        {
            isDarkTheme = false;
            System.out.println("Light Theme Set");
        }

        // Send notification to screen about user's choice
        //Toast.makeText(getBaseContext(), radioButton.getText(), Toast.LENGTH_LONG).show();
    }
}
