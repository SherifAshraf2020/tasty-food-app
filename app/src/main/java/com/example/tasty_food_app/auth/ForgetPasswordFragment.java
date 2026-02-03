package com.example.tasty_food_app.auth;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.tasty_food_app.R;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;


public class ForgetPasswordFragment extends Fragment {
    private FirebaseAuth mAuth;
    private TextInputEditText etEmail;
    private TextView tvSubmitMsg;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mAuth = FirebaseAuth.getInstance();
        View view = inflater.inflate(R.layout.fragment_forget_password, container, false);

        etEmail = view.findViewById(R.id.etForgotPasswordEmail);
        tvSubmitMsg = view.findViewById(R.id.tvSubmitMsg);

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        view.findViewById(R.id.btnForgotPasswordSubmit).setOnClickListener(v -> {
            String email = etEmail.getText().toString().trim();

            if (email.isEmpty()) {
                etEmail.setError("Required");
            } else {
                sendResetPasswordEmail(email);
            }
        });
    }

    private void sendResetPasswordEmail(String email) {
        mAuth.sendPasswordResetEmail(email)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        tvSubmitMsg.setVisibility(View.VISIBLE);
                        Toast.makeText(getContext(), "Reset link sent to your email", Toast.LENGTH_LONG).show();
                    } else {
                        Toast.makeText(getContext(), "Error: " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
    }
}