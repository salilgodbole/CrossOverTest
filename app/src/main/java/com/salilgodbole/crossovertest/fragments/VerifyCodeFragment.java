package com.salilgodbole.crossovertest.fragments;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.ContentLoadingProgressBar;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatEditText;
import android.support.v7.widget.AppCompatTextView;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.salilgodbole.crossovertest.R;
import com.salilgodbole.crossovertest.utils.Utils;


/**
 * A simple {@link Fragment} subclass.
 */
public class VerifyCodeFragment extends Fragment {

    private static final String ARG_MOBILE_NO = "ARG_MOBILE_NO";

    private ContentLoadingProgressBar mProgressAutoReadingOTP = null;
    private AppCompatEditText mEditOTP = null;
    private AppCompatTextView mTxtOTPSentMessage = null;
    private AppCompatButton mBtnVerifyCode = null;
    private AppCompatButton mBtnResendCode = null;
    private String mMobileNo = null;
    private VerifyCodeFragmentListener mListener = null;

    public VerifyCodeFragment() {
        // Required empty public constructor
    }

    public static VerifyCodeFragment newInstance(String mobileNo) {
        VerifyCodeFragment fragment = new VerifyCodeFragment();
        Bundle bundle = new Bundle();
        bundle.putString(ARG_MOBILE_NO, mobileNo);
        fragment.setArguments(bundle);

        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            mMobileNo = getArguments().getString(ARG_MOBILE_NO);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_verify_code, container, false);

        mTxtOTPSentMessage = (AppCompatTextView) rootView.findViewById(R.id.txt_verification_sent_message);
        mProgressAutoReadingOTP = (ContentLoadingProgressBar) rootView.findViewById(R.id.progress_auto_read_otp);
        mEditOTP = (AppCompatEditText) rootView.findViewById(R.id.edit_otp);
        mBtnVerifyCode = (AppCompatButton) rootView.findViewById(R.id.btn_verify_code);
        mBtnResendCode = (AppCompatButton) rootView.findViewById(R.id.btn_resend_code);

        mEditOTP.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (charSequence.length() == 4) {
                    Utils.hideKeyboard(getContext(), mEditOTP);
                }

                mBtnVerifyCode.setEnabled(charSequence.length() == 4);
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        mBtnVerifyCode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mListener != null) {
                    String otp = mEditOTP.getText().toString();
                    mListener.onVerifyOTPClicked(mMobileNo, otp);
                }
            }
        });

        mBtnResendCode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mListener != null) {
                    mListener.onResendOTPClicked(mMobileNo);
                }
            }
        });

        if (!TextUtils.isEmpty(mMobileNo)) {
            String message = String.format(getString(R.string.verification_code_sent_message_with_format), mMobileNo);
            mTxtOTPSentMessage.setText(message);
        }

        return rootView;
    }

    public void setListener(VerifyCodeFragmentListener listener) {
        mListener = listener;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        mTxtOTPSentMessage = null;
        mMobileNo = null;
        mBtnVerifyCode = null;
        mEditOTP = null;
        mListener = null;
        mProgressAutoReadingOTP = null;
        mBtnResendCode = null;
    }

    public interface VerifyCodeFragmentListener {
        void onVerifyOTPClicked(String mobileNo, String otp);

        void onResendOTPClicked(String mobileNo);
    }
}
