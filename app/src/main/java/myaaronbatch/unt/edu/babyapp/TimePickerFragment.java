package myaaronbatch.unt.edu.babyapp;

import android.app.Dialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;

import java.util.Calendar;

import AppDataStructures.Settings;

/**
 * Created by aaronbatch on 4/11/18.
 */

// Class representing a timepicker fragment
public class TimePickerFragment extends DialogFragment
{
    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState)
    {
        // Create a temp calendar var with current time
        Calendar tempCal = Calendar.getInstance();

        // Store current time values into int variables
        int hour = tempCal.get(Calendar.HOUR_OF_DAY);
        int minute = tempCal.get(Calendar.MINUTE);

        // Get an instance of the settings singleton
        Settings tempSettings = Settings.getInstance();

        // Return a time picker dialog with current time
        return new TimePickerDialog(getActivity(), (TimePickerDialog.OnTimeSetListener) getActivity(), hour, minute, tempSettings.is24HTime);
    }
}
