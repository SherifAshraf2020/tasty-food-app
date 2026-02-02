package com.example.tasty_food_app;

import android.animation.Animator;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.airbnb.lottie.LottieAnimationView;
import com.example.tasty_food_app.datasource.SharedPrefsLocalDataSource;

import org.jspecify.annotations.NonNull;


public class SplashFragment extends Fragment implements SplashView{

    private SplashPresenter presenter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_splash, container, false);
    }

        @Override
        public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
            super.onViewCreated(view, savedInstanceState);

            SharedPrefsLocalDataSource dataSource = new SharedPrefsLocalDataSource(requireContext());
            presenter = new SplashPresenterImp(this, dataSource);

            LottieAnimationView lottie = view.findViewById(R.id.lottie);
            lottie.playAnimation();

            lottie.addAnimatorListener(new Animator.AnimatorListener() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    presenter.checkDestination();
                }
                @Override public void onAnimationStart(Animator animation) {}
                @Override public void onAnimationCancel(Animator animation) {}
                @Override public void onAnimationRepeat(Animator animation) {}
            });
        }


    @Override
    public void navigateToHome() {

    }

    @Override
    public void navigateToOnBoarding() {

    }
}