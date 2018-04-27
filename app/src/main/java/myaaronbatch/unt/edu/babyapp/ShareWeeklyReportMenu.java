package myaaronbatch.unt.edu.babyapp;


import android.annotation.SuppressLint;
import android.graphics.pdf.PdfDocument;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.view.View;
import android.content.Intent;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.ShareActionProvider;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Collections;
import java.util.Objects;
import java.util.Comparator;
import java.sql.Wrapper;
import java.util.stream.Collectors;
import static java.util.stream.Collectors.groupingBy;
import static java.util.stream.Collectors.mapping;
import static java.util.stream.Collectors.toList;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

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

    ScrollView scrollpdf; //pdf
    private static final String TAG = "ShareWeeklyReport";
    PdfDocument document = new PdfDocument();
    String allInfo = "";
    int totalFeeding = 0;
    int totalSleeping = 0;
    int totalMedical = 0;
    int totalWaste = 0;
    int maxLength = 100;
    String[] wasteTypeMode = new String[maxLength];
    String[] wasteColorMode = new String[maxLength];
    String[] wasteConsistencyMode = new String[maxLength];

    String[] medTypeMode = new String[maxLength];
    String[] medWayMode = new String[maxLength];
    int[] medDosageMode = new int[maxLength];
    String emptyChecker = "";

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

        backButton = findViewById(R.id.backButton);
        scrollpdf = findViewById(R.id.scrollView5);
        //share button
        shareButton = findViewById(R.id.shareButton);
        sessionList = findViewById(R.id.sessionList);
        params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        for (int i = kid.sessionArray.size() - 1; i >= 0; i--) {
            Button temp = new Button(this);
            //set button background to white so it blends in
            temp.setBackgroundColor(0xFFFFFF);
            temp.setId(i);
            switch (kid.sessionArray.get(i).sessionType) {
                case WASTE_SESSION:
                    if (kid.sessionArray.get(i).isFinished) {
                        String wastetype1;
                        String wastecolor1;
                        String consistency1;

                        WasteSession myws;
                        myws = (WasteSession) kid.sessionArray.get(i);
                        wastecolor1 = myws.wasteColor;
                        wastetype1 = myws.wasteType;
                        consistency1 = myws.consistency;
                        if (wastecolor1.equals(emptyChecker))
                            wastecolor1 = "User Did not Enter";
                        if (consistency1.equals(emptyChecker))
                            consistency1 = "User Did not Enter";

                        wasteTypeMode[i] = wastetype1;
                        wasteColorMode[i] = wastecolor1;
                        wasteConsistencyMode[i] = consistency1;

                        temp.setText("Waste Session start time: " + sdFormat.format(kid.sessionArray.get(i).startTime.getTime()) +
                                "\nWaste Color: " + wastecolor1 +
                                "\n Waste Type: " + wastetype1 +
                                "\nWaste Consistency: " + consistency1);

                        String wasteDummy = ("Waste Session start time: " + sdFormat.format(kid.sessionArray.get(i).startTime.getTime()) +
                                "\nWaste Color: " + wastecolor1 +
                                "\n Waste Type: " + wastetype1 +
                                "\nWaste Consistency: " + consistency1);

                        allInfo = allInfo + System.getProperty("line.separator") + wasteDummy + System.getProperty("line.separator");
                        totalWaste = totalWaste + 1;

                    }
                    sessionList.addView(temp, params);
                    break;
                case MEDICATION_SESSION:
                    if (kid.sessionArray.get(i).isFinished) {
                        String type1;
                        int dosage1;
                        medGiven way1;

                        MedicalSession myms;
                        myms = (MedicalSession) kid.sessionArray.get(i);
                        type1 = myms.type;
                        dosage1 = myms.dosage;
                        way1 = myms.way;

                        if (type1.equals(emptyChecker))
                            type1 = "User Did not Enter";

                        medDosageMode[i] = dosage1;
                        medTypeMode[i] = type1;
                        //medWayMode[i] = way1;

                        temp.setText("Medical Session start time: " + sdFormat.format(kid.sessionArray.get(i).startTime.getTime()) +
                                "\n Medication Type: " + type1 +
                                "\n Dosage: " + dosage1 +
                                "\n Way Given: " + way1);

                        String medicalDummy = ("Medical Session start time: " + sdFormat.format(kid.sessionArray.get(i).startTime.getTime()) +
                                "\n Medication Type: " + type1 +
                                "\n Dosage: " + dosage1 +
                                "\n Way Given: " + way1);

                        allInfo = allInfo + System.getProperty("line.separator") + medicalDummy + System.getProperty("line.separator");
                        totalMedical = totalMedical + 1;
                    }
                    sessionList.addView(temp, params);
                    break;
                case SLEEPING_SESSION:
                    if (kid.sessionArray.get(i).isFinished) {
                        final String endTime = sdFormat.format(kid.sessionArray.get(i).getEndTime().getTime());

                        temp.setText("Sleeping Session started at " + sdFormat.format(kid.sessionArray.get(i).startTime.getTime())
                                + ", and it ended at " + endTime + ". ");

                        String sleepDummy = ("Sleeping Session started at " + sdFormat.format(kid.sessionArray.get(i).startTime.getTime())
                                + ", and it ended at " + endTime + ". ");

                        allInfo = allInfo + System.getProperty("line.separator") + sleepDummy + System.getProperty("line.separator");
                        totalSleeping = totalSleeping + 1;
                    }
                    sessionList.addView(temp, params);
                    break;
                case FEEDING_SESSION:
                    if (kid.sessionArray.get(i).isFinished) {
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
                    break;
            }

            final String[] practice = new String[maxLength];

            practice[0] = "blue";
            practice[1] = "red";
            practice[2] = "orange";
            practice[3] = "blue";
            practice[4] = "blue";
            practice[5] = "black";
            practice[6] = "red";
            practice[7] = "red";
            practice[8] = "blue";


           Arrays.sort(practice, new Comparator<String>() {
                @Override
                public int compare(String o1, String o2) {
                    if (o1 == null && o2 == null) {
                        return 0;
                    }
                    if (o1 == null) {
                        return 1;
                    }
                    if (o2 == null) {
                        return -1;
                    }
                    return o1.compareTo(o2);
                }
            });
            int total = 0;
            for (int y=0;y<maxLength;y++)
            {
                if (practice[0] == null)
                {
                    total = 0;
                }
                else if (practice[y] != null)
                {
                    total = y+1;
                }
                y++;
            }

            final String[] helper = new String[total];
            for (int s=0;s<total;s++)
            {
                helper[s] = practice[s];
            }

            final Map<String, Integer> map = new HashMap();


            for (String x:helper){

                if (!map.containsKey(x)){
                    map.put(x,1);
                }
                else{
                    map.put(x, map.get(x)+1);
                }
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
                    body.append("\n" + helper);
                    body.append("]n\n" + map);
                    body.append("\nThe most common recorded answer for waste color was " + Arrays.toString(helper));
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




