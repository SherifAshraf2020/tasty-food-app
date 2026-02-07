package com.example.tasty_food_app.home.home.details;

import com.example.tasty_food_app.datasource.model.Meal;

public interface DetailsView {
    void showLoading();
    void hideLoading();
    void showMealDetails(Meal meal);
    void showErrorMessage(String message);


    void onFavoriteAdded(Meal meal);
    void onFavoriteDeleted(Meal meal);


    void updateFavoriteIcon(boolean isFavorite);
}
