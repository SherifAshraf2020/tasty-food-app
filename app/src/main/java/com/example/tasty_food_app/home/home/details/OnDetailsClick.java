package com.example.tasty_food_app.home.home.details;

import com.example.tasty_food_app.datasource.model.Meal;

public interface OnDetailsClick {
    void onAddFavorite(Meal meal);
    void onRemoveFavorite(Meal meal);
}
