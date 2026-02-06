package com.example.tasty_food_app.datasource.remote.meal;

import com.example.tasty_food_app.datasource.model.MealResponse;
import com.example.tasty_food_app.datasource.network.Network;

import io.reactivex.rxjava3.core.Single;

public class MealRemoteDataSource {

    private MealService mealService;
    public MealRemoteDataSource()
    {
        mealService = new Network().getMealService();
    }

    public Single<MealResponse> getRandomMeal() {
        return mealService.getRandomMeal();
    }

    public Single<MealResponse> getMealsByFirstLetter(String letter) {
        return mealService.getMealsByFirstLetter(letter);
    }

}
