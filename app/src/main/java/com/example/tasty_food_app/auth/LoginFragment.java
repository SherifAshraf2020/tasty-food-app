package com.example.tasty_food_app.auth;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.tasty_food_app.R;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;


public class LoginFragment extends Fragment {

    private FirebaseAuth mAuth;
    private TextInputEditText etEmail, etPassword;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mAuth = FirebaseAuth.getInstance();
        View view = inflater.inflate(R.layout.fragment_login, container, false);

        etEmail = view.findViewById(R.id.etSinInEmail);
        etPassword = view.findViewById(R.id.etSinInPassword);

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        view.findViewById(R.id.tvRegister).setOnClickListener(v ->
                Navigation.findNavController(v).navigate(R.id.action_loginFragment_to_signUpFragment));

        view.findViewById(R.id.tvForgotPassword).setOnClickListener(v ->
                Navigation.findNavController(v).navigate(R.id.action_loginFragment_to_forgetPasswordFragment));

        view.findViewById(R.id.btnSignIn).setOnClickListener(v -> {
            String email = etEmail.getText().toString().trim();
            String password = etPassword.getText().toString().trim();

            if (validateData(email, password)) {
                performLogin(email, password, v);
            }
        });
    }

    private boolean validateData(String email, String password) {
        if (email.isEmpty()) {
            etEmail.setError("Required");
            return false;
        }
        if (password.isEmpty()) {
            etPassword.setError("Required");
            return false;
        }
        return true;
    }

    private void performLogin(String email, String password, View v) {
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        Toast.makeText(getContext(), "Welcome Back!", Toast.LENGTH_SHORT).show();
                        // Navigation.findNavController(v).navigate(R.id.action_to_home);
                    } else {
                        Toast.makeText(getContext(), task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
    }
}