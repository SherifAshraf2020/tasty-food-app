package com.example.tasty_food_app.datasource.remote.meal;

import com.example.tasty_food_app.datasource.model.MealResponse;
import com.example.tasty_food_app.datasource.model.area.AreaResponse;
import com.example.tasty_food_app.datasource.model.category.CategoryResponse;
import com.example.tasty_food_app.datasource.model.ingredient.IngredientResponse;
import com.example.tasty_food_app.datasource.network.Network;

import io.reactivex.rxjava3.core.Single;

public class MealRemoteDataSource {

    private MealService mealService;
    public MealRemoteDataSource()
    {
        mealService = new Network().getMealService();
    }

    public Single<MealResponse> getRandomMeal() {
        return mealService.getRandomMeal();
    }

    public Single<MealResponse> getMealsByFirstLetter(String letter) {
        return mealService.getMealsByFirstLetter(letter);
    }

    public Single<MealResponse> getMealDetails(String id) {
        return mealService.getMealById(id);
    }



    public Single<AreaResponse> getAreas() {
        return mealService.getAreas();
    }

    public Single<CategoryResponse> getCategories() {
        return mealService.getCategories();
    }

    public Single<IngredientResponse> getIngredients() {
        return mealService.getIngredients();
    }


    public Single<MealResponse> searchMealsByName(String name) {
        return mealService.searchMealsByName(name);
    }

    public Single<MealResponse> getMealsByArea(String area) {
        return mealService.filterByArea(area);
    }

    public Single<MealResponse> getMealsByCategory(String category) {
        return mealService.filterByCategory(category);
    }

    public Single<MealResponse> getMealsByIngredient(String ingredient) {
        return mealService.filterByIngredient(ingredient);
    }
}
