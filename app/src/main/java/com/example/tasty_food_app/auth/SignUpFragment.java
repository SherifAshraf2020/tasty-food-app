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
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;


public class SignUpFragment extends Fragment {

    private TextInputEditText etName, etEmail, etPassword;
    private FirebaseAuth mAuth;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mAuth = FirebaseAuth.getInstance();

        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_sign_up, container, false);

        etName = view.findViewById(R.id.etSinUpName);
        etEmail = view.findViewById(R.id.etSinUpEmail);
        etPassword = view.findViewById(R.id.etSinUpPassword);

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        view.findViewById(R.id.tvLoginPage).setOnClickListener(v ->
                Navigation.findNavController(v).navigate(R.id.action_signUpFragment_to_loginFragment2));

        view.findViewById(R.id.btnSignUp).setOnClickListener(v -> {
            String name = etName.getText().toString().trim();
            String email = etEmail.getText().toString().trim();
            String password = etPassword.getText().toString().trim();

            if (validateData(name, email, password)) {
                performSignUp(email, password, v);
            }
        });
    }
        private boolean validateData(String name, String email, String password) {
            if (name.isEmpty()) {
                etName.setError("Required");
                return false;
            }
            if (email.isEmpty()) {
                etEmail.setError("Required");
                return false;
            }
            if (password.length() < 6) {
                etPassword.setError("Min 6 characters");
                return false;
            }
            return true;
        }

    private void performSignUp(String email, String password, View v) {
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        Navigation.findNavController(v).navigate(R.id.action_signUpFragment_to_loginFragment2);
                    }
                });
    }
}