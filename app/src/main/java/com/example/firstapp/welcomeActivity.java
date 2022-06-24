package com.example.firstapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

public class welcomeActivity extends AppCompatActivity {
    private Button btn_wel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);

        btn_wel = (Button) findViewById(R.id.btn_wel);
        btn_wel.setOnClickListener(view -> {
            Intent intents = new Intent(getApplicationContext(),MenuActivity.class);
            startActivity(intents);
        });


    }
}