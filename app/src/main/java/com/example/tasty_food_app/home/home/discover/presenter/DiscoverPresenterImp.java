package com.example.tasty_food_app.home.home.discover.presenter;

import android.app.Application;

import com.example.tasty_food_app.datasource.model.Meal;
import com.example.tasty_food_app.datasource.remote.FirestoreRemoteDataSource;
import com.example.tasty_food_app.datasource.remote.FirestoreService;
import com.example.tasty_food_app.datasource.repository.MealRepository;
import com.example.tasty_food_app.home.home.discover.view.DiscoverView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class DiscoverPresenterImp implements DiscoverPresenter{
    private DiscoverView discoverView;
    private MealRepository mealRepository;
    private CompositeDisposable disposable = new CompositeDisposable();

    public DiscoverPresenterImp(Application application, DiscoverView discoverView) {
        this.mealRepository = new MealRepository(application.getApplicationContext(), new FirestoreRemoteDataSource(new FirestoreService()));
        this.discoverView = discoverView;
    }

    private String getCurrentUserId() {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        return (user != null) ? user.getUid() : null;
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
        String uId = getCurrentUserId();
        if (uId != null) {
            disposable.add(
                    mealRepository.insertMeal(meal, uId)
                            .subscribeOn(Schedulers.io())
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribe(
                                    () -> discoverView.showMessage("Added " + meal.getStrMeal() + " to favorites"),
                                    throwable -> discoverView.showError("Failed to add: " + throwable.getMessage())
                            )
            );
        } else {
            discoverView.showError("Please log in to add favorites");
        }
    }

    @Override
    public void deleteMealFromFav(Meal meal) {
        String uId = getCurrentUserId();
        if (uId != null) {
            disposable.add(
                    mealRepository.deleteMeal(meal, uId)
                            .subscribeOn(Schedulers.io())
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribe(
                                    () -> discoverView.showMessage("Deleted " + meal.getStrMeal() + " from favorites"),
                                    throwable -> discoverView.showError("Failed to delete: " + throwable.getMessage())
                            )
            );
        }
    }

    @Override
    public void clearResources() {
        disposable.clear();
        discoverView = null;
    }


}
