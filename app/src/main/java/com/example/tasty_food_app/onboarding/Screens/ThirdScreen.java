package com.example.tasty_food_app.onboarding.Screens;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.tasty_food_app.R;
import com.example.tasty_food_app.onboarding.view.ViewPagerFragment;


public class ThirdScreen extends Fragment {


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_third_screen, container, false);
        View finishButton = view.findViewById(R.id.finish);

        finishButton.setOnClickListener(v -> {
            if (getParentFragment() instanceof ViewPagerFragment) {
                ((ViewPagerFragment) getParentFragment()).getPresenter().onFinishClicked();
            }
        });

        return view;
    }
}