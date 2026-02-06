package com.example.tasty_food_app.datasource.model;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.google.gson.annotations.SerializedName;

@Entity(tableName = "meals_table")
public class Meal {
    @SerializedName("id")
    @PrimaryKey
    public String idMeal;

    @SerializedName("name")
    public String strMeal;

    @SerializedName("thumb")
    public String strMealThumb;

    @SerializedName("category")
    public String strCategory;
    @SerializedName("country")
    public String strArea;

    @SerializedName("instructions")
    public String strInstructions;




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
