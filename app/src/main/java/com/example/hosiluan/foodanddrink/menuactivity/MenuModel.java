package com.example.hosiluan.foodanddrink.menuactivity;

import android.util.Log;

import com.example.hosiluan.foodanddrink.model.MenuDetail;
import com.example.hosiluan.foodanddrink.retrofit.remote.ApiUtils;
import com.example.hosiluan.foodanddrink.retrofit.remote.MenuDetailService;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Ho Si Luan on 8/25/2017.
 */

public class MenuModel {

    private MenuDetailService menuDetailService;
    private MenuModelListener menuModelListener;

    public MenuModel(MenuModelListener menuModelListener) {
        this.menuModelListener = menuModelListener;
        menuDetailService = ApiUtils.getMenuDetailService();
    }

    public void getMenuDetail(int menu){
        menuDetailService.getMenuDetail("getMenuDetail",menu)
                .enqueue(new Callback<List<MenuDetail>>() {
            @Override
            public void onResponse(Call<List<MenuDetail>> call, Response<List<MenuDetail>> response) {
                menuModelListener.getSelectedMenuDetail(response.body());
            }

            @Override
            public void onFailure(Call<List<MenuDetail>> call, Throwable t) {
                Log.d("Luan",t.toString());
                menuModelListener.onConnectionFailer();
            }
        });
    }

    interface MenuModelListener{
        void getSelectedMenuDetail(List<MenuDetail> menuDetails);
        void onConnectionFailer();
    }
}
