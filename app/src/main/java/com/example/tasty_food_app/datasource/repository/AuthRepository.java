package com.example.tasty_food_app.datasource.repository;

import com.example.tasty_food_app.datasource.SharedPrefsLocalDataSource;
import com.example.tasty_food_app.datasource.remote.AuthNetworkResponse;
import com.example.tasty_food_app.datasource.remote.AuthRemoteDataSource;

public class AuthRepository {
    private AuthRemoteDataSource authRemoteDataSource;
    private SharedPrefsLocalDataSource sharedPrefsLocalDataSource;
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


    public void saveUserSession(String email) {
        sharedPrefsLocalDataSource.saveUserSession(email);
    }

    public boolean isUserLoggedIn() {
        return sharedPrefsLocalDataSource.isLoggedIn();
    }

    public void setOnBoardingFinished(boolean isFinished) {
        sharedPrefsLocalDataSource.setOnBoardingFinished(isFinished);
    }

    public boolean isOnBoardingFinished() {
        return sharedPrefsLocalDataSource.isOnBoardingFinished();
    }

    public void logout() {
        sharedPrefsLocalDataSource.clearSession();
    }


    public void SignUpWithEmail(String email, String password, AuthNetworkResponse callback) {
        authRemoteDataSource.signUpWithEmail(email, password, callback);
    }

    public void LogInWithEmail(String email, String password, AuthNetworkResponse callback) {
        authRemoteDataSource.logInWithEmail(email, password, callback);
    }

    public void ResetPassword(String email, AuthNetworkResponse callback) {
        authRemoteDataSource.resetPassword(email, callback);
    }

    public void LogInWithGoogle(String idToken, AuthNetworkResponse callback) {
        authRemoteDataSource.logInWithGoogle(idToken, callback);
    }
}
