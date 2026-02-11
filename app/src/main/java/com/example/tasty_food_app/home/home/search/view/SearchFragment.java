package com.example.tasty_food_app.home.home.search.view;


import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import com.example.tasty_food_app.R;
import com.example.tasty_food_app.datasource.model.Meal;
import com.example.tasty_food_app.datasource.model.area.Area;
import com.example.tasty_food_app.datasource.model.category.Category;
import com.example.tasty_food_app.datasource.model.ingredient.Ingredient;
import com.example.tasty_food_app.datasource.remote.FirestoreRemoteDataSource;
import com.example.tasty_food_app.datasource.remote.FirestoreService;
import com.example.tasty_food_app.datasource.repository.MealRepository;

import com.example.tasty_food_app.home.home.details.view.DetailsFragment;
import com.example.tasty_food_app.home.home.search.presenter.SearchPresenter;
import com.example.tasty_food_app.home.home.search.presenter.SearchPresenterImp;
import com.google.android.material.chip.ChipGroup;
import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;
import java.util.List;

public class SearchFragment extends Fragment implements SearchView , OnSearchClick{

    private final List<?> EMPTY_LIST = new ArrayList<>(0);
    private String selectedDay = null;
    private String userId = "";

    private EditText etSearch;
    private ChipGroup chipGroupMain;
    private RecyclerView rvCategory, rvFiltersCircular, rvMeals, rvSuggestions;
    private CardView cardSuggestions;
    private ProgressBar progressBar;

    private CategoryAdapter categoryAdapter;
    private FilterAdapter filterAdapter;
    private MealAdapter mealAdapter;
    private SuggestionAdapter suggestionAdapter;

    private SearchPresenter presenter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_search, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        if (getArguments() != null && getArguments().containsKey("selected_day")) {
            selectedDay = getArguments().getString("selected_day");
        }

        if (FirebaseAuth.getInstance().getCurrentUser() != null) {
            userId = FirebaseAuth.getInstance().getCurrentUser().getUid();
        }

