package com.example.tasty_food_app.onboarding;

import androidx.fragment.app.Fragment;

import java.util.ArrayList;

public interface OnBoardingView {
    void setupViewPager(ArrayList<Fragment> fragments);
    void navigateToAuth();
}
