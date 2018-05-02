package myaaronbatch.unt.edu.babyapp;


import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.pdf.PdfDocument;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ScrollView;

import java.text.SimpleDateFormat;

import AppDataStructures.Child;
import AppDataStructures.FeedingSession;
import AppDataStructures.MedicalSession;
import AppDataStructures.Settings;
import AppDataStructures.WasteSession;
import AppDataStructures.medGiven;


public class ShareWeeklyReportMenu extends BaseActivity {


    Button backButton;
    LinearLayout sessionList;
    LinearLayout.LayoutParams params;
    Settings blank;
    Child kid;
    //share button
    ImageButton shareButton;

    ScrollView scrollpdf; //pdf
    private static final String TAG = "ShareWeeklyReport";
    PdfDocument document = new PdfDocument();
    String allInfo = "";
    int totalFeeding = 0;
    int totalSleeping = 0;
    int totalMedical = 0;
    int totalWaste = 0;

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
        scrollpdf = (ScrollView) findViewById(R.id.scrollView5);
        //share button
        shareButton = (ImageButton) findViewById(R.id.shareButton);
        sessionList = (LinearLayout) findViewById(R.id.sessionList);
        params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
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

                        String wasteDummy = ("Waste Session at " + sdFormat.format(kid.sessionArray.get(i).startTime.getTime()) +
                                ". The color of the waste was " + wastecolor1 + ", the type of waste was " + wastetype1 +
                                ", and the consistency was " + consistency1 + ". ");

                        allInfo = allInfo + System.getProperty("line.separator") + wasteDummy + System.getProperty("line.separator");
                        totalWaste = totalWaste + 1;


                    }
                    sessionList.addView(temp, params);
                    ;
                    break;
                case MEDICATION_SESSION:
                    if (kid.sessionArray.get(i).isFinished)
                    {
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
                                ", and the way it was administered was " + way1 );

                        String medicalDummy = ("Medical Session at " + sdFormat.format(kid.sessionArray.get(i).startTime.getTime()) +
                                ". The type of medication was " + type1 + ", the dosage was " + dosage1 +
                                ", and the way it was administered was " + way1 );

                        allInfo = allInfo + System.getProperty("line.separator") + medicalDummy + System.getProperty("line.separator");
                        totalMedical = totalMedical + 1;
                    }
                    sessionList.addView(temp, params);
                    ;
                    break;
                case SLEEPING_SESSION:
                    if (kid.sessionArray.get(i).isFinished)
                    {
                        final String endTime = sdFormat.format(kid.sessionArray.get(i).getEndTime().getTime());

                        temp.setText("Sleeping Session started at " + sdFormat.format(kid.sessionArray.get(i).startTime.getTime())
                                + ", and it ended at "+ endTime + ". ");

                        String sleepDummy = ("Sleeping Session started at " + sdFormat.format(kid.sessionArray.get(i).startTime.getTime())
                                + ", and it ended at "+ endTime + ". ");

                        allInfo = allInfo + System.getProperty("line.separator") + sleepDummy + System.getProperty("line.separator");
                        totalSleeping = totalSleeping + 1;
                    }
                    sessionList.addView(temp, params);
                    ;
                    break;
                case FEEDING_SESSION:
                    if (kid.sessionArray.get(i).isFinished)
                    {
                        String method1;
                        String foodtype1;
                        int amount1;


                        FeedingSession myfs = (FeedingSession) kid.sessionArray.get(i);
                        amount1 = myfs.amount;
                        method1 = myfs.method;
                        foodtype1 = myfs.foodType;

                        temp.setText("Feeding Session at " + sdFormat.format(kid.sessionArray.get(i).startTime.getTime()) +
                                    ". The method it was consumed was by " + method1 + ", the food type was " + foodtype1 +
                                    ", and the amount consumed was " + amount1 + ". ");

                        String foodDummy = ("Feeding Session at " + sdFormat.format(kid.sessionArray.get(i).startTime.getTime()) +
                                ". The method it was consumed was by " + method1 + ", the food type was " + foodtype1 +
                                ", and the amount consumed was " + amount1 + ". ");

                        allInfo = allInfo + System.getProperty("line.separator") + foodDummy + System.getProperty("line.separator");
                        totalFeeding = totalFeeding + 1;

                    }
                    sessionList.addView(temp, params);
                    ;
                    break;
            }

            //controls the on click listener for the share button
            shareButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    StringBuilder body = new StringBuilder();
                    body.append("The total number of Feeding Sessions logged was " + totalFeeding);
                    body.append("\nThe total number of Sleeping Sessions logged was " + totalSleeping);
                    body.append("\nThe total number of Medical Sessions logged was " + totalMedical);
                    body.append("\nThe total number of Waste Sessions logged was " + totalWaste + "\n");
                    body.append(allInfo.toString());

                    Intent i = new Intent(Intent.ACTION_SEND);
                    i.setType("message/rfc822");
                    i.putExtra(Intent.EXTRA_SUBJECT, "BabyApp Details");
                    i.putExtra(Intent.EXTRA_TEXT, body.toString());
                    startActivity(Intent.createChooser(i, "Share details..."));
                }
            });
        }
    }

    }




