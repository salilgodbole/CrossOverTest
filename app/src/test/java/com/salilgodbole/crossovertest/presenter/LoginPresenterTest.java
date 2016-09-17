package com.salilgodbole.crossovertest.presenter;

import com.salilgodbole.crossovertest.test.BuildConfig;
import com.salilgodbole.crossovertest.view.LoginView;
import com.salilgodbole.silnetworklibrary.client.ApiClient;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.robolectric.RobolectricGradleTestRunner;
import org.robolectric.annotation.Config;

/**
 * Created by salil on 28/8/16.
 */
@RunWith(RobolectricGradleTestRunner.class)
@Config(constants = BuildConfig.class, sdk = 18, manifest = "src/debug/AndroidManifest.xml")
public class LoginPresenterTest {

    private LoginPresenter loginPresenter;
    private LoginView loginView;
    private ApiClient apiClient;

    @Before
    public void setup() {
        apiClient = Mockito.mock(ApiClient.class);
        loginView = Mockito.mock(LoginView.class);

        loginPresenter = new LoginPresenter(apiClient, loginView);

    }

    @Test
    public void testValidateMobile() throws Exception {
        Assert.assertFalse(loginPresenter.validateEmail(""));
        Assert.assertFalse(loginPresenter.validateEmail("salilgodbole")); // Mobile no < 10 digits.
        Assert.assertFalse(loginPresenter.validateEmail("salilgodbole@gmail.com.com")); // Mobile no > 10 digits.

        Assert.assertTrue(loginPresenter.validateEmail("salilgodbole@gmail.com"));
    }
}