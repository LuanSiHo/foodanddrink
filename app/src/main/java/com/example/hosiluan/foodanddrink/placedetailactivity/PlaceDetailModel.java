package com.example.hosiluan.foodanddrink.placedetailactivity;

import android.util.Log;

import com.example.hosiluan.foodanddrink.model.Menu;
import com.example.hosiluan.foodanddrink.model.Place;
import com.example.hosiluan.foodanddrink.retrofit.remote.ApiUtils;
import com.example.hosiluan.foodanddrink.retrofit.remote.MenuService;
import com.example.hosiluan.foodanddrink.retrofit.remote.SavedPlaceService;
import com.google.android.gms.common.api.Api;

import java.util.ArrayList;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Ho Si Luan on 8/25/2017.
 */

public class PlaceDetailModel {

    private MenuService menuService;
    private PlaceDetailModelListener placeDetailModelListener;
    private SavedPlaceService savedPlaceService;

    public PlaceDetailModel(PlaceDetailModelListener placeDetailModelListener) {
        this.placeDetailModelListener = placeDetailModelListener;
        this.menuService = ApiUtils.getMenuService();
        this.savedPlaceService = ApiUtils.getSavedPlaceService();
    }

    public void getMenu(int place){
        menuService.getMenu("getMenu",place).enqueue(new Callback<List<Menu>>() {
            @Override
            public void onResponse(Call<List<Menu>> call, Response<List<Menu>> response) {
                placeDetailModelListener.getPlaceDetail(response.body());
            }

            @Override
            public void onFailure(Call<List<Menu>> call, Throwable t) {
                Log.d("Luan",t.toString());
                placeDetailModelListener.onConnectionFailer();
            }
        });
    }

    public void insertSavedPlace(Place place){

        savedPlaceService.addSavedPlace("addSavedPlace",place.getmId()).enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                    placeDetailModelListener.insertSavedPlaceResult(response.body());
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                Log.d("Luan",t.toString());
                placeDetailModelListener.onConnectionFailer();
            }
        });
    }

    interface PlaceDetailModelListener{
        void getPlaceDetail(List<Menu> menus);
        void insertSavedPlaceResult(String result);
        void onConnectionFailer();
    }
}
