package com.example.hosiluan.foodanddrink.retrofit.remote;

import com.example.hosiluan.foodanddrink.model.MenuDetail;

/**
 * Created by Ho Si Luan on 8/20/2017.
 */

public class ApiUtils {

    public static final String baseUrl = "https://hosiluanfoodanddrink.000webhostapp.com/";

    public static CityService getCityService(){
       return RetrofitClient.getClient(baseUrl).create(CityService.class);
    }

    public static TypeOfPlaceService getTypeOfPlaceService(){
        return RetrofitClient.getClient(baseUrl).create(TypeOfPlaceService.class);
    }

    public static PlaceService getPlaceService(){
        return RetrofitClient.getClient(baseUrl).create(PlaceService.class);
    }
    public static MenuService getMenuService(){
        return RetrofitClient.getClient(baseUrl).create(MenuService.class);
    }
    public static MenuDetailService getMenuDetailService(){
        return RetrofitClient.getClient(baseUrl).create(MenuDetailService.class);
    }

    public static DistanceService getDistanceService(){
        return RetrofitClient.getClient("").create(DistanceService.class);
    }

    public static SavedPlaceService getSavedPlaceService(){
        return RetrofitClient.getClient(baseUrl).create(SavedPlaceService.class);
    }
}
