package com.example.tasty_food_app.datasource.repository.meal;

import android.content.Context;

import com.example.tasty_food_app.datasource.local.SharedPrefsLocalDataSource;
import com.example.tasty_food_app.datasource.local.MealLocalDataSource;
import com.example.tasty_food_app.datasource.model.meal.Meal;
import com.example.tasty_food_app.datasource.model.meal.MealResponse;
import com.example.tasty_food_app.datasource.model.plan.PlanMeal;
import com.example.tasty_food_app.datasource.model.recent.RecentMeal;
import com.example.tasty_food_app.datasource.model.area.AreaResponse;
import com.example.tasty_food_app.datasource.model.category.CategoryResponse;
import com.example.tasty_food_app.datasource.model.ingredient.IngredientResponse;
import com.example.tasty_food_app.datasource.remote.firestore.FirestoreRemoteDataSource;
import com.example.tasty_food_app.datasource.remote.firestore.FirestoreService;
import com.example.tasty_food_app.datasource.remote.meal.MealRemoteDataSource;

import java.util.List;
import java.util.Map;

import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Flowable;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.Single;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class MealRepository {
    MealRemoteDataSource mealRemoteDataSource;
    MealLocalDataSource mealLocalDataSource;
    SharedPrefsLocalDataSource sharedPrefsDataSource;
    private final FirestoreRemoteDataSource firestoreRemoteDataSource;


    public MealRepository(Context context) {
        Context appContext = context.getApplicationContext();
        mealRemoteDataSource = new MealRemoteDataSource();
        mealLocalDataSource = new MealLocalDataSource(context);
        sharedPrefsDataSource = new SharedPrefsLocalDataSource(context);

        firestoreRemoteDataSource = new FirestoreRemoteDataSource(new FirestoreService());
    }

    public Single<Meal> getDailyRandomMeal() {
        String todayDate = new java.text.SimpleDateFormat("yyyyMMdd", java.util.Locale.getDefault()).format(new java.util.Date());

        if (sharedPrefsDataSource.getStoredDailyMealDate().equals(todayDate)) {
            Meal cachedMeal = sharedPrefsDataSource.getStoredDailyMeal();
            if (cachedMeal != null) {
                return Single.just(cachedMeal);
            }
        }

        return mealRemoteDataSource.getRandomMeal()
                .map(response -> response.getMeals().get(0))
                .doOnSuccess(meal -> sharedPrefsDataSource.saveDailyMeal(meal, todayDate));
    }

    public Single<MealResponse> getMealsByLetter(String letter) {
        return mealRemoteDataSource.getMealsByFirstLetter(letter);
    }

    public Single<Meal> getMealById(String mealId) {
        return mealRemoteDataSource.getMealDetails(mealId)
                .map(response -> response.getMeals().get(0));
    }




    public Completable insertMeal(Meal meal, String uId) {
        Completable local = mealLocalDataSource.insertMeal(meal);
        if (uId != null && !uId.isEmpty()) {
            return local.andThen(firestoreRemoteDataSource.uploadFavorite(uId, meal)).subscribeOn(Schedulers.io());
        }
        return local.subscribeOn(Schedulers.io());
    }

    public Completable deleteMeal(Meal meal, String uId) {
        Completable local = mealLocalDataSource.deleteMeal(meal);
        if (uId != null && !uId.isEmpty()) {
            return local.andThen(firestoreRemoteDataSource.deleteFromFirestore(uId, "favorites", meal.idMeal)).subscribeOn(Schedulers.io());
        }
        return local.subscribeOn(Schedulers.io());
    }

    public Flowable<List<Meal>> getStoredMeals() {
        return mealLocalDataSource.getAllStoredMeals();
    }

    public Single<Boolean> isFavorite(String id) {
        return mealLocalDataSource.isFavorite(id);
    }






    public Completable insertRecentlyViewed(Meal meal, String uId) {
        RecentMeal recent = new RecentMeal(meal.getIdMeal(), meal.getStrMeal(), meal.getStrMealThumb(), System.currentTimeMillis());
        Completable local = mealLocalDataSource.insertRecentlyViewed(recent);
        if (uId != null && !uId.isEmpty()) {
            return local.andThen(firestoreRemoteDataSource.uploadRecent(uId, recent)).subscribeOn(Schedulers.io());
        }
        return local.subscribeOn(Schedulers.io());
    }

    public Observable<List<RecentMeal>> getRecentlyViewedMeals() {
        return mealLocalDataSource.getRecentlyViewedMeals();
    }










    public Observable<AreaResponse> getAreas() {
        return mealRemoteDataSource.getAreas();
    }

    public Observable<CategoryResponse> getCategories() {
        return mealRemoteDataSource.getCategories();
    }

    public Observable<IngredientResponse> getIngredients() {
        return mealRemoteDataSource.getIngredients();
    }

    public Observable<MealResponse> searchMealsByName(String name) {
        return mealRemoteDataSource.searchMealsByName(name);
    }

    public Observable<MealResponse> filterByArea(String area) {
        return mealRemoteDataSource.getMealsByArea(area);
    }

    public Observable<MealResponse> filterByCategory(String category) {
        return mealRemoteDataSource.getMealsByCategory(category);
    }

    public Observable<MealResponse> filterByIngredient(String ingredient) {
        return mealRemoteDataSource.getMealsByIngredient(ingredient);
    }






    public Completable insertPlanMeal(PlanMeal planMeal, String uId) {
        Completable local = mealLocalDataSource.insertPlanMeal(planMeal);
        if (uId != null && !uId.isEmpty()) {
            return local.andThen(firestoreRemoteDataSource.uploadPlanMeal(uId, planMeal)).subscribeOn(Schedulers.io());
        }
        return local.subscribeOn(Schedulers.io());
    }

    public Completable deletePlanMeal(PlanMeal planMeal, String uId) {
        Completable local = mealLocalDataSource.deletePlanMeal(planMeal);
        if (uId != null && !uId.isEmpty()) {
            String docId = planMeal.getIdMeal() + "_" + planMeal.getDay();
            return local.andThen(firestoreRemoteDataSource.deleteFromFirestore(uId, "plan", docId)).subscribeOn(Schedulers.io());
        }
        return local.subscribeOn(Schedulers.io());
    }

    public Observable<List<PlanMeal>> getPlanMealsByDay(String userId, String day) {
        return mealLocalDataSource.getMealsByDay(userId, day);
    }

    public Observable<List<PlanMeal>> getAllPlannedMeals(String userId){
        return mealLocalDataSource.getAllPlannedMeals(userId);
    }



    public Single<Meal> getRandomMealForPlan() {
        return mealRemoteDataSource.getRandomMeal()
                .map(response -> response.getMeals().get(0)); //random meal for plan
    }









    public Completable syncAllDataFromCloud(String uId) {
        return Completable.mergeArray(
                syncFavorites(uId),
                syncPlan(uId),
                syncRecent(uId)
        ).subscribeOn(Schedulers.io());
    }

    private Completable syncFavorites(String uId) {
        return firestoreRemoteDataSource.fetchFavorites(uId)
                .flatMapObservable(Observable::fromIterable)
                .map(this::mapToMeal)
                .flatMapCompletable(mealLocalDataSource::insertMeal);
    }

    private Completable syncPlan(String uId) {
        return firestoreRemoteDataSource.fetchPlan(uId)
                .flatMapObservable(Observable::fromIterable)
                .map(map -> new PlanMeal((String) map.get("idMeal"), (String) map.get("strMeal"), (String) map.get("strMealThumb"), (String) map.get("day"), uId))
                .flatMapCompletable(mealLocalDataSource::insertPlanMeal);
    }


    private Completable syncRecent(String uId) {
        return firestoreRemoteDataSource.fetchCollection(uId, "recent")
                .flatMapObservable(Observable::fromIterable)
                .map(map -> new RecentMeal(
                        (String) map.get("idMeal"),
                        (String) map.get("strMeal"),
                        (String) map.get("strMealThumb"),
                        (long) map.get("viewedAt")
                ))
                .flatMapCompletable(mealLocalDataSource::insertRecentlyViewed);
    }




    private Meal mapToMeal(Map<String, Object> map) {
        return new Meal(
                (String) map.get("idMeal"), (String) map.get("strMeal"), (String) map.get("strMealThumb"),
                (String) map.get("strCategory"), (String) map.get("strArea"), (String) map.get("strInstructions"), (String) map.get("strYoutube")
        );
    }
}
