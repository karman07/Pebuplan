package com.example.pebuplan.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.pebuplan.R;

public class PinActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pin);

        Button save = findViewById(R.id.button2);
        EditText pincode = findViewById(R.id.pin_Code_box);
        EditText pinconfirm = findViewById(R.id.pin_Code_confirm);

        SharedPreferences preferences = getSharedPreferences("plan", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();



        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(pincode.getText().toString().equals(pinconfirm.getText().toString()) && !pincode.getText().toString().isEmpty()){
                    Intent intent = new Intent(PinActivity.this, PinConfirmActivity.class);
                    editor.putString("pincode", pincode.getText().toString());
                    editor.apply();
                    startActivity(intent);
                    finish();
                }else{
                    Toast.makeText(PinActivity.this, "Pin code doesn't match",Toast.LENGTH_SHORT).show();
                }
            }
        });

    }
}