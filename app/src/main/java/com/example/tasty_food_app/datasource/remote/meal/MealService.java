package com.example.tasty_food_app.datasource.remote.meal;

import com.example.tasty_food_app.datasource.model.MealResponse;
import com.example.tasty_food_app.datasource.model.area.AreaResponse;
import com.example.tasty_food_app.datasource.model.category.CategoryResponse;
import com.example.tasty_food_app.datasource.model.ingredient.IngredientResponse;

import io.reactivex.rxjava3.core.Single;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface MealService {

    @GET("random.php")
    Single<MealResponse> getRandomMeal();

    @GET("search.php")
    Single<MealResponse> getMealsByFirstLetter(@Query("f") String letter);

    @GET("lookup.php")
    Single<MealResponse> getMealById(@Query("i") String id);




    @GET("search.php")
    Single<MealResponse> searchMealsByName(@Query("s") String name);

    @GET("list.php?a=list")
    Single<AreaResponse> getAreas();

    @GET("categories.php")
    Single<CategoryResponse> getCategories();

    @GET("list.php?i=list")
    Single<IngredientResponse> getIngredients();

    @GET("filter.php")
    Single<MealResponse> filterByArea(@Query("a") String area);

    @GET("filter.php")
    Single<MealResponse> filterByCategory(@Query("c") String category);

    @GET("filter.php")
    Single<MealResponse> filterByIngredient(@Query("i") String ingredient);
}
