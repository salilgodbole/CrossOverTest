package com.salilgodbole.crossovertest;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.design.widget.Snackbar;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.Window;
import android.view.WindowManager;
import android.widget.FrameLayout;

import com.salilgodbole.crossovertest.fragments.HomeFragment;
import com.salilgodbole.crossovertest.fragments.LoginFragment;
import com.salilgodbole.crossovertest.presenter.LoginPresenter;
import com.salilgodbole.crossovertest.utils.Utils;
import com.salilgodbole.crossovertest.view.LoginView;
import com.salilgodbole.silnetworklibrary.AppError;
import com.salilgodbole.silnetworklibrary.client.ApiClient;
import com.salilgodbole.silnetworklibrary.client.Environment;
import com.salilgodbole.silnetworklibrary.response.AccessToken;

/**
 * A login screen that offers login via email/password.
 */
public class LoginActivity extends AppCompatActivity implements LoginView, LoginFragment.LoginFragmentListener, HomeFragment.Listener {
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

        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(mContext);
        String token = preferences.getString(Constants.ACCESS_TOKEN_KEY, null);

        if (!Utils.isNetworkConnected(mContext)) {
            showSnackBarMessage(Constants.NO_INTERNET_MESSAGE);
            return;
        }

        if (token != null) {
            showRentCycleActivity();
        } else {
            // Since the token is null, it means that the user is not logged id. Prompt the user to login.
            showHomeFragment();
        }

        // Set action bar color. Available only on android version Lollipop or higher.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(Color.parseColor("#00796B"));
        }
    }

    private void showHomeFragment() {
        HomeFragment homeFragment = new HomeFragment();
        homeFragment.setListener(this);

        FragmentTransaction fragmentTransaction = mFragmentManager.beginTransaction()
                .setCustomAnimations(android.R.anim.fade_in, android.R.anim.fade_out)
                .add(R.id.container, homeFragment);
        fragmentTransaction.commit();
    }

    private void showLoginFragment(int loginType) {
        LoginFragment loginFragment = LoginFragment.newInstance(loginType);
        loginFragment.setListener(this);

        FragmentTransaction fragmentTransaction = mFragmentManager.beginTransaction()
                .setCustomAnimations(android.R.anim.fade_in, android.R.anim.fade_out)
                .add(R.id.container, loginFragment);
        fragmentTransaction.commit();
    }

    private void showSnackBarMessage(String message) {
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
    public void onSignUpClicked() {
        showLoginFragment(LoginFragment.LOGIN_TYPE_SIGNUP);
    }

    @Override
    public void onSignInClicked() {
        showLoginFragment(LoginFragment.LOGIN_TYPE_SIGNIN);
    }

    @Override
    public void onLoginSuccessful(AccessToken accessToken) {
        saveToken(accessToken);
        showRentCycleActivity();
    }

    @Override
    public void onLoginError(String message) {
        showSnackBarMessage(message);
    }

    @Override
    public void onSignUpSuccessful(AccessToken accessToken) {
        saveToken(accessToken);
        showRentCycleActivity();
    }

    @Override
    public void onSignUpError(AppError appError) {
        showSnackBarMessage(appError.getMessage());
    }

    @Override
    public void onSignInClicked(String emailId, String password) {
        mLoginPresenter.signIn(emailId, password);
    }

    @Override
    public void onSignUpClicked(String emailId, String password) {
        mLoginPresenter.signUp(emailId, password);
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
