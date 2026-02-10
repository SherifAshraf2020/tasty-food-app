package com.example.tasty_food_app.home.home.search.presenter;

import com.example.tasty_food_app.datasource.model.Meal;
import com.example.tasty_food_app.datasource.repository.MealRepository;
import com.example.tasty_food_app.home.home.search.view.SearchView;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.schedulers.Schedulers;
import io.reactivex.rxjava3.subjects.PublishSubject;

public class SearchPresenterImp implements SearchPresenter {

    private SearchView searchView;
    private MealRepository mealRepository;
    private CompositeDisposable disposable = new CompositeDisposable();
    private PublishSubject<String> searchSubject = PublishSubject.create();

    public SearchPresenterImp(SearchView searchView, MealRepository mealRepository) {
        this.searchView = searchView;
        this.mealRepository = mealRepository;
        initSearchProcessor();
    }


    @Override
    public void checkFavoritesAndShow(List<Meal> apiMeals) {
        if (apiMeals == null || apiMeals.isEmpty()) {
            searchView.showMealsResult(new ArrayList<>());
            return;
        }

        disposable.add(mealRepository.getStoredMeals()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(favMeals -> {
                    for (Meal apiMeal : apiMeals) {
                        apiMeal.setFavorite(false); // ريسيت
                        for (Meal fav : favMeals) {
                            if (apiMeal.getIdMeal().equals(fav.getIdMeal())) {
                                apiMeal.setFavorite(true);
                                break;
                            }
                        }
                    }
                    searchView.showMealsResult(apiMeals);
                }, throwable -> {
                    searchView.showMealsResult(apiMeals);
                }));
    }

    private void initSearchProcessor() {
        disposable.add(searchSubject
                .debounce(500, TimeUnit.MILLISECONDS)
                .distinctUntilChanged()
                .doOnNext(query -> {
                    if (query.trim().isEmpty()) {
                        searchView.showMealsResult(new ArrayList<>());
                    }
                })
                .filter(query -> !query.trim().isEmpty())
                .switchMap(query -> mealRepository.searchMealsByName(query)
                        .subscribeOn(Schedulers.io()))
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        response -> {
                            if (response.getMeals() != null) {
                                checkFavoritesAndShow(response.getMeals());
                            } else {
                                searchView.showEmptyResult();
                            }
                        },
                        throwable -> searchView.showError(throwable.getMessage())
                ));
    }

    @Override
    public void addToPlan(Meal meal, String day, String userId) {
        com.example.tasty_food_app.datasource.model.PlanMeal planMeal =
                new com.example.tasty_food_app.datasource.model.PlanMeal(
                        meal.getIdMeal(),
                        meal.getStrMeal(),
                        meal.getStrMealThumb(),
                        day,
                        userId
                );

        disposable.add(mealRepository.insertPlanMeal(planMeal)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        () -> { },
                        throwable -> searchView.showError(throwable.getMessage())
                ));
    }

    @Override
    public void addToFavorite(Meal meal) {
        disposable.add(mealRepository.insertMeal(meal)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        () -> {
                            meal.setFavorite(true);
                        },
                        throwable -> searchView.showError(throwable.getMessage())
                ));
    }

    @Override
    public void removeFromFavorite(Meal meal) {
        disposable.add(mealRepository.deleteMeal(meal)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        () -> {
                            meal.setFavorite(false);
                        },
                        throwable -> searchView.showError(throwable.getMessage())
                ));
    }

    @Override
    public void getCategories() {
        searchView.showLoading();
        disposable.add(mealRepository.getCategories()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        response -> {
                            searchView.hideLoading();
                            searchView.showCategories(response.getCategories());
                        },
                        throwable -> {
                            searchView.hideLoading();
                            searchView.showError(throwable.getMessage());
                        }
                ));
    }

    @Override
    public void getIngredients() {
        searchView.showLoading();
        disposable.add(mealRepository.getIngredients()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        response -> {
                            searchView.hideLoading();
                            searchView.showIngredients(response.getIngredients());
                        },
                        throwable -> {
                            searchView.hideLoading();
                            searchView.showError(throwable.getMessage());
                        }
                ));
    }

    @Override
    public void getAreas() {
        searchView.showLoading();
        disposable.add(mealRepository.getAreas()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        response -> {
                            searchView.hideLoading();
                            searchView.showAreas(response.getAreas());
                        },
                        throwable -> {
                            searchView.hideLoading();
                            searchView.showError(throwable.getMessage());
                        }
                ));
    }

    @Override
    public void getMealsByCategory(String categoryName) {
        searchView.showLoading();
        disposable.add(mealRepository.filterByCategory(categoryName)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        response -> {
                            searchView.hideLoading();
                            checkFavoritesAndShow(response.getMeals());
                        },
                        throwable -> {
                            searchView.hideLoading();
                            searchView.showError(throwable.getMessage());
                        }
                ));
    }

    @Override
    public void getMealsByArea(String areaName) {
        searchView.showLoading();
        disposable.add(mealRepository.filterByArea(areaName)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        response -> {
                            searchView.hideLoading();
                            checkFavoritesAndShow(response.getMeals());
                        },
                        throwable -> {
                            searchView.hideLoading();
                            searchView.showError(throwable.getMessage());
                        }
                ));
    }

    @Override
    public void getMealsByIngredient(String ingredientName) {
        searchView.showLoading();
        disposable.add(mealRepository.filterByIngredient(ingredientName)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        response -> {
                            searchView.hideLoading();
                            checkFavoritesAndShow(response.getMeals());
                        },
                        throwable -> {
                            searchView.hideLoading();
                            searchView.showError(throwable.getMessage());
                        }
                ));
    }

    @Override
    public void getMealsByLetter(String letter) {
        disposable.add(mealRepository.getMealsByLetter(letter)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        response -> checkFavoritesAndShow(response.getMeals()),
                        throwable -> searchView.showError(throwable.getMessage())
                ));
    }

    @Override
    public void searchMeals(String query) {
        searchSubject.onNext(query);
    }

    @Override
    public void clearResources() {
        disposable.clear();
    }
}
