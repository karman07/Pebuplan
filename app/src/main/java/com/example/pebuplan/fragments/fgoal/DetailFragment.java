package com.example.pebuplan.fragments.fgoal;

import static android.app.Activity.RESULT_OK;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.viewmodel.CreationExtras;

import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageView;

import com.example.pebuplan.R;
import com.google.android.material.textfield.TextInputEditText;

import java.util.Calendar;


public class DetailFragment extends Fragment implements DatePickerDialog.OnDateSetListener {


    private static final int PICK_IMAGE = 1;
    TextInputEditText goal_amount, target_date, monthly_contribution;

    String imagepath;



    public DetailFragment() {

    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_detail, container, false);

        Button submit_btn = view.findViewById(R.id.button3);

        goal_amount = view.findViewById(R.id.goal_amount_tbox);
        target_date = view.findViewById(R.id.target_date_tbox);
        monthly_contribution = view.findViewById(R.id.monthly_contribution_tbox);
        SharedPreferences sharedPref = getActivity().getSharedPreferences("plan", Context.MODE_PRIVATE);

        SharedPreferences.Editor editor = sharedPref.edit();

        ImageView upload_image = view.findViewById(R.id.upload_image);
        upload_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(intent, PICK_IMAGE);
            }
        });

        target_date.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    Calendar calendar = Calendar.getInstance();
                    int year = calendar.get(Calendar.YEAR);
                    int month = calendar.get(Calendar.MONTH);
                    int day = calendar.get(Calendar.DAY_OF_MONTH);
                    DatePickerDialog datePickerDialog = new DatePickerDialog( getContext(), DetailFragment.this, year, month, day);
                    datePickerDialog.show();

                    target_date.clearFocus();
                } else {
                    target_date.clearFocus();
                }
            }
        });

        submit_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                editor.putString("goal_amount",goal_amount.getText().toString());
                editor.putString("target_date",target_date.getText().toString());
                editor.putString("monthly_contribution",monthly_contribution.getText().toString());
                editor.putString("image_goals",imagepath);
                editor.apply();
            }
        });

        return view;
    }

    public void selectImage(View view) {
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intent, 0);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK && requestCode == 0) {
            Uri uri = data.getData();
            ImageView imageView = getView().findViewById(R.id.upload_image);
            imageView.setImageURI(uri);
            imagepath = uri.getPath();
        }
    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        target_date.setText(String.format("%d/%d/%d", month + 1, dayOfMonth, year));
    }
}