package com.example.hosiluan.foodanddrink.menuactivity.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.hosiluan.foodanddrink.R;

/**
 * Created by Ho Si Luan on 9/7/2017.
 */

public class EmptyMenuFragment extends Fragment {
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_empty,container,false);
        return view;

    }

}
