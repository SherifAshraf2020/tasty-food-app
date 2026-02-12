package com.example.tasty_food_app.home.home.discover.presenter;

import com.example.tasty_food_app.datasource.model.meal.Meal;

public interface DiscoverPresenter {
    void getRandomMeal();
    void getMealsByLetter(String letter);
    void addToFavorites(Meal meal);
    void clearResources();
    void deleteMealFromFav(Meal meal);
}
