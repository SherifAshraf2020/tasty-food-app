package com.example.tasty_food_app.datasource.remote.firestore;

import com.example.tasty_food_app.datasource.network.FirebaseClient;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Single;

public class FirestoreService {
    private final FirebaseFirestore db;

    public FirestoreService() {
        this.db = FirebaseClient.getInstance().getFirestore();
    }

    public Completable save(String collectionPath, String docId, Map<String, Object> data) {
        return Completable.create(emitter -> {
            db.collection(collectionPath).document(docId).set(data)
                    .addOnSuccessListener(aVoid -> { if (!emitter.isDisposed()) emitter.onComplete(); })
                    .addOnFailureListener(e -> { if (!emitter.isDisposed()) emitter.onError(e); });
        });
    }

    public Completable delete(String collectionPath, String docId) {
        return Completable.create(emitter -> {
            db.collection(collectionPath).document(docId).delete()
                    .addOnSuccessListener(aVoid -> { if (!emitter.isDisposed()) emitter.onComplete(); })
                    .addOnFailureListener(e -> { if (!emitter.isDisposed()) emitter.onError(e); });
        });
    }

    public Single<List<Map<String, Object>>> getAll(String collectionPath) {
        return Single.create(emitter -> {
            db.collection(collectionPath).get()
                    .addOnSuccessListener(querySnapshot -> {
                        List<Map<String, Object>> list = new ArrayList<>();
                        for (DocumentSnapshot doc : querySnapshot.getDocuments()) {
                            if (doc.getData() != null) list.add(doc.getData());
                        }
                        if (!emitter.isDisposed()) emitter.onSuccess(list);
                    })
                    .addOnFailureListener(e -> { if (!emitter.isDisposed()) emitter.onError(e); });
        });
    }
}
