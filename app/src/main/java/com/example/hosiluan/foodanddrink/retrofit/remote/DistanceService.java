package com.example.hosiluan.foodanddrink.retrofit.remote;

import org.json.JSONObject;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by Ho Si Luan on 8/29/2017.
 */

public interface DistanceService {

    @GET("https://maps.googleapis.com/maps/api/directions/json")
    Call<Object> getDistanceJson(@Query("origin") String origin,
                                       @Query("destination") String destination,
                                       @Query("key") String key);
}
