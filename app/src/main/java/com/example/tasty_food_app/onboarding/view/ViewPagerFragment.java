package com.example.tasty_food_app.onboarding.view;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.viewpager2.widget.ViewPager2;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.tasty_food_app.R;
import com.example.tasty_food_app.datasource.SharedPrefsLocalDataSource;
import com.example.tasty_food_app.datasource.remote.auth.AuthRemoteDataSource;
import com.example.tasty_food_app.datasource.repository.AuthRepository;
import com.example.tasty_food_app.onboarding.presenter.OnBoardingPresenterImp;

import java.util.ArrayList;


public class ViewPagerFragment extends Fragment implements OnBoardingView{

    private ViewPager2 viewPager;
    private OnBoardingPresenterImp onBoardingPresenterImp;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_view_pager, container, false);
        viewPager = view.findViewById(R.id.viewPager);

        AuthRepository repository = AuthRepository.getInstance(
                new AuthRemoteDataSource(requireContext()),
                new SharedPrefsLocalDataSource(requireContext())
        );

        onBoardingPresenterImp = new OnBoardingPresenterImp(this, repository);
        onBoardingPresenterImp.loadOnBoardingData();

        return view;
    }

    @Override
    public void setupViewPager(ArrayList<Fragment> fragments) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(
                getChildFragmentManager(),
                getLifecycle(),
                fragments);
        viewPager.setAdapter(adapter);
    }

    @Override
    public void navigateToAuth() {
        Navigation.findNavController(requireView()).navigate(R.id.action_viewPagerFragment_to_auth_graph);
    }

    @Override
    public void scrollToPage(int pageIndex) {
        if (viewPager != null) {
            viewPager.setCurrentItem(pageIndex, true);
        }
    }

    public OnBoardingPresenterImp getPresenter() {
        return onBoardingPresenterImp;
    }
}