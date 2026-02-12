package com.example.tasty_food_app.auth.sign_up.presenter;

import com.example.tasty_food_app.auth.sign_up.view.SignUpView;
import com.example.tasty_food_app.datasource.repository.auth.AuthRepository;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.CompositeDisposable;

public class SignUpPresenterImp implements SignUpPresenter {

    private final SignUpView signUpView;
    private final AuthRepository authRepository;
    private final CompositeDisposable disposable = new CompositeDisposable();

    public SignUpPresenterImp(SignUpView signUpView, AuthRepository authRepository) {
        this.signUpView = signUpView;
        this.authRepository = authRepository;
    }

    @Override
    public void signUp(String name, String email, String password) {
        if (email.isEmpty() || password.isEmpty() || name.isEmpty()) {
            signUpView.onSignUpError("Please fill all fields");
            return;
        }

        signUpView.showLoading();

        disposable.add(
                authRepository.signUpWithEmail(email, password)
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(
                                () -> {
                                    signUpView.hideLoading();
                                    signUpView.onSignUpSuccess();
                                },
                                throwable -> {
                                    signUpView.hideLoading();
                                    signUpView.onSignUpError(throwable.getMessage());
                                }
                        )
        );
    }

    public void clear() {
        disposable.clear();
    }
}
