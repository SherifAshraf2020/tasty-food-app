package com.example.tasty_food_app.splash.presenter;

import com.example.tasty_food_app.datasource.repository.AuthRepository;
import com.example.tasty_food_app.datasource.repository.MealRepository;
import com.example.tasty_food_app.splash.view.SplashView;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class SplashPresenterImp implements SplashPresenter{
    private final SplashView view;
    private final AuthRepository authRepository;
    private final MealRepository mealRepository;
    private final CompositeDisposable disposable = new CompositeDisposable();

    public SplashPresenterImp(SplashView view, AuthRepository authRepository, MealRepository mealRepository) {
        this.view = view;
        this.authRepository = authRepository;
        this.mealRepository = mealRepository;
    }

    @Override
    public void onAnimationStarted() {
        view.setAppReady();
    }

    @Override
    public void onAnimationFinished() {
        if (!authRepository.isOnBoardingFinished()) {
            view.navigateToOnBoarding();
        } else {
            disposable.add(
                    authRepository.isUserLoggedIn()
                            .subscribeOn(Schedulers.io())
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribe(
                                    isLoggedIn -> {
                                        if (isLoggedIn) {
                                            String uId = com.google.firebase.auth.FirebaseAuth.getInstance().getCurrentUser().getUid();
                                            syncAndNavigate(uId);
                                        } else {
                                            view.navigateToAuth();
                                        }
                                    },
                                    throwable -> view.navigateToAuth()
                            )
            );
        }
    }

    private void syncAndNavigate(String uId) {
        disposable.add(
                mealRepository.syncUserDataFromFirestore(uId)
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(
                                () -> view.navigateToHome(),
                                throwable -> view.navigateToHome()
                        )
        );
    }

    public void clear() {
        disposable.clear();
    }
}
