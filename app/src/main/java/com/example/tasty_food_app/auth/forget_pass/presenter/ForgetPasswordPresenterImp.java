package com.example.tasty_food_app.auth.forget_pass.presenter;

import com.example.tasty_food_app.auth.forget_pass.view.ForgetPasswordView;
import com.example.tasty_food_app.datasource.repository.auth.AuthRepository;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.CompositeDisposable;

public class ForgetPasswordPresenterImp implements ForgetPasswordPresenter {
    private final ForgetPasswordView forgetPasswordView;
    private final AuthRepository authRepository;
    private final CompositeDisposable disposable = new CompositeDisposable();

    public ForgetPasswordPresenterImp(ForgetPasswordView forgetPasswordView, AuthRepository authRepository) {
        this.forgetPasswordView = forgetPasswordView;
        this.authRepository = authRepository;
    }

    @Override
    public void sendResetEmail(String email) {
        if (email.isEmpty()) {
            forgetPasswordView.onEmailSentError("Email is required");
            return;
        }

        forgetPasswordView.showLoading();

        disposable.add(
                authRepository.resetPassword(email)
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(
                                () -> {
                                    forgetPasswordView.hideLoading();
                                    forgetPasswordView.onEmailSentSuccess();
                                },
                                throwable -> {
                                    forgetPasswordView.hideLoading();
                                    forgetPasswordView.onEmailSentError(throwable.getMessage());
                                }
                        )
        );
    }

    public void clear() {
        disposable.clear();
    }
}
