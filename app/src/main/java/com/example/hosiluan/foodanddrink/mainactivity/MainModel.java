package com.example.hosiluan.foodanddrink.mainactivity;

import android.util.Log;

import com.example.hosiluan.foodanddrink.model.Place;
import com.example.hosiluan.foodanddrink.model.TypeOfPlace;
import com.example.hosiluan.foodanddrink.retrofit.remote.ApiUtils;
import com.example.hosiluan.foodanddrink.retrofit.remote.PlaceService;
import com.example.hosiluan.foodanddrink.retrofit.remote.SavedPlaceService;
import com.example.hosiluan.foodanddrink.retrofit.remote.TypeOfPlaceService;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Ho Si Luan on 8/21/2017.
 */

public class MainModel {
    private TypeOfPlaceService typeOfPlaceService;
    private PlaceService placeService;
    private MainModelListener mainModelListener;
    private SavedPlaceService savedPlaceService;

    public MainModel(MainModelListener mainModelListener) {
        typeOfPlaceService = ApiUtils.getTypeOfPlaceService();
        placeService = ApiUtils.getPlaceService();
        savedPlaceService = ApiUtils.getSavedPlaceService();
        this.mainModelListener = mainModelListener;
    }

    public void getTypeOfPlaceList(){
        typeOfPlaceService.getTypeOfPlaceList("getTypeOfPlace").enqueue(new Callback<List<TypeOfPlace>>() {
            @Override
            public void onResponse(Call<List<TypeOfPlace>> call, Response<List<TypeOfPlace>> response) {
                mainModelListener.getTypeOfPlaceListResult((ArrayList<TypeOfPlace>) response.body());
                Log.d("Luan",response.body().size() + " size ne");
            }

            @Override
            public void onFailure(Call<List<TypeOfPlace>> call, Throwable t) {
                Log.d("Luan","fuck " + t.toString());
                mainModelListener.onConnectionFailer();

            }
        });
    }

    public void getSavedPlace(){
        placeService.getSavedPlace("getSavedPlace").enqueue(new Callback<List<Place>>() {
            @Override
            public void onResponse(Call<List<Place>> call, Response<List<Place>> response) {
                mainModelListener.getSavedPlaceResult((ArrayList<Place>) response.body());
            }

            @Override
            public void onFailure(Call<List<Place>> call, Throwable t) {
                Log.d("Luan",t.toString());
                mainModelListener.onConnectionFailer();
            }
        });
    }



    public void deleteSelectedSavedPlace(ArrayList<Place> places){
        for (int i = 0; i < places.size();i++){
            savedPlaceService.deleteSavedPlace("deleteSavedPlace",places.get(i).getmId())
                    .enqueue(new Callback<String>() {
                @Override
                public void onResponse(Call<String> call, Response<String> response) {

                }

                @Override
                public void onFailure(Call<String> call, Throwable t) {

                }
            });
        }
    }

    public void findPlace(String clue){
        placeService.findPlace("findPlace",clue).enqueue(new Callback<List<Place>>() {
            @Override
            public void onResponse(Call<List<Place>> call, Response<List<Place>> response) {
                mainModelListener.getfoundPlaceResult((ArrayList<Place>) response.body());
            }

            @Override
            public void onFailure(Call<List<Place>> call, Throwable t) {
                Log.d("Luan",t.toString());
                mainModelListener.onConnectionFailer();

            }
        });
    }

    interface MainModelListener{
        void getTypeOfPlaceListResult(ArrayList<TypeOfPlace> typeOfPlaces);
        void getSavedPlaceResult(ArrayList<Place> places);
        void getfoundPlaceResult(ArrayList<Place> places);
        void onConnectionFailer();
    }

}
