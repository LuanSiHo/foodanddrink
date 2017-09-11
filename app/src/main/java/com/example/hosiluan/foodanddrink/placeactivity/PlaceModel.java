package com.example.hosiluan.foodanddrink.placeactivity;

import android.util.Log;
import android.widget.Toast;

import com.example.hosiluan.foodanddrink.model.Place;
import com.example.hosiluan.foodanddrink.retrofit.remote.ApiUtils;
import com.example.hosiluan.foodanddrink.retrofit.remote.PlaceService;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Ho Si Luan on 8/21/2017.
 */

public class PlaceModel {

    private PlaceService placeService;
    private PlaceModelListener placeModelListener;

    public PlaceModel(PlaceModelListener placeModelListener) {
        placeService = ApiUtils.getPlaceService();
        this.placeModelListener = placeModelListener;
    }

    public void getPlaceListByCityAndType(String function,int city,int typeOfPlace){
        placeService.getPlaceListByCityAndType(function,city,typeOfPlace)
                .enqueue(new Callback<List<Place>>() {
            @Override
            public void onResponse(Call<List<Place>> call, Response<List<Place>> response) {
                placeModelListener.getPlaceListByCityAndTypeResult((ArrayList<Place>) response.body());
            }

            @Override
            public void onFailure(Call<List<Place>> call, Throwable t) {
                Log.d("Luan",t.getMessage().toString());
                placeModelListener.onConnectionFailer();
            }
        });
    }

    interface PlaceModelListener{
        void getPlaceListByCityAndTypeResult(ArrayList<Place> places);
        void onConnectionFailer();
    }
}
