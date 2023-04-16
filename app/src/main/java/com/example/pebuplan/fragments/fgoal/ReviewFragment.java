package com.example.pebuplan.fragments.fgoal;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.BitmapFactory;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.pebuplan.R;
import com.google.android.material.textfield.TextInputEditText;


public class ReviewFragment extends Fragment {



    ImageView review_image;
    TextInputEditText title, goal_amount,target_date,monthly_tbox, duration_tbox;
    public ReviewFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_review, container, false);

        SharedPreferences sharedPref = getActivity().getSharedPreferences("plan", Context.MODE_PRIVATE);

        title = view.findViewById(R.id.title_tbox);
        goal_amount = view.findViewById(R.id.goal_amaount_tbox);
        target_date = view.findViewById(R.id.target_date_tbox);
        monthly_tbox = view.findViewById(R.id.monthly_tbox);
        duration_tbox = view.findViewById(R.id.duration_tbox);
        review_image = view.findViewById(R.id.review_image);

        String title_text = sharedPref.getString("title", "Home");
        String goal_text = sharedPref.getString("fgoals_price", "");
        String date_text = sharedPref.getString("fgoals_date", "");
        String monthly_contribution = sharedPref.getString("monthly_contribution", "");
        String duration = sharedPref.getString("target_date", "");
        String image = sharedPref.getString("image_goals", "R.drawable.upload_image");


        title.setText(title_text);
        goal_amount.setText(goal_text);
        target_date.setText(date_text);
        monthly_tbox.setText(monthly_contribution);
        duration_tbox.setText(duration);
        review_image.setImageBitmap(BitmapFactory.decodeFile(image));

        return view;
    }
}