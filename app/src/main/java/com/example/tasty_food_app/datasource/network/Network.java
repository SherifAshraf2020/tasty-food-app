package com.example.tasty_food_app.datasource.network;

import com.example.tasty_food_app.datasource.remote.meal.MealService;

import hu.akarnokd.rxjava3.retrofit.RxJava3CallAdapterFactory;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Network {
    private static final String BASE_URL = "https://www.themealdb.com/api/json/v1/1/";
    private static Network instance = null;
    private MealService mealService;
    private Retrofit retrofit;

    public Network() {
        retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
                .build();
    }



    public MealService getMealService() {
        if (mealService == null) {
            mealService = retrofit.create(MealService.class);
        }
        return mealService;
    }
}
