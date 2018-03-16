package myaaronbatch.unt.edu.babyapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

// Class which represents the "Start/End Session Menu"
public class StartOrEndSessionMenu extends AppCompatActivity
{
    Button backBtn; // Button which will take the user to the previous screen
    Button startOrEndBtn; // Button which will start a session or end a session (depending on what it is displaying at that time)
    TextView titleTextView; // Textview which represents the title of the activity
    TextView testTextView; // Textview which exists for testing purposes
    String sessionType; // String representing what kind of session the user would like to potentially start or end
    Boolean startPushed = false; // Boolean representing whether the user has pressed
    String menuName; // String representing the name if the menu that the user came from before they got to this menu
    Intent sentIntent;

    Integer index;

    // On activity creation, set up all of the buttons, textviews, and other vital elements of the activity
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start_or_end_session_menu);

        // Get references to the UI elements on the activity
        backBtn = (Button) findViewById(R.id.backBtn);
        startOrEndBtn = (Button) findViewById(R.id.startOrEndBtn);
        testTextView = (TextView) findViewById(R.id.testTextView);

        if(getIntent().hasExtra("MenuName"))
        {
            menuName = getIntent().getExtras().getString("MenuName");
        }

        if(menuName == "SessionRecordingMenu")
        {
            startPushed = false;

            // If the previous view has sent us some information under the data-title "Session_Type", then use that info to find out what type of session the user
            // wanted to start
            if (getIntent().hasExtra("Session_Type"))
            {
                // Get intent
                sentIntent = getIntent();

                // Get the session type that was sent in the string message
                sessionType = sentIntent.getExtras().getString("Session_Type");

                testTextView.setText(sessionType);
            }

        }

        else if(menuName == "SessionViewingMenu")
        {
            if(getIntent().hasExtra("index"))
            {
                sentIntent = getIntent();

                index = sentIntent.getExtras().getInt("index");
            }

            //if(getIntent().hasExtra(""))
        }

        // If the back button is selected, then go to the previous view that the user was on
        backBtn.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                finish();
            }
        });


    }
}
