package com.example.tasty_food_app.auth;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.example.tasty_food_app.R;


public class ForgetPasswordFragment extends Fragment {



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_forget_password, container, false);
    }
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        view.findViewById(R.id.btnForgotPasswordSubmit).setOnClickListener(v -> {
            EditText etEmail = view.findViewById(R.id.etForgotPasswordEmail);
            String email = etEmail.getText().toString().trim();

            if (!email.isEmpty()) {
                // Firebase Reset Password Logic
                view.findViewById(R.id.tvSubmitMsg).setVisibility(View.VISIBLE);
            }
        });
    }
}