package com.example.hosiluan.foodanddrink.mainactivity.fragment;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.hosiluan.foodanddrink.R;
import com.example.hosiluan.foodanddrink.mainactivity.adapter.RecyclerViewSaveAdapter;
import com.example.hosiluan.foodanddrink.model.Place;

import java.util.ArrayList;

import static com.example.hosiluan.foodanddrink.mainactivity.MainActivity.BUNDLE_TO_SAVED_FRAGMENT;

/**
 * Created by Ho Si Luan on 9/2/2017.
 */

public class SaveFragment extends Fragment implements
        RecyclerViewSaveAdapter.RecyclerViewSaveAdapterListener,
        ToolbarDeleteSavePlaceFragment.ToolbarDeleteFragmentListener {
    public static final String TOOLBAR_SAVE_FRAGMENT = "toolbar save fragment";
    public static final String TOOLBAR_DELETE_FRAGMENT = "toolbar detele fragment";
    private View view;
    private ArrayList<Place> mPlaces, receivedPlaces;
    private RecyclerView mRecyclerViewSave;
    private RecyclerViewSaveAdapter mRecyclerViewSaveAdapter;
    private SaveFragmentListener saveFragmentListener;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        saveFragmentListener = (SaveFragmentListener) activity;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_save, container, false);
        setView();
        receiveBundleFromMainActivity();
        return view;
    }

    public void setView() {
        mPlaces = new ArrayList<>();
        mRecyclerViewSave = (RecyclerView) view.findViewById(R.id.recyclerview_save);
        mRecyclerViewSaveAdapter = new RecyclerViewSaveAdapter(getActivity().getApplicationContext()
                , mPlaces, this);
        mRecyclerViewSave.setAdapter(mRecyclerViewSaveAdapter);
        mRecyclerViewSave.setLayoutManager(new LinearLayoutManager(
                getActivity().getApplicationContext(), LinearLayoutManager.VERTICAL, false));

        ToolbarSavePlaceFragment toolbarSavePlaceFragment = new ToolbarSavePlaceFragment();
        setFragment(toolbarSavePlaceFragment, TOOLBAR_SAVE_FRAGMENT);
    }

    public void setFragment(Fragment fragment, String TAG) {
        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.toolbar_save_place, fragment, TAG);
        fragmentTransaction.commit();
//        getActivity().getSupportFragmentManager().executePendingTransactions();
    }

    public void receiveBundleFromMainActivity() {
        Bundle bundle = getArguments();
        receivedPlaces = bundle.getParcelableArrayList(BUNDLE_TO_SAVED_FRAGMENT);

        mPlaces.clear();
        for (int i = 0; i < receivedPlaces.size(); i++) {
            mPlaces.add(receivedPlaces.get(i));
        }
        mRecyclerViewSaveAdapter.notifyDataSetChanged();
    }

    public void deleteSelectedList(ArrayList<Place> list){
        for (int i = 0; i < list.size();i++)
        {
            mPlaces.remove(list.get(i));
        }
        mRecyclerViewSaveAdapter.notifyDataSetChanged();

    }

    @Override
    public void getSelectedItem(Place place) {
        saveFragmentListener.getSelectedItem(place);
    }

    @Override
    public void onItemLongClick() {
        ToolbarDeleteSavePlaceFragment toolbarDeleteSavePlaceFragment
                = new ToolbarDeleteSavePlaceFragment();
        toolbarDeleteSavePlaceFragment.setListener(this);
        setFragment(toolbarDeleteSavePlaceFragment, TOOLBAR_DELETE_FRAGMENT);
    }

    @Override
    public void onCancelListener() {
        mRecyclerViewSaveAdapter.removeAllSelectedItem();
        mPlaces.clear();

        for (int i = 0; i < receivedPlaces.size(); i++) {
            mPlaces.add(receivedPlaces.get(i));
        }
        mRecyclerViewSaveAdapter.notifyDataSetChanged();

        mRecyclerViewSaveAdapter.setUnHighLight();
        mRecyclerViewSaveAdapter.setIsLongClicked(false);

        ToolbarSavePlaceFragment toolbarSavePlaceFragment = new ToolbarSavePlaceFragment();
        setFragment(toolbarSavePlaceFragment,TOOLBAR_SAVE_FRAGMENT);
    }

    @Override
    public void onDeleteListener() {
        ArrayList<Place> mSelectedItems = mRecyclerViewSaveAdapter.getSelectedList();
        saveFragmentListener.getSelectedList(mSelectedItems);
    }

    public interface SaveFragmentListener {
        void getSelectedItem(Place place);
        void getSelectedList(ArrayList<Place> places);
    }
}
