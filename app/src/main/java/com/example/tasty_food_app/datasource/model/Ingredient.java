package com.example.tasty_food_app.datasource.model;

public class Ingredient {


    private String name;
    private String measure;
    public Ingredient(String name, String measure) {
        this.name = name;
        this.measure = measure;
    }

    public String getName() {
        return name;
    }

    public String getMeasure() {
        return measure;
    }


    public String getImageUrl() {
        if (name != null && !name.isEmpty()) {
            return "https://www.themealdb.com/images/ingredients/" + name.replace(" ", "%20") + "-Small.png";
        }
        return "";
    }}
