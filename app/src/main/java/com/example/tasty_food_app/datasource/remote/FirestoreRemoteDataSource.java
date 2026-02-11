package com.example.tasty_food_app.datasource.remote;

import com.google.firebase.firestore.DocumentSnapshot;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Single;

public class FirestoreRemoteDataSource {
    private final FirestoreService firestoreService;

    public FirestoreRemoteDataSource(FirestoreService firestoreService) {
        this.firestoreService = firestoreService;
    }


    public Completable addMealToFirestore(String uId, String subCollectionName, String mealId, Map<String, Object> mealData) {
        return Completable.create(emitter -> {
            firestoreService.addToUserSubCollection(uId, subCollectionName, mealId, mealData)
                    .addOnSuccessListener(aVoid -> {
                        if (!emitter.isDisposed()) emitter.onComplete();
                    })
                    .addOnFailureListener(e -> {
                        if (!emitter.isDisposed()) emitter.onError(e);
                    });
        });
    }


    public Completable removeMealFromFirestore(String uId, String subCollectionName, String mealId) {
        return Completable.create(emitter -> {
            firestoreService.removeFromUserSubCollection(uId, subCollectionName, mealId)
                    .addOnSuccessListener(aVoid -> {
                        if (!emitter.isDisposed()) emitter.onComplete();
                    })
                    .addOnFailureListener(e -> {
                        if (!emitter.isDisposed()) emitter.onError(e);
                    });
        });
    }

    public Single<List<Map<String, Object>>> getMealsFromFirestore(String uId, String collectionName) {
        return Single.create(emitter -> {
            firestoreService.getUserSubCollection(uId, collectionName)
                    .addOnSuccessListener(queryDocumentSnapshots -> {
                        List<Map<String, Object>> meals = new ArrayList<>();
                        for (DocumentSnapshot doc : queryDocumentSnapshots) {
                            meals.add(doc.getData());
                        }
                        if (!emitter.isDisposed()) emitter.onSuccess(meals);
                    })
                    .addOnFailureListener(e -> {
                        if (!emitter.isDisposed()) emitter.onError(e);
                    });
        });
    }
}


