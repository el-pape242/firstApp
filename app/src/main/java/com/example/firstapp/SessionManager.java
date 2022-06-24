package com.example.firstapp;

import android.content.Context;
import android.content.SharedPreferences;

public class SessionManager {
    private SharedPreferences prefs;
    private SharedPreferences.Editor editor;
    private final static String PREFS_NAMES= "app_prefs";
    private final static int PRIVATE_MODE= 0;
    private final static String IS_LOGGED="is_logged";
    private final static String MAIL="mail";
    private final static String ID="id";
    private Context context;

    public SessionManager(Context context){
        this.context= context;
        prefs= context.getSharedPreferences(PREFS_NAMES, PRIVATE_MODE);
        editor=prefs.edit();
    }

    public boolean is_logged(){
        return prefs.getBoolean(IS_LOGGED,false);
    }

    public String getMail() {
        return prefs.getString(MAIL,null);
    }

    public String getId() {
        return prefs.getString(ID,null);
    }

    public void insertUser(String id,String mail){

        editor.putString(ID,id);
        editor.putString(MAIL,mail);
        editor.putBoolean(IS_LOGGED,true);
        editor.commit();
    }

    public void logout(){
        editor.clear().commit();
    }
}
