package myaaronbatch.unt.edu.babyapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class StartOrEndSessionMenu extends AppCompatActivity
{
    Button backBtn;
    Button startOrEndBtn;
    TextView titleTextView;
    TextView testTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start_or_end_session_menu);

        backBtn = (Button) findViewById(R.id.backBtn);
        startOrEndBtn = (Button) findViewById(R.id.startOrEndBtn);
        testTextView = (TextView) findViewById(R.id.testTextView);

        if(getIntent().hasExtra("Session_Type"))
        {
            String test = getIntent().getExtras().getString("Session_Type");

            testTextView.setText(test);
        }

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
