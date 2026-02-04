package com.example.tasty_food_app.auth.sign_up.presenter;

import com.example.tasty_food_app.auth.sign_up.view.SignUpView;
import com.example.tasty_food_app.datasource.remote.AuthNetworkResponse;
import com.example.tasty_food_app.datasource.repository.AuthRepository;

public class SignUpPresenterImp implements SignUpPresenter {

    private SignUpView signUpView;
    private AuthRepository authRepository;

    public SignUpPresenterImp(SignUpView signUpView, AuthRepository authRepository) {
        this.signUpView = signUpView;
        this.authRepository = authRepository;
    }

    @Override
    public void signUp(String name, String email, String password) {
        if (signUpView != null) {
            signUpView.showLoading();
        }
        authRepository.SignUpWithEmail(email, password, new AuthNetworkResponse() {
            @Override
            public void onSuccess() {
                if (signUpView != null) {
                    signUpView.hideLoading();
                    signUpView.onSignUpSuccess();
                }
            }

            @Override
            public void onFailure(String error) {
                if(signUpView != null)
                {
                    signUpView.hideLoading();
                    signUpView.onSignUpError(error);
                }
            }
        });
    }
}
