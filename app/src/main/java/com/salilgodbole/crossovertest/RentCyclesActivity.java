package com.salilgodbole.crossovertest;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.FragmentActivity;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.salilgodbole.crossovertest.presenter.RentCyclesPresenter;
import com.salilgodbole.crossovertest.view.RentCycleView;
import com.salilgodbole.silnetworklibrary.client.ApiClient;
import com.salilgodbole.silnetworklibrary.client.Environment;
import com.salilgodbole.silnetworklibrary.response.AccessToken;
import com.salilgodbole.silnetworklibrary.response.Location;
import com.salilgodbole.silnetworklibrary.response.Place;

import java.util.List;

public class RentCyclesActivity extends FragmentActivity implements RentCycleView, OnMapReadyCallback {

    private GoogleMap mMap;
    private AccessToken mAccessToken;
    private Context mContext = this;
    private ApiClient mApiClient = null;
    private RentCyclesPresenter mRentCyclesPresenter = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rent_cycles);

        mApiClient = new ApiClient(mContext, Environment.LOCAL);
        mRentCyclesPresenter = new RentCyclesPresenter(mApiClient, this);

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(mContext);
        String token = preferences.getString(Constants.ACCESS_TOKEN_KEY, null);

        if (token != null) {
            mAccessToken = new AccessToken(token);
        }

        mMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(Marker marker) {

                return false;
            }
        });
    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        fetchPlaces();
    }

    private void fetchPlaces() {
        mRentCyclesPresenter.getPlaces(mAccessToken);
    }

    private void plotPlacesOnMap(List<Place> placeList) {
        for (Place place : placeList) {
            Location location = place.getLocation();
            LatLng placeOnMap = new LatLng(location.getLatitude(), location.getLongitude());
            mMap.addMarker(new MarkerOptions().position(placeOnMap).title(place.getName()));
        }
    }

    @Override
    public void onReceivedPlaces(List<Place> placeList) {
        plotPlacesOnMap(placeList);
    }
}
