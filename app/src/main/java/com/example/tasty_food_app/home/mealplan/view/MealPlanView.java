package com.example.tasty_food_app.home.mealplan.view;

import com.example.tasty_food_app.datasource.model.plan.PlanMeal;

import java.util.List;

public interface MealPlanView {

    void showDaysList(List<String> days);

    void showPlanData(List<PlanMeal> planMeals);

    void onPlanGenerated();

    void onInsertSuccess();

    void onDeleteSuccess();

    void onError(String message);



    void showGuestView();
}
