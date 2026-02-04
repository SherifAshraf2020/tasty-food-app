package com.example.tasty_food_app.auth.log_in;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.tasty_food_app.R;
import com.example.tasty_food_app.datasource.remote.AuthRemoteDataSource;
import com.example.tasty_food_app.datasource.repository.AuthRepository;
import com.google.android.material.textfield.TextInputEditText;


public class LoginFragment extends Fragment implements LoginView{
    private LoginPresenter presenter;
    private TextInputEditText etEmail, etPassword;
    private Button btnSignIn;
    private TextView tvRegister,tvForgetPassword;
    private ProgressBar progressBar;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_login, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        etEmail = view.findViewById(R.id.etSinInEmail);
        etPassword = view.findViewById(R.id.etSinInPassword);
        btnSignIn = view.findViewById(R.id.btnSignIn);
        tvRegister = view.findViewById(R.id.tvRegister);
        tvForgetPassword = view.findViewById(R.id.tvForgotPassword);

        progressBar = new ProgressBar(getContext());

        presenter = new LoginPresenterImp(this,
                AuthRepository.getInstance(new AuthRemoteDataSource()));

        btnSignIn.setOnClickListener(v -> {
            String email = etEmail.getText().toString().trim();
            String password = etPassword.getText().toString().trim();

            if (email.isEmpty() || password.isEmpty()) {
                Toast.makeText(getContext(), "Please enter email and password", Toast.LENGTH_SHORT).show();
            } else {
                presenter.signIn(email, password);
            }
        });

        tvForgetPassword.setOnClickListener(v -> {
            String email = etEmail.getText().toString().trim();

            if (email.isEmpty()) {
                etEmail.setError("Email is required");
                Toast.makeText(getContext(), "Please enter your email", Toast.LENGTH_SHORT).show();
            } else {
                Bundle bundle = new Bundle();
                bundle.putString("userEmail", email);
                Navigation.findNavController(v).navigate(R.id.action_loginFragment_to_forgetPasswordFragment, bundle);
            }
        });

        tvRegister.setOnClickListener(v -> {
            Navigation.findNavController(v).navigate(R.id.action_loginFragment_to_signUpFragment);
        });
    }


    @Override
    public void showLoading() {
        btnSignIn.setEnabled(false);
        btnSignIn.setAlpha(0.5f);
        progressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideLoading() {
        btnSignIn.setEnabled(true);
        btnSignIn.setAlpha(1.0f);
        progressBar.setVisibility(View.GONE);
    }

    @Override
    public void onLoginSuccess() {
        Toast.makeText(getContext(), "Login Successful", Toast.LENGTH_SHORT).show();
         Navigation.findNavController(requireView()).navigate(R.id.action_auth_graph_to_home_nav_graph);
    }

    @Override
    public void onLoginError(String error) {
        Toast.makeText(getContext(), error, Toast.LENGTH_LONG).show();
    }
}