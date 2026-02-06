package com.example.tasty_food_app.datasource.model;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import com.google.gson.annotations.SerializedName;

@Entity(tableName = "meals_table")
public class Meal {
    @PrimaryKey
    @NonNull
    @SerializedName("idMeal")
    public String idMeal;

    @SerializedName("strMeal")
    public String strMeal;

    @SerializedName("strMealThumb")
    public String strMealThumb;

    @SerializedName("strCategory")
    public String strCategory;

    @SerializedName("strArea")
    public String strArea;

    @SerializedName("strInstructions") // كانت "instructions" غلط
    public String strInstructions;


    @Ignore
    private boolean isFavorite;

    public void setFavorite(boolean favorite) {
        this.isFavorite = favorite;
    }
    public boolean isFavorite() {
        return isFavorite;
    }



    public Meal(String idMeal, String strMeal, String strMealThumb, String strCategory, String strArea, String strInstructions) {
        this.idMeal = idMeal;
        this.strMeal = strMeal;
        this.strMealThumb = strMealThumb;
        this.strCategory = strCategory;
        this.strArea = strArea;
        this.strInstructions = strInstructions;
    }
    public String getIdMeal() {
        return idMeal;
    }

    public String getStrMeal() {
        return strMeal;
    }

    public String getStrMealThumb() {
        return strMealThumb;
    }

    public String getStrCategory() {
        return strCategory;
    }

    public String getStrArea() {
        return strArea;
    }

    public String getStrInstructions() {
        return strInstructions;
    }
}
