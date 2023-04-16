package com.example.pebuplan.fragments.savings;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.pebuplan.R;
import com.example.pebuplan.activity.HomeActivity;

import java.util.ArrayList;
import java.util.List;


public class SavingFragment extends Fragment {

    private RecyclerView recyclerView;
    private List<DataModel> dataList;
    private SavingsAdapter adapter;


    public SavingFragment() {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_saving, container, false);


        recyclerView = view.findViewById(R.id.recylerview);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        ImageView back_image = view.findViewById(R.id.back_image);

        TextView title = view.findViewById(R.id.title);
        title.setText("Savings");

        dataList = new ArrayList<>();
        dataList.add(new DataModel("April", "30", "₱200"));

        // Set adapter
        adapter = new SavingsAdapter(dataList);
        recyclerView.setAdapter(adapter);

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