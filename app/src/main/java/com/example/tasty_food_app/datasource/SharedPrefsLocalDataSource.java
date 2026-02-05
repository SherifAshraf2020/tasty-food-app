package com.example.tasty_food_app.datasource;

import android.content.Context;
import android.content.SharedPreferences;

public class SharedPrefsLocalDataSource {
    private static final String PREF_NAME = "tasty-food-prefs";
    private static final String KEY_ONBOARDING = "is_onboarding_finished";

    private static final String KEY_IS_LOGGED_IN = "is_logged_in";
    private static final String KEY_USER_EMAIL = "user_email";

    private final SharedPreferences sharedPreferences;

    public SharedPrefsLocalDataSource(Context context) {
        sharedPreferences =
                context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
    }

    public void setOnBoardingFinished(boolean isFinished){
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean(KEY_ONBOARDING, isFinished);
        editor.apply();
    }

    public Boolean isOnBoardingFinished(){
        return sharedPreferences.getBoolean(KEY_ONBOARDING, false);
    }


    public void saveUserSession(String email) {
        sharedPreferences.edit()
                .putBoolean(KEY_IS_LOGGED_IN, true)
                .putString(KEY_USER_EMAIL, email)
                .apply();
    }

    public boolean isLoggedIn() {
        return sharedPreferences.getBoolean(KEY_IS_LOGGED_IN, false);
    }

    public void clearSession() {
        sharedPreferences.edit().clear().apply();
    }
}
