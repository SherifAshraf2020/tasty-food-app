package com.example.tasty_food_app.auth.sign_up.view;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.tasty_food_app.R;

import com.example.tasty_food_app.auth.sign_up.presenter.SignUpPresenter;
import com.example.tasty_food_app.auth.sign_up.presenter.SignUpPresenterImp;
import com.example.tasty_food_app.datasource.SharedPrefsLocalDataSource;
import com.example.tasty_food_app.datasource.remote.auth.AuthRemoteDataSource;
import com.example.tasty_food_app.datasource.repository.AuthRepository;
import com.google.firebase.auth.FirebaseAuth;


public class SignUpFragment extends Fragment implements SignUpView{

    //private TextInputEditText etName, etEmail, etPassword;
    private FirebaseAuth mAuth;

    private SignUpPresenter presenter;
    private EditText etName, etEmail, etPassword;
    private Button btnSignUp;
    private ProgressBar progressBar;
    private TextView tvLoginPage;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mAuth = FirebaseAuth.getInstance();

        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_sign_up, container, false);

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        etName = view.findViewById(R.id.etSinUpName);
        etEmail = view.findViewById(R.id.etSinUpEmail);
        etPassword = view.findViewById(R.id.etSinUpPassword);
        btnSignUp = view.findViewById(R.id.btnSignUp);
        progressBar = view.findViewById(R.id.progressBar);
        tvLoginPage = view.findViewById(R.id.tvLoginPage);


        presenter = new SignUpPresenterImp(this,
                AuthRepository.getInstance(
                        new AuthRemoteDataSource(),
                        new SharedPrefsLocalDataSource(requireContext())
                ));

        btnSignUp.setOnClickListener(v -> {
            String name = etName.getText().toString().trim();
            String email = etEmail.getText().toString().trim();
            String pass = etPassword.getText().toString().trim();

            if (validateInputs(name, email, pass)) {
                presenter.signUp(name, email, pass);
            }
        });

    }
    private boolean validateInputs(String name, String email, String pass) {
        if (name.isEmpty() || email.isEmpty() || pass.isEmpty()) {
            Toast.makeText(getContext(), "All fields are required", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (pass.length() < 6) {
            Toast.makeText(getContext(), "Password must be > 6 characters", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }


    @Override
    public void showLoading() {
        progressBar.setVisibility(View.VISIBLE);
        btnSignUp.setEnabled(false);
        btnSignUp.setAlpha(0.5f);
    }

    @Override
    public void hideLoading() {
        progressBar.setVisibility(View.GONE);
        btnSignUp.setEnabled(true);
        btnSignUp.setAlpha(1.0f);
    }

    @Override
    public void onSignUpSuccess() {
        Toast.makeText(getContext(), "Success", Toast.LENGTH_SHORT).show();
         Navigation.findNavController(requireView()).navigate(R.id.action_auth_graph_to_home_nav_graph);
    }

    @Override
    public void onSignUpError(String error) {
        Toast.makeText(getContext(), error, Toast.LENGTH_LONG).show();
    }
}