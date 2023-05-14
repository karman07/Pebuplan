package com.example.pebuplan.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.pebuplan.R;



public class PinConfirmActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pin_confirm);

        TextView pin_Code_box = findViewById(R.id.pin_Code_box);
        Button sign_in = findViewById(R.id.button4);

        TextView signwithfinger = findViewById(R.id.signwithfinger);

        signwithfinger.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PinConfirmActivity.this, FingerPrintActivity.class);
                startActivity(intent);
            }
        });

        SharedPreferences sharedPref = getSharedPreferences("plan", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();

        String value = sharedPref.getString("pincode", "default_value");

        Log.d("1",value);

        sign_in.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(pin_Code_box.getText().toString().equals(value)){
                    Intent intent = new Intent(PinConfirmActivity.this, HomeActivity.class);
                    editor.putString("login", "yes");
                    editor.apply();
                    startActivity(intent);
                }else{
                    Log.d("2", pin_Code_box.getText().toString());
                    pin_Code_box.setError("Error: Error is invalid");
                }
            }
        });

    }
}