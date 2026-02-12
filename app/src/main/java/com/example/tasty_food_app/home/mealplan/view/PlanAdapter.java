package com.example.tasty_food_app.home.mealplan.view;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.tasty_food_app.R;
import com.example.tasty_food_app.datasource.model.plan.PlanMeal;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.rxjava3.annotations.NonNull;

public class PlanAdapter extends RecyclerView.Adapter<PlanAdapter.PlanViewHolder> {

    private List<String> days = new ArrayList<>();
    private List<PlanMeal> planMeals = new ArrayList<>();
    private OnPlanClick listener;

    public PlanAdapter(OnPlanClick listener) {
        this.listener = listener;
    }

    public void setListData(List<String> days, List<PlanMeal> planMeals) {
        this.days = days;
        this.planMeals = planMeals;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public PlanViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_plan_day, parent, false);
        return new PlanViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PlanViewHolder holder, int position) {
        String currentDay = days.get(position);
        holder.tvDayName.setText(currentDay);

        PlanMeal mealForThisDay = null;
        for (PlanMeal pm : planMeals) {
            if (pm.getDay().equalsIgnoreCase(currentDay)) {
                mealForThisDay = pm;
                break;
            }
        }

        if (mealForThisDay != null) {
            holder.tvMealName.setText(mealForThisDay.getStrMeal());
            holder.tvMealName.setVisibility(View.VISIBLE);
            holder.btnAdd.setVisibility(View.GONE);
            holder.btnDelete.setVisibility(View.VISIBLE);

            Glide.with(holder.itemView.getContext())
                    .load(mealForThisDay.getStrMealThumb())
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .into(holder.imgMeal);

            PlanMeal finalMeal = mealForThisDay;
            holder.btnDelete.setOnClickListener(v -> listener.onDeleteClick(finalMeal));
            holder.itemView.setOnClickListener(v -> listener.onMealClick(finalMeal.getIdMeal()));
        } else {
            holder.tvMealName.setVisibility(View.GONE);
            holder.btnAdd.setVisibility(View.VISIBLE);
            holder.btnDelete.setVisibility(View.GONE);
            holder.imgMeal.setImageResource(R.color.primary_orange);

            holder.btnAdd.setOnClickListener(v -> listener.onAddClick(currentDay));
            holder.itemView.setOnClickListener(null);
        }
    }

    @Override
    public int getItemCount() { return days.size(); }

    static class PlanViewHolder extends RecyclerView.ViewHolder {
        TextView tvDayName, tvMealName;
        ImageView imgMeal, btnAdd, btnDelete;

        public PlanViewHolder(@NonNull View itemView) {
            super(itemView);
            tvDayName = itemView.findViewById(R.id.tvDayName);
            tvMealName = itemView.findViewById(R.id.tvMealName);
            imgMeal = itemView.findViewById(R.id.imgMealPlan);
            btnAdd = itemView.findViewById(R.id.btnAddMealToDay);
            btnDelete = itemView.findViewById(R.id.btnDeleteMeal);
        }
    }
}
