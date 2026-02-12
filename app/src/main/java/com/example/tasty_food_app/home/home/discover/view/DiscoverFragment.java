package com.example.tasty_food_app.home.home.discover.view;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.tasty_food_app.R;
import com.example.tasty_food_app.datasource.model.Meal;
import com.example.tasty_food_app.home.home.discover.presenter.DiscoverPresenter;
import com.example.tasty_food_app.home.home.discover.presenter.DiscoverPresenterImp;

import java.util.List;

public class DiscoverFragment extends Fragment implements DiscoverView, OnMealClick {

    private RecyclerView rvMoreMeals;
    private MealsAdapter adapter;
    private DiscoverPresenter presenter;
    private ProgressBar progressBar;
    private EditText etSearch;
    private ImageView imgRandomMeal;
    private TextView tvRandomMealName;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_discover, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        etSearch = view.findViewById(R.id.etSearch);
        rvMoreMeals = view.findViewById(R.id.rvMoreMeals);
        progressBar = view.findViewById(R.id.progress_circular);
        imgRandomMeal = view.findViewById(R.id.imgRandomMeal);
        tvRandomMealName = view.findViewById(R.id.tvRandomMealName);

        adapter = new MealsAdapter(this);
        rvMoreMeals.setLayoutManager(new GridLayoutManager(getContext(), 2));
        rvMoreMeals.setAdapter(adapter);

        presenter = new DiscoverPresenterImp(requireActivity().getApplication(), this);

        presenter.getRandomMeal();
        presenter.getMealsByLetter("a");

        etSearch.setFocusable(false);
        etSearch.setOnClickListener(v ->
                Navigation.findNavController(v).navigate(R.id.action_discoverFragment_to_searchFragment)
        );
    }

    @Override
    public void onResume() {
        super.onResume();
        presenter.getMealsByLetter("a");
    }

    @Override
    public void addMealToFav(Meal meal) {
        presenter.addToFavorites(meal);
    }

    @Override
    public void deleteMealFromFav(Meal meal) {
        presenter.deleteMealFromFav(meal);
    }

    @Override
    public void onMealDetailsClick(Meal meal) {
        Bundle bundle = new Bundle();
        bundle.putString("mealId", meal.getIdMeal());
        Navigation.findNavController(requireView()).navigate(R.id.action_discoverFragment_to_mealDetailsFragment, bundle);
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
    public void showRandomMeal(Meal meal) {
        if (meal != null) {
            tvRandomMealName.setText(meal.getStrMeal());
            Glide.with(requireContext()).load(meal.getStrMealThumb()).into(imgRandomMeal);
            imgRandomMeal.setOnClickListener(v -> onMealDetailsClick(meal));
        }
    }

    @Override
    public void showMealsByLetter(List<Meal> meals) {
        adapter.setMealList(meals);
    }

    @Override
    public void showError(String errorMessage) {
        Toast.makeText(getContext(), errorMessage, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showMessage(String message) {
        Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (presenter != null) {
            presenter.clearResources();
        }
    }
}