package com.example.hosiluan.foodanddrink.retrofit.remote;

import com.example.hosiluan.foodanddrink.model.Menu;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by Ho Si Luan on 8/25/2017.
 */

public interface MenuService {
    @GET("menu.php")
    Call<List<Menu>> getMenu(@Query("func") String function,
                             @Query("place") int place);
}
