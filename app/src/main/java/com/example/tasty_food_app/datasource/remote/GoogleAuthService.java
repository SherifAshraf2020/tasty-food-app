package com.example.tasty_food_app.datasource.remote;

import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.GoogleAuthProvider;

public class GoogleAuthService {
    private FirebaseAuth mAuth;

    public GoogleAuthService() {
        mAuth = FirebaseAuth.getInstance();
    }

    public void signInWithGoogle(String idToken, AuthNetworkResponse callback) {
        AuthCredential credential = GoogleAuthProvider.getCredential(idToken, null);
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        callback.onSuccess();
                    } else {
                        String error = task.getException() != null ?
                                task.getException().getMessage() : "Google Sign-In Failed";
                        callback.onFailure(error);
                    }
                });
    }
}
