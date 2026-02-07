package com.example.tasty_food_app.datasource.local;

import android.content.Context;

import com.example.tasty_food_app.datasource.db.AppDatabase;
import com.example.tasty_food_app.datasource.model.Meal;
import com.example.tasty_food_app.datasource.model.RecentMeal;

import java.util.List;

import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Flowable;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.Single;

public class MealLocalDataSource {
    private MealDao mealDao;

    public MealLocalDataSource(Context context) {
        this.mealDao = AppDatabase.getInstance(context).mealDao();

    }


    public Completable insertMeal(Meal meal) {
        return mealDao.insertMeal(meal);
    }

    public Completable deleteMeal(Meal meal) {
        return mealDao.deleteMeal(meal);
    }

    public Flowable<List<Meal>> getAllStoredMeals() {
        return mealDao.getAllStoredMeals();
    }

    public Single<Boolean> isFavorite(String id) {
        return mealDao.isFavorite(id);
    }





    public Completable insertRecentlyViewed(RecentMeal meal) {
        return mealDao.insertRecentlyViewed(meal);
    }

    public Observable<List<RecentMeal>> getRecentlyViewedMeals() {
        return mealDao.getRecentlyViewed();
    }

    public Completable clearAllRecent() {
        return mealDao.clearAllRecent();
    }
}
