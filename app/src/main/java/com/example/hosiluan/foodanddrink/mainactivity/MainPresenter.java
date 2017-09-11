package com.example.hosiluan.foodanddrink.mainactivity;

import com.example.hosiluan.foodanddrink.model.Place;
import com.example.hosiluan.foodanddrink.model.TypeOfPlace;

import java.util.ArrayList;

/**
 * Created by Ho Si Luan on 8/21/2017.
 */

public class MainPresenter implements MainModel.MainModelListener {
    private MainModel mainModel;
    private MainPresenterListener mainPresenterListener;

    public MainPresenter(MainPresenterListener mainPresenterListener) {
        this.mainModel = new MainModel(this);
        this.mainPresenterListener = mainPresenterListener;
    }

    public void getTypeOfPlaceList() {
        mainModel.getTypeOfPlaceList();
    }

    public void getSavedPlace(){
        mainModel.getSavedPlace();
    }
    public void deleteSelectedSavedPlace(ArrayList<Place> places){
        mainModel.deleteSelectedSavedPlace(places);
    }

    @Override
    public void getTypeOfPlaceListResult(ArrayList<TypeOfPlace> typeOfPlaces) {
        mainPresenterListener.getTypeOfPlaceListResult(typeOfPlaces);
    }

    @Override
    public void getSavedPlaceResult(ArrayList<Place> places) {
        mainPresenterListener.getSavedPlaceResult(places);
    }

    @Override
    public void getfoundPlaceResult(ArrayList<Place> places) {
        mainPresenterListener.getfoundPlaceResult(places);
    }

    @Override
    public void onConnectionFailer() {
        mainPresenterListener.onConnectionFailer();
    }

    public void findPlace(String clue){
        mainModel.findPlace(clue);
    }


    interface MainPresenterListener {
        void getTypeOfPlaceListResult(ArrayList<TypeOfPlace> typeOfPlaces);
        void getSavedPlaceResult(ArrayList<Place> places);
        void getfoundPlaceResult(ArrayList<Place> places);
        void onConnectionFailer();

    }
}
