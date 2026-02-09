package com.example.tasty_food_app.home.home.search.view;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.tasty_food_app.R;
import com.example.tasty_food_app.datasource.model.category.Category;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.rxjava3.annotations.NonNull;

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.ViewHolder>{
    private List<Category> categories = new ArrayList<>();
    private List<Category> fullList = new ArrayList<>();
    private OnSearchClick listener;



    public CategoryAdapter(OnSearchClick listener) {
        this.listener = listener;
    }

    public void setList(List<Category> list) {
        if (list == null || list.isEmpty()) {
            this.categories = new ArrayList<>(0);
            this.fullList = new ArrayList<>(0);
        } else {
            this.categories = list;
            this.fullList = new ArrayList<>(list);
        }
        notifyDataSetChanged();
    }

    public void filter(String query) {
        String lowerCaseQuery = query.toLowerCase().trim();

        if (lowerCaseQuery.isEmpty()) {
            categories = new ArrayList<>(fullList);
        } else {
            List<Category> filteredList = new ArrayList<>();
            for (Category category : fullList) {
                String categoryName = category.getStrCategory();
                if (categoryName != null && categoryName.toLowerCase().startsWith(lowerCaseQuery)) {
                    filteredList.add(category);
                }
            }
            categories = filteredList;
        }
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_category_card, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Category category = categories.get(position);
        holder.tvName.setText(category.getStrCategory());

        Glide.with(holder.itemView.getContext())
                .load(category.getStrCategoryThumb())
                .placeholder(R.drawable.ic_launcher_background)
                .into(holder.imgCategory);

        holder.itemView.setOnClickListener(v -> listener.onCategoryClick(category));
    }

    @Override
    public int getItemCount() {
        return categories != null ? categories.size() : 0;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imgCategory;
        TextView tvName;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imgCategory = itemView.findViewById(R.id.img_category_photo);
            tvName = itemView.findViewById(R.id.tv_category_name);
        }
    }
}
