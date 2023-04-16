package com.example.pebuplan.fragments.tracker;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.pebuplan.R;
import com.example.pebuplan.activity.HomeActivity;
import com.google.android.material.tabs.TabLayout;

import org.eazegraph.lib.charts.PieChart;
import org.eazegraph.lib.models.PieModel;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class TrackerMainFragment extends Fragment {

    PieChart pieChart;
    ImageView back_image;


    public TrackerMainFragment() {

    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Log.d("TrackerMainFragment", "onCreateView called");
        View view = inflater.inflate(R.layout.fragment_tracker_main, container, false);
        pieChart = view.findViewById(R.id.piechart);

        TabLayout tabLayout = view.findViewById(R.id.tabLayout);

        TabLayout.Tab firstTab = tabLayout.newTab().setText("Income");
        TabLayout.Tab secondTab = tabLayout.newTab().setText("Expenses");

        tabLayout.addTab(firstTab);
        tabLayout.addTab(secondTab);

        pieChart.addPieSlice(new PieModel(
                "Income",
                100,
                Color.parseColor("#00FF00")));


        TextView title = view.findViewById(R.id.title);
        title.setText("Tracker");

        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat dateFormat = new SimpleDateFormat("MMMM", Locale.getDefault());
        String month = dateFormat.format(calendar.getTime());
        int year = calendar.get(Calendar.YEAR);

        SharedPreferences sharedPref = getActivity().getSharedPreferences("plan", Context.MODE_PRIVATE);

        String value = sharedPref.getString(month + "\t1-30\t" + year + "_spent", "0,0,0,0,0,0");

        String[] numberList = value.split(", ");
        String[] colours = {"#FF0000","#00ff00","#0000FF","#FFFF00","#FFA500"};

        back_image = view.findViewById(R.id.back_image);

        TrackerIncome fragment = new TrackerIncome();
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        transaction.replace(R.id.fragment_container, fragment);
        transaction.addToBackStack(null);
        transaction.commit();

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {

                switch (tab.getPosition()) {
                    case 0:
                        TrackerIncome fragment = new TrackerIncome();
                        FragmentTransaction transaction = getFragmentManager().beginTransaction();
                        transaction.replace(R.id.fragment_container, fragment);
                        transaction.addToBackStack(null);
                        transaction.commit();
                        spent();
                        break;
                    case 1:
                        TrackerExpensesFragment trackerExpensesFragment = new TrackerExpensesFragment();
                        FragmentTransaction transactio = getFragmentManager().beginTransaction();
                        transactio.replace(R.id.fragment_container, trackerExpensesFragment);
                        transactio.addToBackStack(null);
                        transactio.commit();
                        income();
                        break;

                }
            }

            private void spent() {
                pieChart.clearChart();
                pieChart.addPieSlice(new PieModel(
                        "Income",
                        100,
                        Color.parseColor("#00FF00")));
            }

            private void income() {
                pieChart.clearChart();
                for (int i = 0; i < numberList.length; i++) {
                    pieChart.addPieSlice(new PieModel(
                            "Food",
                            Integer.parseInt(numberList[i]),
                            Color.parseColor(colours[i])));
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        back_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), HomeActivity.class);
                startActivity(intent);
            }
        });


        return view;
    }


}