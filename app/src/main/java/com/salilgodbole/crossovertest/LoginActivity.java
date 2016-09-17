package com.salilgodbole.crossovertest;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.widget.FrameLayout;

import com.salilgodbole.crossovertest.fragments.LoginFragment;
import com.salilgodbole.crossovertest.presenter.LoginPresenter;
import com.salilgodbole.crossovertest.view.LoginView;
import com.salilgodbole.silnetworklibrary.AppError;
import com.salilgodbole.silnetworklibrary.client.ApiClient;
import com.salilgodbole.silnetworklibrary.client.Environment;
import com.salilgodbole.silnetworklibrary.response.AccessToken;

/**
 * A login screen that offers login via email/password.
 */
public class LoginActivity extends AppCompatActivity implements LoginView, LoginFragment.LoginFragmentListener {

    /**
     * Id to identity READ_CONTACTS permission request.
     */
    private static final int REQUEST_READ_CONTACTS = 0;

    private Context mContext = this;
    private ApiClient mApiClient = null;
    private LoginPresenter mLoginPresenter = null;
    private FragmentManager mFragmentManager = null;
    private FrameLayout mFrameLayout = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        init();
    }

    private void init() {
        mFrameLayout = (FrameLayout) findViewById(R.id.container);
        mFragmentManager = getSupportFragmentManager();

        mApiClient = new ApiClient(mContext, Environment.LOCAL);
        mLoginPresenter = new LoginPresenter(mApiClient, this);

        showLoginFragment();
    }

    private void showLoginFragment() {
        LoginFragment loginFragment = new LoginFragment();
        loginFragment.setListener(this);

        FragmentTransaction fragmentTransaction = mFragmentManager.beginTransaction()
                .setCustomAnimations(android.R.anim.fade_in, android.R.anim.fade_out)
                .add(R.id.container, loginFragment);
        fragmentTransaction.commit();
    }

    private void showMessage(String message) {
        Snackbar.make(mFrameLayout, message, Snackbar.LENGTH_SHORT);
    }

    private void showRentCycleActivity() {
        Intent intent = new Intent(mContext, RentCyclesActivity.class);
        startActivity(intent);
        finish();
    }

    private void saveToken(AccessToken accessToken) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(mContext);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(Constants.ACCESS_TOKEN_KEY, accessToken.getToken());
        editor.apply();
    }

    @Override
    public void onLoginSuccessful(AccessToken accessToken) {
        saveToken(accessToken);
        showRentCycleActivity();
    }

    @Override
    public void onLoginError(String message) {
        showMessage(message);
    }

    @Override
    public void onSignUpSuccessful(AccessToken accessToken) {
        saveToken(accessToken);
        showRentCycleActivity();
    }

    @Override
    public void onSignUpError(AppError appError) {
        showMessage(appError.getMessage());
    }

    @Override
    public void onSignInClicked(String emailId, String password) {
        mLoginPresenter.signIn(emailId, password);
    }

    @Override
    public void onSignUpClicked(String emailId, String password) {
        mLoginPresenter.signUp(emailId, password);
    }

    /**
     * Callback received when a permissions request has been completed.
     */
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        if (requestCode == REQUEST_READ_CONTACTS) {
            if (grantResults.length == 1 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        if (mApiClient != null) {
            mApiClient.destroy();
            mApiClient = null;
        }

        mLoginPresenter = null;
        mFragmentManager = null;

        mFrameLayout = null;
        mContext = null;
    }


}
