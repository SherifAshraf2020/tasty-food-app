package com.example.tasty_food_app.home.home.discover.view;

import com.example.tasty_food_app.datasource.model.meal.Meal;

public interface OnMealClick {
    void addMealToFav(Meal meal);
    void onMealDetailsClick(Meal meal);
    void deleteMealFromFav(Meal meal);

}
