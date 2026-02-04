package com.example.tasty_food_app.auth.log_in.presenter;

import com.example.tasty_food_app.auth.log_in.view.LoginView;
import com.example.tasty_food_app.datasource.remote.AuthNetworkResponse;
import com.example.tasty_food_app.datasource.repository.AuthRepository;

public class LoginPresenterImp implements LoginPresenter{
    private LoginView loginView;
    private AuthRepository authRepository;

    public LoginPresenterImp(LoginView loginView, AuthRepository authRepository) {
        this.loginView = loginView;
        this.authRepository = authRepository;
    }
    @Override
    public void signIn(String email, String password) {
        authRepository.LogInWithEmail(email, password, new AuthNetworkResponse() {
            @Override
            public void onSuccess() {
                if (loginView != null) {
                    loginView.hideLoading();
                    loginView.onLoginSuccess();
                }
            }

            @Override
            public void onFailure(String error) {
                if (loginView != null) {
                    loginView.hideLoading();
                    loginView.onLoginError(error);
                }
            }
        });
    }
}
