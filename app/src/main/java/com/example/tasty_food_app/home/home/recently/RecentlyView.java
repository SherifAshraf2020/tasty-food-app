package com.example.tasty_food_app.home.home.recently;

import com.example.tasty_food_app.datasource.model.RecentMeal;

import java.util.List;

public interface RecentlyView {
    void showRecentMeals(List<RecentMeal> recentMeals);
    void showEmptyMessage();
    void showError(String message);
}
