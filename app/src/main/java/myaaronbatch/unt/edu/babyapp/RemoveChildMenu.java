package myaaronbatch.unt.edu.babyapp;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import java.util.ArrayList;

import AppDataStructures.Child;
import AppDataStructures.Settings;

public class RemoveChildMenu extends BaseActivity
{
    ArrayList<Child> children; // Array which will hold all of the children
    Settings settings; // var which will hold the settings singleton

    ArrayList<Boolean> removeList; // Array which will hold an array of booleans (to see which button has been clicked)

    LinearLayout childList; // Layout var which will hold the list of child buttons
    LinearLayout.LayoutParams params; // var which will hold the params for each child button

    Button removeChildBtn; // button which will initiate the deletion of children
    Button exitRemoveChildMenuBtn; // Button which will send the user back to the choose child menu

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_remove_child_menu);

        // Get context
        final Context ctx = this;

        // Connect vars to their UI counterparts

        childList = (LinearLayout) findViewById(R.id.childList);
        params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);

        removeChildBtn = (Button) findViewById(R.id.removeChildBtn);
        exitRemoveChildMenuBtn = (Button) findViewById(R.id.exitRemoveChildMenuBtn);

        children = Child.getChildren(this);
        settings = Settings.getInstance();

        // Init. bool array
        removeList = new ArrayList<Boolean>();

        // Give bool array a size of children.count and init. all of the values to false
        for (int i = 0; i < children.size(); i++)
        {
            removeList.add(Boolean.FALSE);
        }

        // Set up list of children buttons
        for(int i = 0; i < children.size(); i++)
        {
            // Make new button
            Button temp = new Button(this);

            // Set button id
            temp.setId(i);

            // Set text of button to child's name

            if (children.get(i).firstName.isEmpty())
            {
                temp.setText("DEBUG CHILD");
            }

            else
            {
                String str = children.get(i).firstName + " " + children.get(i).lastName;

                temp.setText(str);
            }

            // Add button to childlist layout
            childList.addView(temp, params);

            // Set up on click listener for button
            temp.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View view)
                {
                    int ID = view.getId();

                    // If button is selected, then set the button's color to represent the "unselected" color
                    if(removeList.get(ID))
                    {
                        removeList.set(ID, Boolean.FALSE);

                       if(settings.isDarkTheme)
                       {
                           ((Button)view).setTextColor(Color.WHITE);
                       }
                       else
                       {
                           ((Button)view).setTextColor(Color.BLACK);
                       }
                    }

                    // If button is unselected, then set the button's color to represent the "selected" color
                    else
                    {
                        removeList.set(ID, Boolean.TRUE);

                        ((Button)view).setTextColor(Color.RED);
                    }
                }
            });
        }

        // If remove child button is selected, remove the user's selected children from the system and then go back to the choosing child menu
        removeChildBtn.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                if(!removeList.isEmpty())
                {
                    for (int i = removeList.size() - 1; i >= 0; i--)
                    {
                        if (removeList.get(i))
                        {
                            children.remove(i);

                            settings.numberOfChildren = children.size();

                            Child.save(ctx);
                            Settings.save(ctx);
                        }
                    }
                }

                Intent goToChooseChildMenuIntent = new Intent(getApplicationContext(), ChooseChildMenu.class);

                startActivity(goToChooseChildMenuIntent);
            }
        });

        // When this button is pressed, send the user to the choose child menu without deleting anything
        exitRemoveChildMenuBtn.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                Intent goToChooseChildMenuIntent = new Intent(getApplicationContext(), ChooseChildMenu.class);

                startActivity(goToChooseChildMenuIntent);
            }
        });
    }
}
