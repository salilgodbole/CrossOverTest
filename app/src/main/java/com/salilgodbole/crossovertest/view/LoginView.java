package com.salilgodbole.crossovertest.view;


import com.salilgodbole.silnetworklibrary.AppError;
import com.salilgodbole.silnetworklibrary.response.AccessToken;

/**
 * Created by salil on 28/8/16.
 */
public interface LoginView {

    void onLoginSuccessful(AccessToken accessToken);

    void onLoginError(String message);

    void onSignUpSuccessful(AccessToken accessToken);

    void onSignUpError(AppError appError);
}
