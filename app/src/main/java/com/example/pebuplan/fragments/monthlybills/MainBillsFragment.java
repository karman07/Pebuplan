package com.example.pebuplan.fragments.monthlybills;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;
import androidx.viewpager2.widget.ViewPager2;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.pebuplan.R;
import com.example.pebuplan.activity.HomeActivity;
import com.example.pebuplan.activity.MainActivity;
import com.example.pebuplan.fragments.fgoal.GoalsAdapter;
import com.example.pebuplan.model.MonthlyBillModel;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

import java.util.ArrayList;
import java.util.List;


public class MainBillsFragment extends Fragment implements UpdateBill{


    ImageView back_image;
    ImageView addMonthlyBills;
    RecyclerView monthlyBillRecyclerView;

    ArrayList<MonthlyBillModel> monthlyBills = new ArrayList<>();
    MonthlyBillAdapter adapter;
    public MainBillsFragment() {

    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main_bills, container, false);

        back_image = view.findViewById(R.id.back_image);
        monthlyBillRecyclerView = view.findViewById(R.id.monthly_bill_recycler_view);
        addMonthlyBills = view.findViewById(R.id.add_monthly_bill);

        TextView title = view.findViewById(R.id.title);
        title.setText("Monthly Bills");

        back_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), HomeActivity.class);
                startActivity(intent);
            }
        });

        monthlyBillRecyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));
        adapter = new MonthlyBillAdapter(monthlyBills);
        monthlyBillRecyclerView.setAdapter(adapter);
        Dialog dialog = new Dialog(requireContext());
        addMonthlyBills.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CustomDialog cd = new CustomDialog(requireActivity(), MainBillsFragment.this);
                cd.show();
            }

        });
        dialog.show();
        return view;
    }

    @Override
    public void update(MonthlyBillModel monthlyBillModel) {
        monthlyBills.add(monthlyBillModel);
        adapter.updateList(monthlyBills);
    }
}