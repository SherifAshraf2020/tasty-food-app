package com.example.tasty_food_app.datasource.local;

import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.example.tasty_food_app.datasource.model.Meal;

import java.util.List;

import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Flowable;
import io.reactivex.rxjava3.core.Single;

public interface MealDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    Completable insertMeal(Meal meal);

    @Delete
    Completable deleteMeal(Meal meal);

    @Query("SELECT * FROM meals_table")
    Flowable<List<Meal>> getAllStoredMeals();

    @Query("SELECT EXISTS (SELECT 1 FROM meals_table WHERE idMeal = :id)")
    Single<Boolean> isFavorite(String id);
}
