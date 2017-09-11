package com.example.hosiluan.foodanddrink.mainactivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.support.annotation.NonNull;
import android.support.design.internal.BottomNavigationItemView;
import android.support.design.internal.BottomNavigationMenuView;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.MenuItem;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.hosiluan.foodanddrink.profilesettingactivity.ProfileSettingActivity;
import com.example.hosiluan.foodanddrink.mainactivity.fragment.HomeFragment;
import com.example.hosiluan.foodanddrink.mainactivity.fragment.MeFragment;
import com.example.hosiluan.foodanddrink.mainactivity.fragment.SaveFragment;
import com.example.hosiluan.foodanddrink.mainactivity.fragment.SearchFragment;
import com.example.hosiluan.foodanddrink.model.City;
import com.example.hosiluan.foodanddrink.R;
import com.example.hosiluan.foodanddrink.choosecity.ChooseCityActivity;
import com.example.hosiluan.foodanddrink.model.Place;
import com.example.hosiluan.foodanddrink.model.TypeOfPlace;
import com.example.hosiluan.foodanddrink.placeactivity.PlaceActivity;
import com.example.hosiluan.foodanddrink.placedetailactivity.PlaceDetailActivity;
import com.example.hosiluan.foodanddrink.settingactivity.SettingActivity;
import com.facebook.login.LoginManager;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import retrofit2.http.GET;

import static com.example.hosiluan.foodanddrink.loginactivity.LoginActivity.LOGIN_TOKEN;
import static com.example.hosiluan.foodanddrink.settingactivity.SettingActivity.APP_LANGUAGE;
import static com.example.hosiluan.foodanddrink.settingactivity.SettingActivity.CHOOSED_LANGUAGE;
import static com.example.hosiluan.foodanddrink.settingactivity.SettingActivity.LANG_CHANGED;
import static com.example.hosiluan.foodanddrink.settingactivity.SettingActivity.LANG_STATE;

public class MainActivity extends AppCompatActivity
        implements
        MainPresenter.MainPresenterListener,
        HomeFragment.HomeFragmentListener,
        SaveFragment.SaveFragmentListener,
        MeFragment.MeFragmentListener,
        SearchFragment.SearchFragmentListener {

    public static final int REQUEST_TO_CHOOSE_CITY_ACTIVITY = 1;
    public static final String TYPE_OF_PLACE = "type of place";
    public static final String CITY = "city";
    public static final String BUNDLE_TO_PLACE_ACTIVITY = "bundle to place activity";
    public static final String HOME_FRAGMENT = "home fragment";
    public static final String SEARCH_FRAGMENT = "Search fragment";
    public static final String SAVE_FRAGMENT = "save fragment";
    public static final String ME_FRAGMENT = "me fragment";
    public static final String BUNDLE_TO_SAVED_FRAGMENT = "bundle to saved fragment";
    public static final String OBJECT_FROM_MAIN_TO_PLACE_DETAIL = "object from main to place detail";
    public static final String BUNDLE_FROM_MAIN_TO_PLACE_DETAIL = "bundle from main to place detail";
    public static final String LIST_TO_SEARCH_FRAGMENT = "list to search fragment";


    private City city = new City("1", "Hồ Chí Minh");
    private MainPresenter mainPresenter;

    private BottomNavigationView mBottomBar;

    private HomeFragment homeFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setView();
        receiveBundleFromLoginActivity();
        setEvent();
        setLanguage();
        getTypeOfPlaceList();
    }


    @Override
    protected void onResume() {
        super.onResume();
        setLanguage();
    }

    public void setView() {
        mBottomBar = (BottomNavigationView) findViewById(R.id.bottom_navigation_bar);
        disableShiftMode(mBottomBar);
        mainPresenter = new MainPresenter(this);
    }

    public void getTypeOfPlaceList() {
        mainPresenter.getTypeOfPlaceList();
    }

    public void setEvent() {
        mBottomBar.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.action_home: {
                        receiveBundleFromLoginActivity();
                        getTypeOfPlaceList();
//                        getTypePlace();
                        break;
                    }
                    case R.id.action_search: {
                        SearchFragment searchFragment = new SearchFragment();
                        setFragment(searchFragment, SEARCH_FRAGMENT);
                        break;
                    }
                    case R.id.action_save: {
                        getSavedPlace();
                        break;
                    }
                    case R.id.action_me: {
                        MeFragment meFragment = new MeFragment();
                        setFragment(meFragment, ME_FRAGMENT);
                        break;
                    }
                }
                return true;
            }
        });
    }

    public void getTypePlace() {
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        StringRequest stringRequest = new StringRequest(Request.Method.GET, "http://myfoody.byethost7.com/typeofplace.php?func=getTypeOfPlace",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d("Luan", response.toString());
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("Luan", error.toString());

            }
        }){
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> headers = new HashMap<>();
                headers.put("Content-Type","application/json");
                headers.put("Accept","application/json");
                return headers;
            }
        };
        requestQueue.add(stringRequest);

    }

    public void getSavedPlace() {
        mainPresenter.getSavedPlace();
    }

    public void setFragment(Fragment fragment, String TAG) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.fragment_main, fragment, TAG);
