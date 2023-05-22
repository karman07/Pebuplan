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

import org.w3c.dom.Text;

import java.text.DateFormatSymbols;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

public class TrackerExpensesFragment extends Fragment {

    List<TextView> textViewList = new ArrayList<>();


    public TrackerExpensesFragment() {
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_expenses, container, false);

        TextView food = view.findViewById(R.id.salary_text);
        TextView transport = view.findViewById(R.id.transport_text);
        TextView housing = view.findViewById(R.id.housing_text);
        TextView utilities = view.findViewById(R.id.utilities_text);
        TextView insurance = view.findViewById(R.id.insuarance_text);

        textViewList.addAll(Arrays.asList(food,transport, housing,utilities,insurance));

        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat dateFormat = new SimpleDateFormat("MMMM", Locale.getDefault());
        int month = calendar.get(Calendar.MONTH);

        int year = calendar.get(Calendar.YEAR);

        int day = calendar.get(Calendar.DAY_OF_MONTH);

        String[] monthNames = new DateFormatSymbols().getMonths();


        SharedPreferences sharedPref = requireActivity().getSharedPreferences("plan", Context.MODE_PRIVATE);

        String value = sharedPref.getString(String.valueOf(day) + "_spent", "0,0,0,0,0");

        String[] numberList = value.split(", ");

        for (int i = 0; i < textViewList.size(); i++) {
            TextView textView = textViewList.get(i);
            String number = numberList[i];
            textView.setText("₱" + String.valueOf(number));
        }


        return view;
    }

}