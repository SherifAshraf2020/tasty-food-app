package com.example.tasty_food_app.splash.presenter;

import com.example.tasty_food_app.datasource.repository.auth.AuthRepository;
import com.example.tasty_food_app.splash.view.SplashView;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class SplashPresenterImp implements SplashPresenter{
    private final SplashView view;
    private final AuthRepository authRepository;
    private final CompositeDisposable disposable = new CompositeDisposable();

    public SplashPresenterImp(SplashView view, AuthRepository repository) {
        this.view = view;
        this.authRepository = repository;
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
                                            view.navigateToHome();
                                        } else {
                                            view.navigateToAuth();
                                        }
                                    },
                                    throwable -> view.navigateToAuth()
                            )
            );
        }
    }

    public void clear() {
        disposable.clear();
    }
}
