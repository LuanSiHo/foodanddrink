package com.example.hosiluan.foodanddrink.placeactivity.fragment;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.RelativeLayout;

import com.example.hosiluan.foodanddrink.R;
import com.example.hosiluan.foodanddrink.model.Place;
import com.example.hosiluan.foodanddrink.placeactivity.adapter.RecyclerViewPlaceAdapter;

import java.util.ArrayList;

import static com.example.hosiluan.foodanddrink.placeactivity.PlaceActivity.BUNDLE_FROM_PLACE_TO_FRAGMENT;

/**
 * Created by Ho Si Luan on 8/30/2017.
 */

public class PlaceFragment extends Fragment implements
        RecyclerViewPlaceAdapter.RecyclerViewPlaceAdapterListener {

    private View view;
    private RecyclerView mRecyclerView;
    private RecyclerViewPlaceAdapter mRecyclerViewPlaceAdapter;
    private ArrayList<Place> mPlaces, mListForLoadMore;
    private RelativeLayout bottomLayout;

    private boolean userScrolled = true;
    private int pastVisiblesItems, visibleItemCount, totalItemCount;

    private PlaceFragmentListener placeFragmentListener;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        placeFragmentListener = (PlaceFragmentListener) activity;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_place, container, false);

        setView();
        receiveBundleFromActivity();
        setEvent();
        return view;
    }

    public void setView() {
        mRecyclerView = (RecyclerView) view.findViewById(R.id.recyclerview_place);
        mPlaces = new ArrayList<>();
        mListForLoadMore = new ArrayList<>();

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager
                (getActivity().getApplicationContext(), LinearLayoutManager.VERTICAL, false);
        mRecyclerView.setLayoutManager(linearLayoutManager);
        mRecyclerView.hasFixedSize();

        mRecyclerViewPlaceAdapter = new RecyclerViewPlaceAdapter(
                getActivity().getApplicationContext(), mListForLoadMore, this);
        mRecyclerView.setAdapter(mRecyclerViewPlaceAdapter);
        bottomLayout = (RelativeLayout) view.findViewById(R.id.loading);

    }

    public void receiveBundleFromActivity() {
        Bundle bundle = getArguments();
        ArrayList<Place> places = bundle.getParcelableArrayList(BUNDLE_FROM_PLACE_TO_FRAGMENT);
        for (int i = 0; i < places.size(); i++) {
            mPlaces.add(places.get(i));
        }

        if (places.size() > 10) {
            for (int i = 0; i < 10; i++) {
                mListForLoadMore.add(places.get(i));
            }
        } else {
            for (int i = 0; i < mPlaces.size(); i++) {
                mListForLoadMore.add(places.get(i));
            }
        }
        mRecyclerViewPlaceAdapter.notifyDataSetChanged();
    }


    public void setEvent() {
        mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                LinearLayoutManager linearLayoutManager =
                        (LinearLayoutManager) mRecyclerView.getLayoutManager();

                visibleItemCount = linearLayoutManager.getChildCount();
                totalItemCount = linearLayoutManager.getItemCount();
                pastVisiblesItems = linearLayoutManager
                        .findFirstVisibleItemPosition();

                if (userScrolled
                        && (visibleItemCount + pastVisiblesItems) == totalItemCount
                        && totalItemCount < mPlaces.size()) {
                    userScrolled = false;
                    updateRecyclerView();
                }
            }

            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if (newState == AbsListView.OnScrollListener.SCROLL_STATE_TOUCH_SCROLL) {
                    userScrolled = true;
                }
            }
        });
    }

    public void getPlaceListAfterSorting(ArrayList<Place> places) {
        mPlaces = places;

        mListForLoadMore.clear();
//        distanceList.clear();
        Log.d("Luan", mPlaces.get(0).getmName());
        mRecyclerViewPlaceAdapter.notifyDataSetChanged();

        if (mPlaces.size() > 10) {
            for (int i = 0; i < 10; i++) {
                mListForLoadMore.add(mPlaces.get(i));
            }
        } else {
            for (int i = 0; i < mPlaces.size(); i++) {
                mListForLoadMore.add(mPlaces.get(i));
            }
        }
        mRecyclerViewPlaceAdapter.notifyDataSetChanged();

    }


    /**
     * show progressbar and load more data when scroll to bottom of screen
     */
    private void updateRecyclerView() {
        bottomLayout.setVisibility(View.VISIBLE);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                int index = mListForLoadMore.size();
                int end = index + 10;
                for (int i = index; i < end; i++) {
                    if (mPlaces.size() > i) {
                        mListForLoadMore.add(mPlaces.get(i));
                    }
                }
                mRecyclerViewPlaceAdapter.notifyDataSetChanged();
                bottomLayout.setVisibility(View.GONE);
            }
        }, 1000);
    }

    @Override
    public void getSelectedItem(Place place) {
        placeFragmentListener.getSelectedItem(place);
    }

    public interface PlaceFragmentListener {
        void getSelectedItem(Place place);
    }
}
