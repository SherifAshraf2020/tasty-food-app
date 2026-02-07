package com.example.tasty_food_app.datasource.model;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

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

    @SerializedName("strInstructions")
    public String strInstructions;


    @SerializedName("strYoutube")
    public String strYoutube;

    //Ingredients Names
    public String strIngredient1, strIngredient2, strIngredient3, strIngredient4, strIngredient5,
            strIngredient6, strIngredient7, strIngredient8, strIngredient9, strIngredient10,
            strIngredient11, strIngredient12, strIngredient13, strIngredient14, strIngredient15,
            strIngredient16, strIngredient17, strIngredient18, strIngredient19, strIngredient20;

    // Ingredients Measures
    public String strMeasure1, strMeasure2, strMeasure3, strMeasure4, strMeasure5,
            strMeasure6, strMeasure7, strMeasure8, strMeasure9, strMeasure10,
            strMeasure11, strMeasure12, strMeasure13, strMeasure14, strMeasure15,
            strMeasure16, strMeasure17, strMeasure18, strMeasure19, strMeasure20;

    @Ignore
    private boolean isFavorite;

    public void setFavorite(boolean favorite) {
        this.isFavorite = favorite;
    }

    public boolean isFavorite() {
        return isFavorite;
    }


    public Meal(String idMeal, String strMeal, String strMealThumb, String strCategory, String strArea, String strInstructions,String strYoutube) {
        this.idMeal = idMeal;
        this.strMeal = strMeal;
        this.strMealThumb = strMealThumb;
        this.strCategory = strCategory;
        this.strArea = strArea;
        this.strInstructions = strInstructions;
        this.strYoutube = strYoutube;
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


    public String getStrYoutube() {
        return strYoutube;
    }

    public List<Ingredient> getIngredientsList() {
        List<Ingredient> ingredients = new ArrayList<>();
        String[] names = {strIngredient1, strIngredient2, strIngredient3, strIngredient4, strIngredient5, strIngredient6, strIngredient7, strIngredient8, strIngredient9, strIngredient10, strIngredient11, strIngredient12, strIngredient13, strIngredient14, strIngredient15, strIngredient16, strIngredient17, strIngredient18, strIngredient19, strIngredient20};
        String[] measures = {strMeasure1, strMeasure2, strMeasure3, strMeasure4, strMeasure5, strMeasure6, strMeasure7, strMeasure8, strMeasure9, strMeasure10, strMeasure11, strMeasure12, strMeasure13, strMeasure14, strMeasure15, strMeasure16, strMeasure17, strMeasure18, strMeasure19, strMeasure20};

        for (int i = 0; i < names.length; i++) {
            if (names[i] != null && !names[i].trim().isEmpty()) {
                String m = (measures[i] != null) ? measures[i] : "";
                ingredients.add(new Ingredient(names[i], m));
            }
        }
        return ingredients;
    }
}

