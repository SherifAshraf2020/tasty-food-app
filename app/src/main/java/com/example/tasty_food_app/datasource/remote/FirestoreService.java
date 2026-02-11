package com.example.tasty_food_app.datasource.remote;

import com.example.tasty_food_app.datasource.network.FirebaseClient;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.Map;

public class FirestoreService {
    private final FirebaseFirestore db;

    public FirestoreService() {
        this.db = FirebaseClient.getInstance().getFirestore();
    }

    public Task<Void> addToUserSubCollection(String uId, String subCollectionName, String mealId, Map<String, Object> data) {
        return db.collection("users")
                .document(uId)
                .collection(subCollectionName)
                .document(mealId)
                .set(data);
    }

    public Task<Void> removeFromUserSubCollection(String uId, String subCollectionName, String mealId) {
        return db.collection("users")
                .document(uId)
                .collection(subCollectionName)
                .document(mealId)
                .delete();
    }


    public Task<QuerySnapshot> getUserSubCollection(String uId, String subCollectionName) {
        return db.collection("users")
                .document(uId)
                .collection(subCollectionName)
                .get();
    }


}