//        fragmentTransaction.addToBackStack(TAG);
        fragmentTransaction.commit();
        getSupportFragmentManager().executePendingTransactions();
    }

    public void disableShiftMode(BottomNavigationView view) {
        BottomNavigationMenuView menuView = (BottomNavigationMenuView) view.getChildAt(0);
        try {
            Field shiftingMode = menuView.getClass().getDeclaredField("mShiftingMode");
            shiftingMode.setAccessible(true);
            shiftingMode.setBoolean(menuView, false);
            shiftingMode.setAccessible(false);
            for (int i = 0; i < menuView.getChildCount(); i++) {
                BottomNavigationItemView item = (BottomNavigationItemView) menuView.getChildAt(i);
                item.setShiftingMode(false);
                // set once again checked value, so view will be updated
                item.setChecked(item.getItemData().isChecked());
            }
        } catch (NoSuchFieldException e) {
            Log.e("Luan", "Unable to get shift mode field");
        } catch (IllegalAccessException e) {
            Log.e("Luan", "Unable to change value of shift mode");
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case REQUEST_TO_CHOOSE_CITY_ACTIVITY: {
                if (resultCode == 1) {
                    Bundle bundle = data.getBundleExtra("selected_city_bundle");
                    HomeFragment homeFragment = (HomeFragment) getSupportFragmentManager().findFragmentByTag(HOME_FRAGMENT);
                    if (homeFragment != null) {
                        homeFragment.setSelectedCity(bundle);
                    }
//                    city = bundle.getParcelable("selected_city");
//                    tvCity.setText(city.getName());
                }
            }
        }
    }

    private void receiveBundleFromLoginActivity() {
        Intent intent = getIntent();
        Bundle bundle = intent.getBundleExtra("loginResult");

        homeFragment = new HomeFragment();
        homeFragment.setArguments(bundle);

        setFragment(homeFragment, HOME_FRAGMENT);
    }

    public void changeLanguage(String language) {
        Locale locale = new Locale(language);
        Configuration configuration = getBaseContext().getResources().getConfiguration();
        configuration.locale = locale;
        getBaseContext().getResources().updateConfiguration(
                configuration, getBaseContext().getResources().getDisplayMetrics());
    }

    public void setLanguage() {
        SharedPreferences sharedPreferences = getSharedPreferences(APP_LANGUAGE, MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        String lang = sharedPreferences.getString(CHOOSED_LANGUAGE, null);
        if (lang != null && lang.length() > 0) {
            changeLanguage(lang);
            if (sharedPreferences.getInt(LANG_STATE, -1) == 1) {
                recreate();
                mBottomBar.setSelectedItemId(R.id.action_home);
                editor.putInt(LANG_STATE, 0);
                editor.apply();
            }
        }
    }

    @Override
    public void getSelectedTypeOfPlace(TypeOfPlace typeOfPlace, City city) {
        Intent intent = new Intent(MainActivity.this, PlaceActivity.class);
        Bundle bundle = new Bundle();
        bundle.putParcelable(TYPE_OF_PLACE, typeOfPlace);
        bundle.putParcelable(CITY, city);
        intent.putExtra(BUNDLE_TO_PLACE_ACTIVITY, bundle);
        startActivity(intent);
    }

    @Override
    public void getTypeOfPlaceListResult(ArrayList<TypeOfPlace> typeOfPlaces) {
        HomeFragment homeFragment = (HomeFragment) getSupportFragmentManager().findFragmentByTag(HOME_FRAGMENT);
        if (homeFragment != null) {
            homeFragment.setRecyclerViewMenu(typeOfPlaces);
        }
    }

    @Override
    public void getSavedPlaceResult(ArrayList<Place> places) {
        Bundle bundle = new Bundle();
        bundle.putParcelableArrayList(BUNDLE_TO_SAVED_FRAGMENT, places);

        SaveFragment saveFragment = new SaveFragment();
        saveFragment.setArguments(bundle);
        setFragment(saveFragment, SAVE_FRAGMENT);
    }

    @Override
    public void getfoundPlaceResult(ArrayList<Place> places) {
        SearchFragment searchFragment = (SearchFragment) getSupportFragmentManager()
                .findFragmentByTag(SEARCH_FRAGMENT);
        searchFragment.setSearchList(places);

    }

    @Override
    public void onConnectionFailer() {
        Toast.makeText(this, "Something wrong. Please check your internet connection", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void startChooseCityActivity(String cityName) {
        Intent intent = new Intent(MainActivity.this, ChooseCityActivity.class);
        intent.putExtra("city_name", cityName);
        startActivityForResult(intent, REQUEST_TO_CHOOSE_CITY_ACTIVITY);
    }

    @Override
    public void getSelectedItem(Place place) {
        Intent intent = new Intent(MainActivity.this, PlaceDetailActivity.class);
        Bundle bundle = new Bundle();
        bundle.putParcelable(OBJECT_FROM_MAIN_TO_PLACE_DETAIL, place);
        intent.putExtra(BUNDLE_FROM_MAIN_TO_PLACE_DETAIL, bundle);
        startActivity(intent);
    }

    @Override
    public void getSelectedList(final ArrayList<Place> places) {

        final AlertDialog alertDialog;
        final AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
        builder.setMessage("Bạn có muốn xóa những địa điểm đã chọn")
                .setTitle("Delete")
                .setNegativeButton("Không", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                })
                .setPositiveButton("Có", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        mainPresenter.deleteSelectedSavedPlace(places);
                        SaveFragment saveFragment = (SaveFragment) getSupportFragmentManager()
                                .findFragmentByTag(SAVE_FRAGMENT);
                        if (saveFragment != null) {
                            saveFragment.deleteSelectedList(places);
                        }

                    }
                });
        alertDialog = builder.create();
        alertDialog.show();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();

        Intent intent = new Intent(Intent.ACTION_MAIN);
        intent.addCategory(Intent.CATEGORY_HOME);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);//***Change Here***
        startActivity(intent);
