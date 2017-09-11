package com.example.hosiluan.foodanddrink.menuactivity;

import com.example.hosiluan.foodanddrink.model.MenuDetail;

import java.util.List;

/**
 * Created by Ho Si Luan on 8/25/2017.
 */

public class MenuPresenter implements MenuModel.MenuModelListener {

    private MenuModel menuModel;
    private MenuPresenterListener menuPresenterListener;

    public MenuPresenter(MenuPresenterListener menuPresenterListener) {
        this.menuModel = new MenuModel(this);
        this.menuPresenterListener = menuPresenterListener;
    }

    public void getMenuDetail(int menu){
        menuModel.getMenuDetail(menu);
    }

    @Override
    public void getSelectedMenuDetail(List<MenuDetail> menuDetails) {
        menuPresenterListener.getSelectedMenuDetail(menuDetails);
    }

    @Override
    public void onConnectionFailer() {
        menuPresenterListener.onConnectionFailer();
    }

    interface MenuPresenterListener{
        void getSelectedMenuDetail(List<MenuDetail> menuDetails);
        void onConnectionFailer();
    }
}
