package com.example.tasty_food_app.auth.sign_up.presenter;

import com.example.tasty_food_app.auth.sign_up.view.SignUpView;
import com.example.tasty_food_app.datasource.remote.auth.AuthNetworkResponse;
import com.example.tasty_food_app.datasource.repository.AuthRepository;
import com.example.tasty_food_app.datasource.repository.MealRepository;
import com.google.firebase.auth.FirebaseAuth;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.disposables.CompositeDisposable;

public class SignUpPresenterImp implements SignUpPresenter {

    private final SignUpView signUpView;
    private final AuthRepository authRepository;
    private final MealRepository mealRepository;
    private final CompositeDisposable disposable = new CompositeDisposable();

    public SignUpPresenterImp(SignUpView signUpView, AuthRepository authRepository, MealRepository mealRepository) {
        this.signUpView = signUpView;
        this.authRepository = authRepository;
        this.mealRepository = mealRepository;
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
                        .andThen(Completable.defer(() -> {
                            String uId = FirebaseAuth.getInstance().getCurrentUser().getUid();
                            return mealRepository.syncUserDataFromFirestore(uId);
                        }))
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(
                                () -> {
                                    signUpView.hideLoading();
                                    signUpView.onSignUpSuccess();
                                },
                                throwable -> {
                                    signUpView.hideLoading();
                                    signUpView.onSignUpSuccess();
                                }
                        )
        );
    }

    public void clear() {
        disposable.clear();
    }
}
