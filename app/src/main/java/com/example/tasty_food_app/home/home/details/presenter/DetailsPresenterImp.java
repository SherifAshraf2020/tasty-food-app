package com.example.tasty_food_app.home.home.details.presenter;

import android.util.Log;

import com.example.tasty_food_app.datasource.model.Meal;
import com.example.tasty_food_app.datasource.repository.MealRepository;
import com.example.tasty_food_app.home.home.details.view.DetailsView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class DetailsPresenterImp implements DetailsPresenter{
    private DetailsView detailsView;
    private final MealRepository mealRepository;
    private final CompositeDisposable disposables = new CompositeDisposable();

    public DetailsPresenterImp(DetailsView detailsView, MealRepository mealRepository) {
        this.detailsView = detailsView;
        this.mealRepository = mealRepository;
    }

    private String getCurrentUserId() {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        return (user != null) ? user.getUid() : null;
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
                                    addMealToRecentlyViewed(meal);
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
        String uId = getCurrentUserId();
        if (uId != null) {
            disposables.add(
                    mealRepository.insertMeal(meal, uId)
                            .subscribeOn(Schedulers.io())
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribe(
                                    () -> detailsView.onFavoriteAdded(meal),
                                    throwable -> detailsView.showErrorMessage("Failed to add: " + throwable.getMessage())
                            )
            );
        } else {
            detailsView.showErrorMessage("Please log in to add to favorites");
        }
    }

    @Override
    public void deleteMealFromFav(Meal meal) {
        String uId = getCurrentUserId();
        if (uId != null) {
            disposables.add(
                    mealRepository.deleteMeal(meal, uId)
                            .subscribeOn(Schedulers.io())
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribe(
                                    () -> detailsView.onFavoriteDeleted(meal),
                                    throwable -> detailsView.showErrorMessage("Failed to delete: " + throwable.getMessage())
                            )
            );
        }
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
    public void addMealToRecentlyViewed(Meal meal) {
        String uId = getCurrentUserId();
        if (uId != null) {
            disposables.add(
                    mealRepository.insertRecentlyViewed(meal, uId)
                            .subscribeOn(Schedulers.io())
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribe(
                                    () -> Log.d("DetailsPresenter", "Added to Recent: " + meal.getStrMeal()),
                                    throwable -> Log.e("DetailsPresenter", "Error: " + throwable.getMessage())
                            )
            );
        }
    }

    @Override
    public void clearResources() {
        disposables.clear();
        detailsView = null;
    }
}
