package com.example.tasty_food_app.datasource.local;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.example.tasty_food_app.datasource.model.Meal;
import com.example.tasty_food_app.datasource.model.RecentMeal;

import java.util.List;

import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Flowable;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.Single;

@Dao
public interface MealDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    Completable insertMeal(Meal meal);

    @Delete
    Completable deleteMeal(Meal meal);

    @Query("SELECT * FROM meals_table")
    Flowable<List<Meal>> getAllStoredMeals();

    @Query("SELECT EXISTS (SELECT 1 FROM meals_table WHERE idMeal = :id)")
    Single<Boolean> isFavorite(String id);




    @Insert(onConflict = OnConflictStrategy.REPLACE)
    Completable insertRecentlyViewed(RecentMeal meal);

    @Query("SELECT * FROM recent_meals_table ORDER BY viewedAt DESC LIMIT 10")
    Observable<List<RecentMeal>> getRecentlyViewed();

    @Query("DELETE FROM recent_meals_table")
    Completable clearAllRecent();






    @Insert(onConflict = OnConflictStrategy.REPLACE)
    Completable insertAllMeals(List<Meal> meals);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    Completable insertAllRecent(List<RecentMeal> recentMeals);

    @Query("DELETE FROM meals_table")
    Completable clearAllFavorites();


}
