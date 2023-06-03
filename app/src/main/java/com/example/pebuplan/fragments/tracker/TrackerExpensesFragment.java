package com.example.pebuplan.fragments.tracker;

import android.content.Context;
import android.content.SharedPreferences;
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
import com.github.mikephil.charting.charts.PieChart;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.w3c.dom.Text;

import java.text.DateFormatSymbols;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

public class TrackerExpensesFragment extends Fragment {


    public TrackerExpensesFragment() {
    }


    AnyChartView pieChartExpense;

    TextView months_expense;
    SharedPreferences.Editor editor;
    SharedPreferences preferences;
    ImageView forward_expense, backward_expense;

    ArrayList<BudgetModel> monthlyBillsArrayList = new ArrayList<>();
    int currentYear;
    int currentMonth;
    int currentDay;
    String selectedDate;
    List<DataEntry> dataEntries;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_expenses, container, false);

        preferences = getActivity().getSharedPreferences("plan", Context.MODE_PRIVATE);
        editor = preferences.edit();
        Calendar calendar = Calendar.getInstance();
        currentYear = calendar.get(Calendar.YEAR);
        currentMonth = calendar.get(Calendar.MONTH)+1;
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

        months_expense = view.findViewById(R.id.timeline_expense);

        backward_expense = view.findViewById(R.id.backward_image_expense);
        forward_expense = view.findViewById(R.id.forward_image_expense);

        months_expense.setText(monthNames[month] + ", " + year);

        backward_expense.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                calendar.add(Calendar.MONTH, -1);
                SimpleDateFormat dateFormat = new SimpleDateFormat("MMMM", Locale.getDefault());
                int month = calendar.get(Calendar.MONTH);
                int year = calendar.get(Calendar.YEAR);
                String[] monthNames = new DateFormatSymbols().getMonths();
                months_expense.setText(monthNames[month] + ", " + year);
                for (int start=1;start<=31;start++){
                    currentDay = start;
                    if (start/10 != 0 || start == 10) {
                        if ((currentMonth+1) / 10 != 0 || (currentMonth+1) == 10){
                            selectedDate = currentYear + "-" + (currentMonth+1) + "-" + start;
                        }else {
                            selectedDate = currentYear + "-" + "0" + (currentMonth+1) + "-" + start;
                        }
                    }else{
                        if ((currentMonth-1) / 10 != 0 || (currentMonth-1) == 10){
                            selectedDate = currentYear + "-" + (currentMonth+1) + "-" + "0" + start;
                        }else {
                            selectedDate = currentYear + "-" + "0" + (currentMonth+1) + "-" + "0" + start;
                        }
                    }
                    if (hashMap.get(selectedDate) != null) {
                        if (monthlyBillsArrayList == null){
                            monthlyBillsArrayList = new ArrayList<>();
                        }
                        monthlyBillsArrayList.addAll(hashMap.get(selectedDate));
                    }
                }
                Pie pieExpense = AnyChart.pie();
                if (dataEntries.size() != 0) {
                    pieExpense.data(dataEntries);
                    pieChartExpense.setChart(pieExpense);
                }
            }
        });

        forward_expense.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                calendar.add(Calendar.MONTH, 1);
                SimpleDateFormat dateFormat = new SimpleDateFormat("MMMM", Locale.getDefault());
                int month = calendar.get(Calendar.MONTH);
                int year = calendar.get(Calendar.YEAR);
                String[] monthNames = new DateFormatSymbols().getMonths();
                months_expense.setText(monthNames[month] + ", " + year);
                for (int start=1;start<=31;start++){
                    currentDay = start;
                    if (start/10 != 0 || start == 10) {
                        if ((currentMonth+1) / 10 != 0 || (currentMonth+1) == 10){
                            selectedDate = currentYear + "-" + (currentMonth+1) + "-" + start;
                        }else {
                            selectedDate = currentYear + "-" + "0" + (currentMonth+1) + "-" + start;
                        }
                    }else{
                        if ((currentMonth+1) / 10 != 0 || (currentMonth+1) == 10){
                            selectedDate = currentYear + "-" + (currentMonth+1) + "-" + "0" + start;
                        }else {
                            selectedDate = currentYear + "-" + "0" + (currentMonth+1) + "-" + "0" + start;
                        }
                    }
                    if (hashMap.get(selectedDate) != null) {
                        if (monthlyBillsArrayList == null){
                            monthlyBillsArrayList = new ArrayList<>();
                        }
                        monthlyBillsArrayList.addAll(hashMap.get(selectedDate));
                    }
                }
                for (int start=0;start<monthlyBillsArrayList.size();start++){
                    if (!monthlyBillsArrayList.get(start).getSpent().equals("")) {
                        dataEntries.add(new ValueDataEntry(monthlyBillsArrayList.get(start).getCategory(), Integer.parseInt(monthlyBillsArrayList.get(start).getSpent())));
                    }
                }
                Pie pieExpense = AnyChart.pie();
                if (dataEntries.size() != 0) {
                    pieExpense.data(dataEntries);
                    pieChartExpense.setChart(pieExpense);
                }
            }
        });
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        pieChartExpense = view.findViewById(R.id.pieChartExpense);

        Pie pieExpense = AnyChart.pie();
        if (dataEntries.size() != 0) {
            pieExpense.data(dataEntries);
            pieChartExpense.setChart(pieExpense);
        }
    }
}