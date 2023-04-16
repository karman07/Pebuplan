package com.example.pebuplan.fragments.tracker;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.pebuplan.R;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;


public class TrackerIncome extends Fragment {

    public TrackerIncome() {

    }



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_tracker_income, container, false);

        TextView salary = view.findViewById(R.id.salary_text);

        SharedPreferences sharedPref = getActivity().getSharedPreferences("plan", Context.MODE_PRIVATE);

        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat dateFormat = new SimpleDateFormat("MMMM", Locale.getDefault());
        String month = dateFormat.format(calendar.getTime());
        int year = calendar.get(Calendar.YEAR);

        String value = sharedPref.getString(month + "\t1-30\t" + year + "_income", "0" );
        salary.setText("₱" + value);
        Log.d("why ? ", value);
        return view;
    }
}