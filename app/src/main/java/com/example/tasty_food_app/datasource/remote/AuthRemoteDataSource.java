package com.example.tasty_food_app.datasource.remote;

public class AuthRemoteDataSource {
    private EmailAuthService emailAuthService;

    public AuthRemoteDataSource() {
        this.emailAuthService = new EmailAuthService();
    }

    public void signUpWithEmail(String email, String password, AuthNetworkResponse callback) {
        emailAuthService.signUp(email, password, callback);
    }
    public void LogInWithEmail(String email, String password, AuthNetworkResponse callback) {
        emailAuthService.signIn(email, password, callback);
    }

}
