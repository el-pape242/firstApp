package com.example.firstapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.android.volley.RequestQueue;

import myrequest.MyRequest;

public class MainActivity extends AppCompatActivity {

    private Button btn_register,btn_log;
    private EditText uti,mdp;
    private ProgressBar pb_loader;
    private Handler handler;
    private RequestQueue queue;
    private MyRequest request;
    private SessionManager sessionManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Intent intent = getIntent();
        if (intent.hasExtra("REGISTER")){
            Toast.makeText(this,intent.getStringExtra("REGISTER"),Toast.LENGTH_SHORT).show();
        }

        btn_register = (Button) findViewById(R.id.btn_register);
        btn_log = (Button) findViewById(R.id.btn_log);
        uti = (EditText) findViewById(R.id.uti);
        mdp = (EditText) findViewById(R.id.mdp);
        pb_loader = (ProgressBar) findViewById(R.id.pb_loader);

        queue =MySingleton.getInstance(this).getRequestQueue();
        request= new MyRequest(this,queue);
        handler=new Handler();
        sessionManager=new SessionManager(this);
        if(sessionManager.is_logged()){
            Intent intent1  = new Intent(this,MenuActivity.class);
            startActivity(intent1);
            finish();
        }

        //inscription
        btn_register.setOnClickListener(view -> {
            Intent intents = new Intent(getApplicationContext(),RegisterActivity.class);
            startActivity(intents);
        });

        //connexion
        btn_log.setOnClickListener(view -> {
            checkCrededentials();

            String mail = uti.getText().toString().trim();
            String password = mdp.getText().toString().trim();
            pb_loader.setVisibility(View.VISIBLE);

            if (mail.length()>0 && password.length()>0){

                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {

                        request.connexion(mail, password, new MyRequest.LoginCallback() {
                            @Override
                            public void onSuccess(String id, String mail) {

                                pb_loader.setVisibility(View.GONE);
                                sessionManager.insertUser(id,mail);

                                Intent intent = new Intent(getApplicationContext(), MenuActivity.class);
                                startActivity(intent);
                                finish();
                            }

                            @Override
                            public void onError(String message) {

                                pb_loader.setVisibility(View.GONE);

                                Toast.makeText(getApplicationContext(), message,Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                },1000);


            }else {
                Toast.makeText(getApplicationContext(), "Remplir tout les champs!",Toast.LENGTH_SHORT).show();
            }

        });
    }
//gestion des erreurs
    private void checkCrededentials() {
        String mails = uti.getText().toString().trim();
        String passwords = mdp.getText().toString().trim();
        if(mails.isEmpty()){
            showError(uti,"inserer mail svp");
        }else if( !mails.contains("@") ){showError(uti,"mail invalide");}
        else if( passwords.isEmpty() ){showError(mdp,"inserer password svp");}
        else {Toast.makeText(getApplicationContext(), "call registration method",Toast.LENGTH_SHORT).show();}
    }

    private void showError(EditText input, String s) {

        input.setError(s);
        input.requestFocus();
    }
}