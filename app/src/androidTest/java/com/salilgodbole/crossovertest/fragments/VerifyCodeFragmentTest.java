package com.salilgodbole.crossovertest.fragments;

import android.support.test.runner.AndroidJUnit4;

import com.salilgodbole.crossovertest.R;
import com.salilgodbole.crossovertest.fragments.rules.FragmentTestRule;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isCompletelyDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;

/**
 * Created by salil on 29/8/16.
 */
@RunWith(AndroidJUnit4.class)
public class VerifyCodeFragmentTest {

    @Rule
    public FragmentTestRule fragmentTestRule = new FragmentTestRule();
    private VerifyCodeFragment fragment;

    @Before
    public void setup() {
        fragment = new VerifyCodeFragment();
        fragmentTestRule.launch(fragment);
    }


    @Test
    public void verifyAllUIElementsVisibleOnScreen() throws InterruptedException {
        Thread.sleep(4000);
        onView(withId(R.id.txt_verification_sent_message)).check(matches(isCompletelyDisplayed()));
    }
}