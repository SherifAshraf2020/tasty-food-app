package com.example.tasty_food_app.auth.log_in.view;

public interface LoginView {
    void showLoading();
    void hideLoading();
    void onLoginSuccess();
    void onLoginError(String error);
}
