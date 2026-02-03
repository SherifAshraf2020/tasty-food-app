package com.example.tasty_food_app.onboarding.Screens;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.tasty_food_app.R;
import com.example.tasty_food_app.onboarding.ViewPagerFragment;


public class FirstScreen extends Fragment {


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_first_screen, container, false);

        View nextButton = view.findViewById(R.id.next);

        nextButton.setOnClickListener(v -> {
            if (getParentFragment() instanceof ViewPagerFragment) {
                ((ViewPagerFragment) getParentFragment()).getPresenter().onNextClicked(0);
            }
        });

        return view;
    }
}