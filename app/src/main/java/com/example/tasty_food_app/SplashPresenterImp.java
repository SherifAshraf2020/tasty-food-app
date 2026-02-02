package com.example.tasty_food_app;

import com.example.tasty_food_app.datasource.SharedPrefsLocalDataSource;

public class SplashPresenterImp implements SplashPresenter{

    private final SplashView view;
    private final SharedPrefsLocalDataSource sharedPrefsLocalDataSource;

    public SplashPresenterImp(SplashView view, SharedPrefsLocalDataSource sharedPrefsLocalDataSource) {
        this.view = view;
        this.sharedPrefsLocalDataSource = sharedPrefsLocalDataSource;
    }


    @Override
    public void checkDestination() {
        if (sharedPrefsLocalDataSource.isOnBoardingFinished()) {
            view.navigateToHome();
        } else {
            view.navigateToOnBoarding();
        }
    }
}
