package com.example.tasty_food_app.datasource.local;

import android.content.Context;
import android.content.SharedPreferences;

import com.example.tasty_food_app.datasource.model.meal.Meal;
import com.google.gson.Gson;

import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Single;

public class SharedPrefsLocalDataSource {
    private static final String PREF_NAME = "tasty-food-prefs";
    private static final String KEY_ONBOARDING = "is_onboarding_finished";
    private static final String KEY_IS_LOGGED_IN = "is_logged_in";
    private static final String KEY_USER_EMAIL = "user_email";
    private static final String KEY_DAILY_MEAL_JSON = "daily_meal_json";
    private static final String KEY_DAILY_MEAL_DATE = "daily_meal_date";
    private static final String KEY_IS_GUEST = "is_guest_mode";
    private final SharedPreferences sharedPreferences;
    private final Gson gson;

    public SharedPrefsLocalDataSource(Context context) {
        sharedPreferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        this.gson = new Gson();
    }


    public Completable saveUserSession(String email , boolean isGuest) {
        return Completable.fromAction(() -> {
            sharedPreferences.edit()
                    .putBoolean(KEY_IS_LOGGED_IN, true)
                    .putString(KEY_USER_EMAIL, email)
                    .putBoolean(KEY_IS_GUEST, isGuest)
                    .apply();
        });
    }

    public boolean isGuest() {
        return sharedPreferences.getBoolean(KEY_IS_GUEST, false);
    }

    public Single<Boolean> isLoggedIn() {
        return Single.just(sharedPreferences.getBoolean(KEY_IS_LOGGED_IN, false));
    }

    public Completable clearSession() {
        return Completable.fromAction(() -> {
            boolean onBoardingFinished = sharedPreferences.getBoolean(KEY_ONBOARDING, false);

            sharedPreferences.edit().clear().commit();

            sharedPreferences.edit().putBoolean(KEY_ONBOARDING, onBoardingFinished).apply();
        });    }







    public void setOnBoardingFinished(boolean isFinished) {
        sharedPreferences.edit().putBoolean(KEY_ONBOARDING, isFinished).apply();
    }

    public boolean isOnBoardingFinished() {
        return sharedPreferences.getBoolean(KEY_ONBOARDING, false);
    }









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






    public String getUserEmail() {
        return sharedPreferences.getString(KEY_USER_EMAIL, "");
    }
}
