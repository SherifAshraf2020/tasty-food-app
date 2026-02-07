package com.example.tasty_food_app.home.home.details;

import com.example.tasty_food_app.datasource.model.Meal;

public interface DetailsView {
    void showMealDetails(Meal meal);

    void updateFavoriteStatus(boolean isFavorite);

    void showError(String errorMessage);

    void showMessage(String message);
}
