package com.example.tasty_food_app.home.home.recently.presenter;

import com.example.tasty_food_app.datasource.repository.MealRepository;
import com.example.tasty_food_app.home.home.recently.view.RecentlyView;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class RecentlyPresenterImp implements RecentlyPresenter{
    private RecentlyView recentlyView;
    private MealRepository mealRepository;
    private final CompositeDisposable disposables = new CompositeDisposable();

    public RecentlyPresenterImp(RecentlyView recentlyView, MealRepository mealRepository) {
        this.recentlyView = recentlyView;
        this.mealRepository = mealRepository;
    }

    @Override
    public void getRecentMeals() {
        disposables.add(
                mealRepository.getRecentlyViewedMeals() // الميثود اللي بترجع Observable<List<RecentMeal>>
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



    @Override
    public void clearResources() {
        disposables.clear();
        recentlyView = null; // تجنباً للـ Memory Leak
    }
}
