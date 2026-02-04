package com.example.tasty_food_app.onboarding.presenter;

public interface OnBoardingPresenter {
    void loadOnBoardingData();
    void markOnBoardingFinished();
    void onNextClicked(int currentPos);
    void onGetStartedClicked();

}
