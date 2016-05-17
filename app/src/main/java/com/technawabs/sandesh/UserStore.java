package com.technawabs.sandesh;

import android.content.Context;
import android.content.SharedPreferences;

public class UserStore {

    private SharedPreferences pref;
    private SharedPreferences.Editor editor;
    private static final String PREF_NAME = "SessionSharedPreference";
    private static final String IS_LOGGED_IN = "IsLoggedIn";
    private static final int PRIVATE_MODE = 0;

    public UserStore(Context context){
        pref = context.getSharedPreferences(PREF_NAME, PRIVATE_MODE);
    }

    public void createLoginSession() {
        editor = pref.edit();
        editor.putBoolean(IS_LOGGED_IN, true);
        editor.apply();
    }

    public boolean getLoggedIn(){
        return pref.getBoolean(IS_LOGGED_IN,false);
    }

}
