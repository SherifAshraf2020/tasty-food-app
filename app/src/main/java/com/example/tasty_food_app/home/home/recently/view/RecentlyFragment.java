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
import com.example.tasty_food_app.datasource.model.RecentMeal;
import com.example.tasty_food_app.datasource.remote.FirestoreRemoteDataSource;
import com.example.tasty_food_app.datasource.remote.FirestoreService;
import com.example.tasty_food_app.datasource.repository.MealRepository;
import com.example.tasty_food_app.home.home.recently.presenter.RecentlyPresenter;
import com.example.tasty_food_app.home.home.recently.presenter.RecentlyPresenterImp;

import java.util.ArrayList;
import java.util.List;


public class RecentlyFragment extends Fragment implements RecentlyView, RecentlyAdapter.OnRecentMealClickListener{

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
        recentlyAdapter = new RecentlyAdapter(new ArrayList<>(), this);
        recyclerView.setAdapter(recentlyAdapter);

        FirestoreService firestoreService = new FirestoreService();
        FirestoreRemoteDataSource firestoreRemoteDataSource = new FirestoreRemoteDataSource(firestoreService);

        MealRepository repository = new MealRepository(
                requireContext(),
                firestoreRemoteDataSource
        );

        recentlyPresenter = new RecentlyPresenterImp(this, repository);

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
    public void onRecentClick(String mealId) {
        Bundle bundle = new Bundle();
        bundle.putString("mealId", mealId);
        Navigation.findNavController(requireView())
                .navigate(R.id.action_recentlyFragment_to_detailsFragment, bundle);    }
}