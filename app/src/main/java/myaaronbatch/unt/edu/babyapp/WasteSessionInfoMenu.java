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

import AppDataStructures.Child;
import AppDataStructures.WasteSession;

public class WasteSessionInfoMenu extends BaseActivity {

    NumberPicker wasteTypePicker = null; // Number picker for the waste type method
    EditText wasteColorTextBox; // editable text box for the waste color
    EditText consistencyTextBox; // editable text box for was consistency
    Button saveBtn; // Button var representing the save button on the screen


    String[] wasteTypeStringArray = new String[] {"Solid", "Liquid", "Both"}; // String array for the waste type picker

    // WasteTypePicker method picker min and max values
    int wasteTypeMin = 0, wasteTypeMax = 2;

    String MenuName; // String representing the view that the user came here from
    Integer index; // Integer representing the place in the child's session array which houses the session we are viewing/editing

    Child child; // Child var which will hold the child singleton
    WasteSession mySession; // waste session var which will hold the sessio nwe are viewing/editing

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_waste_session_info_menu);

        // Hide keyboard initially
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

        // Get the child singleton
        child = Child.getChild(this);

        final Context ctx = this;

        // Get references to UI elements
        wasteTypePicker = (NumberPicker) findViewById(R.id.wasteTypePicker);
        wasteColorTextBox = (EditText) findViewById(R.id.wasteColorTextBox);
        consistencyTextBox = (EditText) findViewById(R.id.consistencyTextBox);
        saveBtn = (Button) findViewById(R.id.saveBtn1);

        //set max and min for wasteType picker
        wasteTypePicker.setMaxValue(wasteTypeMax);
        wasteTypePicker.setMinValue(wasteTypeMin);
        wasteTypePicker.setWrapSelectorWheel(false);
        wasteTypePicker.setDisplayedValues(wasteTypeStringArray);
        wasteTypePicker.setDescendantFocusability(NumberPicker.FOCUS_BLOCK_DESCENDANTS);

        wasteColorTextBox.setFocusable(false);
        consistencyTextBox.setFocusable(false);

        // Get menuname and index from activity that the user came here from
        MenuName = getIntent().getStringExtra("MenuName");
        index = getIntent().getIntExtra("index", 0);

        // Get session that we are editing/viewing
        mySession = (WasteSession) child.sessionArray.get(index);

        // If waste type method is not empty, then give user defined waste method to the waste type picker
        if(!mySession.wasteType.isEmpty())
        {
            switch (mySession.wasteType)
            {
                case "Solid": wasteTypePicker.setValue(0);
                    break;

                case "Liquid": wasteTypePicker.setValue(1);
                    break;

                case "Both": wasteTypePicker.setValue(2);
                    break;
            }
        }
        // If waste color type is not empty, then give user defined waste type to the waste color  text box
        if(!mySession.wasteColor.isEmpty())
        {
            wasteColorTextBox.setText(mySession.wasteColor, TextView.BufferType.EDITABLE);
        }
        // If consistency is not empty, then give user defined waste type to the conssitency  text box
        if(!mySession.consistency.isEmpty())
        {
            consistencyTextBox.setText(mySession.consistency, TextView.BufferType.EDITABLE);
        }
        // When the save button is pressed, "save" the data entries into the session, and go to the next view
        saveBtn.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                // Get current string in waste type picker, and put it in the waste method in the session
                Integer val = wasteTypePicker.getValue();

                mySession.wasteType = wasteTypeStringArray[val];

                // Set waste type var in the session
                mySession.wasteColor = wasteColorTextBox.getText().toString();

                // Set waste type var in the session
                mySession.consistency = consistencyTextBox.getText().toString();
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

        wasteColorTextBox.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                wasteColorTextBox.setFocusableInTouchMode(true);
                return false;
            }
        });

        consistencyTextBox.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                consistencyTextBox.setFocusableInTouchMode(true);
                return false;
            }
        });




    }
}
