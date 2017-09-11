package com.example.hosiluan.foodanddrink.retrofit.remote;

import com.example.hosiluan.foodanddrink.model.Place;

import java.util.List;

import retrofit2.Call;
import retrofit2.Response;
import retrofit2.http.DELETE;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.HTTP;
import retrofit2.http.Query;

/**
 * Created by Ho Si Luan on 8/21/2017.
 */

public interface PlaceService {

    @GET("place.php")
    Call<List<Place>> getPlaceListByCityAndType(@Query("func") String function,
                                                @Query("area") int city,
                                                @Query("typeofplace") int typeOfPlace);

    @GET("savedplace.php")
    Call<List<Place>> getSavedPlace(@Query("func") String function);

    @GET("place.php")
    Call<List<Place>> findPlace(@Query("func") String func,
                                @Query("clue") String clue);

}
