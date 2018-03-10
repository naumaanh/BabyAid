package myaaronbatch.unt.edu.babyapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import java.util.ArrayList;
import AppDataStructures.Session;

public class SessionViewingMenu extends AppCompatActivity
{
    ArrayList<Session> dummy;
    ArrayList<Button> buttons;
    Button backButton;
    LinearLayout sessionList;
    LinearLayout.LayoutParams params;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_session_viewing_menu);

        backButton = (Button) findViewById(R.id.backButton);
        sessionList = (LinearLayout) findViewById(R.id.sessionList);
        params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);

        final Intent goToStartEndSessionMenuIntent = new Intent(getApplicationContext(), StartOrEndSessionMenu.class);
        final Intent goToSessionInformationMenuIntent = new Intent(getApplicationContext(), SessionInformationMenu.class);

        for (int i = 0; i < dummy.size(); i++)
        {
            buttons.add(i, new Button(this));
            buttons.get(i).setId(i);
            switch (dummy.get(i).sType)
            {
                case WASTE: buttons.get(i).setText("Waste Session " + dummy.get(i).StartTime.getTime());
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
                case MEDICAL: buttons.get(i).setText("Medicine Session " + dummy.get(i).StartTime.getTime());
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
                case SLEEPING: buttons.get(i).setText("Sleeping Session " + dummy.get(i).StartTime.getTime());
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
                case FEEDING: buttons.get(i).setText("Feeding Session " + dummy.get(i).StartTime.getTime());
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
