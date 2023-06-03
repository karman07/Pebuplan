package com.example.pebuplan.fragments.monthlybudget;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.pebuplan.R;
import com.example.pebuplan.adapter.MonthlyBudgetAdapter;
import com.example.pebuplan.model.BudgetModel;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;

public class WeeklyBudget extends Fragment {


    TextView curr_month1, curr_month2, curr_month3, curr_month4;
    String current_month;
    LinearLayout ll_week1, ll_week2, ll_week3, ll_week4;
    View select_view1, select_view2, select_view3, select_view4;

    RecyclerView budget_rec_view_week;
    ImageView fab;

    MonthlyBudgetAdapter adapter_week;
    SharedPreferences.Editor editor;
    SharedPreferences preferences;
    int currentDay;
    int currentMonth;
    int currentYear;
    ArrayList<BudgetModel> weeklyBillArrayList = new ArrayList<>();


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        View view = inflater.inflate(R.layout.fragment_monthly_budget, container, false);
        preferences = getActivity().getSharedPreferences("plan", Context.MODE_PRIVATE);
        editor = preferences.edit();

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Calendar calendar = Calendar.getInstance();
        currentYear = calendar.get(Calendar.YEAR);
        currentMonth = calendar.get(Calendar.MONTH) + 1;

        Gson gson = new Gson();
        String storedHashMapString = preferences.getString("DayData", "oopsDintWork");
        java.lang.reflect.Type type = new TypeToken<HashMap<String, ArrayList<BudgetModel>>>(){}.getType();
        HashMap<String, ArrayList<BudgetModel>> hashMap = gson.fromJson(storedHashMapString, type);

        DateFormat dateFormat = new SimpleDateFormat("MMMM");
        Date date = new Date();
        for (int start=1;start<=7;start++){
            currentDay = start;
            String selectedDate = currentYear + "-" + "0" + currentMonth + "-" + "0" + start;
            if (hashMap.get(selectedDate) != null) {
                if (weeklyBillArrayList == null){
                    weeklyBillArrayList = new ArrayList<>();
                }
                weeklyBillArrayList.addAll(hashMap.get(selectedDate));
            }
        }

        current_month = dateFormat.format(date);

        curr_month1 = view.findViewById(R.id.curr_month1);
        curr_month2 = view.findViewById(R.id.curr_month2);
        curr_month3 = view.findViewById(R.id.curr_month3);
        curr_month4 = view.findViewById(R.id.curr_month4);

        curr_month1.setText(current_month);
        curr_month2.setText(current_month);
        curr_month3.setText(current_month);
        curr_month4.setText(current_month);

        ll_week1 = view.findViewById(R.id.ll_week1);
        ll_week2 = view.findViewById(R.id.ll_week2);
        ll_week3 = view.findViewById(R.id.ll_week3);
        ll_week4 = view.findViewById(R.id.ll_week4);

        select_view1 = view.findViewById(R.id.select_Week1);
        select_view2 = view.findViewById(R.id.select_Week2);
        select_view3 = view.findViewById(R.id.select_Week3);
        select_view4 = view.findViewById(R.id.select_Week4);

        ll_week1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                select_view1.setVisibility(View.VISIBLE);
                select_view2.setVisibility(View.INVISIBLE);
                select_view3.setVisibility(View.INVISIBLE);
                select_view4.setVisibility(View.INVISIBLE);
                for (int start=1;start<=7;start++){
                    currentDay = start;
                    String selectedDate = currentYear + "-" + "0" + currentMonth + "-" + "0" + start;
                    if (hashMap.get(selectedDate) != null) {
                        if (weeklyBillArrayList == null){
                            weeklyBillArrayList = new ArrayList<>();
                        }
                        weeklyBillArrayList.addAll(hashMap.get(selectedDate));
                    }
                }
                adapter_week.updateRecyclerView(weeklyBillArrayList);
            }
        });

        ll_week2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                select_view1.setVisibility(View.INVISIBLE);
                select_view2.setVisibility(View.VISIBLE);
                select_view3.setVisibility(View.INVISIBLE);
                select_view4.setVisibility(View.INVISIBLE);
                weeklyBillArrayList.clear();
                for (int start=8;start<=14;start++){
                    currentDay = start;
                    String selectedDate = currentYear + "-" + "0" + currentMonth + "-" + "0" + start;
                    if (hashMap.get(selectedDate) != null) {
                        if (weeklyBillArrayList == null){
                            weeklyBillArrayList = new ArrayList<>();
                        }
                        weeklyBillArrayList.addAll(hashMap.get(selectedDate));
                    }
                }
                adapter_week.updateRecyclerView(weeklyBillArrayList);
            }
        });

        ll_week3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                select_view1.setVisibility(View.INVISIBLE);
                select_view2.setVisibility(View.INVISIBLE);
                select_view3.setVisibility(View.VISIBLE);
                select_view4.setVisibility(View.INVISIBLE);
                weeklyBillArrayList.clear();
                for (int start=15;start<=21;start++){
                    currentDay = start;
                    String selectedDate = currentYear + "-" + "0" + currentMonth + "-" + "0" + start;
                    if (hashMap.get(selectedDate) != null) {
                        if (weeklyBillArrayList == null){
                            weeklyBillArrayList = new ArrayList<>();
                        }
                        weeklyBillArrayList.addAll(hashMap.get(selectedDate));
                    }
                }
                adapter_week.updateRecyclerView(weeklyBillArrayList);
            }
        });

        ll_week4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                select_view1.setVisibility(View.INVISIBLE);
                select_view2.setVisibility(View.INVISIBLE);
                select_view3.setVisibility(View.INVISIBLE);
                select_view4.setVisibility(View.VISIBLE);
                weeklyBillArrayList.clear();
                for (int start=22;start<=28;start++){
                    currentDay = start;
                    String selectedDate = currentYear + "-" + "0" + currentMonth + "-" + "0" + start;
                    if (hashMap.get(selectedDate) != null) {
                        if (weeklyBillArrayList == null){
                            weeklyBillArrayList = new ArrayList<>();
                        }
                        weeklyBillArrayList.addAll(hashMap.get(selectedDate));
                    }
                }
                adapter_week.updateRecyclerView(weeklyBillArrayList);
            }
        });

        adapter_week = new MonthlyBudgetAdapter(weeklyBillArrayList);
        budget_rec_view_week = view.findViewById(R.id.rec_view_budget_week);
        budget_rec_view_week.setLayoutManager(new LinearLayoutManager(requireContext()));
        budget_rec_view_week.setAdapter(adapter_week);

    }

}