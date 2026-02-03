package com.example.tasty_food_app.auth;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.tasty_food_app.R;


public class LoginFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_login, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        view.findViewById(R.id.tvRegister).setOnClickListener(v ->
                Navigation.findNavController(v).navigate(R.id.action_loginFragment_to_signUpFragment));

        view.findViewById(R.id.tvForgotPassword).setOnClickListener(v ->
                Navigation.findNavController(v).navigate(R.id.action_loginFragment_to_forgetPasswordFragment));

        view.findViewById(R.id.btnSignIn).setOnClickListener(v -> {
            // Firebase Login Logic later
        });

        view.findViewById(R.id.btnSignInWithGoogle).setOnClickListener(v -> {
            // Google SignIn Logic later
        });
    }
}