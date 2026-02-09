package com.example.tasty_food_app.home.home.search.view;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.tasty_food_app.R;
import com.example.tasty_food_app.datasource.model.area.Area;
import com.example.tasty_food_app.datasource.model.ingredient.Ingredient;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.rxjava3.annotations.NonNull;

public class FilterAdapter extends RecyclerView.Adapter<FilterAdapter.ViewHolder>{
    private List<Object> itemList = new ArrayList<>();
    private List<Object> fullList = new ArrayList<>();
    private OnSearchClick listener;



    public FilterAdapter(OnSearchClick listener) {
        this.listener = listener;
    }

    public void setList(List<Object> list) {
        if (list == null || list.isEmpty()) {
            this.itemList = new ArrayList<>(0);
            this.fullList = this.itemList;
        } else {
            this.itemList = list;
            this.fullList = new ArrayList<>(list);
        }
        notifyDataSetChanged();
    }

    public void filter(String query) {
        String lowerCaseQuery = query.toLowerCase().trim();

        if (lowerCaseQuery.isEmpty()) {
            itemList = new ArrayList<>(fullList);
        } else {
            List<Object> filteredList = new ArrayList<>();
            for (Object item : fullList) {
                String name = "";
                if (item instanceof Area) {
                    name = ((Area) item).getStrArea();
                } else if (item instanceof Ingredient) {
                    name = ((Ingredient) item).getName();
                }

                if (name != null && name.toLowerCase().startsWith(lowerCaseQuery)) {
                    filteredList.add(item);
                }
            }
            itemList = filteredList;
        }
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_filter_circle, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        final Object item = itemList.get(position);
        String name = "";
        String imageUrl = "";

        if (item instanceof Area) {
            Area area = (Area) item;
            name = area.getStrArea();
            imageUrl = area.getFlagUrl();
        } else if (item instanceof Ingredient) {
            Ingredient ingredient = (Ingredient) item;
            name = ingredient.getName();
            imageUrl = ingredient.getImageUrl();
        }

        holder.tvName.setText(name);

        Glide.with(holder.itemView.getContext())
                .load(imageUrl)
                .placeholder(R.drawable.ic_launcher_background)
                .into(holder.imgFilter);

        holder.itemView.setOnClickListener(v -> {
            if (item instanceof Area) {
                listener.onAreaClick((Area) item);
            } else if (item instanceof Ingredient) {
                listener.onIngredientClick((Ingredient) item);
            }
        });
    }

    @Override
    public int getItemCount() {
        return (itemList != null) ? itemList.size() : 0;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imgFilter;
        TextView tvName;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imgFilter = itemView.findViewById(R.id.img_filter_item);
            tvName = itemView.findViewById(R.id.tv_filter_name);
        }
    }
}
