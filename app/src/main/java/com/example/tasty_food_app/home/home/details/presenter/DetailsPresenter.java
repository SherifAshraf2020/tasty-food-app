package com.example.tasty_food_app.home.home.details.presenter;

import com.example.tasty_food_app.datasource.model.Meal;

public interface DetailsPresenter {
    void getMealDetails(String mealId);
    void addToFavorites(Meal meal);
    void deleteMealFromFav(Meal meal);
    void checkIsFavorite(String mealId);
    void clearResources();


    void addMealToRecentlyViewed(Meal meal);
}
