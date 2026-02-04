package com.example.tasty_food_app.onboarding.view;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Lifecycle;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import java.util.ArrayList;

public class ViewPagerAdapter extends FragmentStateAdapter {
    private final ArrayList<Fragment> fragmentList;

    public ViewPagerAdapter(@NonNull FragmentManager fm, @NonNull Lifecycle lifecycle, ArrayList<Fragment> list) {
        super(fm, lifecycle);
        this.fragmentList = list;
    }



    @Override
    public int getItemCount() {
        return fragmentList.size();
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        return fragmentList.get(position);
    }
}
