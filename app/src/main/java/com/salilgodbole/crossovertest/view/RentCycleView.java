package com.salilgodbole.crossovertest.view;

import com.salilgodbole.silnetworklibrary.AppError;
import com.salilgodbole.silnetworklibrary.response.Place;

import java.util.List;

/**
 * Created by salil on 18/9/16.
 */
public interface RentCycleView {
    void onReceivedPlaces(List<Place> placeList);

    void onRentCollectionSuccessful();

    void onRentCollectionUnsuccessful(AppError appError);
}
