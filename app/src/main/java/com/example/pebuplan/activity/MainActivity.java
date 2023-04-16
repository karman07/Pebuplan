package com.example.pebuplan.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.widget.FrameLayout;

import com.example.pebuplan.R;
import com.example.pebuplan.fragments.fgoal.FGoals;
import com.example.pebuplan.fragments.monthlybills.MainBillsFragment;
import com.example.pebuplan.fragments.monthlybudget.MonthlyBudget;
import com.example.pebuplan.fragments.savings.SavingFragment;
import com.example.pebuplan.fragments.tracker.TrackerMainFragment;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FrameLayout fragContainer = findViewById(R.id.fragment_container_main);
        String fragDetails = getIntent().getStringExtra("FragDetails");


        if(fragDetails.equals("m_budget")){
            MonthlyBudget fragment = new MonthlyBudget();
            transaction(fragment);
        } else if (fragDetails.equals("f_goals")) {
            FGoals fragment = new FGoals();
            transaction(fragment);
        } else if (fragDetails.equals("i_tracker")) {
            TrackerMainFragment fragment = new TrackerMainFragment();
            transaction(fragment);
        }else if(fragDetails.equals("m_bills")){
            MainBillsFragment mainBillsFragment = new MainBillsFragment();
            transaction(mainBillsFragment);
        }else{
            SavingFragment savingFragment = new SavingFragment();
            transaction(savingFragment);
        }
    }

    public void transaction(Fragment fragment){
        FragmentManager fragmentManager = this.getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.fragment_container_main, fragment);
        fragmentTransaction.commit();
    }

    public void onBackPressed() {

        Intent intent = new Intent(MainActivity.this, HomeActivity.class);
        startActivity(intent);

    }
}