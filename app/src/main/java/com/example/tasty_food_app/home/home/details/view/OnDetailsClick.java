package com.example.tasty_food_app.home.home.details.view;

import com.example.tasty_food_app.datasource.model.meal.Meal;

public interface OnDetailsClick {
    void onAddFavorite(Meal meal);
    void onRemoveFavorite(Meal meal);
}
