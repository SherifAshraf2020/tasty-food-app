package com.example.tasty_food_app.datasource.model;

import androidx.room.Entity;

import androidx.annotation.NonNull;

@Entity(tableName = "plan_table", primaryKeys = {"idMeal", "day", "userId"})
public class PlanMeal {
    @NonNull
    private String idMeal;
    private String strMeal;
    private String strMealThumb;

    @NonNull
    private String day;

    @NonNull
    private String userId;

    public PlanMeal(String idMeal, String strMeal, String strMealThumb, String day, String userId){
        this.idMeal = idMeal;
        this.strMeal = strMeal;
        this.strMealThumb = strMealThumb;
        this.day = day;
        this.userId = userId;
    }


    @NonNull

    public String getIdMeal() {
        return idMeal;
    }

    public void setIdMeal(String idMeal) {
        this.idMeal = idMeal;
    }

    public String getStrMeal() {
        return strMeal;
    }

    public void setStrMeal(String strMeal) {
        this.strMeal = strMeal;
    }

    public String getStrMealThumb() {
        return strMealThumb;
    }

    public void setStrMealThumb(String strMealThumb) {
        this.strMealThumb = strMealThumb;
    }


    @NonNull
    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }


    @NonNull
    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

}
