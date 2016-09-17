package com.salilgodbole.crossovertest.presenter;

import com.salilgodbole.crossovertest.utils.Utils;
import com.salilgodbole.crossovertest.view.LoginView;
import com.salilgodbole.silnetworklibrary.AppError;
import com.salilgodbole.silnetworklibrary.Callback;
import com.salilgodbole.silnetworklibrary.client.ApiClient;
import com.salilgodbole.silnetworklibrary.response.AccessToken;

/**
 * Created by salil.
 */
public class LoginPresenter {

    private final ApiClient mApiClient;
    private final LoginView mLoginView;

    public LoginPresenter(final ApiClient apiClient, final LoginView loginView) {
        this.mApiClient = apiClient;
        this.mLoginView = loginView;
    }

    public boolean validateEmail(String emailId) {
        return Utils.isValidEmail(emailId);
    }

    public void signIn(String emailId, String password) {
        mApiClient.signIn(emailId, password, new Callback<AccessToken>() {
            @Override
            public void success(AccessToken accessToken) {
                mLoginView.onLoginSuccessful(accessToken);
            }

            @Override
            public void error(AppError appError) {
                mLoginView.onLoginError(appError.getMessage());
            }
        });
    }

    public void signUp(String emailId, String password) {
        mApiClient.signUp(emailId, password, new Callback<AccessToken>() {
            @Override
            public void success(AccessToken accessToken) {
                mLoginView.onSignUpSuccessful(accessToken);
            }

            @Override
            public void error(AppError appError) {
                mLoginView.onSignUpError(appError);
            }
        });
    }
}
