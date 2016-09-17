package com.salilgodbole.crossovertest.presenter;

import com.salilgodbole.crossovertest.view.RentCycleView;
import com.salilgodbole.silnetworklibrary.client.ApiClient;

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
}
