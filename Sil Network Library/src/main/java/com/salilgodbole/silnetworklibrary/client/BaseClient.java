package com.salilgodbole.silnetworklibrary.client;

import android.content.Context;

import com.android.volley.VolleyError;
import com.salilgodbole.silnetworklibrary.AppError;
import com.salilgodbole.silnetworklibrary.Callback;

/**
 * Created by salil on 17/9/16.
 */
public class BaseClient {

    protected final Environment mEnvironment;
    protected final Context mContext;

    public BaseClient(Context context, Environment environment) {
        mContext = context;
        this.mEnvironment = environment;
    }

    public <T> void sendResponse(Callback callback, T t) {
        if (callback != null) {
            callback.success(t);
        }
    }

    protected void sendError(Callback callback, VolleyError volleyError) {
        if (callback != null) {
            if (volleyError.networkResponse != null) {
                String message = new String(volleyError.networkResponse.data);
                callback.error(new AppError(message));
            } else {
                callback.error(new AppError(volleyError.getMessage()));
            }
        }
    }

    protected void sendError(Callback callback, AppError appError) {
        if (callback != null) {
            callback.error(appError);
        }
    }
}
