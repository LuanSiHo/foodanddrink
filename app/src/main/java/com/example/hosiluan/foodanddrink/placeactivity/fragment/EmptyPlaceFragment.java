package com.example.hosiluan.foodanddrink.placeactivity.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.hosiluan.foodanddrink.R;

/**
 * Created by Ho Si Luan on 8/30/2017.
 */

public class EmptyPlaceFragment  extends Fragment{

    private View view;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_empty,container,false);
        return view;
    }


}
