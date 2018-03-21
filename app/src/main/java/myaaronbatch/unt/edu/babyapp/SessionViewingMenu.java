package myaaronbatch.unt.edu.babyapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

import AppDataStructures.Child;
import AppDataStructures.MedicalSession;
import AppDataStructures.Session;
import AppDataStructures.Settings;

public class SessionViewingMenu extends AppCompatActivity
{
    Button backButton;
    LinearLayout sessionList;
    LinearLayout.LayoutParams params;
    Settings blank;
    Child kid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_session_viewing_menu);

        blank = Settings.getInstance();
        kid = Child.getChild();
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

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        final Intent goToStartEndSessionMenuIntent = new Intent(getApplicationContext(), StartOrEndSessionMenu.class);
        final Intent goToSessionInformationMenuIntent = new Intent(getApplicationContext(), SessionInformationMenu.class);

        for (int i = kid.sessionArray.size() - 1; i >= 0; i--)
        {
            Button temp = new Button(this);
            temp.setId(i);
            switch (kid.sessionArray.get(i).sessionType)
            {
                case WASTE_SESSION:
                    if (kid.sessionArray.get(i).isFinished)
                    {
                        temp.setText("Waste Session " + sdFormat.format(kid.sessionArray.get(i).startTime.getTime()) + " FINISHED");
                    }
                    else
                    {
                        temp.setText("Waste Session " + sdFormat.format(kid.sessionArray.get(i).startTime.getTime()) + " ONGOING");
                    }
                    sessionList.addView(temp, params);
                    temp.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            if (kid.sessionArray.get(view.getId()).isFinished)
                            {
                                goToSessionInformationMenuIntent.putExtra("index", view.getId());
                                startActivity(goToSessionInformationMenuIntent);
                            }
                            else
                            {
                                goToStartEndSessionMenuIntent.putExtra("SessionType","Waste");
                                goToStartEndSessionMenuIntent.putExtra("MenuName", "SessionViewingMenu");
                                goToStartEndSessionMenuIntent.putExtra("index", view.getId());
                                startActivity(goToStartEndSessionMenuIntent);
                            }
                        }
                    });
                    break;
                case MEDICATION_SESSION:
                    if (kid.sessionArray.get(i).isFinished)
                    {
                        temp.setText("Medicine Session " + sdFormat.format(kid.sessionArray.get(i).startTime.getTime()) + " FINISHED");
                    }
                    else
                    {
                        temp.setText("Medicine Session " + sdFormat.format(kid.sessionArray.get(i).startTime.getTime()) + " ONGOING");
                    }
                    sessionList.addView(temp, params);
                    temp.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            if (kid.sessionArray.get(view.getId()).isFinished)
                            {
                                goToSessionInformationMenuIntent.putExtra("index", view.getId());
                                startActivity(goToSessionInformationMenuIntent);
                            }
                            else
                            {
                                goToStartEndSessionMenuIntent.putExtra("SessionType","Medication");
                                goToStartEndSessionMenuIntent.putExtra("MenuName", "SessionViewingMenu");
                                goToStartEndSessionMenuIntent.putExtra("index", view.getId());
                                startActivity(goToStartEndSessionMenuIntent);
                            }
                        }
                    });
                    break;
                case SLEEPING_SESSION:
                    if (kid.sessionArray.get(i).isFinished)
                    {
                        temp.setText("Sleeping Session " + sdFormat.format(kid.sessionArray.get(i).startTime.getTime()) + " FINISHED");
                    }
                    else
                    {
                        temp.setText("Sleeping Session " + sdFormat.format(kid.sessionArray.get(i).startTime.getTime()) + " ONGOING");
                    }
                    sessionList.addView(temp, params);
                    temp.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            if (kid.sessionArray.get(view.getId()).isFinished)
                            {
                                goToSessionInformationMenuIntent.putExtra("index", view.getId());
                                startActivity(goToSessionInformationMenuIntent);
                            }
                            else
                            {
                                goToStartEndSessionMenuIntent.putExtra("SessionType","Sleeping");
                                goToStartEndSessionMenuIntent.putExtra("MenuName", "SessionViewingMenu");
                                goToStartEndSessionMenuIntent.putExtra("index", view.getId());
                                startActivity(goToStartEndSessionMenuIntent);
                            }
                        }
                    });
                    break;
                case FEEDING_SESSION:
                    if (kid.sessionArray.get(i).isFinished)
                    {
                        temp.setText("Feeding Session " + sdFormat.format(kid.sessionArray.get(i).startTime.getTime()) + " FINISHED");
                    }
                    else
                    {
                        temp.setText("Feeding Session " + sdFormat.format(kid.sessionArray.get(i).startTime.getTime()) + " ONGOING");
                    }
                    sessionList.addView(temp, params);
                    temp.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            if (kid.sessionArray.get(view.getId()).isFinished)
                            {
                                goToSessionInformationMenuIntent.putExtra("index", view.getId());
                                startActivity(goToSessionInformationMenuIntent);
                            }
                            else
                            {
                                goToStartEndSessionMenuIntent.putExtra("SessionType","Feeding");
                                goToStartEndSessionMenuIntent.putExtra("MenuName", "SessionViewingMenu");
                                goToStartEndSessionMenuIntent.putExtra("index", view.getId());
                                startActivity(goToStartEndSessionMenuIntent);
                            }
                        }
                    });
                    break;
            }
        }
    }
}