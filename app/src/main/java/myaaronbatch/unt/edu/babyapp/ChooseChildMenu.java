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
import AppDataStructures.Settings;

public class ChooseChildMenu extends AppCompatActivity {
    Button rmvBtn;
    Button addBtn;
    LinearLayout childList;
    LinearLayout.LayoutParams params;
    Settings options;
    ArrayList<Child> kids;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_child_menu);

        options = Settings.getInstance();
        //Child.addChild("Billy", "Bob", 2017, 10, 14);
        //Child.addChild("Bobby", "Bill", 2017, 10, 14);
        kids = Child.getChildren();
        SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");

        rmvBtn = findViewById(R.id.removeChildBtn);
        addBtn = findViewById(R.id.addChildBtn);
        childList = findViewById(R.id.childlistLayout);
        params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);

        rmvBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), RemoveChildMenu.class);
                startActivity(intent);
            }
        });

        addBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), AddChildMenu.class);
                intent.putExtra("MenuName", "ChoosingChildMenu");
                startActivity(intent);
            }
        });

        for (int i = 0; i < kids.size(); i++)
        {
            Button temp = new Button(this);
            temp.setId(i);
            if (kids.get(i).firstName.isEmpty())
            {
                temp.setText("DEBUG CHILD");
            }
            else
            {
                temp.setText(kids.get(i).firstName + ", born " + sdf.format(kids.get(i).dateOfBirth.getTime()));
            }
            childList.addView(temp, params);
            temp.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    options.childIndex = view.getId();

                    Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                    startActivity(intent);
                }
            });
        }
    }
}