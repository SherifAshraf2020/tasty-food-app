package com.example.tasty_food_app.home.home.details;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.tasty_food_app.R;
import com.example.tasty_food_app.datasource.model.Ingredient;
import java.util.ArrayList;
import java.util.List;

public class IngredientsAdapter extends RecyclerView.Adapter<IngredientsAdapter.ViewHolder> {

    private List<Ingredient> ingredientList = new ArrayList<>();

    public void setList(List<Ingredient> list) {
        this.ingredientList = list;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_ingredient, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Ingredient currentItem = ingredientList.get(position);

        holder.tvName.setText(currentItem.getName());
        holder.tvMeasure.setText(currentItem.getMeasure());

        Glide.with(holder.itemView.getContext())
                .load(currentItem.getImageUrl())
                .placeholder(R.drawable.ic_launcher_background)
                .error(R.drawable.ic_launcher_foreground);
    }

    @Override
    public int getItemCount() {
        return ingredientList != null ? ingredientList.size() : 0;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imgIngredient;
        TextView tvName, tvMeasure;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imgIngredient = itemView.findViewById(R.id.img_ingredient);
            tvName = itemView.findViewById(R.id.tv_ingredient_name);
            tvMeasure = itemView.findViewById(R.id.tv_ingredient_measure);
        }
    }
}