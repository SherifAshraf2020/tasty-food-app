package com.example.tasty_food_app.datasource.repository;

import android.app.Application;
import android.content.Context;

import com.example.tasty_food_app.datasource.local.MealLocalDataSource;
import com.example.tasty_food_app.datasource.model.Meal;
import com.example.tasty_food_app.datasource.model.MealResponse;
import com.example.tasty_food_app.datasource.remote.meal.MealRemoteDataSource;

import java.util.List;

import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Flowable;
import io.reactivex.rxjava3.core.Single;

public class MealRepository {
    MealRemoteDataSource mealRemoteDataSource;
    MealLocalDataSource mealLocalDataSource;


public MealRepository(Context context)
{
    mealRemoteDataSource = new MealRemoteDataSource();
    mealLocalDataSource = new MealLocalDataSource(context);
}
    public Single<MealResponse> getRandomMeal(){
        return mealRemoteDataSource.getRandomMeal();
    }

    public Single<MealResponse> getMealsByLetter(String letter){
        return mealRemoteDataSource.getMealsByFirstLetter(letter);
    }





    public Completable insertMeal(Meal meal) {
        return mealLocalDataSource.insertMeal(meal);
    }

    public Completable deleteMeal(Meal meal) {
        return mealLocalDataSource.deleteMeal(meal);
    }

    public Flowable<List<Meal>> getStoredMeals() {
        return mealLocalDataSource.getAllStoredMeals();
    }

    public Single<Boolean> isFavorite(String id) {
        return mealLocalDataSource.isFavorite(id);
    }

}
