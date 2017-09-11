package com.example.hosiluan.foodanddrink.mainactivity.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.hosiluan.foodanddrink.R;

/**
 * Created by Ho Si Luan on 9/4/2017.
 */

public class ToolbarDeleteSavePlaceFragment extends Fragment {

    private View view;
    private TextView tvHuy, tvXoa;
    private ToolbarDeleteFragmentListener toolbarDeleteFragmentListener;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.toolbar_delete_save_place, container, false);
        setView();
        setEvent();
        return view;
    }

    public void setView() {
        tvHuy = (TextView) view.findViewById(R.id.tv_huy_delete_toolbar);
        tvXoa = (TextView) view.findViewById(R.id.tv_xoa_delete_toolbar);
    }

    public void setListener(ToolbarDeleteFragmentListener toolbarDeleteFragmentListener) {
        this.toolbarDeleteFragmentListener = toolbarDeleteFragmentListener;
    }

    public void setEvent() {
        tvHuy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                toolbarDeleteFragmentListener.onCancelListener();
            }
        });

        tvXoa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                toolbarDeleteFragmentListener.onDeleteListener();
            }
        });
    }

    interface ToolbarDeleteFragmentListener {
        void onCancelListener();
        void onDeleteListener();
    }
}
