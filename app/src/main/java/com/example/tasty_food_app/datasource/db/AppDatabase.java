package com.example.tasty_food_app.datasource.db;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.tasty_food_app.datasource.local.MealDao;
import com.example.tasty_food_app.datasource.model.Meal;

@Database(entities = {Meal.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {
    public abstract MealDao mealDao();
    private static AppDatabase INSTANCE;

    public static AppDatabase getInstance(Context context){
        if (INSTANCE == null) {
            INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            AppDatabase.class, "meals_db")
                    .build();
        }
        return INSTANCE;
    }
}
