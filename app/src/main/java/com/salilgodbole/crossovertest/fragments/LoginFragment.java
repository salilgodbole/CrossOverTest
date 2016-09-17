package com.salilgodbole.crossovertest.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatEditText;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.TextView;

import com.salilgodbole.crossovertest.R;
import com.salilgodbole.crossovertest.utils.Utils;

/**
 * A placeholder fragment containing a simple view.
 */
public class LoginFragment extends Fragment {

    public static final int LOGIN_TYPE_SIGNIN = 1;
    public static final int LOGIN_TYPE_SIGNUP = 2;

    public static final String INTENT_EXTRA_LOGIN_TYPE = "INTENT_EXTRA_LOGIN_TYPE";

    private LoginFragmentListener mListener = null;
    // UI references.
    private AppCompatEditText mEditEmailId;
    private AppCompatEditText mEditPassword;
    private int loginType = -1;


    public LoginFragment() {
    }

    public static LoginFragment newInstance(int loginType) {
        LoginFragment loginFragment = new LoginFragment();

        Bundle bundle = new Bundle();
        bundle.putInt(INTENT_EXTRA_LOGIN_TYPE, loginType);
        loginFragment.setArguments(bundle);

        return loginFragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Bundle bundle = getArguments();
        if (bundle != null) {
            loginType = bundle.getInt(INTENT_EXTRA_LOGIN_TYPE);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_login, container, false);

        // Set up the login form.
        mEditEmailId = (AppCompatEditText) rootView.findViewById(R.id.edit_email_id);
        mEditPassword = (AppCompatEditText) rootView.findViewById(R.id.edit_password);
        AppCompatButton btnSigninSignup = (AppCompatButton) rootView.findViewById(R.id.btn_signin_signup);

        if (loginType == LOGIN_TYPE_SIGNUP) {
            btnSigninSignup.setText(getString(R.string.title_signup));
        } else {
            btnSigninSignup.setText(getString(R.string.title_signin));
        }

        btnSigninSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                signinSignupClicked();
            }
        });

        mEditPassword.setOnEditorActionListener(editorActionListener);

        return rootView;
    }

    private TextView.OnEditorActionListener editorActionListener = new TextView.OnEditorActionListener() {
        @Override
        public boolean onEditorAction(TextView v, int actionId,
                                      KeyEvent event) {
            boolean handled = false;
            if (actionId == EditorInfo.IME_ACTION_GO) {

                Utils.hideKeyboard(getContext(), mEditEmailId);
                signinSignupClicked();
                    /* handle action here */
                handled = true;
            }
            return handled;
        }
    };

    private void signinSignupClicked() {
        String emailId = mEditEmailId.getText().toString();
        String password = mEditPassword.getText().toString();
        if (mListener != null) {
            if (loginType == LOGIN_TYPE_SIGNIN) {
                mListener.onSignInClicked(emailId, password);
            } else {
                mListener.onSignUpClicked(emailId, password);
            }
        }
    }

    public void setListener(LoginFragmentListener listener) {
        mListener = listener;
    }

    public interface LoginFragmentListener {
        void onSignInClicked(String emailId, String password);

        void onSignUpClicked(String emailId, String password);
    }
}
