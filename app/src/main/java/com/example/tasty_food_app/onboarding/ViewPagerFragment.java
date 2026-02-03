package com.example.tasty_food_app.onboarding;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.tasty_food_app.R;
import com.example.tasty_food_app.datasource.SharedPrefsLocalDataSource;

import java.util.ArrayList;


public class ViewPagerFragment extends Fragment implements OnBoardingView{

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        OnBoardingPresenterImp onBoardingPresenterImp;
        ViewPager2 viewPager;

        View view = inflater.inflate(R.layout.fragment_view_pager, container, false);
        viewPager = view.findViewById(R.id.viewPager);


        SharedPrefsLocalDataSource sharedPrefsLocalDataSource = new SharedPrefsLocalDataSource(requireContext());
        onBoardingPresenterImp = new OnBoardingPresenterImp(this, sharedPrefsLocalDataSource);


        onBoardingPresenterImp.loadOnBoardingData();

        return view;
    }

    @Override
    public void setupViewPager(ArrayList<Fragment> fragments) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(
                getChildFragmentManager(),
                getLifecycle(),
                fragments);
    }

    @Override
    public void navigateToAuth() {
        // Navigation.findNavController(requireView()).navigate(R.id.action_to_login);
    }
}