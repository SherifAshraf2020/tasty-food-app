package com.example.tasty_food_app.home.home.search.view;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.Glide;
import com.example.tasty_food_app.R;
import com.example.tasty_food_app.datasource.model.meal.Meal;
import java.util.ArrayList;
import java.util.List;

public class MealAdapter extends RecyclerView.Adapter<MealAdapter.ViewHolder> {
    private List<Meal> meals = new ArrayList<>();
    private List<Meal> fullList = new ArrayList<>();
    private OnSearchClick listener;



    public MealAdapter(OnSearchClick listener) {
        this.listener = listener;
    }

    public void setList(List<Meal> list) {
        if (list == null || list.isEmpty()) {
            this.meals = new ArrayList<>(0);
            this.fullList = this.meals;
        } else {
            this.meals = list;
            this.fullList = new ArrayList<>(list);
        }
        notifyDataSetChanged();
    }

    public void filter(String query) {
        String lowerCaseQuery = query.toLowerCase().trim();
        if (lowerCaseQuery.isEmpty()) {
            meals = new ArrayList<>(fullList);
        } else {
            List<Meal> filteredList = new ArrayList<>();
            for (Meal meal : fullList) {
                if (meal.getStrMeal() != null && meal.getStrMeal().toLowerCase().startsWith(lowerCaseQuery)) {
                    filteredList.add(meal);
                }
            }
            meals = filteredList;
        }
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_meal_card, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Meal meal = meals.get(position);
        holder.tvMealName.setText(meal.getStrMeal());

        Glide.with(holder.itemView.getContext())
                .load(meal.getStrMealThumb())
                .placeholder(R.drawable.ic_launcher_background)
                .into(holder.imgMeal);

        holder.imgFavorite.setImageResource(meal.isFavorite() ? R.drawable.ic_favorite : R.drawable.ic_favorite_border);

        holder.itemView.setOnClickListener(v -> {
            if (listener != null) listener.onMealClick(meal);
        });

        holder.imgFavorite.setOnClickListener(v -> {
            if (listener != null) listener.onFavoriteClick(meal);
        });
    }

    @Override
    public int getItemCount() {
        return (meals != null) ? meals.size() : 0;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imgMeal, imgFavorite;
        TextView tvMealName;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imgMeal = itemView.findViewById(R.id.imgMeal);
            imgFavorite = itemView.findViewById(R.id.imgFavorite);
            tvMealName = itemView.findViewById(R.id.tvMealName);
        }
    }
}