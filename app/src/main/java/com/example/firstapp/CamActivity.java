package com.example.firstapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Camera;
import android.os.Bundle;
import android.provider.Settings;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

public class CamActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cam);

        //initialisation des variables
        BottomNavigationView bottomNavigationView = findViewById(R.id.bar_nav);
        bottomNavigationView.setSelectedItemId(R.id.Camera);
        bottomNavigationView.setOnItemSelectedListener(item -> {
            switch (item.getItemId()){
                case R.id.Settings:
                    startActivity(new Intent(getApplicationContext(), SettingActivity.class));
                    overridePendingTransition(0,0);
                    return true;
                case R.id.Home:
                    startActivity(new Intent(getApplicationContext(), MenuActivity.class));
                    overridePendingTransition(0,0);
                    return true;
                case R.id.Camera:
                    return true;
            }

            return false;
        });

    }
}