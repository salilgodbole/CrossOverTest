package com.salilgodbole.crossovertest.utils;

import android.content.Context;
import android.text.TextUtils;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

/**
 * Created by salil.
 */
public final class Utils {

    public static void hideKeyboard(Context context, EditText editText) {
        try {
            InputMethodManager inputManager = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
            inputManager.hideSoftInputFromWindow(editText
                    .getWindowToken(), 0);
        } catch (Exception e) {
        }
    }

    public static boolean isValidEmail(CharSequence target) {

        int emailLength = target.length();
        // if user has entered emailid then validate it else return true below.
        // This is done to keep email non-mandatory during link user
        return emailLength <= 0 || !TextUtils.isEmpty(target) && android.util.Patterns.EMAIL_ADDRESS.matcher(target).matches();
    }
}
