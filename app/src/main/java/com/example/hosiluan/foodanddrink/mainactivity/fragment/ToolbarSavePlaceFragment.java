package com.example.hosiluan.foodanddrink.mainactivity.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.hosiluan.foodanddrink.R;

/**
 * Created by Ho Si Luan on 9/4/2017.
 */

public class ToolbarSavePlaceFragment extends Fragment {

    private View view;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.toolbar_save_place,container,false);
        return view;
    }
}
