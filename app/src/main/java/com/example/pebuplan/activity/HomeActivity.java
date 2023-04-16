package com.example.pebuplan.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import androidx.appcompat.widget.Toolbar;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.view.MenuItem;
import android.view.SurfaceView;
import android.view.View;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import com.example.pebuplan.R;
import com.google.android.exoplayer2.MediaItem;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.source.ProgressiveMediaSource;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.material.navigation.NavigationView;

import org.eazegraph.lib.charts.PieChart;
import org.eazegraph.lib.models.PieModel;

public class HomeActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{

    private VideoView videoView;
    private SeekBar seekBar;

    private DrawerLayout drawer;

    PieChart pieChart;
    private CardView m_budget, f_goals, m_bills, i_tracker, d_savings;

    TextView income_txt, expenses_txt, savings_txt;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        SharedPreferences prefs = getSharedPreferences("plan", Context.MODE_PRIVATE);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        m_budget = findViewById(R.id.m_budget);
        f_goals = findViewById(R.id.f_goals);
        m_bills = findViewById(R.id.m_bills);
        i_tracker = findViewById(R.id.i_tracker);
        d_savings = findViewById(R.id.d_savings);

        income_txt = findViewById(R.id.income);
        expenses_txt = findViewById(R.id.expenses);
        savings_txt = findViewById(R.id.savings);

        videoView = findViewById(R.id.video_view);
        seekBar = findViewById(R.id.seek_bar);
        pieChart = findViewById(R.id.piechart);
        String videoPath = "android.resource://" + getPackageName() + "/" + R.raw.video1;
        videoView.setVideoPath(videoPath);

        drawer = findViewById(R.id.drawer_layout);
        Intent intent = new Intent(HomeActivity.this, MainActivity.class);
        NavigationView navigationView = findViewById(R.id.navigation_view);
        navigationView.setNavigationItemSelectedListener(this);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar,
                R.string.drawer_open, R.string.drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        drawer.closeDrawer(GravityCompat.START);

        String income = prefs.getString("Total_Budget","0").replace("₱", "");
        String expenses = prefs.getString("Total_Spent","0").replace("₱", "");
        String savings = prefs.getString("Total_Remains","0").replace("₱", "");

        final Handler handler = new Handler();
        final Runnable updateSeekBar = new Runnable() {
            @Override
            public void run() {
                if (videoView.isPlaying()) {
                    seekBar.setProgress(videoView.getCurrentPosition());
                    handler.postDelayed(this, 100);
                }
            }
        };


        videoView.start();
        handler.postDelayed(updateSeekBar, 100);


        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if (fromUser) {
                    videoView.seekTo(progress);
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                // pause video when user starts touching seek bar
                videoView.pause();
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

                videoView.start();
            }
        });


        pieChart.addPieSlice(
                new PieModel(
                        "Budget",
                        Integer.parseInt(income),
                        Color.parseColor("#FFA726")));
        pieChart.addPieSlice(
                new PieModel(
                        "Spent",
                        Integer.parseInt(expenses),
                        Color.parseColor("#66BB6A")));
        pieChart.addPieSlice(
                new PieModel(
                        "Savings",
                        Integer.parseInt(savings),
                        Color.parseColor("#22A1E2")));


        income_txt.setText("Budget : " + income);
        expenses_txt.setText("Spent : " + expenses);
        savings_txt.setText("Savings : " + savings);


        m_budget.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                intent.putExtra("FragDetails", "m_budget");
                startActivity(intent);
            }
        });

        f_goals.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                intent.putExtra("FragDetails", "f_goals");
                startActivity(intent);
            }
        });

        m_bills.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                intent.putExtra("FragDetails", "m_bills");
                startActivity(intent);
            }
        });

        i_tracker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                intent.putExtra("FragDetails", "i_tracker");
                startActivity(intent);
            }
        });

        d_savings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                intent.putExtra("FragDetails", "d_savings");
                startActivity(intent);
            }
        });

    }

    @Override
    public void onBackPressed() {
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_budget:
                Toast.makeText(this,"Hi Budget", Toast.LENGTH_LONG).show();
                break;
            case R.id.action_goals:
                Toast.makeText(this,"Hi Goals", Toast.LENGTH_LONG).show();
                break;
        }

        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}