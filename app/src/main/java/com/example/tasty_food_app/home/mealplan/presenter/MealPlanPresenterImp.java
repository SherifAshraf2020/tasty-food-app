package com.example.tasty_food_app.home.mealplan.presenter;

import com.example.tasty_food_app.datasource.model.Meal;
import com.example.tasty_food_app.datasource.model.PlanMeal;
import com.example.tasty_food_app.datasource.repository.MealRepository;
import com.example.tasty_food_app.home.mealplan.view.MealPlanView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class MealPlanPresenterImp implements MealPlanPresenter {


    private MealPlanView view;
    private MealRepository repository;
    private CompositeDisposable disposable = new CompositeDisposable();

    public MealPlanPresenterImp(MealPlanView view, MealRepository repository) {
        this.view = view;
        this.repository = repository;
    }

    @Override
    public void getWeeklyPlan(String userId) {
        disposable.add(
                repository.getAllPlannedMeals(userId)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(
                                planMeals -> view.showPlanData(planMeals),
                                throwable -> view.onError(throwable.getMessage())
                        )
        );
    }

    @Override
    public void generateDaysList() {
        List<String> days = new ArrayList<>();
        SimpleDateFormat sdf = new SimpleDateFormat("EEEE", Locale.getDefault());
        Calendar calendar = Calendar.getInstance();
        for (int i = 0; i < 7; i++) {
            days.add(sdf.format(calendar.getTime()));
            calendar.add(Calendar.DAY_OF_YEAR, 1);
        }
        view.showDaysList(days);
    }

    @Override
    public void addSingleMealToPlan(Meal meal, String day, String userId) {
        PlanMeal planMeal = new PlanMeal(
                meal.getIdMeal(),
                meal.getStrMeal(),
                meal.getStrMealThumb(),
                day,
                userId
        );
        disposable.add(
                repository.insertPlanMeal(planMeal)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(
                                () -> view.onInsertSuccess(),
                                throwable -> view.onError(throwable.getMessage())
                        )
        );
    }

    @Override
    public void generateRandomWeeklyPlan(List<String> days, String userId) {
        disposable.add(
                repository.getAllPlannedMeals(userId)
                        .firstElement()
                        .subscribeOn(Schedulers.io())
                        .flatMapObservable(existingMeals ->
                                Observable.fromIterable(days)
                                        .filter(day -> {
                                            for (PlanMeal existing : existingMeals) {
                                                if (existing.getDay().equalsIgnoreCase(day)) {
                                                    return false;
                                                }
                                            }
                                            return true;
                                        })
                        )
                        .concatMapSingle(day -> repository.getRandomMealForPlan()
                                .subscribeOn(Schedulers.io())
                                .map(meal -> new PlanMeal(
                                        meal.getIdMeal(),
                                        meal.getStrMeal(),
                                        meal.getStrMealThumb(),
                                        day,
                                        userId
                                )))
                        .concatMapCompletable(planMeal -> repository.insertPlanMeal(planMeal))
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(
                                () -> view.onPlanGenerated(),
                                throwable -> view.onError(throwable.getMessage())
                        )
        );
    }

    @Override
    public void removeMealFromPlan(PlanMeal planMeal) {
        disposable.add(
                repository.deletePlanMeal(planMeal)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(
                                () -> view.onDeleteSuccess(),
                                throwable -> view.onError(throwable.getMessage())
                        )
        );
    }

    @Override
    public void onDestroy() {
        disposable.clear();
    }
}
