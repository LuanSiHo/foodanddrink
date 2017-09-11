package com.example.hosiluan.foodanddrink.placedetailactivity;

import com.example.hosiluan.foodanddrink.model.Menu;
import com.example.hosiluan.foodanddrink.model.Place;

import java.util.List;

/**
 * Created by Ho Si Luan on 8/25/2017.
 */

public class PlaceDetailPresenter implements PlaceDetailModel.PlaceDetailModelListener{

    private PlaceDetailModel placeDetailModel;
    private PlaceDetailPresenterListener placeDetailPresenterListener;

    public PlaceDetailPresenter(PlaceDetailPresenterListener placeDetailPresenterListener) {
        this.placeDetailPresenterListener = placeDetailPresenterListener;
        placeDetailModel = new PlaceDetailModel(this);
    }

    public void getMenu(int place){
        placeDetailModel.getMenu(place);
    }
    public void insertSavedPlace(Place place){
        placeDetailModel.insertSavedPlace(place);
    }
    @Override
    public void getPlaceDetail(List<Menu> menus) {
        placeDetailPresenterListener.getPlaceDetail(menus);
    }

    @Override
    public void insertSavedPlaceResult(String result) {
        placeDetailPresenterListener.insertSavedPlaceResult(result);
    }

    @Override
    public void onConnectionFailer() {
        placeDetailPresenterListener.onConnectionFailer();
    }

    interface PlaceDetailPresenterListener{
        void getPlaceDetail(List<Menu> menus);
        void insertSavedPlaceResult(String result);
        void onConnectionFailer();

    }
}
