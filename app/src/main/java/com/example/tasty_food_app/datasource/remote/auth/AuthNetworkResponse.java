package com.example.tasty_food_app.datasource.remote.auth;

public interface AuthNetworkResponse {
    void onSuccess();
    void onFailure(String error);
}