        requireActivity().getOnBackPressedDispatcher().addCallback(getViewLifecycleOwner(), new androidx.activity.OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
                if (rvMeals.getVisibility() == View.VISIBLE) {
                    handleEmptySearch(chipGroupMain.getCheckedChipId());
                } else {
                    setEnabled(false);
                    requireActivity().onBackPressed();
                }
            }
        });

        initViews(view);

        FirestoreService firestoreService = new FirestoreService();
        FirestoreRemoteDataSource firestoreRemoteDataSource = new FirestoreRemoteDataSource(firestoreService);
        MealRepository mealRepository = new MealRepository(requireContext(), firestoreRemoteDataSource);

        presenter = new SearchPresenterImp(this, mealRepository);

        setupAdapters();
        setupListeners();
        showOnly(null);
    }

    private void initViews(View view) {
        etSearch = view.findViewById(R.id.etSearch);
        chipGroupMain = view.findViewById(R.id.chipGroupMain);
        rvCategory = view.findViewById(R.id.rvCategory);
        rvFiltersCircular = view.findViewById(R.id.rvFiltersCircular);
        rvMeals = view.findViewById(R.id.rvMeals);
        rvSuggestions = view.findViewById(R.id.rvSuggestions);
        cardSuggestions = view.findViewById(R.id.cardSuggestions);
        progressBar = view.findViewById(R.id.progressBar);

        rvCategory.setLayoutManager(new LinearLayoutManager(getContext()));
        rvSuggestions.setLayoutManager(new LinearLayoutManager(getContext()));
        rvFiltersCircular.setLayoutManager(new GridLayoutManager(getContext(), 3));
        rvMeals.setLayoutManager(new GridLayoutManager(getContext(), 2));
    }

    private void setupAdapters() {
        categoryAdapter = new CategoryAdapter(this);
        rvCategory.setAdapter(categoryAdapter);
        filterAdapter = new FilterAdapter(this);
        rvFiltersCircular.setAdapter(filterAdapter);
        mealAdapter = new MealAdapter(this);
        rvMeals.setAdapter(mealAdapter);
        suggestionAdapter = new SuggestionAdapter(this);
        rvSuggestions.setAdapter(suggestionAdapter);
    }

    private void setupListeners() {
        chipGroupMain.setOnCheckedChangeListener((group, checkedId) -> {
            etSearch.setText("");
            etSearch.clearFocus();
            clearAllAdapters();
            if (checkedId == R.id.chipCategory) {
                presenter.getCategories();
            } else if (checkedId == R.id.chipIngredient) {
                presenter.getIngredients();
            } else if (checkedId == R.id.chipArea) {
                presenter.getAreas();
            } else {
                showOnly(null);
            }
        });

        etSearch.addTextChangedListener(new TextWatcher() {
            @Override public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            @Override public void onTextChanged(CharSequence s, int start, int before, int count) {
                String query = s.toString().trim();
                int checkedId = chipGroupMain.getCheckedChipId();
                if (query.length() > 0) {
                    if (checkedId == R.id.chipCategory) {
                        categoryAdapter.filter(query);
                    } else if (checkedId == R.id.chipIngredient || checkedId == R.id.chipArea) {
                        filterAdapter.filter(query);
                    } else {
                        presenter.searchMeals(query);
                    }
                } else {
                    handleEmptySearch(checkedId);
                }
            }
            @Override public void afterTextChanged(Editable s) {}
        });
    }

    private void showOnly(RecyclerView visibleRecycler) {
        rvCategory.setVisibility(View.GONE);
        rvFiltersCircular.setVisibility(View.GONE);
        rvMeals.setVisibility(View.GONE);
        cardSuggestions.setVisibility(View.GONE);
        if (visibleRecycler != null) {
            visibleRecycler.setVisibility(View.VISIBLE);
        }
    }

    private void handleEmptySearch(int checkedId) {
        cardSuggestions.setVisibility(View.GONE);
        suggestionAdapter.setList(new ArrayList<>());
        mealAdapter.setList(new ArrayList<>());
        if (checkedId == R.id.chipCategory) {
            showOnly(rvCategory);
            presenter.getCategories();
        } else if (checkedId == R.id.chipIngredient || checkedId == R.id.chipArea) {
            showOnly(rvFiltersCircular);
            if (checkedId == R.id.chipIngredient) presenter.getIngredients();
            else presenter.getAreas();
        } else {
            showOnly(null);
        }
    }

    private void clearAllAdapters() {
        categoryAdapter.setList((List<Category>) EMPTY_LIST);
        filterAdapter.setList((List<Object>) EMPTY_LIST);
        mealAdapter.setList((List<Meal>) EMPTY_LIST);
        suggestionAdapter.setList((List<Meal>) EMPTY_LIST);
        cardSuggestions.setVisibility(View.GONE);
    }

    private void navigateToDetails(String mealId) {
        if (mealId != null && !mealId.isEmpty()) {
            DetailsFragment detailsFragment = new DetailsFragment();
            Bundle bundle = new Bundle();
            bundle.putString("mealId", mealId);
            detailsFragment.setArguments(bundle);
            requireActivity().getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fragmentContainerView, detailsFragment)
                    .addToBackStack(null)
                    .commit();
        } else {
            Toast.makeText(getContext(), "Meal ID not found", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void showCategories(List<Category> categories) {
        showOnly(rvCategory);
        categoryAdapter.setList(categories);
    }

    @Override
    public void showIngredients(List<Ingredient> ingredients) {
        showOnly(rvFiltersCircular);
        filterAdapter.setList(new ArrayList<>(ingredients));
    }

    @Override
    public void showAreas(List<Area> areas) {
        showOnly(rvFiltersCircular);
        filterAdapter.setList(new ArrayList<>(areas));
    }

    @Override
    public void showMealsResult(List<Meal> meals) {
        String query = etSearch.getText().toString().trim();
        int checkedChipId = chipGroupMain.getCheckedChipId();
        if (checkedChipId == View.NO_ID) {
            if (query.isEmpty() || meals == null || meals.isEmpty()) {
                cardSuggestions.setVisibility(View.GONE);
                suggestionAdapter.setList(new ArrayList<>());
                return;
            }
            showOnly(null);
            cardSuggestions.setVisibility(View.VISIBLE);
            suggestionAdapter.setList(meals);
        } else {
            cardSuggestions.setVisibility(View.GONE);
            showOnly(rvMeals);
            mealAdapter.setList(meals);
        }
    }

    @Override
    public void showLoading() {
        if (progressBar != null) progressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideLoading() {
        if (progressBar != null) progressBar.setVisibility(View.GONE);
    }

    @Override
    public void showError(String message) {
        Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showMessage(String message) {
        Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showEmptyResult() {
        showOnly(null);
        cardSuggestions.setVisibility(View.GONE);
        Toast.makeText(getContext(), "No results found", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onCategoryClick(Category category) {
        presenter.getMealsByCategory(category.getStrCategory());
    }

    @Override
    public void onAreaClick(Area area) {
        presenter.getMealsByArea(area.getStrArea());
    }

    @Override
    public void onIngredientClick(Ingredient ingredient) {
        presenter.getMealsByIngredient(ingredient.getName());
    }

    @Override
    public void onMealClick(Meal meal) {
        if (selectedDay != null && !selectedDay.isEmpty() && !selectedDay.equalsIgnoreCase("none")) {
            presenter.addToPlan(meal, selectedDay, userId);
            androidx.navigation.Navigation.findNavController(requireView()).popBackStack();
        } else {
            navigateToDetails(meal.getIdMeal());
        }
    }

    @Override
    public void onFavoriteClick(Meal meal) {
        if (meal == null) return;
        boolean wasFavorite = meal.isFavorite();
        meal.setFavorite(!wasFavorite);
        if (mealAdapter != null) mealAdapter.notifyDataSetChanged();
        if (suggestionAdapter != null) suggestionAdapter.notifyDataSetChanged();

        if (wasFavorite) {
            presenter.removeFromFavorite(meal);
            Toast.makeText(getContext(), "Removed from Favorite 💔", Toast.LENGTH_SHORT).show();
        } else {
            presenter.addToFavorite(meal);
            Toast.makeText(getContext(), "Added to Favorite ❤️", Toast.LENGTH_SHORT).show();
        }
    }
}