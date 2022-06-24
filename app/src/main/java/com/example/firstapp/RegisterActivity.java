package com.example.firstapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.android.volley.RequestQueue;

import java.util.Map;

import myrequest.MyRequest;

public class RegisterActivity extends AppCompatActivity {

    private Button btn_login,btn_inscrit;
    private EditText et_pseudo,et_mail,et_mdp1,et_mdp2;
    private RequestQueue queue;
    private MyRequest request;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        btn_login = (Button) findViewById(R.id.btn_login);
        btn_inscrit = (Button) findViewById(R.id.btn_inscrit);
        et_pseudo = (EditText) findViewById(R.id.et_pseudo);
        et_mail = (EditText) findViewById(R.id.et_mail);
        et_mdp1 = (EditText) findViewById(R.id.et_mdp1);
        et_mdp2 = (EditText) findViewById(R.id.et_mdp2);


        queue =MySingleton.getInstance(this).getRequestQueue();
        request= new MyRequest(this,queue);

        btn_inscrit.setOnClickListener(view1 -> {
            String pseudo = et_pseudo.getText().toString().trim();
            String mail = et_mail.getText().toString().trim();
            String mdp1 = et_mdp1.getText().toString().trim();
            String mdp2 = et_mdp2.getText().toString().trim();
            checkCrededentials();
            if(pseudo.length()>0 && mail.length()>0 && mdp1.length()>0 && mdp2.length()>0){
                request.register(pseudo, mail, mdp1, mdp2, new MyRequest.registerCallback() {
                    @Override
                    public void onSuccess(String message) {

                        Intent intent = new Intent(getApplicationContext(),MainActivity.class);
                        intent.putExtra("REGISTER",message);
                        startActivity(intent);
                        finish();
                    }

                    @Override
                    public void inputError(Map<String, String> errors) {

                    }

                    @Override
                    public void onError(String message) {

                        Toast.makeText(getApplicationContext(), message,Toast.LENGTH_SHORT).show();
                    }
                });
            }else {
                Toast.makeText(getApplicationContext(), "Remplir tout les champs!",Toast.LENGTH_SHORT).show();
            }
        });

        btn_login.setOnClickListener(view -> {
            Intent intent = new Intent(getApplicationContext(),MainActivity.class);
            startActivity(intent);
        });
    }

    private void checkCrededentials() {
        String pseudos = et_pseudo.getText().toString().trim();
        String mails = et_mail.getText().toString().trim();
        String mdp1s = et_mdp1.getText().toString().trim();
        String mdp2s = et_mdp2.getText().toString().trim();
        if(pseudos.isEmpty()){
            showError(et_pseudo,"inserer pseudo svp");

        }else if(mails.isEmpty() ){
            showError(et_mail,"inserer mail svp");
        }else if( mdp1s.isEmpty() ){showError(et_mdp1,"inserer password svp");}
        else if( mdp2s.isEmpty() ){showError(et_mdp2,"confirmer password svp");}
        else if( !mails.contains("@") ){showError(et_mail,"mail invalide");}
        else {Toast.makeText(getApplicationContext(), "call registration method",Toast.LENGTH_SHORT).show();}
    }

    private void showError(EditText input, String s) {

        input.setError(s);
        input.requestFocus();
    }
}