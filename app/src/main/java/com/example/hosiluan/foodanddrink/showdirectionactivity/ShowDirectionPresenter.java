package com.example.hosiluan.foodanddrink.showdirectionactivity;

import android.content.Context;

import java.util.ArrayList;

/**
 * Created by Ho Si Luan on 8/31/2017.
 */

public class ShowDirectionPresenter implements ShowDirectionModel.ShowDirectionModelListener{

    private ShowDirectionModel showDirectionModel;
    private Context mContext;
    private ShowDirectionPresenterListener showDirectionPresenterListener;


    public ShowDirectionPresenter(Context mContext, ShowDirectionPresenterListener showDirectionPresenterListener) {
        this.mContext = mContext;
        this.showDirectionPresenterListener = showDirectionPresenterListener;
        showDirectionModel = new ShowDirectionModel(mContext,this);

    }

    public void getPolyLineListVolley(String url) {
        showDirectionModel.getPolyLineListVolley(url);
    }

    @Override
    public void getPointListResult(ArrayList<String> pointList) {
        showDirectionPresenterListener.getPointListResult(pointList);
    }

    interface ShowDirectionPresenterListener{
        void getPointListResult(ArrayList<String> pointList);
    }
}
