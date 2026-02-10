package com.example.tasty_food_app.home.home.search.presenter;

import com.example.tasty_food_app.datasource.model.Meal;

import java.util.List;

public interface SearchPresenter {


    void addToPlan(Meal meal, String day, String userId);


    void addToFavorite(Meal meal);
    void removeFromFavorite(Meal meal);
    void checkFavoritesAndShow(List<Meal> apiMeals);

    void getCategories();
    void getIngredients();
    void getAreas();



    void getMealsByCategory(String categoryName);
    void getMealsByArea(String areaName);
    void getMealsByIngredient(String ingredientName);


    void getMealsByLetter(String letter);
    void searchMeals(String query);


    void clearResources();
}
