package com.example.pebuplan.fragments.settings;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.pebuplan.R;


public class HelpFragment extends Fragment {

    ImageView open_budget, open_bills, open_expenses;

    TextView help1_text, help2_text, help3_text;
    Boolean budget,bills,expenses = true;

    public HelpFragment() {

    }



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_help, container, false);

        open_budget = view.findViewById(R.id.open_budget);
        open_bills = view.findViewById(R.id.open_bills);
        open_expenses = view.findViewById(R.id.open_expenses);

        help1_text = view.findViewById(R.id.help1_text);
        help2_text = view.findViewById(R.id.help2_text);
        help3_text = view.findViewById(R.id.help3_text);

        open_budget.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(budget){
                    open_budget.setImageResource(R.drawable.up_arrow);
                    budget = false;
                    help1_text.setVisibility(View.VISIBLE);
                }else {
                    open_budget.setImageResource(R.drawable.up_arrow);
                    budget = true;
                    help1_text.setVisibility(View.GONE);
                }
            }
        });

        return view;

    }
}