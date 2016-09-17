package com.salilgodbole.crossovertest.fragments;

import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputEditText;
import android.support.v4.app.DialogFragment;
import android.support.v7.widget.AppCompatButton;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.salilgodbole.crossovertest.R;

public class RentCollectionDialog extends DialogFragment implements View.OnClickListener {
    private Listener mListener;
    private TextInputEditText mEditCardNumber;
    private TextInputEditText mEditCardName;
    private TextInputEditText mEditCardExpiryMonth;
    private TextInputEditText mEditCardExpiryYear;
    private TextInputEditText mEditCardCode;
    private AppCompatButton mBtnRent;
    private RelativeLayout mParentLayout;

    public RentCollectionDialog() {
        // Required empty public constructor
    }

    public static RentCollectionDialog newInstance() {
        return new RentCollectionDialog();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.card_details_for_rent_dialog, null);

        mParentLayout = (RelativeLayout) rootView.findViewById(R.id.parent_layout_rent_dialog);
        mEditCardNumber = (TextInputEditText) rootView.findViewById(R.id.edit_card_number);
        mEditCardName = (TextInputEditText) rootView.findViewById(R.id.edit_card_name);
        mEditCardExpiryMonth = (TextInputEditText) rootView.findViewById(R.id.edit_card_expiry_month);
        mEditCardExpiryYear = (TextInputEditText) rootView.findViewById(R.id.edit_card_expiry_year);
        mEditCardCode = (TextInputEditText) rootView.findViewById(R.id.edit_card_code);
        mBtnRent = (AppCompatButton) rootView.findViewById(R.id.btn_rent);
        mBtnRent.setOnClickListener(this);

        setCancelable(true);
        return rootView;
    }

    @Override
    public void onClick(View view) {
        if (mListener != null) {
            String cardNumber = mEditCardNumber.getText().toString();
            String cardName = mEditCardName.getText().toString();
            String cardExpiryMonth = mEditCardExpiryMonth.getText().toString();
            String cardExpiryYear = mEditCardExpiryYear.getText().toString();
            String cardCode = mEditCardCode.getText().toString();

            if (!TextUtils.isEmpty(cardNumber)) {
                showSnackBarMessage(getString(R.string.enter_card_number));

                return;
            }

            if (!TextUtils.isEmpty(cardName)) {
                showSnackBarMessage(getString(R.string.enter_card_name));
                return;
            }

            if (!TextUtils.isEmpty(cardExpiryMonth)) {
                showSnackBarMessage(getString(R.string.enter_card_expiry_month));
                return;
            }

            if (!TextUtils.isEmpty(cardExpiryYear)) {
                showSnackBarMessage(getString(R.string.enter_card_expiry_year));
                return;
            }

            if (!TextUtils.isEmpty(cardCode)) {
                showSnackBarMessage(getString(R.string.enter_card_code));
                return;
            }

            String cardExpiration = cardExpiryMonth + "/" + cardExpiryYear;

            if (mListener != null) {
                mListener.onPayRentClicked(cardNumber, cardName, cardExpiration, cardCode);
            }
            dismiss();
        }
    }

    private void showSnackBarMessage(String message) {
        Snackbar.make(mParentLayout, message, Snackbar.LENGTH_SHORT);
    }

    public void setListener(Listener listener) {
        mListener = listener;
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public interface Listener {
        void onPayRentClicked(String cardNumber, String cardName, String cardExpiration, String cardCode);
    }
}
