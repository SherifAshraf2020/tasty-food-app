package com.example.tasty_food_app.datasource.model;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "recent_meals_table") // جدول منفصل تماماً عن الفيفورت
public class RecentMeal {
    @PrimaryKey
    @NonNull
    private String idMeal;
    private String strMeal;
    private String strMealThumb;
    private long viewedAt;

    public RecentMeal(@NonNull String idMeal, String strMeal, String strMealThumb, long viewedAt) {
        this.idMeal = idMeal;
        this.strMeal = strMeal;
        this.strMealThumb = strMealThumb;
        this.viewedAt = viewedAt;
    }

    // Getters and Setters
    @NonNull public String getIdMeal() { return idMeal; }
    public void setIdMeal(@NonNull String idMeal) { this.idMeal = idMeal; }
    public String getStrMeal() { return strMeal; }
    public void setStrMeal(String strMeal) { this.strMeal = strMeal; }
    public String getStrMealThumb() { return strMealThumb; }
    public void setStrMealThumb(String strMealThumb) { this.strMealThumb = strMealThumb; }
    public long getViewedAt() { return viewedAt; }
    public void setViewedAt(long viewedAt) { this.viewedAt = viewedAt; }
}
