package com.example.tasty_food_app.onboarding;

public interface OnBoardingPresenter {
    void loadOnBoardingData();
    void markOnBoardingFinished();
    void onNextClicked(int currentPos);
    void onFinishClicked();

}
