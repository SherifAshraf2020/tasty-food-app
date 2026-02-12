package com.example.tasty_food_app.home.home.favorite.presenter;

import com.example.tasty_food_app.datasource.model.meal.Meal;
import com.example.tasty_food_app.datasource.repository.auth.AuthRepository;
import com.example.tasty_food_app.datasource.repository.meal.MealRepository;
import com.example.tasty_food_app.home.home.favorite.view.FavoritesView;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class FavoritesPresenterImp implements FavoritesPresenter{

    private final FavoritesView favoritesView;
    private final MealRepository mealRepository;
    private final AuthRepository authRepository;
    private final CompositeDisposable disposable = new CompositeDisposable();

    public FavoritesPresenterImp(FavoritesView favoritesView, MealRepository mealRepository, AuthRepository authRepository) {
        this.favoritesView = favoritesView;
        this.mealRepository = mealRepository;
        this.authRepository = authRepository;
    }

    @Override
    public void getFavoriteMeals() {
        disposable.add(
                mealRepository.getStoredMeals()
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(
                                meals -> favoritesView.showFavoriteMeals(meals),
                                throwable -> favoritesView.showError(throwable.getMessage())
                        )
        );
    }

    @Override
    public void removeMeal(Meal meal) {
        String uId = authRepository.getCurrentUserId();
        disposable.add(
                mealRepository.deleteMeal(meal, uId)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(
                                () -> favoritesView.showMessage("Removed: " + meal.getStrMeal()),
                                throwable -> favoritesView.showError(throwable.getMessage())
                        )
        );
    }

    @Override
    public void clearResources() {
        disposable.clear();
    }


}
