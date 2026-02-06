package com.example.tasty_food_app.home.home.discover.presenter;

import android.app.Application;

import com.example.tasty_food_app.datasource.model.Meal;
import com.example.tasty_food_app.datasource.repository.MealRepository;
import com.example.tasty_food_app.home.home.discover.view.DiscoverView;

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
                mealRepository.getDailyRandomMeal()
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(
                                meal -> {
                                    discoverView.hideLoading();

                                    if (meal != null) {
                                        discoverView.showRandomMeal(meal);
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
                        .flattenAsObservable(response -> response.getMeals())
                        .flatMap(meal ->
                                mealRepository.isFavorite(meal.getIdMeal())
                                        .toObservable()
                                        .map(isFav -> {
                                            meal.setFavorite(isFav);
                                            return meal;
                                        })
                        )
                        .toList()
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(
                                meals -> {
                                    if (meals != null) {
                                        discoverView.showMealsByLetter(meals);
                                    }
                                },
                                throwable -> discoverView.showError(throwable.getMessage())
                        )
        );
    }

    @Override
    public void addToFavorites(Meal meal) {
        disposable.add(
                mealRepository.insertMeal(meal)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(
                                () -> discoverView.showMessage("Added " + meal.getStrMeal() + " to favorites"),
                                throwable -> discoverView.showError("Failed to add: " + throwable.getMessage())
                        )
        );
    }

    @Override
    public void deleteMealFromFav(Meal meal) {
        disposable.add(
                mealRepository.deleteMeal(meal)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(
                                () -> discoverView.showMessage("Deleted " + meal.getStrMeal() + " from favorites"),
                                throwable -> discoverView.showError("Failed to delete: " + throwable.getMessage())
                        )
        );
    }


    @Override
    public void clearResources() {
        disposable.clear();
    }




}
