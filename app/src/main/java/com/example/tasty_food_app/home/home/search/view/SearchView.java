package com.example.tasty_food_app.home.home.search.view;

import com.example.tasty_food_app.datasource.model.Meal;
import com.example.tasty_food_app.datasource.model.area.Area;
import com.example.tasty_food_app.datasource.model.category.Category;
import com.example.tasty_food_app.datasource.model.ingredient.Ingredient;

import java.util.List;

public interface SearchView {
    void showCategories(List<Category> categories);
    void showIngredients(List<Ingredient> ingredients);
    void showAreas(List<Area> areas);



    void showMealsResult(List<Meal> meals);


    void showError(String message);
    void showMessage(String message);
    void showLoading();
    void hideLoading();


    void showEmptyResult();
}
