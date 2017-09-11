package com.example.hosiluan.foodanddrink.showdirectionactivity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Location;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.hosiluan.foodanddrink.R;
import com.example.hosiluan.foodanddrink.model.Place;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.maps.android.PolyUtil;

import java.util.ArrayList;

import static com.example.hosiluan.foodanddrink.placedetailactivity.PlaceDetailActivity.BUNDLE_FROM_DETAIL_TO_DIRECT;
import static com.example.hosiluan.foodanddrink.placedetailactivity.PlaceDetailActivity.OBJECCT_FROM_PLACE_DETAIL_TO_SHOW_DIRECT;

public class ShowDirectionActivity extends AppCompatActivity
        implements OnMapReadyCallback, ShowDirectionPresenter.ShowDirectionPresenterListener {

    private static final int PERMISSION_ACCESS_COARSE_LOCATION = 1;
    private GoogleMap googleMap;
    private GoogleApiClient googleApiClient;
    private LatLng myCurrentLocation;

    private Handler handler;
    private Place destinationPlace;

    private ShowDirectionPresenter showDirectionPresenter;
    private ArrayList<LatLng> latLngsList = new ArrayList<>();
    private TextView tvTitleDirection;
    private ImageButton imageButtonBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_direction);
        receiveBundleFromPlaceDetailActivity();
        getCurrentLocation();
        handleMessage();
        setView();
        setEvent();
    }

    private void setEvent() {
        imageButtonBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (googleApiClient != null) {
            googleApiClient.disconnect();
        }
    }

    public void receiveBundleFromPlaceDetailActivity() {
        Intent intent = getIntent();
        Bundle bundle = intent.getBundleExtra(BUNDLE_FROM_DETAIL_TO_DIRECT);
        destinationPlace = bundle.getParcelable(OBJECCT_FROM_PLACE_DETAIL_TO_SHOW_DIRECT);
    }

    private void handleMessage() {
        handler = new Handler(new Handler.Callback() {
            @Override
            public boolean handleMessage(Message message) {
                switch (message.what) {
                    case 1: {
                        getPointList(
                                "https://maps.googleapis.com/maps/api/directions/" +
                                        "json?origin=" + myCurrentLocation.latitude + ","
                                        + myCurrentLocation.longitude +
                                        "&destination=" + destinationPlace.getMlat() + ","
                                        + destinationPlace.getmLon());
                    }
                    case 2: {
                        createMap();
                    }
                }
                return true;
            }
        });
    }

    public void setView() {
        showDirectionPresenter = new ShowDirectionPresenter(getApplicationContext(), this);
        tvTitleDirection  = (TextView) findViewById(R.id.tv_title_direction);
        imageButtonBack  = (ImageButton) findViewById(R.id.img_btn_back_show_direction);
        tvTitleDirection.setText(destinationPlace.getmName());
    }

    public void createMap() {
        SupportMapFragment supportMapFragment = (SupportMapFragment)
                getSupportFragmentManager().findFragmentById(R.id.map_fragment);
        supportMapFragment.getMapAsync(this);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        this.googleMap = googleMap;

        this.googleMap.addMarker(new MarkerOptions()
                .position(myCurrentLocation)
                .title("Your Location"))
                .showInfoWindow();

        this.googleMap.addMarker(new MarkerOptions()
                .position(new LatLng(destinationPlace.getMlat(), destinationPlace.getmLon()))
                .snippet(destinationPlace.getmAddress())
                .title(destinationPlace.getmName()))
                .showInfoWindow();

        zoomMarkerToFitScreen(googleMap);

        PolylineOptions polylineOptions = createPolyOption();
        this.googleMap.addPolyline(polylineOptions);
    }

    private void zoomMarkerToFitScreen(GoogleMap googleMap) {
        LatLngBounds.Builder builder = new LatLngBounds.Builder();
        builder.include(myCurrentLocation);
        builder.include(new LatLng(destinationPlace.getMlat(), destinationPlace.getmLon()));

        LatLngBounds latLngBounds = builder.build();

        DisplayMetrics displayMetrics = getResources().getDisplayMetrics();
        int width = displayMetrics.widthPixels;
        int height = displayMetrics.heightPixels;

        CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngBounds(latLngBounds, width - 600, height - 600, 5);
        googleMap.moveCamera(cameraUpdate);
        googleMap.animateCamera(cameraUpdate);
    }

    public PolylineOptions createPolyOption() {
        PolylineOptions polylineOptions = new PolylineOptions();
        polylineOptions.add(myCurrentLocation);

        for (int i = 0; i < latLngsList.size(); i++) {
            polylineOptions.add(latLngsList.get(i));
        }

        polylineOptions.width(20);
        polylineOptions.color(Color.BLUE);

        return polylineOptions;
    }


    public void getCurrentLocation() {
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                if (checkLocationPermission()) {
                    getLocation();
                } else {
                    requestLocatinPermission();
                }
            }
        });
        thread.start();

    }

    private boolean checkLocationPermission() {
        if (ContextCompat.checkSelfPermission(getApplicationContext(),
                Manifest.permission.ACCESS_COARSE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            return true;
        }
        return false;
    }

    private void requestLocatinPermission() {
        ActivityCompat.requestPermissions(this, new String[]
                        {Manifest.permission.ACCESS_COARSE_LOCATION}
                , PERMISSION_ACCESS_COARSE_LOCATION);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case PERMISSION_ACCESS_COARSE_LOCATION: {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    getLocation();
                } else {
                    Toast.makeText(this, "Need Your Location", Toast.LENGTH_SHORT).show();
                }
            }
        }
    }

    private void getLocation() {
        googleApiClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(new GoogleApiClient.ConnectionCallbacks() {
                    @Override
                    public void onConnected(@Nullable Bundle bundle) {
                        if (ContextCompat.checkSelfPermission(getApplicationContext(),
                                Manifest.permission.ACCESS_COARSE_LOCATION) ==
                                PackageManager.PERMISSION_GRANTED) {
                            Location location = LocationServices
                                    .FusedLocationApi
                                    .getLastLocation(googleApiClient);
                            myCurrentLocation = new LatLng(location.getLatitude(),
                                    location.getLongitude());

                            //send message to main thread
                            Message message = new Message();
                            message.what = 1;
                            handler.sendMessage(message);
                        }
                    }

                    @Override
                    public void onConnectionSuspended(int i) {

                    }
                })
                .addApi(LocationServices.API)
                .build();

        googleApiClient.connect();

    }

    public void getPointList(String url) {
        showDirectionPresenter.getPolyLineListVolley(url);
    }

    @Override
    public void getPointListResult(final ArrayList<String> pointList) {

        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i < pointList.size(); i++) {
                    ArrayList<LatLng> convertedToLatLngList = (ArrayList<LatLng>)
                            PolyUtil.decode(pointList.get(i));

                    for (int j = 0; j < convertedToLatLngList.size(); j++) {
                        latLngsList.add(convertedToLatLngList.get(j));

                        if (i == pointList.size() - 1 && j == convertedToLatLngList.size() - 1) {
                            Message message = new Message();
                            message.what = 2;
                            handler.sendMessage(message);
                        }
                    }
                }
            }
        });
        thread.start();

    }
}

