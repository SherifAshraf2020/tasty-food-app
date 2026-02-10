package com.example.tasty_food_app.auth.log_in.presenter;

import com.example.tasty_food_app.auth.log_in.view.LoginView;
import com.example.tasty_food_app.datasource.remote.auth.AuthNetworkResponse;
import com.example.tasty_food_app.datasource.repository.AuthRepository;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.CompositeDisposable;

public class LoginPresenterImp implements LoginPresenter{
    private final LoginView loginView;
    private final AuthRepository authRepository;
    private final CompositeDisposable disposable = new CompositeDisposable();

    public LoginPresenterImp(LoginView loginView, AuthRepository authRepository) {
        this.loginView = loginView;
        this.authRepository = authRepository;
    }

    @Override
    public void signIn(String email, String password) {
        if (email.isEmpty() || password.isEmpty()) {
            loginView.onLoginError("Email and Password cannot be empty");
            return;
        }

        loginView.showLoading();
        disposable.add(
                authRepository.logInWithEmail(email, password)
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(
                                () -> {
                                    loginView.hideLoading();
                                    loginView.onLoginSuccess();
                                },
                                throwable -> {
                                    loginView.hideLoading();
                                    loginView.onLoginError(throwable.getMessage());
                                }
                        )
        );
    }


    @Override
    public void signInWithGoogle(String idToken, String email) {
        loginView.showLoading();
        disposable.add(
                authRepository.logInWithGoogle(idToken, email)
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(
                                () -> {
                                    loginView.hideLoading();
                                    loginView.onLoginSuccess();
                                },
                                throwable -> {
                                    loginView.hideLoading();
                                    loginView.onLoginError(throwable.getMessage());
                                }
                        )
        );
    }

    public void clear() {
        disposable.clear();
    }
}
