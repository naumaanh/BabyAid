package myaaronbatch.unt.edu.babyapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import AppDataStructures.Settings;

// This base class will set the theme for all of the activities in the entire application (since all of the activities will inherit from this activity)
public class BaseActivity extends AppCompatActivity
{
    Settings settings; // Variable which will hold the settings singleton

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        // Get settings singleton
        settings = Settings.getInstance(this);

        // If theme is set to the dark theme, switch to the dark theme
        if(settings.isDarkTheme)
        {
            setTheme(R.style.Dark_Theme);
        }

        // If theme is set to the light theme, switch to the light theme
        else
        {
            setTheme(R.style.Light_Theme);
        }

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_base);
    }
}
