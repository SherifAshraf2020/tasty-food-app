package com.example.tasty_food_app.datasource.remote.auth.google;

import android.content.Context;

import com.example.tasty_food_app.R;
import com.example.tasty_food_app.datasource.network.FirebaseClient;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.GoogleAuthProvider;

import io.reactivex.rxjava3.core.Completable;

public class GoogleAuthService {
    private final FirebaseAuth mAuth;
    private final GoogleSignInClient mGoogleSignInClient;

    public GoogleAuthService(Context context) {
        this.mAuth = FirebaseClient.getInstance().getAuth();

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(context.getString(R.string.default_web_client_id))
                .requestEmail()
                .build();
        mGoogleSignInClient = GoogleSignIn.getClient(context, gso);
    }

    public Completable signInWithGoogle(String idToken) {
        return Completable.create(emitter -> {
            AuthCredential credential = GoogleAuthProvider.getCredential(idToken, null);
            mAuth.signInWithCredential(credential)
                    .addOnSuccessListener(authResult -> emitter.onComplete())
                    .addOnFailureListener(e -> { if (!emitter.isDisposed()) emitter.onError(e); });
        });
    }

    public Completable signOut() {
        return Completable.create(emitter -> {
            mGoogleSignInClient.signOut().addOnCompleteListener(task -> {
                if (task.isSuccessful()) {
                    emitter.onComplete();
                } else {
                    emitter.onError(task.getException());
                }
            });
        });
    }
}
