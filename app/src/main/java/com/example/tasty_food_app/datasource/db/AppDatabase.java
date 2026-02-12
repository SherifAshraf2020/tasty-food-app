package com.example.tasty_food_app.datasource.db;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.tasty_food_app.datasource.local.MealDao;
import com.example.tasty_food_app.datasource.local.PlanDao;
import com.example.tasty_food_app.datasource.model.meal.Meal;
import com.example.tasty_food_app.datasource.model.plan.PlanMeal;
import com.example.tasty_food_app.datasource.model.recent.RecentMeal;

@Database(entities = {Meal.class, RecentMeal.class, PlanMeal.class}, version = 3, exportSchema = false)
public abstract class AppDatabase extends RoomDatabase {

    private static AppDatabase instance = null;

    public abstract MealDao mealDao();
    public abstract PlanDao planDao();


    public static synchronized AppDatabase getInstance(Context context) {
        if (instance == null) {
            instance = Room.databaseBuilder(
                            context.getApplicationContext(),
                            AppDatabase.class,
                            "tasty_food_db"
                    )
                    .fallbackToDestructiveMigration()
                    .build();
        }
        return instance;
    }
}
