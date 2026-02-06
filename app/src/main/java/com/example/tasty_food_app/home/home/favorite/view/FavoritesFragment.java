package com.example.tasty_food_app.home.home.favorite.view;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.tasty_food_app.R;
import com.example.tasty_food_app.datasource.model.Meal;

import com.example.tasty_food_app.datasource.repository.MealRepository;
import com.example.tasty_food_app.home.home.favorite.presenter.FavoritesPresenter;
import com.example.tasty_food_app.home.home.favorite.presenter.FavoritesPresenterImp;
import com.example.tasty_food_app.home.home.favorite.view.FavoritesAdapter;

import java.util.ArrayList;
import java.util.List;


public class FavoritesFragment extends Fragment implements FavoritesView, OnFavoriteClick {
    private RecyclerView rvFavorites;
    private FavoritesAdapter favoritesAdapter;
    private FavoritesPresenter presenter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_favorites, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        rvFavorites = view.findViewById(R.id.rv_favorites);

        GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(), 2);
        rvFavorites.setLayoutManager(gridLayoutManager);

        favoritesAdapter = new FavoritesAdapter(new ArrayList<>(), this);
        rvFavorites.setAdapter(favoritesAdapter);

        MealRepository repository = new MealRepository(requireActivity().getApplicationContext());
        presenter = new FavoritesPresenterImp(this, repository);

        presenter.getFavoriteMeals();
    }

    @Override
    public void showFavoriteMeals(List<Meal> meals) {
        if (meals != null) {
            favoritesAdapter.setMealList(meals);
        }
    }

    @Override
    public void showMessage(String message) {
        Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showError(String error) {
        Toast.makeText(getContext(), error, Toast.LENGTH_LONG).show();
    }

    @Override
    public void onDeleteClick(Meal meal) {
        presenter.removeMeal(meal);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (presenter != null) {
            presenter.clearResources();
        }
    }
}
