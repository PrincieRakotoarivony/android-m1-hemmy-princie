package com.quiz;
import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;

public class KidzyApplication extends Application{

    static Context context;
    public static final String SHARED_PREFS = "kidzy";

    public static Context getContext() {
        return context;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        context = getApplicationContext();
    }

    public static SharedPreferences getSharedPrefs(){
        return getContext().getSharedPreferences(SHARED_PREFS, Context.MODE_PRIVATE);
    }

    public static String get(String key){
        return getSharedPrefs().getString(key, null);
    }

    public static void set(String key, String value){
        SharedPreferences.Editor editor = getSharedPrefs().edit();
        editor.putString(key, value);
        editor.apply();
    }

    public static void remove(String key){
        SharedPreferences.Editor editor = getSharedPrefs().edit();
        if(key != null) editor.remove(key);
        else editor.clear();
        editor.apply();
    }

    public static boolean has(String key){
        return get(key) != null;
    }
}
