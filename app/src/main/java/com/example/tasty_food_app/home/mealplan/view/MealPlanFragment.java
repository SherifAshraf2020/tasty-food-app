package com.example.tasty_food_app.home.mealplan.view;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.example.tasty_food_app.R;
import com.example.tasty_food_app.datasource.local.SharedPrefsLocalDataSource;
import com.example.tasty_food_app.datasource.model.plan.PlanMeal;
import com.example.tasty_food_app.datasource.remote.auth.AuthRemoteDataSource;
import com.example.tasty_food_app.datasource.repository.auth.AuthRepository;
import com.example.tasty_food_app.datasource.repository.meal.MealRepository;
import com.example.tasty_food_app.home.mealplan.presenter.MealPlanPresenter;
import com.example.tasty_food_app.home.mealplan.presenter.MealPlanPresenterImp;

import java.util.List;

import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.annotations.Nullable;


public class MealPlanFragment extends Fragment implements MealPlanView , OnPlanClick {


    private RecyclerView recyclerView;
    private PlanAdapter adapter;
    private MealPlanPresenter presenter;
    private Button btnGenerateRandom;
    private List<String> currentDays;
    private String userId;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_meal_plan, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        MealRepository mealRepo = new MealRepository(requireContext());

        AuthRepository authRepo = AuthRepository.getInstance(
                new AuthRemoteDataSource(requireContext()),
                new SharedPrefsLocalDataSource(requireContext())
        );

        userId = authRepo.getCurrentUserId();
        presenter = new MealPlanPresenterImp(this, mealRepo, authRepo);


        recyclerView = view.findViewById(R.id.rvWeeklyPlan);
        btnGenerateRandom = view.findViewById(R.id.btnGenerateRandomPlan);

        adapter = new PlanAdapter(this);

        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(adapter);

        presenter.generateDaysList();

        btnGenerateRandom.setOnClickListener(v -> onGenerateRandomClick());
    }


    @Override
    public void onResume() {
        super.onResume();
        if (userId != null) {
            presenter.getWeeklyPlan(userId);
        }
    }

    @Override
    public void onAddClick(String day) {
        Bundle bundle = new Bundle();
        bundle.putString("selected_day", day);
        Navigation.findNavController(requireView()).navigate(R.id.action_global_searchFragment, bundle);
    }

    @Override
    public void onDeleteClick(PlanMeal planMeal) {
        presenter.removeMealFromPlan(planMeal);
    }

    @Override
    public void onMealClick(String mealId) {
        Bundle bundle = new Bundle();
        bundle.putString("mealId", mealId);
        Navigation.findNavController(requireView()).navigate(R.id.action_global_detailsFragment, bundle);
    }

    @Override
    public void onGenerateRandomClick() {
        if (currentDays != null && userId != null) {
            presenter.generateRandomWeeklyPlan(currentDays, userId);
        }
    }




    @Override
    public void showDaysList(List<String> days) {
        this.currentDays = days;
    }

    @Override
    public void showPlanData(List<PlanMeal> planMeals) {
        adapter.setListData(currentDays, planMeals);
    }

    @Override
    public void onPlanGenerated() {
        Toast.makeText(getContext(), "Weekly Plan Generated!", Toast.LENGTH_SHORT).show();
        presenter.getWeeklyPlan(userId);
    }

    @Override
    public void onInsertSuccess() {
        presenter.getWeeklyPlan(userId);
    }

    @Override
    public void onDeleteSuccess() {
        presenter.getWeeklyPlan(userId);
    }

    @Override
    public void onError(String message) {
        Toast.makeText(getContext(), "Error: " + message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (presenter != null) presenter.onDestroy();
    }
}