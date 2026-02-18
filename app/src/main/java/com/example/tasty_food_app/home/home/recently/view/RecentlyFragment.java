package com.example.tasty_food_app.home.home.recently.view;

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
import com.example.tasty_food_app.datasource.model.recent.RecentMeal;
import com.example.tasty_food_app.datasource.remote.auth.AuthRemoteDataSource;
import com.example.tasty_food_app.datasource.repository.auth.AuthRepository;
import com.example.tasty_food_app.datasource.repository.meal.MealRepository;
import com.example.tasty_food_app.home.home.recently.presenter.RecentlyPresenter;
import com.example.tasty_food_app.home.home.recently.presenter.RecentlyPresenterImp;

import java.util.ArrayList;
import java.util.List;


public class RecentlyFragment extends Fragment implements RecentlyView, RecentlyAdapter.OnRecentMealClickListener{
    private androidx.constraintlayout.widget.ConstraintLayout guestLayout;
    private android.widget.Button btnLogin;

    private RecyclerView recyclerView;
    private RecentlyAdapter recentlyAdapter;
    private RecentlyPresenter recentlyPresenter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_recently, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        recyclerView = view.findViewById(R.id.rv_recently);
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 2));
        guestLayout = view.findViewById(R.id.layout_guest_recent);
        btnLogin = view.findViewById(R.id.btn_recent_login);

        AuthRepository authRepo = AuthRepository.getInstance(
                new AuthRemoteDataSource(requireContext()),
                new SharedPrefsLocalDataSource(requireContext())
        );

        recentlyAdapter = new RecentlyAdapter(new ArrayList<>(), this);
        recyclerView.setAdapter(recentlyAdapter);

        recentlyPresenter = new RecentlyPresenterImp(this, new MealRepository(requireContext()), authRepo);
        recentlyPresenter.getRecentMeals();
    }

    @Override
    public void showRecentMeals(List<RecentMeal> recentMeals) {
        recentlyAdapter.setList(recentMeals);
    }

    @Override
    public void showEmptyMessage() {
        recentlyAdapter.setList(new ArrayList<>());
    }

    @Override
    public void showError(String message) {
        Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showGuestView() {
        recyclerView.setVisibility(View.GONE);
        guestLayout.setVisibility(View.VISIBLE);

        btnLogin.setOnClickListener(v -> {
            Navigation.findNavController(v).navigate(R.id.action_global_to_auth_graph);
        });
    }

    @Override
    public void onRecentClick(String mealId) {
        Bundle bundle = new Bundle();
        bundle.putString("mealId", mealId);
        Navigation.findNavController(requireView())
                .navigate(R.id.action_recentlyFragment_to_detailsFragment, bundle);    }
}