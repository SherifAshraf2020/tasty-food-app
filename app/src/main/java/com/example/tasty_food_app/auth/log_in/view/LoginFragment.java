package com.example.tasty_food_app.auth.log_in.view;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;



import androidx.credentials.Credential;
import androidx.credentials.CredentialManager;
import androidx.credentials.CredentialManagerCallback;
import androidx.credentials.CustomCredential;
import androidx.credentials.GetCredentialRequest;
import androidx.credentials.GetCredentialResponse;
import androidx.credentials.exceptions.GetCredentialException;

import com.example.tasty_food_app.datasource.SharedPrefsLocalDataSource;
import com.google.android.libraries.identity.googleid.GetGoogleIdOption;
import com.google.android.libraries.identity.googleid.GoogleIdTokenCredential;


import android.util.Log;

import com.example.tasty_food_app.R;
import com.example.tasty_food_app.auth.log_in.presenter.LoginPresenter;
import com.example.tasty_food_app.auth.log_in.presenter.LoginPresenterImp;
import com.example.tasty_food_app.datasource.remote.auth.AuthRemoteDataSource;
import com.example.tasty_food_app.datasource.repository.AuthRepository;
import com.google.android.material.textfield.TextInputEditText;


public class LoginFragment extends Fragment implements LoginView{
    private LoginPresenterImp presenter;
    private TextInputEditText etEmail, etPassword;
    private Button btnSignIn;
    private CardView btnGoogle;
    private TextView tvRegister, tvForgetPassword;
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
        btnGoogle = view.findViewById(R.id.btnSignInWithGoogle);
        progressBar = view.findViewById(R.id.progressBar);

        presenter = new LoginPresenterImp(this,
                AuthRepository.getInstance(
                        new AuthRemoteDataSource(requireContext()),
                        new SharedPrefsLocalDataSource(requireContext())
                ));

        btnSignIn.setOnClickListener(v -> {
            String email = etEmail.getText().toString().trim();
            String password = etPassword.getText().toString().trim();
            presenter.signIn(email, password);
        });

        setupGoogleLogin();

        tvForgetPassword.setOnClickListener(v -> {
            String email = etEmail.getText().toString().trim();
            if (email.isEmpty()) {
                etEmail.setError("Email is required");
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

    private void setupGoogleLogin() {
        CredentialManager credentialManager = CredentialManager.create(requireContext());

        btnGoogle.setOnClickListener(v -> {
            GetGoogleIdOption googleIdOption = new GetGoogleIdOption.Builder()
                    .setFilterByAuthorizedAccounts(false)
                    .setServerClientId(getString(R.string.default_web_client_id))
                    .setAutoSelectEnabled(false)
                    .build();

            GetCredentialRequest request = new GetCredentialRequest.Builder()
                    .addCredentialOption(googleIdOption)
                    .build();

            credentialManager.getCredentialAsync(
                    requireActivity(),
                    request,
                    null,
                    Runnable::run,
                    new CredentialManagerCallback<GetCredentialResponse, GetCredentialException>() {
                        @Override
                        public void onResult(GetCredentialResponse result) {
                            handleGoogleSignIn(result);
                        }

                        @Override
                        public void onError(GetCredentialException e) {
                            Log.e("GoogleAuth", "Error: " + e.getMessage());
                            requireActivity().runOnUiThread(() -> onLoginError("Google Sign-in cancelled"));
                        }
                    }
            );
        });
    }

    private void handleGoogleSignIn(GetCredentialResponse result) {
        Credential credential = result.getCredential();
        if (credential instanceof CustomCredential &&
                credential.getType().equals(GoogleIdTokenCredential.TYPE_GOOGLE_ID_TOKEN_CREDENTIAL)) {
            try {
                GoogleIdTokenCredential googleIdToken = GoogleIdTokenCredential.createFrom(credential.getData());
                String idToken = googleIdToken.getIdToken();
                String email = googleIdToken.getId();
                requireActivity().runOnUiThread(() -> presenter.signInWithGoogle(idToken, email));
            } catch (Exception e) {
                Log.e("GoogleAuth", "Parsing Error", e);
            }
        }
    }

    @Override
    public void showLoading() {
        btnSignIn.setEnabled(false);
        progressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideLoading() {
        btnSignIn.setEnabled(true);
        progressBar.setVisibility(View.GONE);
    }

    @Override
    public void onLoginSuccess() {
        Toast.makeText(getContext(), "Welcome!", Toast.LENGTH_SHORT).show();
        Navigation.findNavController(requireView()).navigate(R.id.action_auth_graph_to_home_nav_graph);
    }

    @Override
    public void onLoginError(String error) {
        Toast.makeText(getContext(), error, Toast.LENGTH_LONG).show();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        presenter.clear();
    }
}