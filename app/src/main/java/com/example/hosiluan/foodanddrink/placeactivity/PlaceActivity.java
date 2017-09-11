package com.example.hosiluan.foodanddrink.placeactivity;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.PopupMenu;
import android.util.Log;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.hosiluan.foodanddrink.R;
import com.example.hosiluan.foodanddrink.model.City;
import com.example.hosiluan.foodanddrink.model.Distance;
import com.example.hosiluan.foodanddrink.model.Place;
import com.example.hosiluan.foodanddrink.model.TypeOfPlace;
import com.example.hosiluan.foodanddrink.placeactivity.fragment.EmptyPlaceFragment;
import com.example.hosiluan.foodanddrink.placeactivity.fragment.PlaceFragment;
import com.example.hosiluan.foodanddrink.placedetailactivity.PlaceDetailActivity;
import com.example.hosiluan.foodanddrink.retrofit.remote.ApiUtils;
import com.example.hosiluan.foodanddrink.retrofit.remote.DistanceService;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationServices;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;

import retrofit2.Call;
import retrofit2.Callback;

import static com.example.hosiluan.foodanddrink.mainactivity.MainActivity.BUNDLE_TO_PLACE_ACTIVITY;
import static com.example.hosiluan.foodanddrink.mainactivity.MainActivity.CITY;
import static com.example.hosiluan.foodanddrink.mainactivity.MainActivity.TYPE_OF_PLACE;

