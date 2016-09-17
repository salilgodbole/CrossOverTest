package com.salilgodbole.silnetworklibrary.client;


import android.content.Context;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

/**
 * Created by salil on 17/9/16.
 */
public class NetworkClient {

    private Context mContext = null;
    private RequestQueue mRequestQueue = null;
    private static NetworkClient mInstance = null;

    private NetworkClient(Context context) {
        mContext = context;
        mRequestQueue = getRequestQueue();
    }

    public static NetworkClient getInstance(Context context) {
        if (mInstance == null) {
            synchronized (NetworkClient.class) {
                if (mInstance == null) {
                    mInstance = new NetworkClient(context);
                }
            }
        }
        return mInstance;
    }

    public RequestQueue getRequestQueue() {
        if (mRequestQueue == null) {
            synchronized (NetworkClient.class) {
                if (mRequestQueue == null) {
                    mRequestQueue = Volley.newRequestQueue(mContext.getApplicationContext());
                }
            }
        }

        return mRequestQueue;
    }

    public synchronized <T> void addToRequestQueue(Request<T> req) {
        getRequestQueue().add(req);
    }

    public synchronized void cancelRequests(final String tag) {
        if (mRequestQueue != null) {
            getRequestQueue().cancelAll(tag);
        }
    }

}
