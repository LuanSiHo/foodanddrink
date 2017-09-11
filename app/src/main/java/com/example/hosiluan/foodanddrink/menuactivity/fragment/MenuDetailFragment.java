package com.example.hosiluan.foodanddrink.menuactivity.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.hosiluan.foodanddrink.R;
import com.example.hosiluan.foodanddrink.menuactivity.adapter.RecyclerViewMenuAdapter;
import com.example.hosiluan.foodanddrink.model.MenuDetail;

import java.util.ArrayList;

import static com.example.hosiluan.foodanddrink.menuactivity.MenuActivity.MENU_DETAIL_LIST;

/**
 * Created by Ho Si Luan on 9/7/2017.
 */

public class MenuDetailFragment extends Fragment {
    private RecyclerView mRecycyclerViewMenu;
    private RecyclerViewMenuAdapter mRecyclerViewMenuAdapter;
    private ArrayList<MenuDetail> mMenuDetails;
    private View view;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_menu_detail,container,false);
        setView();
        receiveBundleFromMenuActivity();
        return view;
    }

    public void setView(){
        mRecycyclerViewMenu = (RecyclerView) view.findViewById(R.id.recyclerview_menu_detail);
        mMenuDetails = new ArrayList<>();
        mRecyclerViewMenuAdapter = new RecyclerViewMenuAdapter(getActivity().getApplicationContext()
                , mMenuDetails);
        mRecycyclerViewMenu.setAdapter(mRecyclerViewMenuAdapter);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getActivity()
                .getApplicationContext()
                , 2);
        mRecycyclerViewMenu.setLayoutManager(gridLayoutManager);
    }

    public void receiveBundleFromMenuActivity(){
        Bundle bundle = getArguments();
        ArrayList<MenuDetail> menuDetails = bundle.getParcelableArrayList(MENU_DETAIL_LIST);
        this.mMenuDetails.clear();
        for (int i = 0; i < menuDetails.size(); i++){
            this.mMenuDetails.add(menuDetails.get(i));
        }
        mRecyclerViewMenuAdapter.notifyDataSetChanged();

    }
}
