package com.salilgodbole.crossovertest.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.AppCompatButton;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.salilgodbole.crossovertest.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends Fragment implements View.OnClickListener {

    private Listener mListener = null;

    public HomeFragment() {
        // Required empty public constructor
    }

    public interface Listener {
        void onSignUpClicked();

        void onSignInClicked();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_home, container, false);

        Button btnSignup = (AppCompatButton) rootView.findViewById(R.id.btn_signup);
        Button btnSignin = (AppCompatButton) rootView.findViewById(R.id.btn_signin);

        btnSignup.setOnClickListener(this);
        btnSignin.setOnClickListener(this);

        return rootView;
    }

    public void setListener(Listener listener) {
        mListener = listener;
    }

    @Override
    public void onClick(View view) {
        if (mListener != null) {
            if (view.getId() == R.id.btn_signup) {
                mListener.onSignUpClicked();
            } else {
                mListener.onSignInClicked();
            }
        }
    }
}
