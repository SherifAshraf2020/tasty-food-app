package com.example.tasty_food_app.auth.forget_pass.view;

public interface ForgetPasswordView {
    void onEmailSentSuccess();
    void onEmailSentError(String errorMessage);
    void showLoading();
    void hideLoading();
}
