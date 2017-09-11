package com.example.hosiluan.foodanddrink.retrofit.remote;

import com.example.hosiluan.foodanddrink.model.City;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by Ho Si Luan on 8/20/2017.
 */

public interface CityService {
    @GET("city.php")
    Call<List<City>> getCityList(@Query("func") String function);
}
