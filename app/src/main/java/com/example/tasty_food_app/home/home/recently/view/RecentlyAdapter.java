package com.example.tasty_food_app.home.home.recently.view;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.Glide;
import com.example.tasty_food_app.R;
import com.example.tasty_food_app.datasource.model.RecentMeal;
import java.util.List;

public class RecentlyAdapter extends RecyclerView.Adapter<RecentlyAdapter.ViewHolder> {

    private List<RecentMeal> recentMeals;
    private OnRecentMealClickListener listener;

    public RecentlyAdapter(List<RecentMeal> recentMeals, OnRecentMealClickListener listener) {
        this.recentMeals = recentMeals;
        this.listener = listener;
    }

    public void setList(List<RecentMeal> recentMeals) {
        this.recentMeals = recentMeals;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_recently_card, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        RecentMeal current = recentMeals.get(position);
        holder.tvTitle.setText(current.getStrMeal());

        Glide.with(holder.itemView.getContext())
                .load(current.getStrMealThumb())
                .into(holder.imgMeal);

        holder.itemView.setOnClickListener(v -> listener.onRecentClick(current.getIdMeal()));
    }

    @Override
    public int getItemCount() {
        return recentMeals != null ? recentMeals.size() : 0;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imgMeal;
        TextView tvTitle;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imgMeal = itemView.findViewById(R.id.recently_img);
            tvTitle = itemView.findViewById(R.id.recently_name);
        }
    }

    public interface OnRecentMealClickListener {
        void onRecentClick(String mealId);
    }
}