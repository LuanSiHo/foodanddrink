package com.example.hosiluan.foodanddrink.placeactivity;

import com.example.hosiluan.foodanddrink.model.Place;
import com.example.hosiluan.foodanddrink.retrofit.remote.PlaceService;

import java.util.ArrayList;

/**
 * Created by Ho Si Luan on 8/21/2017.
 */

public class PlacePresenter implements PlaceModel.PlaceModelListener{

    private PlaceModel placeModel;
    private PlacePresenterListener placePresenterListener;

    public PlacePresenter(PlacePresenterListener placePresenterListener) {
        placeModel = new PlaceModel(this);
        this.placePresenterListener = placePresenterListener;
    }

    public void getPlaceListByCityAndType(String function,int city,int typeOfPlace){
        placeModel.getPlaceListByCityAndType(function,city,typeOfPlace);
    }

    @Override
    public void getPlaceListByCityAndTypeResult(ArrayList<Place> places) {
        placePresenterListener.getPlaceListByCityAndTypeResult(places);
    }

    @Override
    public void onConnectionFailer() {
        placePresenterListener.onConnectionFailer();
    }

    interface PlacePresenterListener{
        void getPlaceListByCityAndTypeResult(ArrayList<Place> places);
        void onConnectionFailer();

    }
}
