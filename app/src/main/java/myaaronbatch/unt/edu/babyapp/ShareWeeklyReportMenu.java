package myaaronbatch.unt.edu.babyapp;


import android.annotation.SuppressLint;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.view.View;
import android.content.Intent;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ShareActionProvider;
import java.text.SimpleDateFormat;
import java.util.Objects;

import AppDataStructures.Child;
import AppDataStructures.Session;
import AppDataStructures.Settings;
import AppDataStructures.FeedingSession;
import AppDataStructures.SleepingSession;
import AppDataStructures.WasteSession;
import AppDataStructures.MedicalSession;
import AppDataStructures.medGiven;



public class ShareWeeklyReportMenu extends AppCompatActivity {


    Button backButton;
    LinearLayout sessionList;
    LinearLayout.LayoutParams params;
    Settings blank;
    Child kid;
    //share button
    ImageButton shareButton;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_share_weekly_report_menu);

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
        //share button
        shareButton = (ImageButton) findViewById(R.id.shareButton);
        sessionList = (LinearLayout) findViewById(R.id.sessionList);
        params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);

        //controls the on click listener for the share button
        shareButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent shareIntent = new Intent(Intent.ACTION_SEND);
                shareIntent.setType("text/plain");
                String shareBody = "I've shared a Weekly Report with you!";
                String shareSubject = "Using the BabyAid app, I've shared data with you";
                shareIntent.putExtra(Intent.EXTRA_SUBJECT, shareSubject);
                shareIntent.putExtra(Intent.EXTRA_TEXT, shareBody);
                startActivity(Intent.createChooser(shareIntent, "Share data from BabyAid using"));
            }
        });
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        for (int i = kid.sessionArray.size() - 1; i >= 0; i--)
        {
            Button temp = new Button(this);
            //set button background to white so it blends in
            temp.setBackgroundColor(0xFFFFFF);
            temp.setId(i);
            switch (kid.sessionArray.get(i).sessionType)
            {
                case WASTE_SESSION:
                    if (kid.sessionArray.get(i).isFinished)
                    {
                        String allInformationF = "";
                        String wastetype1;
                        String wastecolor1;
                        String consistency1;

                        WasteSession myws;
                        myws = (WasteSession) kid.sessionArray.get(i);
                        wastecolor1 = myws.wasteColor;
                        wastetype1 = myws.wasteType;
                        consistency1 = myws.consistency;

                        temp.setText("Waste Session at " + sdFormat.format(kid.sessionArray.get(i).startTime.getTime()) +
                                ". The color of the waste was " + wastecolor1 + ", the type of waste was " + wastetype1 +
                                ", and the consistency was " + consistency1 + ". ");
                    }
                    sessionList.addView(temp, params);
                    ;
                    break;
                case MEDICATION_SESSION:
                    if (kid.sessionArray.get(i).isFinished)
                    {
                        String allInformationF = "";
                        String type1;
                        int dosage1;
                        medGiven way1;

                        MedicalSession myms;
                        myms = (MedicalSession) kid.sessionArray.get(i);
                        type1 = myms.type;
                        dosage1 = myms.dosage;
                        way1 = myms.way;

                        temp.setText("Medical Session at " + sdFormat.format(kid.sessionArray.get(i).startTime.getTime()) +
                                ". The type of medication was " + type1 + ", the dosage was " + dosage1 +
                                ", and the way it was administered was " );

                    }
                    sessionList.addView(temp, params);
                    ;
                    break;
                case SLEEPING_SESSION:
                    if (kid.sessionArray.get(i).isFinished)
                    {
                        String allInformationF = "";
                        /*
                        String type1;
                        String dosage1;
                        medGiven way1;

                        SleepingSession myss;
                        myss = (SleepingSession) kid.sessionArray.get(i);
                        type1 = myss.type;
                        dosage1 = myss.dosage;
                        way1 = myss.way;

                        */
                        /* final String endTime = sdFormat.format(kid.sessionArray.get(i).getEndTime()); */

                        temp.setText("Sleeping Session started at " + sdFormat.format(kid.sessionArray.get(i).startTime.getTime()));
                    }
                    sessionList.addView(temp, params);
                    ;
                    break;
                case FEEDING_SESSION:
                    if (kid.sessionArray.get(i).isFinished)
                    {
                        String allInformationF = "";
                        String method1;
                        String foodtype1;
                        int amount1;

                        FeedingSession myfs = (FeedingSession) kid.sessionArray.get(i);
                        amount1 = myfs.amount;
                        method1 = myfs.method;
                        foodtype1 = myfs.foodType;

                        if (Objects.equals(foodtype1, "")) {
                            temp.setText("Feeding Session at " + sdFormat.format(kid.sessionArray.get(i).startTime.getTime()) +
                                    ". The method it was consumed was by " + method1 + ", and the amount consumed was " + amount1 + ". ");
                        } else {
                            temp.setText("Feeding Session at " + sdFormat.format(kid.sessionArray.get(i).startTime.getTime()) +
                                    ". The method it was consumed was by " + method1 + ", the food type was " + foodtype1 +
                                    ", and the amount consumed was " + amount1 + ". ");
                        }
                    }
                    sessionList.addView(temp, params);
                    ;
                    break;
            }
        }
    }
}