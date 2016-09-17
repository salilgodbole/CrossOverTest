package com.salilgodbole.silnetworklibrary.client;

import android.content.Context;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.salilgodbole.silnetworklibrary.AppError;
import com.salilgodbole.silnetworklibrary.Callback;
import com.salilgodbole.silnetworklibrary.response.AccessToken;
import com.salilgodbole.silnetworklibrary.response.PaymentResponse;
import com.salilgodbole.silnetworklibrary.response.Place;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by salil on 28/8/16.
 */
public class ApiClient extends BaseClient {

    private NetworkClient networkClient = null;

    // Need to implement network layer here.
    public ApiClient(Context context, Environment environment) {
        super(context, environment);

        networkClient = NetworkClient.getInstance(mContext);
    }


    public void signIn(String emailId, String password, final Callback<AccessToken> callback) {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("email", emailId);
            jsonObject.put("password", password);

            String url = mEnvironment.getBaseUrl() + "/api/v1/auth";

            JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, url, jsonObject, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                    AccessToken accessToken = AccessToken.fromJSONObject(response);
                    if (accessToken != null) {
                        sendResponse(callback, accessToken);
                    } else {
                        sendError(callback, new AppError("Unable to signin"));
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    sendError(callback, error);
                }
            });

            networkClient.addToRequestQueue(request);

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public void signUp(String emailId, String password, final Callback<AccessToken> callback) {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("email", emailId);
            jsonObject.put("password", password);

            String url = mEnvironment.getBaseUrl() + "/api/v1/register";

            JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, url, jsonObject, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                    AccessToken accessToken = AccessToken.fromJSONObject(response);
                    if (accessToken != null) {
                        sendResponse(callback, accessToken);
                    } else {
                        sendError(callback, new AppError("Unable to signup"));
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    sendError(callback, error);
                }
            });

            networkClient.addToRequestQueue(request);

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public void getPlaces(final AccessToken accessToken, final Callback<List<Place>> callback) {
        if (accessToken != null) {
            String url = mEnvironment.getBaseUrl() + "/api/v1/places";

            JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                    List<Place> placeList = new ArrayList<>();
                    if (response != null) {
                        JSONArray resultsArray = response.optJSONArray("results");
                        for (int i = 0; i < resultsArray.length(); i++) {
                            JSONObject placeObj = resultsArray.optJSONObject(i);
                            Place place = Place.fromJSONObject(placeObj);

                            placeList.add(place);
                        }
                    }

                    sendResponse(callback, placeList);
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    sendError(callback, error);
                }
            }) {
                @Override
                public Map<String, String> getHeaders() throws AuthFailureError {
                    Map<String, String> headers = new HashMap<>();

                    headers.put("Authorization", accessToken.getToken());
                    return headers;
                }
            };

            networkClient.addToRequestQueue(request);
        } else {
            sendError(callback, new AppError("Access token should not be null"));
        }
    }

    public void payRent(final AccessToken accessToken, final Callback<PaymentResponse> callback) {
//        /api/v1/rent
    }

    public void destroy() {

    }
}
