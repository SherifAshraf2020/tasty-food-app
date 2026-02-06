package com.example.tasty_food_app.home.home.favorite.view;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.tasty_food_app.R;
import com.example.tasty_food_app.datasource.model.Meal;
import com.example.tasty_food_app.home.home.favorite.OnFavoriteClick;

import java.util.List;

public class FavoritesAdapter extends RecyclerView.Adapter<FavoritesAdapter.ViewHolder> {

    private List<Meal> mealList;
    private OnFavoriteClick onFavoriteClick;
    public FavoritesAdapter(List<Meal> mealList, OnFavoriteClick onFavoriteClick) {
        this.mealList = mealList;
        this.onFavoriteClick = onFavoriteClick;
    }

    public void setMealList(List<Meal> mealList) {
        this.mealList = mealList;
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
        Meal currentMeal = mealList.get(position);

        holder.tvMealName.setText(currentMeal.getStrMeal());


        Glide.with(holder.itemView.getContext())
                .load(currentMeal.getStrMealThumb())
                .placeholder(R.drawable.ic_launcher_background)
                .into(holder.imgMeal);



        holder.imgFavorite.setImageResource(R.drawable.ic_favorite);

        holder.imgFavorite.setOnClickListener(v -> {
            if (onFavoriteClick != null) {
                onFavoriteClick.onDeleteClick(currentMeal);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mealList != null ? mealList.size() : 0;
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