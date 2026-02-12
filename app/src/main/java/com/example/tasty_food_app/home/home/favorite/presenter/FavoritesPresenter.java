package com.example.tasty_food_app.home.home.favorite.presenter;

import com.example.tasty_food_app.datasource.model.meal.Meal;

public interface FavoritesPresenter {
    void getFavoriteMeals();

    void removeMeal(Meal meal);

    void clearResources();
}
