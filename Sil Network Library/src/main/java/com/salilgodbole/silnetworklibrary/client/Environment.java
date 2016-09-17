package com.salilgodbole.silnetworklibrary.client;

/**
 * Created by salil on 17/9/16.
 */
public enum Environment {
    PRODUCTION {
        @Override
        public String getBaseUrl() {
            return "http://server.com";
        }
    }, LOCAL {
        @Override
        public String getBaseUrl() {
            return "http://192.168.1.8:8080";
        }
    };

    public abstract String getBaseUrl();
}