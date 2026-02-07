package com.example.tasty_food_app.home.home.details.view;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.tasty_food_app.R;
import com.example.tasty_food_app.datasource.model.Meal;
import com.example.tasty_food_app.datasource.repository.MealRepository;
import com.example.tasty_food_app.home.home.details.presenter.DetailsPresenter;
import com.example.tasty_food_app.home.home.details.presenter.DetailsPresenterImp;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.views.YouTubePlayerView;

import java.util.ArrayList;

public class DetailsFragment extends Fragment implements DetailsView {

    private ImageView imgMeal;
    private FloatingActionButton btnFavorite;
    private TextView tvName, tvOrigin, tvInstructions;
    private RecyclerView rvIngredients;
    private YouTubePlayerView youtubePlayer;
    private ProgressBar progressBar;

    private DetailsPresenter presenter;
    private IngredientsAdapter ingredientsAdapter;
    private Meal currentMeal;
    private boolean isFavorite = false;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_details, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initViews(view);
        setupIngredientsRecyclerView();

        // --- تعديل: استقبال الـ Bundle يدوي ---
        String mealId = "";
        if (getArguments() != null) {
            mealId = getArguments().getString("mealId");
        }

        MealRepository repository = new MealRepository(requireActivity().getApplicationContext());
        presenter = new DetailsPresenterImp(this, repository);

        if (mealId != null && !mealId.isEmpty()) {
            presenter.getMealDetails(mealId);
            presenter.checkIsFavorite(mealId);
        }

        btnFavorite.setOnClickListener(v -> {
            if (currentMeal != null) {
                if (isFavorite) {
                    presenter.deleteMealFromFav(currentMeal);
                } else {
                    presenter.addToFavorites(currentMeal);
                }
            }
        });
    }

    private void initViews(View view) {
        imgMeal = view.findViewById(R.id.imgMealDetails);
        btnFavorite = view.findViewById(R.id.btnFavorite);
        tvName = view.findViewById(R.id.tvMealNameDetails);
        tvOrigin = view.findViewById(R.id.tvMealOriginDetails);
        tvInstructions = view.findViewById(R.id.tvInstructions);
        rvIngredients = view.findViewById(R.id.rvIngredients);
        youtubePlayer = view.findViewById(R.id.youtubePlayerView);
        progressBar = view.findViewById(R.id.progressBar);
    }

    private void setupIngredientsRecyclerView() {
        LinearLayoutManager layoutManager = new LinearLayoutManager(requireContext(), RecyclerView.HORIZONTAL, false);
        rvIngredients.setLayoutManager(layoutManager);
        ingredientsAdapter = new IngredientsAdapter(new ArrayList<>());
        rvIngredients.setAdapter(ingredientsAdapter);
    }

    // --- تنفيذ الميثودز (Implementation) ---

    @Override
    public void showLoading() {
        progressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideLoading() {
        progressBar.setVisibility(View.GONE);
    }

    @Override
    public void showMealDetails(Meal meal) {
        this.currentMeal = meal;
        tvName.setText(meal.getStrMeal());
        tvOrigin.setText(meal.getStrArea());
        tvInstructions.setText(meal.getStrInstructions());

        Glide.with(requireContext()).load(meal.getStrMealThumb()).into(imgMeal);

        // عرض المكونات في الـ Adapter الأفقي
        ingredientsAdapter.setList(meal.getIngredientsList());

        // تشغيل اليوتيوب
        if (meal.getStrYoutube() != null && !meal.getStrYoutube().isEmpty()) {
            setupYoutube(meal.getStrYoutube());
        }
    }

    private void setupYoutube(String url) {
        String videoId = "";
        if (url.contains("v=")) {
            videoId = url.split("v=")[1];
            // لو اللينك فيه باراميترز تانية بعد الـ ID
            int ampersandIndex = videoId.indexOf("&");
            if (ampersandIndex != -1) {
                videoId = videoId.substring(0, ampersandIndex);
            }
        }

        final String finalVideoId = videoId;
        getLifecycle().addObserver(youtubePlayer);
        youtubePlayer.addYouTubePlayerListener(new AbstractYouTubePlayerListener() {
            @Override
            public void onReady(@NonNull YouTubePlayer player) {
                player.cueVideo(finalVideoId, 0);
            }
        });
    }

    @Override
    public void updateFavoriteIcon(boolean isFavorite) {
        this.isFavorite = isFavorite;
        if (isFavorite) {
            btnFavorite.setImageResource(R.drawable.ic_favorite); // القلب المليان
        } else {
            btnFavorite.setImageResource(R.drawable.ic_favorite_border); // القلب الفاضي
        }
    }

    @Override
    public void onFavoriteAdded(Meal meal) {
        updateFavoriteIcon(true);
        Toast.makeText(getContext(), "Added to Favorites", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onFavoriteDeleted(Meal meal) {
        updateFavoriteIcon(false);
        Toast.makeText(getContext(), "Removed from Favorites", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showErrorMessage(String message) {
        Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (youtubePlayer != null) youtubePlayer.release();
        if (presenter != null) presenter.clearResources();
    }
}