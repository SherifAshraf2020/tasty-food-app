package com.example.tasty_food_app.datasource.model.ingredient;

import com.google.gson.annotations.SerializedName;

public class Ingredient {
    @SerializedName("strIngredient")
    private String name;

    private String measure;

    private String imageUrl;

    public Ingredient() {}

    public Ingredient(String name, String measure) {
        this.name = name;
        this.measure = measure;
        this.imageUrl = "https://www.themealdb.com/images/ingredients/" + name + "-Small.png";
    }

    public String getName() {
        return name;
    }

    public String getMeasure() {
        return measure;
    }

    public String getImageUrl() {
        if (imageUrl == null && name != null) {
            return "https://www.themealdb.com/images/ingredients/" + name + "-Small.png";
        }
        return imageUrl;
    }
}
