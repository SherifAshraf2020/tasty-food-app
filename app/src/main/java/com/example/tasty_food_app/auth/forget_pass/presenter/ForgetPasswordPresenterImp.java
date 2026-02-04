package com.example.tasty_food_app.auth.forget_pass.presenter;

import com.example.tasty_food_app.auth.forget_pass.view.ForgetPasswordView;
import com.example.tasty_food_app.datasource.remote.AuthNetworkResponse;
import com.example.tasty_food_app.datasource.repository.AuthRepository;

public class ForgetPasswordPresenterImp implements ForgetPasswordPresenter {
    private ForgetPasswordView forgetPasswordView;
    private AuthRepository authRepository;

    public  ForgetPasswordPresenterImp(ForgetPasswordView forgetPasswordView, AuthRepository authRepository){
        this.forgetPasswordView = forgetPasswordView;
        this.authRepository = authRepository;
    }
    @Override
    public void sendResetEmail(String email) {
        if (forgetPasswordView != null) {
            forgetPasswordView.showLoading();
        }
        authRepository.ResetPassword(email, new AuthNetworkResponse() {
            @Override
            public void onSuccess() {
                forgetPasswordView.hideLoading();
                forgetPasswordView.onEmailSentSuccess();
            }

            @Override
            public void onFailure(String error) {
                forgetPasswordView.hideLoading();
                forgetPasswordView.onEmailSentError(error);
            }
        });
    }
}
