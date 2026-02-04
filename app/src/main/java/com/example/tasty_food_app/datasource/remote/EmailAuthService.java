package com.example.tasty_food_app.datasource.remote;

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
}
