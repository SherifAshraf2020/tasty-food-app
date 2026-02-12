package com.example.tasty_food_app.datasource.remote.auth.email;

import com.example.tasty_food_app.datasource.network.FirebaseClient;
import com.google.firebase.auth.FirebaseAuth;

import io.reactivex.rxjava3.core.Completable;

public class EmailAuthService {

    private final FirebaseAuth mAuth;

    public EmailAuthService() {
        this.mAuth = FirebaseClient.getInstance().getAuth();
    }

    public Completable signUp(String email, String password) {
        return Completable.create(emitter ->
                mAuth.createUserWithEmailAndPassword(email, password)
                        .addOnSuccessListener(authResult -> emitter.onComplete())
                        .addOnFailureListener(e -> { if (!emitter.isDisposed()) emitter.onError(e); })
        );
    }

    public Completable signIn(String email, String password) {
        return Completable.create(emitter ->
                mAuth.signInWithEmailAndPassword(email, password)
                        .addOnSuccessListener(authResult -> emitter.onComplete())
                        .addOnFailureListener(e -> { if (!emitter.isDisposed()) emitter.onError(e); })
        );
    }

    public Completable resetPassword(String email) {
        return Completable.create(emitter ->
                mAuth.sendPasswordResetEmail(email)
                        .addOnSuccessListener(unused -> emitter.onComplete())
                        .addOnFailureListener(e -> { if (!emitter.isDisposed()) emitter.onError(e); })
        );
    }

    public Completable signOut() {
        return Completable.fromAction(mAuth::signOut);
    }
}
