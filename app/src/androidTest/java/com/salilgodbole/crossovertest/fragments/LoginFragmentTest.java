package com.salilgodbole.crossovertest.fragments;

import android.support.test.espresso.matcher.ViewMatchers;
import android.support.test.runner.AndroidJUnit4;

import com.salilgodbole.crossovertest.R;
import com.salilgodbole.crossovertest.fragments.rules.FragmentTestRule;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isCompletelyDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;

/**
 * Created by salil on 25/8/16.
 */
@RunWith(AndroidJUnit4.class)
public class LoginFragmentTest {

    @Rule
    public FragmentTestRule fragmentTestRule = new FragmentTestRule();
    private LoginFragment fragment;

    @Test
    public void verifyAllUIElementsShownOnScreenForSignin() {
        fragment = LoginFragment.newInstance(LoginFragment.LOGIN_TYPE_SIGNIN);
        fragmentTestRule.launch(fragment);

        onView(ViewMatchers.withId(R.id.edit_email_id)).check(matches(isCompletelyDisplayed()));
        onView(ViewMatchers.withId(R.id.edit_password)).check(matches(isCompletelyDisplayed()));
        onView(withId(R.id.btn_signin_signup)).check(matches(isCompletelyDisplayed()));
        onView(withId(R.id.btn_signin_signup)).check(matches(withText(R.string.title_signin)));
    }

    @Test
    public void verifyAllUIElementsShownOnScreenForSignup() {
        fragment = LoginFragment.newInstance(LoginFragment.LOGIN_TYPE_SIGNUP);
        fragmentTestRule.launch(fragment);

        onView(ViewMatchers.withId(R.id.edit_email_id)).check(matches(isCompletelyDisplayed()));
        onView(ViewMatchers.withId(R.id.edit_password)).check(matches(isCompletelyDisplayed()));
        onView(ViewMatchers.withId(R.id.edit_confirm_password)).check(matches(isCompletelyDisplayed()));
        onView(withId(R.id.btn_signin_signup)).check(matches(isCompletelyDisplayed()));
        onView(withId(R.id.btn_signin_signup)).check(matches(withText(R.string.title_signup)));
    }
//
//    @Test
//    public void verifySigninSignupButtonClick() {
//        LoginFragment.LoginFragmentListener loginFragmentListener = Mockito.mock(LoginFragment.LoginFragmentListener.class);
//        fragment.setListener(loginFragmentListener);
//
//        String mobileNo = "9970950374";
//        onView(withId(R.id.txt_mobile_no)).perform(typeText(mobileNo));
//
//        onView(withId(R.id.btn_send_code)).perform(click());
//        Mockito.verify(loginFragmentListener).sendCodeButtonClicked(eq(mobileNo));
//    }
}