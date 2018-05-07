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
import AppDataStructures.MedicalSession;
import AppDataStructures.medGiven;

public class MedicalSessionInfoMenu extends BaseActivity {

    NumberPicker dosagePicker = null;
    EditText medTypeText = null;
    NumberPicker wayPicker = null;
    Button saveMed;

    String [] wayArr = new String[] {"Liquid", "Pill", "Suppository", "Injection"};
    medGiven [] givenArr = new medGiven[] {medGiven.ORAL_LIQUID, medGiven.ORAL_PILL, medGiven.SUPPOSITORY, medGiven.INJECTION};

    String menuName;
    int index;
    Child child;
    MedicalSession thisSession;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_medical_session_info_menu);

        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

        final Context ctx = this;

        child = Child.getChild(this);

        dosagePicker = (NumberPicker)findViewById(R.id.dosagePickr);
        wayPicker = (NumberPicker)findViewById(R.id.wayPickr);
        medTypeText = (EditText)findViewById(R.id.editText3);
        saveMed = (Button)findViewById(R.id.saveMedInfo);

        dosagePicker.setMinValue(0);
        dosagePicker.setMaxValue(99);
        dosagePicker.setWrapSelectorWheel(false);
        dosagePicker.setDescendantFocusability(NumberPicker.FOCUS_BLOCK_DESCENDANTS);

        wayPicker.setMinValue(0);
        wayPicker.setMaxValue(3);
        wayPicker.setWrapSelectorWheel(false);
        wayPicker.setDisplayedValues(wayArr);
        wayPicker.setDescendantFocusability(NumberPicker.FOCUS_BLOCK_DESCENDANTS);

        medTypeText.setFocusable(false);

        menuName = getIntent().getStringExtra("MenuName");
        index = getIntent().getIntExtra("index", 0);

        thisSession = (MedicalSession)child.sessionArray.get(index);

        if(!(thisSession.way == null))
        {
            switch (thisSession.way)
            {
                case ORAL_LIQUID: wayPicker.setValue(0);
                    break;

                case ORAL_PILL: wayPicker.setValue(1);
                    break;

                case SUPPOSITORY: wayPicker.setValue(2);
                    break;

                case INJECTION: wayPicker.setValue(3);
                    break;
            }
        }

        if (!thisSession.type.isEmpty())
        {
            medTypeText.setText(thisSession.type, TextView.BufferType.EDITABLE);
        }

        if (thisSession.dosage != 0)
        {
            dosagePicker.setValue(thisSession.dosage);
        }
        else
        {
            dosagePicker.setValue(0);
        }

        saveMed.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                thisSession.dosage = dosagePicker.getValue();

                thisSession.way = givenArr[wayPicker.getValue()];

                thisSession.type = medTypeText.getText().toString();

                Child.save(ctx);

                if(menuName.equals("StartOrEndSessionMenu"))
                {
                    // Intent made for going to the session recording menu
                    Intent goToSessionRecordingMenuIntent = new Intent(getApplicationContext(), MainActivity.class);

                    startActivity(goToSessionRecordingMenuIntent);
                }

                // If we came here from the session viewing menu, then transition back to the session viewing menu
                else if(menuName.equals("SessionViewingMenu"))
                {
                    // Intent made for going to the session viewing menu
                    Intent goToSessionViewingMenuIntent = new Intent(getApplicationContext(), MainActivity.class);

                    startActivity(goToSessionViewingMenuIntent);
                }
            }
        });

        medTypeText.setOnTouchListener(new View.OnTouchListener()
        {
            @Override
            public boolean onTouch(View v, MotionEvent event){
                medTypeText.setFocusableInTouchMode(true);

                return false;
            }
        });
    }
}
