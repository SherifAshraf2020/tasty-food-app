package com.example.tasty_food_app.datasource.network;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

public class FirebaseClient {
    private static FirebaseClient instance = null;
    private final FirebaseAuth mAuth;
    private final FirebaseFirestore mFirestore;

    private FirebaseClient() {
        mAuth = FirebaseAuth.getInstance();
        mFirestore = FirebaseFirestore.getInstance();
    }

    public static synchronized FirebaseClient getInstance() {
        if (instance == null) {
            instance = new FirebaseClient();
        }
        return instance;
    }

    public FirebaseAuth getAuth() {
        return mAuth;
    }

    public FirebaseFirestore getFirestore() {
        return mFirestore;
    }
}
