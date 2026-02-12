package com.example.tasty_food_app.auth.log_in.presenter;

import com.example.tasty_food_app.auth.log_in.view.LoginView;
import com.example.tasty_food_app.datasource.repository.auth.AuthRepository;
import com.example.tasty_food_app.datasource.repository.meal.MealRepository;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.CompositeDisposable;

public class LoginPresenterImp implements LoginPresenter{
    private final LoginView loginView;
    private final AuthRepository authRepository;
    private final MealRepository mealRepository;
    private final CompositeDisposable disposable = new CompositeDisposable();

    public LoginPresenterImp(LoginView loginView, AuthRepository authRepository, MealRepository mealRepository) {
        this.loginView = loginView;
        this.authRepository = authRepository;
        this.mealRepository = mealRepository;
    }

    @Override
    public void signIn(String email, String password) {
        if (email.isEmpty() || password.isEmpty()) {
            loginView.onLoginError("Email and Password cannot be empty");
            return;
        }

        loginView.showLoading();
        disposable.add(
                authRepository.logInWithEmail(email, password, mealRepository)
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
                authRepository.logInWithGoogle(idToken, email, mealRepository)
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
    public void signInAsGuest() {
        loginView.showLoading();
        disposable.add(
                authRepository.logInAsGuest()
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
