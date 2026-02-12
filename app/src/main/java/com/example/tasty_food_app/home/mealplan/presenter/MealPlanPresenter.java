package com.example.tasty_food_app.home.mealplan.presenter;

import com.example.tasty_food_app.datasource.model.meal.Meal;
import com.example.tasty_food_app.datasource.model.plan.PlanMeal;

import java.util.List;

public interface MealPlanPresenter {
    void getWeeklyPlan(String userId);
    void removeMealFromPlan(PlanMeal planMeal);
    void generateDaysList();
    void addSingleMealToPlan(Meal meal, String day, String userId);
    void generateRandomWeeklyPlan(List<String> days, String userId);
    void onDestroy();

}
