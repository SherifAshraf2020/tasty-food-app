package com.example.tasty_food_app.home.settings;

import com.example.tasty_food_app.datasource.repository.auth.AuthRepository;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.CompositeDisposable;

public class SettingsPresenterImp implements SettingsPresenter {
    private final SettingsView settingsView;
    private final AuthRepository authRepository;
    private final CompositeDisposable disposable = new CompositeDisposable();

    public SettingsPresenterImp(SettingsView settingsView, AuthRepository authRepository) {
        this.settingsView = settingsView;
        this.authRepository = authRepository;
    }

    @Override
    public void logout() {
        disposable.add(
                authRepository.logout()
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(
                                () -> settingsView.onLogoutSuccess(),
                                throwable -> settingsView.showError(throwable.getMessage())
                        )
        );
    }

    @Override
    public void clearResources() {
        disposable.clear();
    }

    @Override
    public void loadUserData() {
        String email = authRepository.getCurrentUserEmail();
        settingsView.displayUserEmail(email);
    }
}
