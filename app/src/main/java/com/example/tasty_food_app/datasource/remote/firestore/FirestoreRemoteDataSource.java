package com.example.tasty_food_app.datasource.remote.firestore;

import com.example.tasty_food_app.datasource.model.meal.Meal;
import com.example.tasty_food_app.datasource.model.plan.PlanMeal;
import com.example.tasty_food_app.datasource.model.recent.RecentMeal;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Single;

public class FirestoreRemoteDataSource {
    private final FirestoreService service;

    public FirestoreRemoteDataSource(FirestoreService service) {
        this.service = service;
    }

    public Completable uploadFavorite(String uId, Meal meal) {
        return service.save("users/" + uId + "/favorites", meal.idMeal, mealToMap(meal));
    }

    public Completable uploadPlanMeal(String uId, PlanMeal plan) {
        String docId = plan.getIdMeal() + "_" + plan.getDay();
        return service.save("users/" + uId + "/plan", docId, planToMap(plan));
    }

    public Completable uploadRecent(String uId, RecentMeal recent) {
        return service.save("users/" + uId + "/recent", recent.getIdMeal(), recentToMap(recent));
    }

    public Completable deleteFromFirestore(String uId, String collectionName, String docId) {
        return service.delete("users/" + uId + "/" + collectionName, docId);
    }

    public Single<List<Map<String, Object>>> fetchCollection(String uId, String collectionName) {
        return service.getAll("users/" + uId + "/" + collectionName);
    }

    public Single<List<Map<String, Object>>> fetchFavorites(String uId) {
        return fetchCollection(uId, "favorites");
    }

    public Single<List<Map<String, Object>>> fetchPlan(String uId) {
        return fetchCollection(uId, "plan");
    }

    private Map<String, Object> mealToMap(Meal meal) {
        Map<String, Object> map = new HashMap<>();
        map.put("idMeal", meal.idMeal);
        map.put("strMeal", meal.strMeal);
        map.put("strMealThumb", meal.strMealThumb);
        map.put("strCategory", meal.strCategory);
        map.put("strArea", meal.strArea);
        map.put("strInstructions", meal.strInstructions);
        map.put("strYoutube", meal.strYoutube);
        return map;
    }

    private Map<String, Object> planToMap(PlanMeal plan) {
        Map<String, Object> map = new HashMap<>();
        map.put("idMeal", plan.getIdMeal());
        map.put("day", plan.getDay());
        map.put("userId", plan.getUserId());
        map.put("strMeal", plan.getStrMeal());
        map.put("strMealThumb", plan.getStrMealThumb());
        return map;
    }

    private Map<String, Object> recentToMap(RecentMeal recent) {
        Map<String, Object> map = new HashMap<>();
        map.put("idMeal", recent.getIdMeal());
        map.put("viewedAt", recent.getViewedAt());
        map.put("strMeal", recent.getStrMeal());
        map.put("strMealThumb", recent.getStrMealThumb());
        return map;
    }
}
