package com.example.tasty_food_app.auth.log_in.presenter;

import com.example.tasty_food_app.auth.log_in.view.LoginView;
import com.example.tasty_food_app.datasource.remote.auth.AuthNetworkResponse;
import com.example.tasty_food_app.datasource.repository.AuthRepository;
import com.example.tasty_food_app.datasource.repository.MealRepository;

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
                authRepository.logInWithEmail(email, password)
                        .andThen(io.reactivex.rxjava3.core.Completable.defer(() -> {
                            String uId = com.google.firebase.auth.FirebaseAuth.getInstance().getCurrentUser().getUid();
                            return mealRepository.syncUserDataFromFirestore(uId);
                        }))
                        .observeOn(io.reactivex.rxjava3.android.schedulers.AndroidSchedulers.mainThread())
                        .subscribe(
                                () -> {
                                    loginView.hideLoading();
                                    loginView.onLoginSuccess();
                                },
                                throwable -> {
                                    loginView.hideLoading();
                                    loginView.onLoginSuccess();
                                }
                        )
        );
    }

    @Override
    public void signInWithGoogle(String idToken, String email) {
        loginView.showLoading();
        disposable.add(
                authRepository.logInWithGoogle(idToken, email)
                        .andThen(io.reactivex.rxjava3.core.Completable.defer(() -> {
                            String uId = com.google.firebase.auth.FirebaseAuth.getInstance().getCurrentUser().getUid();
                            return mealRepository.syncUserDataFromFirestore(uId);
                        }))
                        .observeOn(io.reactivex.rxjava3.android.schedulers.AndroidSchedulers.mainThread())
                        .subscribe(
                                () -> {
                                    loginView.hideLoading();
                                    loginView.onLoginSuccess();
                                },
                                throwable -> {
                                    loginView.hideLoading();
                                    loginView.onLoginSuccess();
                                }
                        )
        );
    }

    public void clear() {
        disposable.clear();
    }
}
