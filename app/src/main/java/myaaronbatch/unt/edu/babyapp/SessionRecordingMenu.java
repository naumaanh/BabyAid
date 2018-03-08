package myaaronbatch.unt.edu.babyapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class SessionRecordingMenu extends AppCompatActivity
{
    Button backButton; // Btn which represents the back button on the view
    Button feedingSessionBtn; // Btn which represents the feeding session button on the view
    Button sleepingSessionBtn; // Btn which represents the sleeping session button on the view
    Button wasteSessionBtn; // Btn which represents the waste session button on the view
    Button medicationSessionBtn; // Btn which represents the medication session button on the view

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_session_recording_menu);

        // Get references to buttons on the activity view
        backButton = (Button) findViewById(R.id.backBtn);
        feedingSessionBtn = (Button) findViewById(R.id.feedingSessionBtn);
        sleepingSessionBtn = (Button) findViewById(R.id.sleepingSessionBtn);
        wasteSessionBtn = (Button) findViewById(R.id.wasteSessionBtn);
        medicationSessionBtn = (Button) findViewById(R.id.medicationSessionBtn);

        // Create intent for going to the StartOrEndSessionMenu
        final Intent goToStartEndSessionMenuIntent = new Intent(getApplicationContext(), StartOrEndSessionMenu.class);

        // When backButton is tapped, send the user to the previous view that they were on
        backButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                finish();
            }
        });

        // When the feedingSessionBtn is tapped, send the user to the Start/End session menu
        feedingSessionBtn.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                // Send what type of session the user chose to the next menu
                goToStartEndSessionMenuIntent.putExtra("Session_Type", "Feeding Session");

                // Go to the menu the user chose
                startActivity(goToStartEndSessionMenuIntent);
            }
        });

        // When the sleepingSessionBtn is tapped, send the user to the Start/End session menu
        sleepingSessionBtn.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                // Send what type of session the user chose to the next menu
                goToStartEndSessionMenuIntent.putExtra("Session_Type", "Sleeping Session");

                // Go to the menu the user chose
                startActivity(goToStartEndSessionMenuIntent);
            }
        });

        // When the wasteSessionBtn is tapped, send the user to the Start/End session menu
        wasteSessionBtn.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                // Send what type of session the user chose to the next menu
                goToStartEndSessionMenuIntent.putExtra("Session_Type", "Waste Session");

                // Go to the menu the user chose
                startActivity(goToStartEndSessionMenuIntent);
            }
        });

        // When the sleepingSessionBtn is tapped, send the user to the Start/End session menu
        medicationSessionBtn.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                // Send what type of session the user chose to the next menu
                goToStartEndSessionMenuIntent.putExtra("Session_Type", "Medication Session");

                // Go to the menu the user chose
                startActivity(goToStartEndSessionMenuIntent);
            }
        });
    }
}
