package com.example.tasty_food_app.datasource;

import android.content.Context;
import android.content.SharedPreferences;

import com.example.tasty_food_app.datasource.model.Meal;
import com.google.gson.Gson;

public class SharedPrefsLocalDataSource {
    private static final String PREF_NAME = "tasty-food-prefs";


    private static final String KEY_ONBOARDING = "is_onboarding_finished";

    private static final String KEY_IS_LOGGED_IN = "is_logged_in";
    private static final String KEY_USER_EMAIL = "user_email";


    private static final String KEY_DAILY_MEAL_JSON = "daily_meal_json";
    private static final String KEY_DAILY_MEAL_DATE = "daily_meal_date";

    private final SharedPreferences sharedPreferences;
    private final Gson gson;

    public SharedPrefsLocalDataSource(Context context) {
        sharedPreferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        this.gson = new Gson();    }

    public void saveDailyMeal(Meal meal, String date) {
        sharedPreferences.edit()
                .putString(KEY_DAILY_MEAL_JSON, gson.toJson(meal))
                .putString(KEY_DAILY_MEAL_DATE, date)
                .apply();
    }


    public Meal getStoredDailyMeal() {
        String json = sharedPreferences.getString(KEY_DAILY_MEAL_JSON, null);
        return json != null ? gson.fromJson(json, Meal.class) : null;
    }

    public String getStoredDailyMealDate() {
        return sharedPreferences.getString(KEY_DAILY_MEAL_DATE, "");
    }








    public void setOnBoardingFinished(boolean isFinished) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean(KEY_ONBOARDING, isFinished);
        editor.apply();
    }

    public Boolean isOnBoardingFinished() {
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
