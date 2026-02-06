package com.example.tasty_food_app.home.home.discover;

import com.example.tasty_food_app.datasource.model.Meal;

import java.util.List;

public interface DiscoverView {
    void showLoading();
    void hideLoading();
    void showRandomMeal(Meal meal);
    void showMealsByLetter(List<Meal> meals);
    void showError(String errorMessage);
    void showMessage(String message);
}
