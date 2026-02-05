package com.example.tasty_food_app.splash.view;

//what Presenter need form UI
public interface SplashView {
    void setAppReady();
    void navigateToAuth();
    void navigateToOnBoarding();
    void navigateToHome();
}
