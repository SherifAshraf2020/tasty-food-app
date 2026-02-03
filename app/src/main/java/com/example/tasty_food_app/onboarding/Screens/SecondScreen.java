package com.example.tasty_food_app.onboarding.Screens;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.tasty_food_app.R;


public class SecondScreen extends Fragment {


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_second_screen, container, false);

        ViewPager2 viewPager = getActivity().findViewById(R.id.viewPager);
        View nextButton = view.findViewById(R.id.next2);

        nextButton.setOnClickListener(v -> {
            viewPager.setCurrentItem(2);
        });

        return view;
    }
}