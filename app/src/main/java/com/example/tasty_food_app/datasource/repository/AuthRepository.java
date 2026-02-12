package com.example.tasty_food_app.datasource.repository;

import com.example.tasty_food_app.datasource.SharedPrefsLocalDataSource;
import com.example.tasty_food_app.datasource.remote.auth.AuthNetworkResponse;
import com.example.tasty_food_app.datasource.remote.auth.AuthRemoteDataSource;
import com.google.firebase.auth.FirebaseAuth;

import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Single;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class AuthRepository {
    private final AuthRemoteDataSource authRemoteDataSource;
    private final SharedPrefsLocalDataSource sharedPrefsLocalDataSource;
    private static AuthRepository instance = null;

    private AuthRepository(AuthRemoteDataSource authRemoteDataSource, SharedPrefsLocalDataSource sharedPrefsLocalDataSource) {
        this.authRemoteDataSource = authRemoteDataSource;
        this.sharedPrefsLocalDataSource = sharedPrefsLocalDataSource;
    }

    public static AuthRepository getInstance(AuthRemoteDataSource authRemoteDataSource, SharedPrefsLocalDataSource sharedPrefsLocalDataSource) {
        if (instance == null) {
            instance = new AuthRepository(authRemoteDataSource, sharedPrefsLocalDataSource);
        }
        return instance;
    }

    public Single<Boolean> isUserLoggedIn() {
        return sharedPrefsLocalDataSource.isLoggedIn();
    }

    public void setOnBoardingFinished(boolean isFinished) {
        sharedPrefsLocalDataSource.setOnBoardingFinished(isFinished);
    }

    public boolean isOnBoardingFinished() {
        return sharedPrefsLocalDataSource.isOnBoardingFinished();
    }

    public Completable logout() {
        return sharedPrefsLocalDataSource.clearSession()
                .andThen(authRemoteDataSource.signOut())
                .onErrorComplete()
                .subscribeOn(Schedulers.io());
    }

    public Completable signUpWithEmail(String email, String password) {
        return authRemoteDataSource.signUpWithEmail(email, password)
                .andThen(sharedPrefsLocalDataSource.saveUserSession(email))
                .subscribeOn(Schedulers.io());
    }

    public Completable logInWithEmail(String email, String password, MealRepository mealRepository) {
        return authRemoteDataSource.logInWithEmail(email, password)
                .andThen(sharedPrefsLocalDataSource.saveUserSession(email))
                .andThen(Completable.defer(() -> {
                    String uId = FirebaseAuth.getInstance().getCurrentUser().getUid();
                    return mealRepository.syncAllDataFromCloud(uId);
                }))
                .subscribeOn(Schedulers.io());
    }

    public Completable logInWithGoogle(String idToken, String email, MealRepository mealRepository) {
        return authRemoteDataSource.logInWithGoogle(idToken)
                .andThen(sharedPrefsLocalDataSource.saveUserSession(email))
                .andThen(Completable.defer(() -> {
                    String uId = FirebaseAuth.getInstance().getCurrentUser().getUid();
                    return mealRepository.syncAllDataFromCloud(uId);
                }))
                .subscribeOn(Schedulers.io());
    }

    public Completable resetPassword(String email) {
        return authRemoteDataSource.resetPassword(email)
                .subscribeOn(Schedulers.io());
    }

    public String getCurrentUserId() {
        if (FirebaseAuth.getInstance().getCurrentUser() != null) {
            return FirebaseAuth.getInstance().getCurrentUser().getUid();
        }
        return null;
    }
    public String getCurrentUserEmail() {
        if (FirebaseAuth.getInstance().getCurrentUser() != null) {
            return FirebaseAuth.getInstance().getCurrentUser().getEmail();
        }
        return "Guest";
    }
}
