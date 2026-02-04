package com.example.tasty_food_app.auth.sign_up.view;

public interface SignUpView {
    void showLoading();
    void hideLoading();
    void onSignUpSuccess();
    void onSignUpError(String message);
}
