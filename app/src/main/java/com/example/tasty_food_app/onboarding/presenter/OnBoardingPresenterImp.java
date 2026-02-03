package com.example.tasty_food_app.onboarding.presenter;

import androidx.fragment.app.Fragment;

import com.example.tasty_food_app.datasource.SharedPrefsLocalDataSource;
import com.example.tasty_food_app.onboarding.view.OnBoardingView;
import com.example.tasty_food_app.onboarding.Screens.FirstScreen;
import com.example.tasty_food_app.onboarding.Screens.SecondScreen;
import com.example.tasty_food_app.onboarding.Screens.ThirdScreen;

import java.util.ArrayList;

public class OnBoardingPresenterImp implements OnBoardingPresenter{

    private final OnBoardingView view;
    private final SharedPrefsLocalDataSource sharedPrefsLocalDataSource;

    public OnBoardingPresenterImp(OnBoardingView view, SharedPrefsLocalDataSource sharedPrefsLocalDataSource) {
        this.view = view;
        this.sharedPrefsLocalDataSource = sharedPrefsLocalDataSource;
    }
    @Override
    public void loadOnBoardingData() {
        ArrayList<Fragment> fragmentList = new ArrayList<>();
        fragmentList.add(new FirstScreen());
        fragmentList.add(new SecondScreen());
        fragmentList.add(new ThirdScreen());

        view.setupViewPager(fragmentList);
    }

    @Override
    public void markOnBoardingFinished() {
        sharedPrefsLocalDataSource.setOnBoardingFinished(true);
        view.navigateToAuth();
    }

    @Override
    public void onNextClicked(int currentPos) {
        int nextPage = currentPos + 1;
        view.scrollToPage(nextPage);
    }

    @Override
    public void onFinishClicked() {
        sharedPrefsLocalDataSource.setOnBoardingFinished(true);
        view.navigateToAuth();
    }
}
