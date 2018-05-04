package myaaronbatch.unt.edu.babyapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import AppDataStructures.Child;

public class MainActivity extends AppCompatActivity
{
    TextView titleTextView; // Textview which represents the title of the activity
    TextView subtitleTextView; // Textview which represents the subtitle of the activity

    Button recordASessionBtn; // Button which if pressed will take the user to the Session Recording Menu
    Button viewAllSessionsBtn; // button which if pressed will take the user to the session viewing menu
    Button shareWeeklyReportBtn; // button which if pressed will take the user to the weekly report menu
    Button optionsBtn; // button which if pressed will take the user to the options menu
    Button returnBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initialize the child singleton object
        Child.getChild();

        // Get references to UI elements
        titleTextView = (TextView) findViewById(R.id.titleTextView);
        subtitleTextView = (TextView) findViewById(R.id.subtitleTextView);
        recordASessionBtn = (Button) findViewById(R.id.recordASessionBtn);
        viewAllSessionsBtn = (Button) findViewById(R.id.viewAllSessionsBtn);
        shareWeeklyReportBtn = (Button) findViewById(R.id.shareWeeklyReportBtn);
        optionsBtn = (Button) findViewById(R.id.optionsBtn);
        returnBtn = findViewById(R.id.rtnBtn);

        // If the recordASessionBtn is pushed, then send the user to the SessionRecordingMenu
        recordASessionBtn.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                Intent intent = new Intent(getApplicationContext(), SessionRecordingMenu.class);

                startActivity(intent);
            }
        });

        // If the viewAllSessionsBtn is pushed, then send the user to the SessionViewingMenu
        viewAllSessionsBtn.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                Intent intent = new Intent(getApplicationContext(), SessionViewingMenu.class);

                startActivity(intent);
            }
        });

        // If the shareWeeklyReportBtn is pushed, then send the user to the ShareWeeklyReportMenu
        shareWeeklyReportBtn.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                Intent intent = new Intent(getApplicationContext(), ShareWeeklyReportMenu.class);

                startActivity(intent);
            }
        });

        // If the optionsBtn is pushed, then send the user to the OptionsMenu
        optionsBtn.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                Intent intent = new Intent(getApplicationContext(), OptionsMenu.class);

                startActivity(intent);
            }
        });

        returnBtn.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                Intent intent = new Intent(getApplicationContext(), ChooseChildMenu.class);

                startActivity(intent);
            }
        });
    }
}
