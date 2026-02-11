package com.example.tasty_food_app.datasource.repository;

import android.app.Application;
import android.content.Context;

import com.example.tasty_food_app.datasource.SharedPrefsLocalDataSource;
import com.example.tasty_food_app.datasource.local.MealLocalDataSource;
import com.example.tasty_food_app.datasource.model.Meal;
import com.example.tasty_food_app.datasource.model.MealResponse;
import com.example.tasty_food_app.datasource.model.PlanMeal;
import com.example.tasty_food_app.datasource.model.RecentMeal;
import com.example.tasty_food_app.datasource.model.area.AreaResponse;
import com.example.tasty_food_app.datasource.model.category.CategoryResponse;
import com.example.tasty_food_app.datasource.model.ingredient.IngredientResponse;
import com.example.tasty_food_app.datasource.remote.FirestoreRemoteDataSource;
import com.example.tasty_food_app.datasource.remote.meal.MealRemoteDataSource;

import java.util.List;
import java.util.Map;

import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Flowable;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.Single;

public class MealRepository {
   private final MealRemoteDataSource mealRemoteDataSource;
   private final MealLocalDataSource mealLocalDataSource;
   private final SharedPrefsLocalDataSource sharedPrefsDataSource;
   private final FirestoreRemoteDataSource firestoreRemoteDataSource;


    public MealRepository(Context context, FirestoreRemoteDataSource firestoreRemoteDataSource) {
        this.mealRemoteDataSource = new MealRemoteDataSource();
        this.mealLocalDataSource = new MealLocalDataSource(context);
        this.sharedPrefsDataSource = new SharedPrefsLocalDataSource(context);
        this.firestoreRemoteDataSource = firestoreRemoteDataSource;
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
        return mealLocalDataSource.insertMeal(meal)
                .andThen(firestoreRemoteDataSource.addMealToFirestore(
                                uId, "favorites", meal.getIdMeal(), convertMealToMap(meal))
                        .onErrorComplete())
                .subscribeOn(io.reactivex.rxjava3.schedulers.Schedulers.io());
    }

    public Completable deleteMeal(Meal meal, String uId) {
        return mealLocalDataSource.deleteMeal(meal)
                .andThen(firestoreRemoteDataSource.removeMealFromFirestore(uId, "favorites", meal.getIdMeal())
                        .onErrorComplete())
                .subscribeOn(io.reactivex.rxjava3.schedulers.Schedulers.io());
    }

    public Flowable<List<Meal>> getStoredMeals() {
        return mealLocalDataSource.getAllStoredMeals();
    }

    public Single<Boolean> isFavorite(String id) {
        return mealLocalDataSource.isFavorite(id);
    }






    public Completable insertRecentlyViewed(Meal meal, String uId) {
        RecentMeal recent = new RecentMeal(
                meal.getIdMeal(),
                meal.getStrMeal(),
                meal.getStrMealThumb(),
                System.currentTimeMillis()
        );
        return mealLocalDataSource.insertRecentlyViewed(recent)
                .andThen(firestoreRemoteDataSource.addMealToFirestore(
                                uId, "recentlyViewed", meal.getIdMeal(), convertMealToMap(meal))
                        .onErrorComplete())
                .subscribeOn(io.reactivex.rxjava3.schedulers.Schedulers.io());
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
        return mealLocalDataSource.insertPlanMeal(planMeal)
                .andThen(firestoreRemoteDataSource.addMealToFirestore(
                                uId, "mealPlan", planMeal.getIdMeal(), convertPlanToMap(planMeal))
                        .onErrorComplete())
                .subscribeOn(io.reactivex.rxjava3.schedulers.Schedulers.io());
    }

    public Completable deletePlanMeal(PlanMeal planMeal, String uId) {
        return mealLocalDataSource.deletePlanMeal(planMeal)
                .andThen(firestoreRemoteDataSource.removeMealFromFirestore(uId, "mealPlan", planMeal.getIdMeal())
                        .onErrorComplete())
                .subscribeOn(io.reactivex.rxjava3.schedulers.Schedulers.io());
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







    public Completable syncUserDataFromFirestore(String uId) {
        return Completable.mergeArray(
                firestoreRemoteDataSource.getMealsFromFirestore(uId, "favorites")
                        .flatMapCompletable(maps -> {
                            List<Meal> meals = new java.util.ArrayList<>();
                            for (Map<String, Object> map : maps) {
                                Meal meal = new Meal(
                                        (String) map.get("idMeal"),
                                        (String) map.get("strMeal"),
                                        (String) map.get("strMealThumb"),
                                        (String) map.get("strCategory"),
                                        (String) map.get("strArea"),
                                        (String) map.get("strInstructions"),
                                        (String) map.get("strYoutube")
                                );
                                meals.add(meal);
                            }
                            return mealLocalDataSource.insertAllMeals(meals);
                        }),

                firestoreRemoteDataSource.getMealsFromFirestore(uId, "mealPlan")
                        .flatMapCompletable(maps -> {
                            List<PlanMeal> plans = new java.util.ArrayList<>();
                            for (Map<String, Object> map : maps) {
                                PlanMeal plan = new PlanMeal(
                                        (String) map.get("idMeal"),
                                        (String) map.get("strMeal"),
                                        (String) map.get("strMealThumb"),
                                        (String) map.get("day"),
                                        uId
                                );
                                plans.add(plan);
                            }
                            return mealLocalDataSource.insertAllPlans(plans);
                        }),

                firestoreRemoteDataSource.getMealsFromFirestore(uId, "recentlyViewed")
                        .flatMapCompletable(maps -> {
                            List<RecentMeal> recentMeals = new java.util.ArrayList<>();
                            for (Map<String, Object> map : maps) {
                                RecentMeal recent = new RecentMeal(
                                        (String) map.get("idMeal"),
                                        (String) map.get("strMeal"),
                                        (String) map.get("strMealThumb"),
                                        System.currentTimeMillis()
                                );
                                recentMeals.add(recent);
                            }
                            return mealLocalDataSource.insertAllRecent(recentMeals);
                        })
        ).subscribeOn(io.reactivex.rxjava3.schedulers.Schedulers.io());
    }





    public Completable clearAllLocalData() {
        return mealLocalDataSource.clearAllData();
    }





    private Map<String, Object> convertMealToMap(Meal meal) {
        Map<String, Object> map = new java.util.HashMap<>();
        map.put("idMeal", meal.getIdMeal());
        map.put("strMeal", meal.getStrMeal());
        map.put("strMealThumb", meal.getStrMealThumb());
        return map;
    }

    private Map<String, Object> convertPlanToMap(PlanMeal planMeal) {
        Map<String, Object> map = new java.util.HashMap<>();
        map.put("idMeal", planMeal.getIdMeal());
        map.put("strMeal", planMeal.getStrMeal());
        map.put("strMealThumb", planMeal.getStrMealThumb());
        map.put("day", planMeal.getDay());
        return map;
    }
}
