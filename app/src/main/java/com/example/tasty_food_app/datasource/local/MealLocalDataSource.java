package com.example.tasty_food_app.datasource.local;

import android.content.Context;

import com.example.tasty_food_app.datasource.db.AppDatabase;
import com.example.tasty_food_app.datasource.model.Meal;
import com.example.tasty_food_app.datasource.model.PlanMeal;
import com.example.tasty_food_app.datasource.model.RecentMeal;

import java.util.List;

import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Flowable;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.Single;

public class MealLocalDataSource {
    private MealDao mealDao;
    private PlanDao planDao;

    public MealLocalDataSource(Context context) {
        AppDatabase database = AppDatabase.getInstance(context);
        this.mealDao = database.mealDao();
        this.planDao = database.planDao();
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





    public Completable insertPlanMeal(PlanMeal planMeal){
        return planDao.insertPlanMeal(planMeal);
    }

    public Completable deletePlanMeal(PlanMeal planMeal){
        return planDao.deletePlanMeal(planMeal);
    }

    public Observable<List<PlanMeal>> getMealsByDay(String userId, String day){
        return planDao.getMealsByDay(userId, day);
    }
    public Observable<List<PlanMeal>> getAllPlannedMeals(String userId){
        return planDao.getAllPlannedMeals(userId);
    }

}
