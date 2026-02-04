package com.example.tasty_food_app.auth;

public interface ForgetPasswordView {
    void onEmailSentSuccess();
    void onEmailSentError(String errorMessage);
    void showLoading();
    void hideLoading();
}
