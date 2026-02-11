package com.example.tasty_food_app.datasource.local;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.example.tasty_food_app.datasource.model.PlanMeal;

import java.util.List;

import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Observable;

@Dao
public interface PlanDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    Completable insertPlanMeal(PlanMeal planMeal);

    @Delete
    Completable deletePlanMeal(PlanMeal planMeal);

    @Query("SELECT * FROM plan_table WHERE userId = :userId AND day = :day")
    Observable<List<PlanMeal>> getMealsByDay(String userId, String day);

    @Query("SELECT * FROM plan_table WHERE userId = :userId")
    Observable<List<PlanMeal>> getAllPlannedMeals(String userId);





    @Insert(onConflict = OnConflictStrategy.REPLACE)
    Completable insertAllPlans(List<PlanMeal> plans);

    @Query("DELETE FROM plan_table")
    Completable clearAllPlans();
}
