package com.example.tasty_food_app.datasource.repository;

import com.example.tasty_food_app.datasource.remote.AuthNetworkResponse;
import com.example.tasty_food_app.datasource.remote.AuthRemoteDataSource;

public class AuthRepository {
    private AuthRemoteDataSource authRemoteDataSource;
    private static AuthRepository instance = null;

    private AuthRepository(AuthRemoteDataSource authRemoteDataSource) {
        this.authRemoteDataSource = authRemoteDataSource;
    }

    public static AuthRepository getInstance(AuthRemoteDataSource authRemoteDataSource) {
        if (instance == null) {
            instance = new AuthRepository(authRemoteDataSource);
        }
        return instance;
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
}
