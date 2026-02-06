package com.example.tasty_food_app.home.home.discover;

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
import java.util.ArrayList;
import java.util.List;

public class MealsAdapter extends RecyclerView.Adapter<MealsAdapter.MealsViewHolder> {

    private List<Meal> mealList;
    private OnMealClick onMealClick;

    public MealsAdapter(OnMealClick onMealClick) {
        this.mealList = new ArrayList<>();
        this.onMealClick = onMealClick;
    }

    public void setMealList(List<Meal> mealList) {
        this.mealList = mealList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public MealsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_meal_card, parent, false);
        return new MealsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MealsViewHolder holder, int position) {
        Meal meal = mealList.get(position);
        holder.bind(meal);
    }

    @Override
    public int getItemCount() {
        return mealList != null ? mealList.size() : 0;
    }

    class MealsViewHolder extends RecyclerView.ViewHolder {
        private ImageView imgMeal;
        private TextView tvMealName;
        private ImageView imgFavorite;

        public MealsViewHolder(@NonNull View itemView) {
            super(itemView);
            imgMeal = itemView.findViewById(R.id.imgMeal);
            tvMealName = itemView.findViewById(R.id.tvMealName);
            imgFavorite = itemView.findViewById(R.id.imgFavorite);
        }

        public void bind(Meal meal) {
            tvMealName.setText(meal.getStrMeal());
            Glide.with(itemView.getContext()).load(meal.getStrMealThumb()).into(imgMeal);

            imgFavorite.setImageResource(R.drawable.ic_favorite_border);
            imgFavorite.setTag("border");

            imgFavorite.setOnClickListener(v -> {
                String currentTag = (String) imgFavorite.getTag();

                if (currentTag.equals("border")) {
                    imgFavorite.setImageResource(R.drawable.ic_favorit);
                    imgFavorite.setTag("filled");
                    onMealClick.addMealToFav(meal);
                } else {
                    imgFavorite.setImageResource(R.drawable.ic_favorite_border);
                    imgFavorite.setTag("border");
                    onMealClick.deleteMealFromFav(meal);
                }
            });

            imgMeal.setOnClickListener(v -> onMealClick.onMealDetailsClick(meal));
        }
    }
}