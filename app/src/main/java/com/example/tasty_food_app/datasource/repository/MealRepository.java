package com.example.tasty_food_app.datasource.repository;

import android.app.Application;
import android.content.Context;

import com.example.tasty_food_app.datasource.local.MealLocalDataSource;
import com.example.tasty_food_app.datasource.model.MealResponse;
import com.example.tasty_food_app.datasource.remote.meal.MealRemoteDataSource;

import io.reactivex.rxjava3.core.Single;

public class MealRepository {
    MealRemoteDataSource mealRemoteDataSource;
    MealLocalDataSource mealLocalDataSource;


    public MealRepository(Context context) {
        mealLocalDataSource = new MealLocalDataSource(context);
        mealRemoteDataSource = new MealRemoteDataSource();
    }
    public Single<MealResponse> getRandomMeal(){
        return mealRemoteDataSource.getRandomMeal();
    }

    public Single<MealResponse> getMealsByLetter(String letter){
        return mealRemoteDataSource.getMealsByFirstLetter(letter);
    }


}
