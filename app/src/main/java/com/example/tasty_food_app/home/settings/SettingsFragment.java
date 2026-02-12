package com.example.tasty_food_app.home.settings;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.tasty_food_app.R;
import com.example.tasty_food_app.datasource.local.SharedPrefsLocalDataSource;
import com.example.tasty_food_app.datasource.remote.auth.AuthRemoteDataSource;
import com.example.tasty_food_app.datasource.repository.auth.AuthRepository;

import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.annotations.Nullable;


public class SettingsFragment extends Fragment implements SettingsView{

    private SettingsPresenter presenter;
    private Button btnLogout;
    private TextView tvUserEmail;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_settings, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        btnLogout = view.findViewById(R.id.btn_logout);
        tvUserEmail = view.findViewById(R.id.tv_user_email);

        AuthRepository authRepository = AuthRepository.getInstance(
                new AuthRemoteDataSource(requireContext()),
                new SharedPrefsLocalDataSource(requireContext())
        );

        presenter = new SettingsPresenterImp(this, authRepository);

        presenter.loadUserData();

        btnLogout.setOnClickListener(v -> presenter.logout());
    }

    @Override
    public void onLogoutSuccess() {
        Navigation.findNavController(requireView())
                .navigate(R.id.action_global_to_auth_graph);

        Toast.makeText(getContext(), "Logged out successfully", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showError(String message) {
        Toast.makeText(getContext(), "Error: " + message, Toast.LENGTH_LONG).show();
    }

    @Override
    public void displayUserEmail(String email) {
        if (tvUserEmail != null) {
            tvUserEmail.setText(email != null ? email : "Guest User");
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (presenter != null) {
            presenter.clearResources();
        }
    }
}