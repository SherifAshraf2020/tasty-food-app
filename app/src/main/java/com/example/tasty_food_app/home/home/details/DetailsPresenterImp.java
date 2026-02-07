package com.example.tasty_food_app.home.home.details;

import com.example.tasty_food_app.datasource.model.Meal;
import com.example.tasty_food_app.datasource.repository.MealRepository;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class DetailsPresenterImp implements DetailsPresenter{
    DetailsView detailsView;
    MealRepository mealRepository;
    CompositeDisposable disposable = new CompositeDisposable();

    public DetailsPresenterImp(DetailsView detailsView, MealRepository mealRepository) {
        this.detailsView = detailsView;
        this.mealRepository = mealRepository;
    }

    @Override
    public void getMealDetails(String mealId) {
        disposable.add(
                mealRepository.getMealById(mealId)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(
                                response -> {
                                    if (response.getMeals() != null && !response.getMeals().isEmpty()) {
                                        Meal meal = response.getMeals().get(0);
                                        detailsView.showMealDetails(meal);
                                        checkIsFavorite(meal.getIdMeal());
                                    }
                                },
                                throwable -> detailsView.showError("Failed to fetch meal details: " + throwable.getMessage())
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
                                () -> {
                                    detailsView.updateFavoriteStatus(true);
                                    detailsView.showMessage("Added to Favorites");
                                },
                                throwable -> detailsView.showError("Could not add to favorites")
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
                                () -> {
                                    detailsView.updateFavoriteStatus(false);
                                    detailsView.showMessage("Removed from Favorites");
                                },
                                throwable -> detailsView.showError("Could not remove from favorites")
                        )
        );
    }

    @Override
    public void checkIsFavorite(String mealId) {
        disposable.add(
                mealRepository.isFavorite(mealId)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(
                                isFav -> detailsView.updateFavoriteStatus(isFav),
                                throwable -> detailsView.updateFavoriteStatus(false)
                        )
        );
    }

    @Override
    public void clearResources() {
        disposable.clear();
    }
}
