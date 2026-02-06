package com.example.tasty_food_app.datasource.remote.meal;

import com.example.tasty_food_app.datasource.model.MealResponse;

import io.reactivex.rxjava3.core.Single;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface MealService {

    @GET("random.php")
    Single<MealResponse> getRandomMeal();

    @GET("search.php")
    Single<MealResponse> getMealsByFirstLetter(@Query("f") String letter);
}
