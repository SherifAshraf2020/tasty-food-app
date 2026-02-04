package com.example.tasty_food_app.auth.log_in;

public interface LoginView {
    void showLoading();
    void hideLoading();
    void onLoginSuccess();
    void onLoginError(String error);
}
