package com.example.tasty_food_app.home.home.discover;

import com.example.tasty_food_app.datasource.model.Meal;

public interface OnMealClick {
    void addMealToFav(Meal meal);
    void onMealDetailsClick(Meal meal);
    void deleteMealFromFav(Meal meal);

}
