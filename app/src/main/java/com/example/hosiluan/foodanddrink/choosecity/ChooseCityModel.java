package com.example.hosiluan.foodanddrink.choosecity;

import android.util.Log;

import com.example.hosiluan.foodanddrink.model.City;
import com.example.hosiluan.foodanddrink.retrofit.remote.ApiUtils;
import com.example.hosiluan.foodanddrink.retrofit.remote.CityService;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Ho Si Luan on 8/21/2017.
 */

public class ChooseCityModel {
    private CityService cityService;
    private ChooseCityModelListener chooseCityModelListener;

    public ChooseCityModel(ChooseCityModelListener chooseCityModelListener) {
        this.cityService = ApiUtils.getCityService();
        this.chooseCityModelListener = chooseCityModelListener;
    }

    public void getCityList() {
        cityService.getCityList("getCityList").enqueue(new Callback<List<City>>() {
            @Override
            public void onResponse(Call<List<City>> call, Response<List<City>> response) {
                chooseCityModelListener.getCityListResult((ArrayList<City>) response.body());
                Log.d("Luan",response.body().size() + " sz");
            }

            @Override
            public void onFailure(Call<List<City>> call, Throwable t) {
                Log.d("Luan", t.toString());
                chooseCityModelListener.onConnectionFailer();
            }
        });
    }

    interface ChooseCityModelListener{
        void getCityListResult(ArrayList<City> cities);
        void onConnectionFailer();
    }
}
