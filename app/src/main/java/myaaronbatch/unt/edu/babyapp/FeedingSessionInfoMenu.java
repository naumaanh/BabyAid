package myaaronbatch.unt.edu.babyapp;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.NumberPicker;
import android.widget.TextView;

import com.thoughtworks.xstream.XStream;

import java.io.IOException;
import java.io.PrintWriter;

import AppDataStructures.Child;
import AppDataStructures.FeedingSession;
import AppDataStructures.MedicalSession;
import AppDataStructures.Session;
import AppDataStructures.SleepingSession;
import AppDataStructures.WasteSession;

// Class which represents the info menu for the feeding session
public class FeedingSessionInfoMenu extends BaseActivity
{
    NumberPicker feedingMethodPicker = null; // Number picker for the feeding method
    EditText foodTypeTextBox; // editable text box for the food type
    NumberPicker foodAmountPicker = null; // Number picker for the food amount
    Button saveBtn; // Button var representing the save button on the screen

    String[] feedingMethodStringArray = new String[] {"Breast Fed", "Bottle Fed", "Utensil Fed"}; // String array for the feeding method picker

    // Feeding method picker min and max values
    int feedingMethodMin = 0, feedingMethodMax = 2;

    // Food amount picker min and max values
    int foodAmountMin = 1, foodAmountMax = 99;

    String MenuName; // String representing the view that the user came here from
    Integer index; // Integer representing the place in the child's session array which houses the session we are viewing/editing

    Child child; // Child var which will hold the child singleton
    FeedingSession mySession; // Feeding session var which will hold the sessio nwe are viewing/editing

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feeding_session_info_menu);

        final Context ctx = this;

        // Hide keyboard initially
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

        // Get the child singleton
        child = Child.getChild(this);

        // Get references to UI elements
        feedingMethodPicker = (NumberPicker) findViewById(R.id.feedingMethodPicker);
        foodTypeTextBox = (EditText) findViewById(R.id.foodTypeTextBox);
        foodAmountPicker = (NumberPicker) findViewById(R.id.foodAmountPicker);
        saveBtn = (Button) findViewById(R.id.saveBtn);

        // Set feeding method picker's vals
        feedingMethodPicker.setMinValue(feedingMethodMin);
        feedingMethodPicker.setMaxValue(feedingMethodMax);
        feedingMethodPicker.setWrapSelectorWheel(false);
        feedingMethodPicker.setDisplayedValues(feedingMethodStringArray);
        feedingMethodPicker.setDescendantFocusability(NumberPicker.FOCUS_BLOCK_DESCENDANTS);

        // Set food amount picker's vals
        foodAmountPicker.setMinValue(foodAmountMin);
        foodAmountPicker.setMaxValue(foodAmountMax);
        foodAmountPicker.setWrapSelectorWheel(false);
        foodAmountPicker.setDescendantFocusability(NumberPicker.FOCUS_BLOCK_DESCENDANTS);

        foodTypeTextBox.setFocusable(false);

        // Get menuname and index from activity that the user came here from
        MenuName = getIntent().getStringExtra("MenuName");
        index = getIntent().getIntExtra("index", 0);

        // Get session that we are editing/viewing
        mySession = (FeedingSession) child.sessionArray.get(index);

        // If feeding method is not empty, then give user defined feeding method to the feeding method picker
        if(!mySession.method.isEmpty())
        {
            switch (mySession.method)
            {
                case "Breast Fed": feedingMethodPicker.setValue(0);
                break;

                case "Bottle Fed": feedingMethodPicker.setValue(1);
                break;

                case "Utensil Fed": feedingMethodPicker.setValue(2);
                break;
            }
        }

        // If food type is not empty, then give user defined food type to the food type text box
        if(!mySession.foodType.isEmpty())
        {
            foodTypeTextBox.setText(mySession.foodType, TextView.BufferType.EDITABLE);
        }

        // If food amount is not equal to 0, then set the food amount picker to this value
        if(mySession.amount != 0)
        {
            foodAmountPicker.setValue(mySession.amount);
        }

        // If food amount is 0, then set food amount picker to this
        else
        {
            foodAmountPicker.setValue(0);
        }

        // When the save button is pressed, "save" the data entries into the session, and go to the next view
        saveBtn.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                // Get current string in feeding method picker, and put it in the feeding method in the session
                Integer val = feedingMethodPicker.getValue();

                mySession.method = feedingMethodStringArray[val];

                // Set food type var in the session
                mySession.foodType = foodTypeTextBox.getText().toString();

                // Set the food amount in the amount var in the session
                mySession.amount = foodAmountPicker.getValue();

                Child.save(ctx);

                // If we came here from the start or end session menu, then transition to the session recording menu
                if(MenuName.equals("StartOrEndSessionMenu"))
                {
                    // Intent made for going to the session recording menu
                    Intent goToSessionRecordingMenuIntent = new Intent(getApplicationContext(), MainActivity.class);

                    startActivity(goToSessionRecordingMenuIntent);
                }

                // If we came here from the session viewing menu, then transition back to the session viewing menu
                else if(MenuName.equals("SessionViewingMenu"))
                {
                    // Intent made for going to the session viewing menu
                    Intent goToSessionViewingMenuIntent = new Intent(getApplicationContext(), MainActivity.class);

                    startActivity(goToSessionViewingMenuIntent);
                }
            }
        });

        foodTypeTextBox.setOnTouchListener(new View.OnTouchListener(){
            @Override
            public boolean onTouch(View v, MotionEvent event){
                foodTypeTextBox.setFocusableInTouchMode(true);

                return false;
            }
        });
    }
}
