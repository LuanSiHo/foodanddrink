package com.example.hosiluan.foodanddrink.retrofit.remote;

import com.example.hosiluan.foodanddrink.model.TypeOfPlace;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Query;

/**
 * Created by Ho Si Luan on 8/20/2017.
 */

public interface TypeOfPlaceService {


    @GET("typeofplace.php")
    Call<List<TypeOfPlace>> getTypeOfPlaceList(@Query("func") String function);
}