//        finish();
        System.exit(0);
    }

    @Override
    public void getSelectedMeFragmentItem(int position) {
        switch (position) {
            case 0: {
                Intent intent = new Intent(MainActivity.this, ProfileSettingActivity.class);
                startActivity(intent);
                break;
            }
            case 1: {
                Intent intent = new Intent(MainActivity.this, SettingActivity.class);
                startActivity(intent);
                break;
            }
            case 2: {

                AlertDialog alertDialog;
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                String logout = getResources().getString(R.string.logout);
                String logoutQuestion = getResources().getString(R.string.logout_question);
                String no = getResources().getString(R.string.no);
                String yes = getResources().getString(R.string.yes);

                builder.setMessage(logoutQuestion);
                builder.setTitle(logout);
                builder.setNegativeButton(no, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                });
                builder.setPositiveButton(yes, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        SharedPreferences sharedPreferences = getSharedPreferences(LOGIN_TOKEN, MODE_PRIVATE);
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.clear();
                        editor.apply();

                        LoginManager.getInstance().logOut();

                        Intent intent = new Intent(Intent.ACTION_MAIN);
                        intent.addCategory(Intent.CATEGORY_HOME);
                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);//***Change Here***
                        startActivity(intent);
                        //finish();
                        System.exit(0);
                    }
                });

                alertDialog = builder.create();
                alertDialog.show();
            }
        }
    }

    @Override
    public void findPlace(String clue) {
        mainPresenter.findPlace(clue);
    }

    @Override
    public void getSelectedSearchItem(Place place) {
        Intent intent = new Intent(MainActivity.this, PlaceDetailActivity.class);
        Bundle bundle = new Bundle();
        bundle.putParcelable(OBJECT_FROM_MAIN_TO_PLACE_DETAIL, place);
        intent.putExtra(BUNDLE_FROM_MAIN_TO_PLACE_DETAIL, bundle);
        startActivity(intent);
    }
}
