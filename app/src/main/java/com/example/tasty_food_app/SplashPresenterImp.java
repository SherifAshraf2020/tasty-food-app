package com.example.tasty_food_app;

import com.example.tasty_food_app.datasource.SharedPrefsLocalDataSource;

public class SplashPresenterImp implements SplashPresenter{
    private final SplashView view;
    private final SharedPrefsLocalDataSource dataSource;

    public SplashPresenterImp(SplashView view, SharedPrefsLocalDataSource dataSource) {
        this.view = view;
        this.dataSource = dataSource;
    }

    @Override
    public void onAnimationStarted() {
        view.setAppReady();
    }

    @Override
    public void onAnimationFinished() {
        if (dataSource.isOnBoardingFinished()) {
            view.navigateToAuth();
        } else {
            view.navigateToOnBoarding();
        }
    }
}
