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
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


import AppDataStructures.Child;
import AppDataStructures.Session;
import AppDataStructures.Settings;
import AppDataStructures.FeedingSession;
import AppDataStructures.SleepingSession;
import AppDataStructures.WasteSession;
import AppDataStructures.MedicalSession;
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
    int maxLength = 100;
    int kjf = 0;
    int kjw = 0;
    int kjm = 0;
    int kjmol = 0;
    int kjmi = 0;
    int kjmop = 0;
    int kjms = 0;
    String emptyChecker = "";
    final String[] wasteTypeAn = new String[maxLength];
    final String[] wasteColorAn = new String[maxLength];
    final String[] wasteConsistAn = new String[maxLength];

    final String[] medTypeAn = new String[maxLength];
    final Integer[] medDosageAn = new Integer[maxLength];
    final String[] medWayAn = new String[maxLength];

    final String[] foodTypeAn = new String[maxLength];
    final Integer[] foodAmountAn = new Integer[maxLength];
    final String[] foodMethodAn = new String[maxLength];

    final String[] sleepAmountAn = new String[maxLength];

    int medWOLCounter =0, medWOPCounte=0, medWICounte=0, medWSCounte=0;
    final String[] medWOL = new String[maxLength]; //oral liquid
    final Integer[] medWOLDosage = new Integer[maxLength]; //oral liquid
    final String[] medWOLType = new String[maxLength]; //oral liquid


    final String[] medWOP = new String[maxLength]; //oral pill
    final Integer[] medWOPosage = new Integer[maxLength]; //oral pill
    final String[] medWOPType = new String[maxLength]; //oral pill


    final String[] medWI = new String[maxLength]; //injection
    final Integer[] medWIDosage = new Integer[maxLength]; //injection
    final String[] medWIType = new String[maxLength]; //injection


    final String[] medWS = new String[maxLength]; //suppository
    final Integer[] medWSDosage = new Integer[maxLength]; //suppository
    final String[] medWSType = new String[maxLength]; //suppository


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

        HashMap<String, Integer> testMap = new HashMap<String, Integer>();


        for (int i = 0; i < kid.sessionArray.size(); i++) {
            // for (int i = kid.sessionArray.size() - 1; i >= 0; i--) {
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

                        WasteSession myws = (WasteSession) kid.sessionArray.get(i);
                        wastecolor1 = myws.wasteColor;
                        wastetype1 = myws.wasteType;
                        consistency1 = myws.consistency;

                        wasteTypeAn[kjw] = wastetype1;
                        wasteColorAn[kjw] = wastecolor1;
                        wasteConsistAn[kjw] = consistency1;

                        if (wastecolor1.equals(emptyChecker))
                            wastecolor1 = "User Did not Enter";
                        if (consistency1.equals(emptyChecker))
                            consistency1 = "User Did not Enter";

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
                        kjw = kjw + 1;
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

                    medDosageAn[kjm] = dosage1;
                    medTypeAn[kjm] = type1;

                    if (myms.way.equals(medGiven.ORAL_LIQUID))
                    {
                        medWOLCounter++;
                        medWOLDosage[kjmol] = dosage1;
                        medWOLType[kjmol] = type1;
                        kjmol = kjmol + 1;
                    }
                    else if (myms.way.equals(medGiven.INJECTION))
                    {       medWICounte++;
                        medWIDosage[kjmi] = dosage1;
                        medWIType[kjmi] = type1;
                        kjmi = kjmi + 1;
                    }

                    else if (myms.way.equals(medGiven.ORAL_PILL))
                    {       medWOPCounte++;
                        medWOPosage[kjmop] = dosage1;
                        medWOPType[kjmop] = type1;
                        kjmop = kjmop + 1;
                    }
                    else if (myms.way.equals(medGiven.SUPPOSITORY))
                    {       medWSCounte++;
                        medWSDosage[kjms] = dosage1;
                        medWSType[kjms] = type1;
                        kjms = kjms + 1;
                    }

                        if (type1.equals(emptyChecker))
                            type1 = "User Did not Enter";

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
                        kjm = kjm + 1;
                    }
                    sessionList.addView(temp, params);
                    break;
                case SLEEPING_SESSION:
                    if (kid.sessionArray.get(i).isFinished) {
                        final String endTime = sdFormat.format(kid.sessionArray.get(i).getEndTime().getTime());
                        final String startTime = sdFormat.format(kid.sessionArray.get(i).startTime.getTime());


                        // Date date1 = sdFormat.parse(startTime);
                        //    long tot =
                        //   Calendar start = Calendar.getInstance();
                        //   start.setTime(date1);

                        //   sleepAmountAn[i] = sdFormat.format(kid.sessionArray.get(i).startTime.getTime() - endTime);
                        temp.setText("Sleeping Session start time:  " + sdFormat.format(kid.sessionArray.get(i).startTime.getTime())
                                + "\n End time: " + endTime);

                        String sleepDummy = ("Sleeping Session start time:  " + sdFormat.format(kid.sessionArray.get(i).startTime.getTime())
                                + "\n End time: " + endTime);

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

                        foodTypeAn[kjf] = foodtype1;
                        foodAmountAn[kjf] = amount1;
                        foodMethodAn[kjf] = method1;

                        if (method1.equals(emptyChecker))
                            method1 = "User Did not Enter";
                        if (foodtype1.equals(emptyChecker))
                            foodtype1 = "User Did not Enter";

                        temp.setText("Feeding Session start time: " + sdFormat.format(kid.sessionArray.get(i).startTime.getTime()) +
                                "\n Method: " + method1 +
                                "\n Food Type: " + foodtype1 +
                                "\n Amount: " + amount1);

                        String foodDummy = ("Feeding Session start time: " + sdFormat.format(kid.sessionArray.get(i).startTime.getTime()) +
                                "\n Method: " + method1 +
                                "\n Food Type: " + foodtype1 +
                                "\n Amount: " + amount1);

                        allInfo = allInfo + System.getProperty("line.separator") + foodDummy + System.getProperty("line.separator");
                        totalFeeding = totalFeeding + 1;
                        kjf = kjf + 1;

                    }
                    sessionList.addView(temp, params);
                    break;
            }
            //controls the on click listener for the share button
            shareButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    StringBuilder body = new StringBuilder();

                    //feeding number total sessions number
                    /*
                    is all working
                    */
                    body.append("The total number of Feeding Sessions logged was " + totalFeeding);
                    body.append("\nThe total number of Sleeping Sessions logged was " + totalSleeping);
                    body.append("\nThe total number of Medical Sessions logged was " + totalMedical);
                    body.append("\nThe total number of Waste Sessions logged was " + totalWaste + "\n");

                    //analytics - averages
             //       body.append(Arrays.toString(medWOLDosage));///foodMethodAn));//foodAmountAn.toString());
                    body.append("\nThe average amounts analytics are as follows: ");
                    if (foodAmountAn[0] == null)
                        body.append("\nThere was not enough data logged to get an average food amount. ");
                    else {
                        body.append("\nFood Amount: " + average(foodAmountAn));
                    }
                    if (medDosageAn[0] == null)
                        body.append("\nThere was not enough data logged to get an average medicine dosage. ");
                    else {
                        body.append("\nMedicine Dosage: " + average(medDosageAn));
                    }
                    body.append("\n");

                    //analytics - medical
                    /////////////////////
                    //////////////////////
                    body.append("\nThe most common response for the various medical sessions are as follows: " + "\n");
                    //injection
                    body.append("\nInjection");
                    body.append("\nDosage: ");
                    if (medWIDosage[0] != null){
                        body.append(analyticsNumbers(medWIDosage).getKey()
                                + ", and it was logged "
                                + analyticsNumbers(medWIDosage).getValue()
                                + " times. ");}
                    else {
                        body.append(" No meaningful data logged. ");

                    }
                    body.append("\nAverage Dosage: ");
                    if (medWIDosage[0] == null)
                        body.append(" No meaningful data logged. ");
                    else {
                        body.append(average(medWIDosage));
                    }
                    /*
                    body.append("\nMedication Name: ");
                    if (medWIType[0] == null)
                        body.append(" No meaningful data logged. ");
                    else {
                        body.append(analytics(medWIType).getKey()
                                + ". ");
                    }
                    */
                    ///////////
                    //////////
                    body.append("\nOral Liquid");
                    body.append("\nDosage: ");
                    if (medWOLDosage[0] == null)
                        body.append(" No meaningful data logged. ");
                    else {
                        body.append(analyticsNumbers(medWOLDosage).getKey()
                                + ", and it was logged "
                                + analyticsNumbers(medWOLDosage).getValue()
                                + " times. ");
                    }
                    body.append("\nAverage Dosage: ");
                    if (medWOLDosage[0] == null)
                        body.append(" No meaningful data logged. ");
                    else {
                        body.append(average(medWOLDosage));
                    }
                    /*
                    body.append("\nMedication Name: ");
                    if (medWOLType[0] == null)
                        body.append(" No meaningful data logged. ");
                    else {
                        body.append(analytics(medWOLType).getKey()
                                + ". ");
                    }
*/
                    //////
                    /////
                    body.append("\nOral Pill");
                    body.append("\nDosage: ");
                    if (medWOPosage[0] == null)
                        body.append(" No meaningful data logged. ");
                    else {
                        body.append(analyticsNumbers(medWOPosage).getKey()
                                + ", and it was logged "
                                + analyticsNumbers(medWOPosage).getValue()
                                + " times. ");
                    }
                    body.append("\nAverage Dosage: ");
                    if (medWOPosage[0] == null)
                        body.append(" No meaningful data logged. ");
                    else {
                        body.append(average(medWOPosage));
                    }
                    /*
                    body.append("\nMedication Name: ");
                    if (medWOLType[0] == null)
                        body.append(" No meaningful data logged. ");
                    else {
                        body.append(analytics(medWOLType).getKey()
                                + ". ");
                    }
                    */
                    /////
                    /////
                    body.append("\nSuppository");
                    body.append("\nDosage: ");
                    if (medWSDosage[0] == null)
                        body.append(" No meaningful data logged. ");
                    else {
                        body.append(analyticsNumbers(medWSDosage).getKey()
                                + ", and it was logged "
                                + analyticsNumbers(medWSDosage).getValue()
                                + " times. ");
                    }
                    body.append("\nAverage Dosage: ");
                    if (medWSDosage[0] == null)
                        body.append(" No meaningful data logged. ");
                    else {
                        body.append(average(medWSDosage));
                    }
                    /*
                    body.append("\nMedication Name: ");
                    if (medWSType[0] == null)
                        body.append(" No meaningful data logged. ");
                    else {
                        body.append(analytics(medWSType).getKey()
                                + ". " + "\n");
                    }
*/
                    //////////////////////
                    ////////////////////
                    //overall
                    //most logged
                    body.append("\n\nThe most logged analytics are as follows: ");

                    if (wasteTypeAn[0] == null)
                        body.append("\nWaste Type: No meaningful data logged. ");
                    else {
                        body.append("\nWaste Type: " + analytics(wasteTypeAn).getKey()
                                + ", and it was logged "
                                + analytics(wasteTypeAn).getValue()
                                + " times. ");
                    }
                    if (wasteColorAn[0] == null)
                        body.append("\nWaste Color: No meaningful data logged. ");
                    else {
                        body.append("\nWaste Color: " + analytics(wasteColorAn).getKey()
                                + ", and it was logged "
                                + analytics(wasteColorAn).getValue()
                                + " times. ");
                    }
                    if (wasteConsistAn[0] == null)
                        body.append("\nWaste Consistency: No meaningful data logged. ");
                    else {
                        body.append("\nWaste Consistency " + analytics(wasteConsistAn).getKey()
                                + ", and it was logged "
                                + analytics(wasteConsistAn).getValue()
                                + " times. ");
                    }

                    if (foodAmountAn[0] == null)
                        body.append("\nFood Amount: No meaningful data logged. ");
                    else {
                        body.append("\nFood Amount: " + analyticsNumbers(foodAmountAn).getKey()
                                + ", and it was logged "
                                + analyticsNumbers(foodAmountAn).getValue()
                                + " times. ");
                    }
                    if (foodMethodAn[0] == null)
                        body.append("\nFood Method: No meaningful data logged. ");
                    else {
                        body.append("\nFood Method " + analytics(foodMethodAn).getKey()
                                + ", and it was logged "
                                + analytics(foodMethodAn).getValue()
                                + " times. " + );
                    }
                    if (foodTypeAn[0] == null )
                        body.append("\nFood Type: No meaningful data logged. ");
                    else {
                        body.append("\nFood Type " + analytics(foodTypeAn).getKey()
                                + ", and it was logged "
                                + analytics(foodTypeAn).getValue()
                                + " times. " + "\n\n\n");
                    }


                    /*
                    if (medDosageAn[0] == null)
                        body.append("\nMedicine Dosage: No meaningful data logged. ");
                    else {
                        body.append("\nMedicine Dosage " + analyticsNumbers(medDosageAn).getKey()
                                + ", and it was logged "
                                + analyticsNumbers(medDosageAn).getValue()
                                + " times. ");
                    }
                    if (medTypeAn[0] == null)
                        body.append("\nMedicine Type: No meaningful data logged. ");
                    else {
                        body.append("\nMedicine Type " + analytics(medTypeAn).getKey()
                                + ", and it was logged "
                                + analytics(medTypeAn).getValue()
                                + " times. ");
                    }
                    */
                    body.append("Session information is below:\n " );
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
    public final Map.Entry<String, Integer> analytics(final String[] practice2) {
        /*
        if (practice2[1] == null)
        {
        Map<String, Integer> nullhelp = new HashMap<>();
        nullhelp.put("hi im joe", 0);
        Map.Entry<String, Integer> woohoo = null;
            for (Map.Entry<String, Integer> entry : nullhelp.entrySet())
            {
                woohoo = entry;
            }
            final Map.Entry<String, Integer> testi = woohoo;
            return woohoo;
        }
        */

        int total = 0;
        for (int y = 0; y < maxLength; y++) {
            if (practice2[0] == null) {
                total = 0;
            } else if (practice2[y] != null) {
                total = y + 1;
            }
            y++;
        }
        final String[] helper = new String[total];
        for (int s = 0; s < total; s++) {
            helper[s] = practice2[s];
        }
        final Map<String, Integer> map = new HashMap();
        for (String x : helper) {
            if (!map.containsKey(x)) {
                map.put(x, 1);
            } else {
                map.put(x, map.get(x) + 1);
            }
        }
        Map.Entry<String, Integer> maxEntry = null;
        for (Map.Entry<String, Integer> entry : map.entrySet()) {
            if (maxEntry == null || entry.getValue().compareTo(maxEntry.getValue()) >= 0) {
                maxEntry = entry;
            }
        }
        final Map.Entry<String, Integer> help = maxEntry;
        return help;
    }
    public final Map.Entry<Integer, Integer> analyticsNumbers(final Integer[] practice2)
    {
        int total = 0;
        for (int y = 0; y < maxLength; y++) {
            if (practice2[0] == null)
            {
                total = 0;
            }
            else if (practice2[y] != null)
            {
                total = total + 1;
            }
          //  y++;
        }
        final Integer[] helper = new Integer[total];
        for (int s = 0; s < total; s++) {
            helper[s] = practice2[s];
        }
        final Map<Integer, Integer> map = new HashMap();
        for (Integer x : helper) {
            if (!map.containsKey(x)) {
                map.put(x, 1);
            } else {
                map.put(x, map.get(x) + 1);
            }
        }
        Map.Entry<Integer, Integer> maxEntry = null;
        for (Map.Entry<Integer, Integer> entry : map.entrySet()) {
            if (maxEntry == null || entry.getValue().compareTo(maxEntry.getValue()) > 0) {
                maxEntry = entry;
            }
        }
        final Map.Entry<Integer, Integer> help = maxEntry;
        return help;
    }
    public final double average(final Integer[] practice){
        double total = 0;
        double amount = 0;
        double average1 = 0.0;
        for (int i = 0; i < maxLength; i++){
            if (practice[i] != null){
                total = total + practice[i];
                amount++;
            }
        }
        if (practice[1] != null) {
            average1 = total / amount;
            return average1;
        }
        else
            average1 = total;
        return average1;
    }
    public final double medaverage(final Integer[] practice, Integer total){
        double amount = 0;
        double average1 = 0.0;
        for (int i = 0; i < maxLength; i++){
            if (practice[i] != null){
                total = total + practice[i];
                amount++;
            }
        }
        if (practice[1] != null) {
            average1 = total / amount;
            return average1;
        }
        else
            average1 = total;
        return average1;
    }
}




