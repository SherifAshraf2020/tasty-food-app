package com.example.tasty_food_app.datasource.repository;

import android.app.Application;
import android.content.Context;

import com.example.tasty_food_app.datasource.SharedPrefsLocalDataSource;
import com.example.tasty_food_app.datasource.local.MealLocalDataSource;
import com.example.tasty_food_app.datasource.model.Meal;
import com.example.tasty_food_app.datasource.model.MealResponse;
import com.example.tasty_food_app.datasource.model.RecentMeal;
import com.example.tasty_food_app.datasource.remote.meal.MealRemoteDataSource;

import java.util.List;

import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Flowable;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.Single;

public class MealRepository {
    MealRemoteDataSource mealRemoteDataSource;
    MealLocalDataSource mealLocalDataSource;
    SharedPrefsLocalDataSource sharedPrefsDataSource;


    public MealRepository(Context context) {
        Context appContext = context.getApplicationContext();
        mealRemoteDataSource = new MealRemoteDataSource();
        mealLocalDataSource = new MealLocalDataSource(context);
        sharedPrefsDataSource = new SharedPrefsLocalDataSource(context);
    }

    public Single<Meal> getDailyRandomMeal() {
        String todayDate = new java.text.SimpleDateFormat("yyyyMMdd", java.util.Locale.getDefault()).format(new java.util.Date());

        if (sharedPrefsDataSource.getStoredDailyMealDate().equals(todayDate)) {
            Meal cachedMeal = sharedPrefsDataSource.getStoredDailyMeal();
            if (cachedMeal != null) {
                return Single.just(cachedMeal);
            }
        }

        return mealRemoteDataSource.getRandomMeal()
                .map(response -> response.getMeals().get(0))
                .doOnSuccess(meal -> sharedPrefsDataSource.saveDailyMeal(meal, todayDate));
    }

    public Single<MealResponse> getMealsByLetter(String letter) {
        return mealRemoteDataSource.getMealsByFirstLetter(letter);
    }

    public Single<Meal> getMealById(String mealId) {
        return mealRemoteDataSource.getMealDetails(mealId)
                .map(response -> response.getMeals().get(0));
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






    public Completable insertRecentlyViewed(Meal meal) {
        RecentMeal recent = new RecentMeal(
                meal.getIdMeal(),
                meal.getStrMeal(),
                meal.getStrMealThumb(),
                System.currentTimeMillis()
        );
        return mealLocalDataSource.insertRecentlyViewed(recent);
    }

    public Observable<List<RecentMeal>> getRecentlyViewedMeals() {
        return mealLocalDataSource.getRecentlyViewedMeals();
    }

}
