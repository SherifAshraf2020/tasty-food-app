package com.example.tasty_food_app.splash.presenter;

import com.example.tasty_food_app.datasource.repository.AuthRepository;
import com.example.tasty_food_app.splash.view.SplashView;

public class SplashPresenterImp implements SplashPresenter{
    private final SplashView view;
    private final AuthRepository authRepository;

    public SplashPresenterImp(SplashView view, AuthRepository repository) {
        this.view = view;
        this.authRepository = repository;
    }

    @Override
    public void onAnimationStarted() {
        view.setAppReady();
    }

    @Override
    public void onAnimationFinished() {

        if (!authRepository.isOnBoardingFinished()) {
            view.navigateToOnBoarding();
        } else if (authRepository.isUserLoggedIn()) {
            view.navigateToHome();
        } else {
            view.navigateToAuth();
        }
    }
}
