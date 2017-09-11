package com.example.hosiluan.foodanddrink.retrofit.remote;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.HTTP;
import retrofit2.http.POST;

/**
 * Created by Ho Si Luan on 8/30/2017.
 */

public interface SavedPlaceService {

    @FormUrlEncoded
    @POST("savedplace.php")
    Call<String> addSavedPlace(@Field("func") String function,
                                        @Field("placeId") int placeId);

    @FormUrlEncoded
    @HTTP(method = "DELETE", path = "savedplace.php", hasBody = true)
    Call<String> deleteSavedPlace(@Field("func") String function, @Field("savedPlaceId") int id);

}
