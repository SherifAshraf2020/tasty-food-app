package com.example.tasty_food_app.datasource.remote.auth;

import android.content.Context;

import com.example.tasty_food_app.datasource.remote.auth.email.EmailAuthService;
import com.example.tasty_food_app.datasource.remote.auth.google.GoogleAuthService;

import io.reactivex.rxjava3.core.Completable;

public class AuthRemoteDataSource {
    private EmailAuthService emailAuthService;
    private GoogleAuthService googleAuthService;

    public AuthRemoteDataSource(Context context) {
        this.emailAuthService = new EmailAuthService();
        this.googleAuthService = new GoogleAuthService(context);
    }

    public Completable signUpWithEmail(String email, String password) {
        return emailAuthService.signUp(email, password);
    }

    public Completable logInWithEmail(String email, String password) {
        return emailAuthService.signIn(email, password);
    }

    public Completable resetPassword(String email) {
        return emailAuthService.resetPassword(email);
    }









    public Completable logInWithGoogle(String idToken) {
        return googleAuthService.signInWithGoogle(idToken);
    }







    public Completable signOut() {
        return emailAuthService.signOut()
                .andThen(googleAuthService.signOut());
    }
}
