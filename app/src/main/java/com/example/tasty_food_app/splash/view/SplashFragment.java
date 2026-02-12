package com.example.tasty_food_app.splash.view;

import android.animation.Animator;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.airbnb.lottie.LottieAnimationView;
import com.example.tasty_food_app.MainActivity;
import com.example.tasty_food_app.R;
import com.example.tasty_food_app.datasource.local.SharedPrefsLocalDataSource;
import com.example.tasty_food_app.datasource.remote.auth.AuthRemoteDataSource;
import com.example.tasty_food_app.datasource.repository.auth.AuthRepository;
import com.example.tasty_food_app.splash.presenter.SplashPresenter;
import com.example.tasty_food_app.splash.presenter.SplashPresenterImp;

import org.jspecify.annotations.NonNull;


public class SplashFragment extends Fragment implements SplashView{

    private SplashPresenter presenter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_splash, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        AuthRepository authRepository = AuthRepository.getInstance(
                new AuthRemoteDataSource(requireContext()),
                new SharedPrefsLocalDataSource(requireContext())
        );
        presenter = new SplashPresenterImp(this, authRepository);

        LottieAnimationView lottie = view.findViewById(R.id.lottie);
        lottie.playAnimation();

        lottie.addAnimatorListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {
                presenter.onAnimationStarted();
            }

            @Override
            public void onAnimationEnd(Animator animation) {
                presenter.onAnimationFinished();
            }

            @Override public void onAnimationCancel(Animator animation) {}
            @Override public void onAnimationRepeat(Animator animation) {}
        });
    }

    @Override
    public void setAppReady() {
        if (getActivity() instanceof MainActivity) {
            ((MainActivity) getActivity()).onAppReady();
        }
    }

    @Override
    public void navigateToAuth() {
        NavHostFragment.findNavController(this).navigate(R.id.action_splashFragment_to_auth_graph);
    }

    @Override
    public void navigateToOnBoarding() {
        NavHostFragment.findNavController(this).navigate(R.id.action_splashFragment_to_viewPagerFragment);
    }

    @Override
    public void navigateToHome() {
        NavHostFragment.findNavController(this).navigate(R.id.action_splashFragment_to_home_nav_graph);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (presenter instanceof SplashPresenterImp) {
            ((SplashPresenterImp) presenter).clear();
        }
    }
}