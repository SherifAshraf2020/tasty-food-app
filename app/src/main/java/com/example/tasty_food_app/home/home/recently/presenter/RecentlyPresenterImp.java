package com.example.tasty_food_app.home.home.recently.presenter;

import com.example.tasty_food_app.datasource.repository.auth.AuthRepository;
import com.example.tasty_food_app.datasource.repository.meal.MealRepository;
import com.example.tasty_food_app.home.home.recently.view.RecentlyView;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class RecentlyPresenterImp implements RecentlyPresenter{
    private RecentlyView recentlyView;
    private MealRepository mealRepository;
    private AuthRepository authRepository;
    private final CompositeDisposable disposables = new CompositeDisposable();

    public RecentlyPresenterImp(RecentlyView recentlyView, MealRepository mealRepository, AuthRepository authRepository) {
        this.recentlyView = recentlyView;
        this.mealRepository = mealRepository;
        this.authRepository = authRepository;
    }

    @Override
    public void getRecentMeals() {
        if (authRepository.isGuest()) {
            recentlyView.showGuestView();
        } else {
            disposables.add(
                    mealRepository.getRecentlyViewedMeals()
                            .subscribeOn(Schedulers.io())
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribe(
                                    recentMeals -> {
                                        if (recentMeals != null && !recentMeals.isEmpty()) {
                                            recentlyView.showRecentMeals(recentMeals);
                                        } else {
                                            recentlyView.showEmptyMessage();
                                        }
                                    },
                                    throwable -> recentlyView.showError(throwable.getMessage())
                            )
            );
        }
    }



    @Override
    public void clearResources() {
        disposables.clear();
    }
}
