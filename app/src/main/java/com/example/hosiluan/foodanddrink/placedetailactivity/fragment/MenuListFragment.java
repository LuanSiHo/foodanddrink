package com.example.hosiluan.foodanddrink.placedetailactivity.fragment;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.hosiluan.foodanddrink.R;
import com.example.hosiluan.foodanddrink.model.Menu;
import com.example.hosiluan.foodanddrink.placedetailactivity.adapter.RecyclerViewPlaceDetailAdapter;

import java.util.ArrayList;

import static com.example.hosiluan.foodanddrink.placedetailactivity.PlaceDetailActivity.LIST_FROM_PLACE_DETAIL_TO_MENU_LIST_FRAGMENT;

/**
 * Created by Ho Si Luan on 8/30/2017.
 */

public class MenuListFragment extends Fragment implements
        RecyclerViewPlaceDetailAdapter.RecyclerViewPlaceDetailAdapterListener {

    private View view;
    private RecyclerView mRecyclerViewMenu;
    private RecyclerViewPlaceDetailAdapter mRecyclerViewPlaceDetailAdapter;
    private ArrayList<Menu> mMenus;
    private MenuListFragmentListener menuListFragmentListener;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        menuListFragmentListener = (MenuListFragmentListener) activity;
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_menu_list, container, false);
        setView();
        receiveBundleFromActivity();
        return view;
    }

    public void setView() {
        mRecyclerViewMenu = (RecyclerView) view.findViewById(R.id.recyclerview_place_menu);
        mMenus = new ArrayList<>();
        mRecyclerViewPlaceDetailAdapter = new RecyclerViewPlaceDetailAdapter(
                getActivity().getApplicationContext(),mMenus,this);
        mRecyclerViewMenu.setLayoutManager(new GridLayoutManager(
                getActivity().getApplicationContext(), 2));
        mRecyclerViewMenu.setAdapter(mRecyclerViewPlaceDetailAdapter);
    }

    public void receiveBundleFromActivity(){
        Bundle bundle = getArguments();
        ArrayList<Menu> menus = bundle.getParcelableArrayList(
                LIST_FROM_PLACE_DETAIL_TO_MENU_LIST_FRAGMENT);

        mMenus.clear();
        for (int i  = 0; i < menus.size(); i++){
            mMenus.add(menus.get(i));
        }
        mRecyclerViewPlaceDetailAdapter.notifyDataSetChanged();
    }

    @Override
    public void getSelectedMenuItem(Menu menu) {
        menuListFragmentListener.getSelectedMenuItem(menu);
    }

   public interface MenuListFragmentListener{
        void getSelectedMenuItem(Menu menu);
    }
}
