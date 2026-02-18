package com.example.tasty_food_app.home.home.favorite.view;

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
import android.widget.Toast;

import com.example.tasty_food_app.R;
import com.example.tasty_food_app.datasource.local.SharedPrefsLocalDataSource;
import com.example.tasty_food_app.datasource.model.meal.Meal;

import com.example.tasty_food_app.datasource.remote.auth.AuthRemoteDataSource;
import com.example.tasty_food_app.datasource.repository.auth.AuthRepository;
import com.example.tasty_food_app.datasource.repository.meal.MealRepository;
import com.example.tasty_food_app.home.home.favorite.presenter.FavoritesPresenter;
import com.example.tasty_food_app.home.home.favorite.presenter.FavoritesPresenterImp;

import java.util.ArrayList;
import java.util.List;


public class FavoritesFragment extends Fragment implements FavoritesView, OnFavoriteClick {
    private RecyclerView rvFavorites;
    private FavoritesAdapter favoritesAdapter;
    private FavoritesPresenter presenter;
    private androidx.constraintlayout.widget.ConstraintLayout guestLayout;
    private android.widget.Button btnLogin;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_favorites, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        rvFavorites = view.findViewById(R.id.rv_favorites);
        guestLayout = view.findViewById(R.id.layout_guest_fav);
        btnLogin = view.findViewById(R.id.btn_fav_login);

        GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(), 2);
        rvFavorites.setLayoutManager(gridLayoutManager);

        favoritesAdapter = new FavoritesAdapter(new ArrayList<>(), this);
        rvFavorites.setAdapter(favoritesAdapter);

        MealRepository repository = new MealRepository(requireActivity().getApplicationContext());

        // Initialize AuthRepository
        AuthRepository authRepository = AuthRepository.getInstance(
                new AuthRemoteDataSource(requireContext()),
                new SharedPrefsLocalDataSource(requireContext())
        );

        // Pass authRepository to presenter
        presenter = new FavoritesPresenterImp(this, repository, authRepository);
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
    public void showGuestView() {
        rvFavorites.setVisibility(View.GONE);
        guestLayout.setVisibility(View.VISIBLE);

        btnLogin.setOnClickListener(v -> {
            Navigation.findNavController(v).navigate(R.id.action_global_to_auth_graph);
        });
    }

    @Override
    public void onDeleteClick(Meal meal) {
        presenter.removeMeal(meal);
    }

    @Override
    public void onMealDetailsClick(Meal meal) {
        Bundle bundle = new Bundle();
        bundle.putString("mealId", meal.getIdMeal());
        Navigation.findNavController(requireView()).navigate(R.id.action_favoritesFragment_to_mealDetailsFragment, bundle);

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (presenter != null) {
            presenter.clearResources();
        }
    }
}
