package com.example.pebuplan.fragments.monthlybudget;

import static com.facebook.appevents.UserDataStore.clear;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.pebuplan.R;

import android.content.Context;
import android.content.SharedPreferences;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.pebuplan.adapter.MonthlyBudgetAdapter;
import com.example.pebuplan.model.BudgetModel;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.text.DateFormat;
import java.text.DateFormatSymbols;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;

public class MonthlyBudgetFragment extends Fragment {
    Calendar calendar = Calendar.getInstance();
    SimpleDateFormat dateFormat = new SimpleDateFormat("MMMM", Locale.getDefault());

    TextView months;

    ImageView forward, backward;

    RecyclerView budget_rec_view_month;
    ArrayList<BudgetModel> monthlyBillsArrayList = new ArrayList<>();

    ImageView fab_month;

    EditText incomeInput;
    MonthlyBudgetAdapter adapter_month;
    SharedPreferences.Editor editor;
    SharedPreferences preferences;
    int currentYear;
    int currentMonth;
    int currentDay;
    String selectedDate;
    TextView totalBudget, totalSpent;
    HashMap<String, ArrayList<BudgetModel>> hashMap = new HashMap<>();

    public MonthlyBudgetFragment() {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        preferences = getActivity().getSharedPreferences("plan", Context.MODE_PRIVATE);
        editor = preferences.edit();

        currentYear = calendar.get(Calendar.YEAR);
        currentMonth = calendar.get(Calendar.MONTH)+1;

        Gson gson = new Gson();
        String storedHashMapString = preferences.getString("DayData", "oopsDintWork");
        if (!storedHashMapString.equals("oopsDintWork")){
            java.lang.reflect.Type type = new TypeToken<HashMap<String, ArrayList<BudgetModel>>>(){}.getType();
            hashMap = gson.fromJson(storedHashMapString, type);
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
        }

        int month = calendar.get(Calendar.MONTH);
        int year = calendar.get(Calendar.YEAR);
        String[] monthNames = new DateFormatSymbols().getMonths();
        View view = inflater.inflate(R.layout.fragment_year, container, false);

        months = view.findViewById(R.id.timeline);
        backward = view.findViewById(R.id.backward_image);
        forward = view.findViewById(R.id.forward_image);

        months.setText(monthNames[month]);


        backward.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                monthlyBillsArrayList.clear();
                calendar.add(Calendar.MONTH, -1);
                SimpleDateFormat dateFormat = new SimpleDateFormat("MMMM", Locale.getDefault());
                currentMonth = calendar.get(Calendar.MONTH);
                String[] monthNames = new DateFormatSymbols().getMonths();
                months.setText(monthNames[currentMonth]);
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
                adapter_month.updateRecyclerView(monthlyBillsArrayList);
            }
        });

        forward.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                monthlyBillsArrayList.clear();
                calendar.add(Calendar.MONTH, 1);
                SimpleDateFormat dateFormat = new SimpleDateFormat("MMMM", Locale.getDefault());
                currentMonth = calendar.get(Calendar.MONTH);
                String[] monthNames = new DateFormatSymbols().getMonths();
                months.setText(monthNames[currentMonth]);
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
                adapter_month.updateRecyclerView(monthlyBillsArrayList);
            }
        });

        incomeInput = view.findViewById(R.id.incomeinput);
        String income = incomeInput.getText().toString();
        editor.putString("Income",income);
        editor.commit();

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if (monthlyBillsArrayList == null){
            monthlyBillsArrayList = new ArrayList<>();
        }
        adapter_month = new MonthlyBudgetAdapter(monthlyBillsArrayList);
        budget_rec_view_month = view.findViewById(R.id.rec_view_budget_month);
        budget_rec_view_month.setLayoutManager(new LinearLayoutManager(requireContext()));
        budget_rec_view_month.setAdapter(adapter_month);

        totalBudget = view.findViewById(R.id.budget_total_month);
        totalSpent = view.findViewById(R.id.spents_total_month);
        int sumOfBudget = 0;
        int sumOfSpent = 0;
        for (int start=0;start<monthlyBillsArrayList.size();start++){
            sumOfBudget += Integer.parseInt(monthlyBillsArrayList.get(start).getBudget());
            sumOfSpent += Integer.parseInt(monthlyBillsArrayList.get(start).getSpent());
        }
        totalBudget.setText(String.valueOf(sumOfBudget));
        totalSpent.setText(String.valueOf(sumOfSpent));
    }
}