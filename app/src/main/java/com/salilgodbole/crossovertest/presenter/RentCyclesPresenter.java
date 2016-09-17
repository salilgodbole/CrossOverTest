package com.salilgodbole.crossovertest.presenter;

import com.salilgodbole.crossovertest.view.RentCycleView;
import com.salilgodbole.silnetworklibrary.AppError;
import com.salilgodbole.silnetworklibrary.Callback;
import com.salilgodbole.silnetworklibrary.client.ApiClient;
import com.salilgodbole.silnetworklibrary.response.AccessToken;
import com.salilgodbole.silnetworklibrary.response.Place;

import java.util.List;

/**
 * Created by salil on 18/9/16.
 */
public class RentCyclesPresenter {

    private final ApiClient mApiClient;
    private final RentCycleView mRentCycleView;

    public RentCyclesPresenter(ApiClient apiClient, RentCycleView rentCycleView) {
        mApiClient = apiClient;
        mRentCycleView = rentCycleView;
    }

    public void rentCycle() {

    }

    public void getPlaces(AccessToken accessToken) {
        mApiClient.getPlaces(accessToken, new Callback<List<Place>>() {
            @Override
            public void success(List<Place> placeList) {
                mRentCycleView.onReceivedPlaces(placeList);
            }

            @Override
            public void error(AppError appError) {

            }
        });
    }
}
