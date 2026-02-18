package com.example.tasty_food_app.home.settings.view;

public interface SettingsView {
    void onLogoutSuccess();
    void showError(String message);
    void displayUserEmail(String email);
}
