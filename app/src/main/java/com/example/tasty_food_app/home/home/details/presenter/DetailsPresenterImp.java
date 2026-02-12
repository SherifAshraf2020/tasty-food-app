package com.example.tasty_food_app.home.home.details.presenter;

import com.example.tasty_food_app.datasource.model.meal.Meal;
import com.example.tasty_food_app.datasource.repository.auth.AuthRepository;
import com.example.tasty_food_app.datasource.repository.meal.MealRepository;
import com.example.tasty_food_app.home.home.details.view.DetailsView;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class DetailsPresenterImp implements DetailsPresenter{
    private DetailsView detailsView;
    private final MealRepository mealRepository;
    private final AuthRepository authRepository;
    private final CompositeDisposable disposables = new CompositeDisposable();

    public DetailsPresenterImp(DetailsView detailsView, MealRepository mealRepository, AuthRepository authRepository) {
        this.detailsView = detailsView;
        this.mealRepository = mealRepository;
        this.authRepository = authRepository;
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
        String uId = authRepository.getCurrentUserId();
        disposables.add(
                mealRepository.insertMeal(meal, uId)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(
                                () -> detailsView.onFavoriteAdded(meal),
                                throwable -> detailsView.showErrorMessage("Failed: " + throwable.getMessage())
                        )
        );
    }

    @Override
    public void deleteMealFromFav(Meal meal) {
        String uId = authRepository.getCurrentUserId();
        disposables.add(
                mealRepository.deleteMeal(meal, uId)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(
                                () -> detailsView.onFavoriteDeleted(meal),
                                throwable -> detailsView.showErrorMessage("Failed: " + throwable.getMessage())
                        )
        );
    }

    @Override
    public void addMealToRecentlyViewed(Meal meal) {
        String uId = authRepository.getCurrentUserId();
        disposables.add(
                mealRepository.insertRecentlyViewed(meal, uId)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(
                                () -> android.util.Log.d("Details", "Recent Sync: " + meal.getStrMeal()),
                                throwable -> android.util.Log.e("Details", "Recent Error: " + throwable.getMessage())
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
