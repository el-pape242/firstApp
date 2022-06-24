package com.example.firstapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Camera;
import android.os.Bundle;
import android.provider.Settings;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

public class SettingActivity extends AppCompatActivity {

    private SessionManager sessionManager;
    private Button btn_lg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);

        btn_lg = (Button)findViewById(R.id.btn_lg);
        sessionManager = new SessionManager(this);
        btn_lg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sessionManager.logout();
                Intent intent = new Intent(getApplicationContext(),MainActivity.class);
                startActivity(intent);
                finish();
            }
        });

        //initialisation des variables
        BottomNavigationView bottomNavigationView = findViewById(R.id.bar_nav);
        bottomNavigationView.setSelectedItemId(R.id.Settings);
        bottomNavigationView.setOnItemSelectedListener(item -> {
            switch (item.getItemId()){
                case R.id.Home:
                    startActivity(new Intent(getApplicationContext(), MenuActivity.class));
                    overridePendingTransition(0,0);
                    return true;
                case R.id.Camera:
                    startActivity(new Intent(getApplicationContext(), CamActivity.class));
                    overridePendingTransition(0,0);
                    return true;
                case R.id.Settings:
                    return true;
            }

            return false;
        });
    }
}