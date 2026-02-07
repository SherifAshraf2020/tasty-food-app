package com.example.tasty_food_app.home.home.details.presenter;

import com.example.tasty_food_app.datasource.model.Meal;
import com.example.tasty_food_app.datasource.repository.MealRepository;
import com.example.tasty_food_app.home.home.details.view.DetailsView;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class DetailsPresenterImp implements DetailsPresenter{
    private DetailsView detailsView;
    private MealRepository mealRepository;
    private final CompositeDisposable disposables = new CompositeDisposable();

    public DetailsPresenterImp(DetailsView detailsView, MealRepository mealRepository) {
        this.detailsView = detailsView;
        this.mealRepository = mealRepository;
    }

    @Override
    public void getMealDetails(String mealId) {
        disposables.add(
                mealRepository.getMealById(mealId)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .doOnSubscribe(disposable -> detailsView.showLoading())
                        .subscribe(
                                meal -> {
                                    detailsView.hideLoading();
                                    detailsView.showMealDetails(meal);
                                },
                                throwable -> {
                                    detailsView.hideLoading();
                                    detailsView.showErrorMessage(throwable.getMessage());
                                }
                        )
        );
    }

    @Override
    public void addToFavorites(Meal meal) {
        disposables.add(
                mealRepository.insertMeal(meal)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(
                                () -> detailsView.onFavoriteAdded(meal),
                                throwable -> detailsView.showErrorMessage("Failed to add: " + throwable.getMessage())
                        )
        );
    }

    @Override
    public void deleteMealFromFav(Meal meal) {
        disposables.add(
                mealRepository.deleteMeal(meal)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(
                                () -> detailsView.onFavoriteDeleted(meal),
                                throwable -> detailsView.showErrorMessage("Failed to delete: " + throwable.getMessage())
                        )
        );
    }

    @Override
    public void checkIsFavorite(String mealId) {
        disposables.add(
                mealRepository.isFavorite(mealId)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(
                                isFav -> detailsView.updateFavoriteIcon(isFav),
                                throwable -> detailsView.showErrorMessage(throwable.getMessage())
                        )
        );
    }

    @Override
    public void clearResources() {
        disposables.clear();
        detailsView = null;
    }
}
