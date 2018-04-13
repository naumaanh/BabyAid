package myaaronbatch.unt.edu.babyapp;


import android.annotation.SuppressLint;
import android.graphics.pdf.PdfDocument;
import android.provider.MediaStore;
import android.support.v4.print.PrintHelper;
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
import java.util.Objects;

import AppDataStructures.Child;
import AppDataStructures.Session;
import AppDataStructures.Settings;
import AppDataStructures.FeedingSession;
import AppDataStructures.SleepingSession;
import AppDataStructures.WasteSession;
import AppDataStructures.MedicalSession;
import AppDataStructures.medGiven;
import android.graphics.pdf.*;
import android.widget.Toast;
import android.print.pdf.PrintedPdfDocument;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;


import android.app.Activity;
import android.support.v4.content.FileProvider;
import android.graphics.pdf.PdfDocument.Page;
import android.graphics.pdf.PdfDocument.PageInfo;
import android.net.Uri;
import android.print.PrintAttributes;
import android.print.PrintAttributes.Margins;
import android.print.PrintAttributes.Resolution;
import android.print.pdf.PrintedPdfDocument;
import android.support.v4.print.PrintHelper;
import android.widget.RelativeLayout;
import android.provider.MediaStore.Images;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.os.Environment;
import android.widget.ProgressBar;

public class ShareWeeklyReportMenu extends AppCompatActivity {


    Button backButton;
    LinearLayout sessionList;
    LinearLayout.LayoutParams params;
    Settings blank;
    Child kid;
    //share button
    ImageButton shareButton;

    ScrollView scrollpdf; //pdf
    /*
    PdfDocument document = new PdfDocument();
    Canvas canvas1 = page.getCanvas();
    PageInfo pageInfo = new PageInfo.Builder(new Rect(0, 0, 100, 100), 1).create();
    Page page = document.startPage(pageInfo);
    View content = findViewById(R.id.scrollView5);
 //   content.draw(canvas1);
    PrintHelper printHelper = new PrintHelper(this);
    printHelper.setScaleMode(PrintHelper.SCALE_MODE_FIT);
    Bitmap bitmap = ((BitmapDrawable) mImageView.getDrawable()).getBitmap();

    LayoutInflater inflater = (LayoutInflater) this.getSystemService(LAYOUT_INFLATER_SERVICE);
    RelativeLayout root = (RelativeLayout) inflater.inflate(R.layout.activity_share_weekly_report_menu, null); //RelativeLayout is root view of my UI(xml) file.
    root.setDrawingCacheEnabled(true);
    Bitmap screen= Bitmap.createBitmap(scr) getBitmapFromView(this.getWindow().findViewById(R.id.scrollView5)); // here give id of our root layout (here its my RelativeLayout's id)
    */
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


        //controls the on click listener for the share button
        shareButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               // downloadData();
              //  String bitmapPath = Images.Media.insertImage(getContentResolver(), bitmap bitmap,"title", null);
               // Uri bitmapUri = Uri.parse(bitmapPath);

                Intent shareIntent = new Intent(Intent.ACTION_SEND);
                shareIntent.setType("text/plain");
                String shareBody = "I've shared a Weekly Report with you!";
                String shareSubject = "Using the BabyAid app, I've shared data with you";

           //     shareIntent.putExtra(Intent.EXTRA_STREAM, bitmapUri);
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
                                ", and the way it was administered was " + way1 );

                    }
                    sessionList.addView(temp, params);
                    ;
                    break;
                case SLEEPING_SESSION:
                    if (kid.sessionArray.get(i).isFinished)
                    {
                        String allInformationF = "";
                         final String endTime = sdFormat.format(kid.sessionArray.get(i).getEndTime().getTime());

                        temp.setText("Sleeping Session started at " + sdFormat.format(kid.sessionArray.get(i).startTime.getTime())
                                + ", and it ended at "+ endTime + ". ");
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
/*
    private void downloadData() {

        ScrollView iv = (ScrollView) findViewById(R.id.scrollView5);
        Bitmap bitmap = Bitmap.createBitmap(
                iv.getChildAt(0).getWidth()*2,
                iv.getChildAt(0).getHeight()*2,
                Bitmap.Config.ARGB_8888);
        Canvas c = new Canvas(bitmap);
        c.scale(2.0f, 2.0f);
        c.drawColor(getResources().getColor(R.color.colorPrimary));
        iv.getChildAt(0).draw(c);
        // Do whatever you want with your bitmap
        saveBitmap(bitmap);

    }
    public void saveBitmap(Bitmap bitmap) {
        File folder = new File(Environment.getExternalStorageDirectory() +
                File.separator + "BabyApp");
        boolean success = true;
        if (!folder.exists()) {
            success = folder.mkdirs();
        }
        if (success) {
            // Do something on success
        } else {
            // Do something else on failure
        }

        File imagePath = new File(Environment.getExternalStorageDirectory() + "/BabyApp/report.png");

        if (imagePath.exists()) {
            imagePath = new File(Environment.getExternalStorageDirectory() + "/BabyApp/report" + ".png");

        }
        FileOutputStream fos;
        try {
            FileOutputStream out = new FileOutputStream(new File(getFilesDir(), "report.png"));
            imageview.setImageBitmap(b1);
            b1.compress(Bitmap.CompressFormat.PNG, 100, out);
            out.flush();
            out.close();
        }
        catch (Exception e) {}
       /* try {
            fos = new FileOutputStream(imagePath);
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fos);
            fos.flush();
            fos.close();
            //      progressBar.cancel();


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
                }
            });


        }*/
    }




