package com.example.tasty_food_app.home.home.search.view;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.Glide;
import com.example.tasty_food_app.R;
import com.example.tasty_food_app.datasource.model.Meal;
import java.util.ArrayList;
import java.util.List;
import io.reactivex.rxjava3.annotations.NonNull;

public class SuggestionAdapter extends RecyclerView.Adapter<SuggestionAdapter.ViewHolder> {
    private List<Meal> suggestions = new ArrayList<>();
    private List<Meal> fullList = new ArrayList<>();
    private OnSearchClick listener;

    public SuggestionAdapter(OnSearchClick listener) {
        this.listener = listener;
    }

    public void setList(List<Meal> list) {
        if (list == null) list = new ArrayList<>();
        this.suggestions = list;
        this.fullList = new ArrayList<>(list);
        notifyDataSetChanged();
    }

    public void filter(String query) {
        String lowerCaseQuery = query.toLowerCase().trim();
        if (lowerCaseQuery.isEmpty()) {
            suggestions = new ArrayList<>(fullList);
        } else {
            List<Meal> filteredList = new ArrayList<>();
            for (Meal meal : fullList) {
                if (meal.getStrMeal() != null &&
                        meal.getStrMeal().toLowerCase().startsWith(lowerCaseQuery)) {
                    filteredList.add(meal);
                }
            }
            suggestions = filteredList;
        }
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_suggestion, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Meal meal = suggestions.get(position);
        holder.tvName.setText(meal.getStrMeal());

        Glide.with(holder.itemView.getContext())
                .load(meal.getStrMealThumb())
                .circleCrop()
                .placeholder(R.drawable.ic_launcher_background)
                .into(holder.imgThumbnail);

        holder.itemView.setOnClickListener(v -> {
            if (listener != null) listener.onMealClick(meal);
        });
    }

    @Override
    public int getItemCount() {
        return (suggestions != null) ? Math.min(suggestions.size(), 6) : 0;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imgThumbnail;
        TextView tvName;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imgThumbnail = itemView.findViewById(R.id.img_suggestion_thumbnail);
            tvName = itemView.findViewById(R.id.tv_suggestion_name);
        }
    }
}