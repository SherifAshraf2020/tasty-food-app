package com.example.tasty_food_app.auth.forget_pass.view;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.tasty_food_app.R;
import com.example.tasty_food_app.auth.forget_pass.presenter.ForgetPasswordPresenter;
import com.example.tasty_food_app.auth.forget_pass.presenter.ForgetPasswordPresenterImp;
import com.example.tasty_food_app.datasource.remote.AuthRemoteDataSource;
import com.example.tasty_food_app.datasource.repository.AuthRepository;
import com.google.android.material.textfield.TextInputEditText;


public class ForgetPasswordFragment extends Fragment implements ForgetPasswordView {
    private TextInputEditText etEmail;
    private TextView tvSubmitMsg;
    private ProgressBar progressBar;
    private Button btnSubmit;
    private ForgetPasswordPresenter presenter;
    private AuthRepository authRepository;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        return  inflater.inflate(R.layout.fragment_forget_password, container, false);


    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        etEmail = view.findViewById(R.id.etForgotPasswordEmail);
        tvSubmitMsg = view.findViewById(R.id.tvSubmitMsg);
        progressBar = view.findViewById(R.id.progressBar);
        btnSubmit = view .findViewById(R.id.btnForgotPasswordSubmit);

        if (getArguments() != null) {
            String emailFromLogin = getArguments().getString("userEmail");
            etEmail.setText(emailFromLogin);
        }

        presenter = new ForgetPasswordPresenterImp(this, AuthRepository.getInstance(new AuthRemoteDataSource()));

        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = etEmail.getText().toString().trim();
                presenter.sendResetEmail(email);
            }
        });

    }


    @Override
    public void onEmailSentSuccess() {
        hideLoading();
        tvSubmitMsg.setVisibility(View.VISIBLE);
        tvSubmitMsg.setText("Check your inbox for the reset link!");
        btnSubmit.setEnabled(false);
    }

    @Override
    public void onEmailSentError(String errorMessage) {
        hideLoading();
        Toast.makeText(getContext(), errorMessage, Toast.LENGTH_LONG).show();
    }

    @Override
    public void showLoading() {
        progressBar.setVisibility(View.VISIBLE);
        btnSubmit.setEnabled(false);
    }

    @Override
    public void hideLoading() {
        progressBar.setVisibility(View.GONE);
        btnSubmit.setEnabled(true);
    }
}