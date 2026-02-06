package com.example.tasty_food_app.home.home.favorite;

import com.example.tasty_food_app.datasource.model.Meal;

public interface FavoritesPresenter {
    void getFavoriteMeals();

    void removeMeal(Meal meal);

    void clearResources();
}