public class PlaceActivity extends AppCompatActivity
        implements PlacePresenter.PlacePresenterListener,
        PlaceFragment.PlaceFragmentListener {

    public static final String BUNDLE_FROM_PLACE_TO_PLACE_DETAIL = "bundle from place to place detail";
    public static final String OBJECT_FROM_PLACE_TO_PLACE_DETAIL = "object from place to place detail";
    private static final int PERMISSION_ACCESS_COARSE_LOCATION = 1;
    public static final String BUNDLE_FROM_PLACE_TO_FRAGMENT = "bundle from place to fragment";

    private ArrayList<Place> mPlaces;
    private PlacePresenter placePresenter;
    public static String GET_PLACE_LIST_BY_CITY_AND_TYPE = "getPlaceListByCityAndType";
    private TextView tvTypeOfPlace;
    private ImageButton imgbtnBack, imgbtnFilterPlace;
    private PopupMenu popupMenuFilter;

    private GoogleApiClient googleApiClient;

    private ArrayList<Distance> distances;
    private double myCurrentLon;
    private double myCurrentLat;

    private ProgressDialog progressDialogLoading;

    private Handler handler;

    RequestQueue requestQueue;
    private DistanceService distanceService;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_place);
        setView();
        initHandle();
        inflateFilterMenu();
        setEvent();
        receiveBundleFromMainActivity();
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (googleApiClient != null) {
            googleApiClient.disconnect();
        }
    }

    public void setView() {
        distanceService = ApiUtils.getDistanceService();

        distances = new ArrayList<>();
        mPlaces = new ArrayList<>();

        placePresenter = new PlacePresenter(this);
        tvTypeOfPlace = (TextView) findViewById(R.id.tv_type_of_place);
        imgbtnBack = (ImageButton) findViewById(R.id.img_btn_back_place_activity);
        imgbtnFilterPlace = (ImageButton) findViewById(R.id.imgbtn_filter_place);
        popupMenuFilter = new PopupMenu(this, imgbtnFilterPlace);

        requestQueue = Volley.newRequestQueue(getApplicationContext());
    }

    /**
     * inflate menu filter for sort place by distance
     */
    public void inflateFilterMenu() {
        MenuInflater menuInflater = popupMenuFilter.getMenuInflater();
        menuInflater.inflate(R.menu.menu_filter, popupMenuFilter.getMenu());
    }

    public void setEvent() {
        imgbtnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        imgbtnFilterPlace.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                popupMenuFilter.show();
            }
        });

        popupMenuFilter.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.near_me: {
                        if (checkLocationPermission()) {
                            getLocation();
                        } else {
                            requestLocationPermission();
                        }
                    }
                }
                return true;
            }


        });

    }

    /**
     * check permission to get current lon lat
     *
     * @return
     */
    private boolean checkLocationPermission() {
        if (ContextCompat.checkSelfPermission(getApplicationContext(),
                Manifest.permission.ACCESS_COARSE_LOCATION) ==
                PackageManager.PERMISSION_GRANTED) {
            return true;
        }
        return false;
    }

    /**
     * request permission to get current lon lat
     */
    private void requestLocationPermission() {
        ActivityCompat.requestPermissions(PlaceActivity.this, new String[]{
                Manifest.permission.ACCESS_COARSE_LOCATION
        }, PERMISSION_ACCESS_COARSE_LOCATION);
    }

    /**
     * permission result
     *
     * @param requestCode
     * @param permissions
     * @param grantResults
     */
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case PERMISSION_ACCESS_COARSE_LOCATION: {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    getLocation();
                } else {
                    Toast.makeText(this, "Need location", Toast.LENGTH_SHORT).show();
                }
                break;
            }
        }
    }

    public void getLocation() {

        googleApiClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(new GoogleApiClient.ConnectionCallbacks() {
                    @Override
                    public void onConnected(@Nullable Bundle bundle) {
                        if (ContextCompat.checkSelfPermission(getApplicationContext(),
                                Manifest.permission.ACCESS_COARSE_LOCATION)
                                == PackageManager.PERMISSION_GRANTED) {

                            Location location = LocationServices.FusedLocationApi
                                    .getLastLocation(googleApiClient);

                            myCurrentLon = location.getLongitude();
                            myCurrentLat = location.getLatitude();
                            Log.d("Luan", myCurrentLon + " " + myCurrentLat);


                            if (mPlaces.size() > 0){
                                showLoadingProgressBar();
                                Log.d("Haiz", distances.size() + " size");
                                getDistanceInOtherThread();
                            }
                        }
                    }

                    @Override
                    public void onConnectionSuspended(int i) {

                    }
                }).addApi(LocationServices.API).build();
        googleApiClient.connect();
    }

    private void getDistanceInOtherThread() {
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                getDistanceList(mPlaces);
            }
        });
        thread.start();
    }

    private void showLoadingProgressBar() {
        progressDialogLoading = new ProgressDialog(PlaceActivity.this);
        progressDialogLoading.setMessage("Loading...");
        progressDialogLoading.setCancelable(false);
        progressDialogLoading.show();
    }

    public void initHandle() {
        handler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                switch (msg.what) {
                    case 100: {
                        Log.d("Luan", "xong thread");
                        sortInOtherThread();
                        break;
                    }
                    case 200: {
                        Log.d("Luan", "sorted");
                        PlaceFragment placeFragment = (PlaceFragment)
                                getSupportFragmentManager().findFragmentById(R.id.place_fragment);
                        placeFragment.getPlaceListAfterSorting(mPlaces);
                        progressDialogLoading.dismiss();
                    }

                }
            }
        };
    }


    private void sortInOtherThread() {
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                quickSortPlaceByDistance(mPlaces, 0, mPlaces.size());
                Message message = new Message();
                message.what = 200;
                handler.sendMessage(message);

            }
        });
        thread.start();
    }
    public void getDistanceList(final ArrayList<Place> mPlaces) {
        distances.clear();
        for (int i = 0; i < mPlaces.size(); i++) {
            getDistanceVolley(mPlaces.get(i).getMlat(), mPlaces.get(i).getmLon(), i);
//            getDistanceRetrofit(mPlaces.get(i).getMlat(), mPlaces.get(i).getmLon(),i);
        }

    }

    public void quickSortPlaceByDistance(ArrayList<Place> mPlaces, int l, int r) {

        if (l <= r) {
            Place place = mPlaces.get((r - 1 + l) / 2);
            int i = l;
            int j = r - 1;

            while (i <= j) {
                while (mPlaces.get(i).getmDistance().getValue() < place.getmDistance().getValue()) {
                    i++;
                }
                while (mPlaces.get(j).getmDistance().getValue() > place.getmDistance().getValue()) {
                    j--;
                }
                if (i <= j) {
                    Collections.swap(mPlaces, i, j);
                    i++;
                    j--;
                }

                if (l < j) {
                    quickSortPlaceByDistance(mPlaces, l, j);
                }
                if (r > i) {
                    quickSortPlaceByDistance(mPlaces, i, r);

                }
            }
        }

    }

    /**
     * receive bundle from main and get list of place by city
     */
    public void receiveBundleFromMainActivity() {
        Intent intent = getIntent();
        Bundle bundle = intent.getBundleExtra(BUNDLE_TO_PLACE_ACTIVITY);
        City city = bundle.getParcelable(CITY);
        TypeOfPlace typeOfPlace = bundle.getParcelable(TYPE_OF_PLACE);
        placePresenter.getPlaceListByCityAndType(GET_PLACE_LIST_BY_CITY_AND_TYPE,
                Integer.parseInt(city.getId()), typeOfPlace.getId());
        tvTypeOfPlace.setText(typeOfPlace.getTypeofplace());

    }

    @Override
    public void getPlaceListByCityAndTypeResult(ArrayList<Place> places) {
        if (places.size() == 0) {
            EmptyPlaceFragment emptyPlaceFragment = new EmptyPlaceFragment();
            setPlaceFragment(emptyPlaceFragment);

        } else {
            for (int i = 0; i < places.size(); i++) {
                mPlaces.add(places.get(i));
            }

            Bundle bundle = new Bundle();
            bundle.putParcelableArrayList(BUNDLE_FROM_PLACE_TO_FRAGMENT,mPlaces);

            PlaceFragment placeFragment = new PlaceFragment();
            placeFragment.setArguments(bundle);
            setPlaceFragment(placeFragment);
        }
    }

    @Override
    public void onConnectionFailer() {
        Toast.makeText(this, "Something wrong. Please check your internet connection", Toast.LENGTH_SHORT).show();

    }

    public void setPlaceFragment(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.place_fragment, fragment);
        fragmentTransaction.commit();
    }

    @Override
    public void getSelectedItem(Place place) {
        Intent intent = new Intent(PlaceActivity.this, PlaceDetailActivity.class);
        Bundle bundle = new Bundle();
        bundle.putParcelable(OBJECT_FROM_PLACE_TO_PLACE_DETAIL, place);
        intent.putExtra(BUNDLE_FROM_PLACE_TO_PLACE_DETAIL, bundle);
        startActivity(intent);
    }

    public void getDistanceVolley(final double lat, double lon, final int i) {

        String url = "https://maps.googleapis.com/maps/api/directions/" +
                "json?origin=" + myCurrentLat + "," + myCurrentLon + "" +
                "&destination=" + lat + "," + lon + "&sensor=false&units=metric&mode=driving%22&key=AIzaSyCa5BL1DS8l5Z1STrbUufVDeBREcP7BJNc";

        StringRequest stringRequest = new StringRequest(StringRequest.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject object = new JSONObject(response);
                    JSONArray array = object.getJSONArray("routes");

                    JSONObject route = array.getJSONObject(0);
                    JSONArray leg = route.getJSONArray("legs");
                    JSONObject object1 = leg.getJSONObject(0);
                    JSONObject distance = object1.getJSONObject("distance");

                    int value = distance.getInt("value");
                    String text = distance.getString("text");
                    Distance distance1 = new Distance(text, value);

                    mPlaces.get(i).setmDistance(new Distance(text, value));
                    distances.add(distance1);

                    if (distances.size() == mPlaces.size()) {
                        Message message = new Message();
                        message.what = 100;
                        handler.sendMessage(message);
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                    Log.d("Luan", e.toString());
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("Luan", error.toString());
            }
        });
        int socketTimeout = 2147400000;//30 seconds - change to what you want
        RetryPolicy policy = new DefaultRetryPolicy(socketTimeout,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
        stringRequest.setRetryPolicy(policy);
        requestQueue.add(stringRequest);

    }

    class getDistance extends AsyncTask<Void, Void, Distance> {

        double desLat;
        double desLon;

        getDistance(double lat, double lon) {
            desLat = lat;
            desLon = lon;
        }

        @Override
        protected Distance doInBackground(Void... voids) {
            StringBuilder stringBuilder = new StringBuilder();
            Distance disObject = null;
            try {
                URL url = new URL("http://maps.googleapis.com/maps/api/directions/" +
                        "json?origin=" + myCurrentLat + "," + myCurrentLon + "" +
                        "&destination=" + desLat + "," + desLon + "&sensor=false&units=metric&mode=driving%22");
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                httpURLConnection.setConnectTimeout(2000000000);

                InputStream inputStream = httpURLConnection.getInputStream();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));

                String line = "";
                while ((line = bufferedReader.readLine()) != null) {
                    stringBuilder.append(line);
                }
                JSONObject object = new JSONObject(stringBuilder.toString());
                JSONArray array = object.getJSONArray("routes");
                JSONObject route = array.getJSONObject(0);
                JSONArray leg = route.getJSONArray("legs");
                JSONObject object1 = leg.getJSONObject(0);
                JSONObject distance = object1.getJSONObject("distance");
                int value = distance.getInt("value");
                String text = distance.getString("text");

                disObject = new Distance(text, value);

            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return disObject;
        }

        @Override
        protected void onPostExecute(Distance s) {
            super.onPostExecute(s);
//            Log.d("Luan",s.getValue() + " / " + s.getText());
        }
    }

    public void getDistanceRetrofit(double lat, double lon, final int i) {
        String origin = myCurrentLat + "," + myCurrentLon;
        String destination = lat + "," + lon;
        String key = "AIzaSyCa5BL1DS8l5Z1STrbUufVDeBREcP7BJNc";

        distanceService.getDistanceJson(origin, destination, key).enqueue(new Callback<Object>() {
            @Override
            public void onResponse(Call<Object> call, retrofit2.Response<Object> response) {
                try {
                    Log.d("Luan", response.body().toString());
                    JSONObject object = new JSONObject(response.body().toString());
                    JSONArray array = object.getJSONArray("routes");
                    JSONObject route = array.getJSONObject(0);
                    JSONArray leg = route.getJSONArray("legs");
                    JSONObject object1 = leg.getJSONObject(0);
                    JSONObject distance = object1.getJSONObject("distance");
                    int value = distance.getInt("value");
                    String text = distance.getString("text");
                    Distance distance1 = new Distance(text, value);
                    Log.d("Luan", distance1.getValue() + " value");

                    mPlaces.get(i).setmDistance(new Distance(text, value));

                    distances.add(distance1);

                    Log.d("Luan", distances.size() + " dis size");
                    Log.d("Luan", mPlaces.size() + " place size");

                    if (distances.size() == mPlaces.size()) {
                        Log.d("Luan", "ra chỗ này nè");
                        Message message = new Message();
                        message.what = 100;
                        handler.sendMessage(message);
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                    Log.d("Luan", e.toString());
                }
            }

            @Override
            public void onFailure(Call<Object> call, Throwable t) {
                Log.d("Luan", t.toString());
            }
        });
    }
}
