package myaaronbatch.unt.edu.babyapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import AppDataStructures.Session;
import AppDataStructures.Settings;

public class SessionViewingMenu extends AppCompatActivity
{
    ArrayList<Session> dummy;
    ArrayList<Button> buttons;
    Button backButton;
    LinearLayout sessionList;
    LinearLayout.LayoutParams params;
    Settings blank;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_session_viewing_menu);

        SimpleDateFormat sdFormat;
        if (blank.is24HTime)
        {
            sdFormat = new SimpleDateFormat("HH:mm MM/dd");
        }
        else
        {
            sdFormat = new SimpleDateFormat("hh:mm a MM/dd");
        }

        backButton = (Button) findViewById(R.id.backButton);
        sessionList = (LinearLayout) findViewById(R.id.sessionList);
        params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);

        final Intent goToStartEndSessionMenuIntent = new Intent(getApplicationContext(), StartOrEndSessionMenu.class);
        final Intent goToSessionInformationMenuIntent = new Intent(getApplicationContext(), SessionInformationMenu.class);

        for (int i = dummy.size() - 1; i >= 0; i++)
        {
            buttons.add(i, new Button(this));
            buttons.get(i).setId(i);
            switch (dummy.get(i).sType)
            {
                case WASTE:
                    if (dummy.get(i).isFinished)
                    {
                        buttons.get(i).setText("Waste Session " + sdFormat.format(dummy.get(i).StartTime.getTime()) + " FINISHED");
                    }
                    else
                    {
                        buttons.get(i).setText("Waste Session " + sdFormat.format(dummy.get(i).StartTime.getTime()) + " ONGOING");
                    }
                    sessionList.addView(buttons.get(i), params);
                    buttons.get(i).setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            if (dummy.get(view.getId()).isFinished)
                            {
                                goToSessionInformationMenuIntent.putExtra("index", view.getId());
                                startActivity(goToSessionInformationMenuIntent);
                            }
                            else
                            {
                                goToStartEndSessionMenuIntent.putExtra("Session_Type", "Waste_IP " + view.getId());
                                startActivity(goToStartEndSessionMenuIntent);
                            }
                        }
                    });
                    break;
                case MEDICAL:
                    if (dummy.get(i).isFinished)
                    {
                        buttons.get(i).setText("Medicine Session " + sdFormat.format(dummy.get(i).StartTime.getTime()) + " FINISHED");
                    }
                    else
                    {
                        buttons.get(i).setText("Medicine Session " + sdFormat.format(dummy.get(i).StartTime.getTime()) + " ONGOING");
                    }
                    sessionList.addView(buttons.get(i), params);
                    buttons.get(i).setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            if (dummy.get(view.getId()).isFinished)
                            {
                                goToSessionInformationMenuIntent.putExtra("index", view.getId());
                                startActivity(goToSessionInformationMenuIntent);
                            }
                            else
                            {
                                goToStartEndSessionMenuIntent.putExtra("Session_Type", "Med_IP " + view.getId());
                                startActivity(goToStartEndSessionMenuIntent);
                            }
                        }
                    });
                    break;
                case SLEEPING:
                    if (dummy.get(i).isFinished)
                    {
                        buttons.get(i).setText("Sleeping Session " + sdFormat.format(dummy.get(i).StartTime.getTime()) + " FINISHED");
                    }
                    else
                    {
                        buttons.get(i).setText("Sleeping Session " + sdFormat.format(dummy.get(i).StartTime.getTime()) + " ONGOING");
                    }
                    sessionList.addView(buttons.get(i), params);
                    buttons.get(i).setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            if (dummy.get(view.getId()).isFinished)
                            {
                                goToSessionInformationMenuIntent.putExtra("index", view.getId());
                                startActivity(goToSessionInformationMenuIntent);
                            }
                            else
                            {
                                goToStartEndSessionMenuIntent.putExtra("Session_Type", "Sleep_IP " + view.getId());
                                startActivity(goToStartEndSessionMenuIntent);
                            }
                        }
                    });
                    break;
                case FEEDING:
                    if (dummy.get(i).isFinished)
                    {
                        buttons.get(i).setText("Feeding Session " + sdFormat.format(dummy.get(i).StartTime.getTime()) + " FINISHED");
                    }
                    else
                    {
                        buttons.get(i).setText("Feeding Session " + sdFormat.format(dummy.get(i).StartTime.getTime()) + " ONGOING");
                    }
                    sessionList.addView(buttons.get(i), params);
                    buttons.get(i).setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            if (dummy.get(view.getId()).isFinished)
                            {
                                goToSessionInformationMenuIntent.putExtra("index", view.getId());
                                startActivity(goToSessionInformationMenuIntent);
                            }
                            else
                            {
                                goToStartEndSessionMenuIntent.putExtra("Session_Type", "Feed_IP " + view.getId());
                                startActivity(goToStartEndSessionMenuIntent);
                            }
                        }
                    });
                    break;
            }
        }
    }
}