package com.example.pebuplan.fragments.tracker;

import static com.facebook.appevents.UserDataStore.clear;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.anychart.AnyChart;
import com.anychart.AnyChartView;
import com.anychart.chart.common.dataentry.DataEntry;
import com.anychart.chart.common.dataentry.ValueDataEntry;
import com.anychart.charts.Pie;
import com.example.pebuplan.R;
import com.example.pebuplan.model.BudgetModel;
import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.PieChart;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.text.DateFormatSymbols;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Objects;


public class TrackerIncome extends Fragment {

    public TrackerIncome() {

    }
    TextView months_income;

    AnyChartView pieChartIncome;
    ImageView forward_income, backward_income;
    ArrayList<BudgetModel> monthlyBillsArrayList = new ArrayList<>();
    int currentYear;
    int currentMonth;
    int currentDay;
    String selectedDate;
    List<DataEntry> dataEntries;
    SharedPreferences.Editor editor;
    SharedPreferences preferences;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_tracker_income, container, false);

        TextView salary = view.findViewById(R.id.salary_text);

        preferences = getActivity().getSharedPreferences("plan", Context.MODE_PRIVATE);
        editor = preferences.edit();
        Calendar calendar = Calendar.getInstance();
        currentYear = calendar.get(Calendar.YEAR);
        currentMonth = calendar.get(Calendar.MONTH)+1;
        String income = preferences.getString("Income", "");
        salary.setText(income);
        Gson gson = new Gson();
        String storedHashMapString = preferences.getString("DayData", "oopsDintWork");
        java.lang.reflect.Type type = new TypeToken<HashMap<String, ArrayList<BudgetModel>>>() {
        }.getType();
        HashMap<String, ArrayList<BudgetModel>> hashMap = gson.fromJson(storedHashMapString, type);

        for (int start=1;start<=31;start++){
            currentDay = start;
            if (start/10 != 0 || start == 10) {
                if (currentMonth / 10 != 0 || currentMonth == 10){
                    selectedDate = currentYear + "-" + currentMonth + "-" + start;
                }else {
                    selectedDate = currentYear + "-" + "0" + currentMonth + "-" + start;
                }
            }else{
                if (currentMonth / 10 != 0 || currentMonth == 10){
                    selectedDate = currentYear + "-" + currentMonth + "-" + "0" + start;
                }else {
                    selectedDate = currentYear + "-" + "0" + currentMonth + "-" + "0" + start;
                }
            }
            if (hashMap.get(selectedDate) != null) {
                if (monthlyBillsArrayList == null){
                    monthlyBillsArrayList = new ArrayList<>();
                }
                monthlyBillsArrayList.addAll(hashMap.get(selectedDate));
            }
        }

        dataEntries = new ArrayList<>();

        for (int start=0;start<monthlyBillsArrayList.size();start++){
            if (!monthlyBillsArrayList.get(start).getSpent().equals("")) {
                dataEntries.add(new ValueDataEntry(monthlyBillsArrayList.get(start).getCategory(), Integer.parseInt(monthlyBillsArrayList.get(start).getSpent())));
            }
        }
        SimpleDateFormat dateFormat = new SimpleDateFormat("MMMM", Locale.getDefault());

        int month = calendar.get(Calendar.MONTH);
        int year = calendar.get(Calendar.YEAR);

        String[] monthNames = new DateFormatSymbols().getMonths();

        months_income = view.findViewById(R.id.timeline_income);

        backward_income = view.findViewById(R.id.backward_image_income);
        forward_income = view.findViewById(R.id.forward_image_income);

        months_income.setText(monthNames[month]+", "+year);

        backward_income.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                calendar.add(Calendar.MONTH, -1);
                SimpleDateFormat dateFormat = new SimpleDateFormat("MMMM", Locale.getDefault());
                int month = calendar.get(Calendar.MONTH);
                int year = calendar.get(Calendar.YEAR);
                String[] monthNames = new DateFormatSymbols().getMonths();
                months_income.setText(monthNames[month]+", "+year);
            }
        });

        forward_income.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                calendar.add(Calendar.MONTH, 1);
                SimpleDateFormat dateFormat = new SimpleDateFormat("MMMM", Locale.getDefault());
                int month = calendar.get(Calendar.MONTH);
                int year = calendar.get(Calendar.YEAR);
                String[] monthNames = new DateFormatSymbols().getMonths();
                months_income.setText(monthNames[month]+", "+year);
            }
        });
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        pieChartIncome = view.findViewById(R.id.pieChartIncome);

        Pie pieIncome = AnyChart.pie();
        if (dataEntries.size() != 0) {
            pieIncome.data(dataEntries);
            pieChartIncome.setChart(pieIncome);
        }
    }
}