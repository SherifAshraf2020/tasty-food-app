package com.example.tasty_food_app.home.mealplan.view;

import com.example.tasty_food_app.datasource.model.plan.PlanMeal;

public interface OnPlanClick {
    void onAddClick(String day);
    void onDeleteClick(PlanMeal planMeal);
    void onMealClick(String mealId);


    void onGenerateRandomClick();
}
