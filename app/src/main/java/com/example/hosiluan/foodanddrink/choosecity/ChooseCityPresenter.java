package com.example.hosiluan.foodanddrink.choosecity;

import com.example.hosiluan.foodanddrink.model.City;

import java.util.ArrayList;

/**
 * Created by Ho Si Luan on 8/21/2017.
 */

public class ChooseCityPresenter implements ChooseCityModel.ChooseCityModelListener {

    private ChooseCityModel chooseCityModel;
    private ChooseCityPresenterListener chooseCityPresenterListener;

    public ChooseCityPresenter(ChooseCityPresenterListener chooseCityPresenterListener) {
        this.chooseCityModel = new ChooseCityModel(this);
        this.chooseCityPresenterListener = chooseCityPresenterListener;
    }

    public void getCityList() {
        chooseCityModel.getCityList();
    }

    @Override
    public void getCityListResult(ArrayList<City> cities) {
        chooseCityPresenterListener.getCityListResult(cities);
    }

    @Override
    public void onConnectionFailer() {
        chooseCityPresenterListener.onConnectionFailer();
    }


    interface ChooseCityPresenterListener{
        void getCityListResult(ArrayList<City> cities);
        void onConnectionFailer();
    }
}
