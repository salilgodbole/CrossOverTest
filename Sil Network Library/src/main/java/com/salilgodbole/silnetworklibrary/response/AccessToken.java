package com.salilgodbole.silnetworklibrary.response;

import org.json.JSONObject;

/**
 * Created by salil on 28/8/16.
 */
public class AccessToken {

    private String token;

    public AccessToken(String token) {
        this.token = token;
    }

    public String getToken() {
        return token;
    }

    public static AccessToken fromJSONObject(JSONObject jsonObject) {
        AccessToken accessToken = null;

        if (jsonObject != null) {
            String token = jsonObject.optString("accessToken");
            accessToken = new AccessToken(token);
        }

        return accessToken;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        AccessToken that = (AccessToken) o;

        return token != null ? token.equals(that.token) : that.token == null;

    }

    @Override
    public int hashCode() {
        return token != null ? token.hashCode() : 0;
    }
}
