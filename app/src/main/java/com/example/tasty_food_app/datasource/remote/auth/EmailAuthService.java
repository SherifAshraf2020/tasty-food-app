package com.example.tasty_food_app.datasource.remote.auth;

import com.google.firebase.auth.FirebaseAuth;

public class EmailAuthService {

    private FirebaseAuth mAuth;

    public EmailAuthService() {
        mAuth = FirebaseAuth.getInstance();
    }

    public void signUp(String email, String password, AuthNetworkResponse callback) {
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        callback.onSuccess();
                    } else {
                        String error = (task.getException() != null) ?
                                task.getException().getMessage() : "Auth Failed";
                        callback.onFailure(error);
                    }
                });
    }

    public void signIn(String email, String password, AuthNetworkResponse callback) {
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        callback.onSuccess();
                    } else {
                        String error = task.getException() != null ?
                                task.getException().getMessage() : "Login Failed";
                        callback.onFailure(error);
                    }
                });
    }

    public void resetPassword(String email, AuthNetworkResponse callback) {
        mAuth.sendPasswordResetEmail(email)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        callback.onSuccess();
                    } else {
                        String error = task.getException() != null ?
                                task.getException().getMessage() : "Error sending reset email";
                        callback.onFailure(error);
                    }
                });
    }
}
