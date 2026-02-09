package com.example.tasty_food_app.home.home.search.view;

import com.example.tasty_food_app.datasource.model.Meal;
import com.example.tasty_food_app.datasource.model.area.Area;
import com.example.tasty_food_app.datasource.model.category.Category;
import com.example.tasty_food_app.datasource.model.ingredient.Ingredient;

public interface OnSearchClick {
    void onCategoryClick(Category category);
    void onAreaClick(Area area);
    void onIngredientClick(Ingredient ingredient);
    void onMealClick(Meal meal);
    void onFavoriteClick(Meal meal);
}
