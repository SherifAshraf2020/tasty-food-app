package com.example.tasty_food_app.auth.log_in.presenter;

public interface LoginPresenter {
    void signIn(String email, String password);
    void signInWithGoogle(String idToken, String email);
}
