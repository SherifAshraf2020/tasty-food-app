package com.example.tasty_food_app.home.home.favorite.view;

import com.example.tasty_food_app.datasource.model.meal.Meal;

import java.util.List;

public interface FavoritesView {
    void showFavoriteMeals(List<Meal> meals);
    void showMessage(String message);

    void showError(String error);

    void showGuestView();
}
