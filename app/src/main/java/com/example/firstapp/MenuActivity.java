package com.example.firstapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Camera;
import android.os.Bundle;
import android.provider.Settings;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

public class MenuActivity extends AppCompatActivity {

    private SessionManager sessionManager;
    private TextView textView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu2);

        textView = (TextView)findViewById(R.id.tv_ps);
        sessionManager = new SessionManager(this);
        if(sessionManager.is_logged()){

            String mail = sessionManager.getMail();
            String id = sessionManager.getId();
            textView.setText(mail);
        }

        //initialisation des variables
        BottomNavigationView bottomNavigationView = findViewById(R.id.bar_nav);
        bottomNavigationView.setSelectedItemId(R.id.Home);
        bottomNavigationView.setOnItemSelectedListener(item -> {
            switch (item.getItemId()){
                case R.id.Settings:
                    startActivity(new Intent(getApplicationContext(), SettingActivity.class));
                    overridePendingTransition(0,0);
                    return true;
                case R.id.Camera:
                    startActivity(new Intent(getApplicationContext(), CamActivity.class));
                    overridePendingTransition(0,0);
                    return true;
                case R.id.Home:
                    return true;
            }
            return false;
        });


    }

}

