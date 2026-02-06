package com.example.tasty_food_app.datasource.remote.auth;

public class AuthRemoteDataSource {
    private EmailAuthService emailAuthService;
    private GoogleAuthService googleAuthService;

    public AuthRemoteDataSource() {
        this.emailAuthService = new EmailAuthService();
        this.googleAuthService = new GoogleAuthService();
    }

    public void signUpWithEmail(String email, String password, AuthNetworkResponse callback) {
        emailAuthService.signUp(email, password, callback);
    }
    public void logInWithEmail(String email, String password, AuthNetworkResponse callback) {
        emailAuthService.signIn(email, password, callback);
    }

    public void resetPassword(String email, AuthNetworkResponse callback) {
        emailAuthService.resetPassword(email, callback);
    }

    public void logInWithGoogle(String idToken, AuthNetworkResponse callback) {
        googleAuthService.signInWithGoogle(idToken, callback);
    }
}
