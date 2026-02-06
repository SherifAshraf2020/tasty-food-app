package com.example.tasty_food_app.home.home;


import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.example.tasty_food_app.home.home.discover.view.DiscoverFragment;

public class HomeTabsAdapter extends FragmentStateAdapter {
    public HomeTabsAdapter(@NonNull Fragment fragment) {
        super(fragment);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position) {
            case 0: return new DiscoverFragment();
            case 1: return new FavoritesFragment();
            case 2: return new RecentlyFragment();
            default: return new DiscoverFragment();
        }
    }

    @Override
    public int getItemCount() {
        return 3;
    }
}
