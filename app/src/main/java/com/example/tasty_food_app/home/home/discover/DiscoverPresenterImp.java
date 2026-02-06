package com.example.tasty_food_app.home.home.discover;

import android.app.Application;

import com.example.tasty_food_app.datasource.model.Meal;
import com.example.tasty_food_app.datasource.repository.MealRepository;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class DiscoverPresenterImp implements DiscoverPresenter{
    private DiscoverView discoverView;
    private MealRepository mealRepository;
    private CompositeDisposable disposable = new CompositeDisposable();

    public DiscoverPresenterImp(Application application, DiscoverView discoverView) {
        mealRepository = new MealRepository(application.getApplicationContext());
        this.discoverView = discoverView;
    }

    @Override
    public void getRandomMeal() {
        discoverView.showLoading();
        disposable.add(
                mealRepository.getRandomMeal()
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(
                                response -> {
                                    discoverView.hideLoading();
                                    if (response.getMeals() != null && !response.getMeals().isEmpty()) {
                                        discoverView.showRandomMeal(response.getMeals().get(0));
                                    }
                                },
                                throwable -> {
                                    discoverView.hideLoading();
                                    discoverView.showError(throwable.getMessage());
                                }
                        )
        );
    }

    @Override
    public void getMealsByLetter(String letter) {
        disposable.add(
                mealRepository.getMealsByLetter(letter)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(
                                response -> {
                                    if (response.getMeals() != null) {
                                        discoverView.showMealsByLetter(response.getMeals());
                                    }
                                },
                                throwable -> discoverView.showError(throwable.getMessage())
                        )
        );
    }

    @Override
    public void addToFavorites(Meal meal) {
        // Implement Room later
        discoverView.showMessage("Added " + meal.getStrMeal() + " to favorites");
    }

    @Override
    public void deleteMealFromFav(Meal meal) {
        // Implement Room later
        discoverView.showMessage("deleted " + meal.getStrMeal() + " from favorites");
    }


    @Override
    public void clearResources() {
        disposable.clear();
    }




}
