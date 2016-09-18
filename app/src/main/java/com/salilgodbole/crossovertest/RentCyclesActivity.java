package com.salilgodbole.crossovertest;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.FragmentActivity;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.salilgodbole.crossovertest.fragments.RentCollectionDialog;
import com.salilgodbole.crossovertest.presenter.RentCyclesPresenter;
import com.salilgodbole.crossovertest.view.RentCycleView;
import com.salilgodbole.silnetworklibrary.AppError;
import com.salilgodbole.silnetworklibrary.client.ApiClient;
import com.salilgodbole.silnetworklibrary.client.Environment;
import com.salilgodbole.silnetworklibrary.response.AccessToken;
import com.salilgodbole.silnetworklibrary.response.Location;
import com.salilgodbole.silnetworklibrary.response.Place;

import java.util.List;

public class RentCyclesActivity extends FragmentActivity implements RentCycleView, OnMapReadyCallback, RentCollectionDialog.Listener {

    private GoogleMap mMap;
    private AccessToken mAccessToken;
    private Context mContext = this;
    private ApiClient mApiClient = null;
    private RentCyclesPresenter mRentCyclesPresenter = null;
    private RentCollectionDialog mRentCollectionDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rent_cycles);

        mApiClient = new ApiClient(mContext, Environment.LOCAL);
        mRentCyclesPresenter = new RentCyclesPresenter(mApiClient, this);
        mRentCollectionDialog = RentCollectionDialog.newInstance();
        mRentCollectionDialog.setListener(this);

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(mContext);
        String token = preferences.getString(Constants.ACCESS_TOKEN_KEY, null);

        if (token != null) {
            mAccessToken = new AccessToken(token);
        }
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        mMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(Marker marker) {
                mRentCollectionDialog.show(getSupportFragmentManager(), "mRentCollectionDialog");

                return false;
            }
        });

        fetchPlaces();
    }

    private void fetchPlaces() {
        mRentCyclesPresenter.getPlaces(mAccessToken);
    }

    private void plotPlacesOnMap(List<Place> placeList) {
        LatLngBounds.Builder b = new LatLngBounds.Builder();

        for (Place place : placeList) {
            Location location = place.getLocation();
            LatLng placeOnMap = new LatLng(location.getLatitude(), location.getLongitude());
            Marker marker = mMap.addMarker(new MarkerOptions().position(placeOnMap).title(place.getName()));

            b.include(marker.getPosition());
        }

        LatLngBounds bounds = b.build();
        //Change the padding as per needed
        CameraUpdate cu = CameraUpdateFactory.newLatLngBounds(bounds, 25, 25, 5);
        mMap.animateCamera(cu);
    }

    @Override
    public void onReceivedPlaces(List<Place> placeList) {
        plotPlacesOnMap(placeList);
    }

    @Override
    public void onRentCollectionSuccessful(String message) {
        Toast.makeText(RentCyclesActivity.this, message, Toast.LENGTH_SHORT).show();
        mRentCollectionDialog.dismiss();
    }

    @Override
    public void onRentCollectionUnsuccessful(AppError appError) {

    }

    @Override
    public void onPayRentClicked(String cardNumber, String cardName, String cardExpiration, String cardCode) {
        mRentCyclesPresenter.rentCycle(mAccessToken, cardNumber, cardName, cardExpiration, cardCode);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        mMap = null;
        mAccessToken = null;
        mContext = null;
        mApiClient = null;
        mRentCyclesPresenter = null;
        mRentCollectionDialog = null;
    }
}











