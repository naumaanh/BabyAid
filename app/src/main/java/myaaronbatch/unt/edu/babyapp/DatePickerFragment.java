package myaaronbatch.unt.edu.babyapp;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;

import java.util.Calendar;

/**
 * Created by aaronbatch on 4/8/18.
 */

// Class representing a datepicker fragment
public class DatePickerFragment extends DialogFragment
{
    Calendar tempCal = Calendar.getInstance(); // temporary calendar variable we will use in the OnCreate method

    int year = tempCal.get(Calendar.YEAR); // integer year (four digit value)
    int month = tempCal.get(Calendar.MONTH); // integer month (starts from zero)
    int day = tempCal.get(Calendar.DAY_OF_MONTH); // integer day of month

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState)
    {
        // Return a date picker dialog with current date
        return new DatePickerDialog(getActivity(), (DatePickerDialog.OnDateSetListener) getActivity(), year, month, day);
    }
}
