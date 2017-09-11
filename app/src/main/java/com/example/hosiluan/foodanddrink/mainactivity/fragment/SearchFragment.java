package com.example.hosiluan.foodanddrink.mainactivity.fragment;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.EditText;
import android.widget.RelativeLayout;

import com.example.hosiluan.foodanddrink.R;
import com.example.hosiluan.foodanddrink.mainactivity.adapter.RecyclerViewSearchAdapter;
import com.example.hosiluan.foodanddrink.model.Place;

import java.util.ArrayList;

import static com.example.hosiluan.foodanddrink.mainactivity.MainActivity.LIST_TO_SEARCH_FRAGMENT;

/**
 * Created by Ho Si Luan on 9/2/2017.
 */

public class SearchFragment extends Fragment
        implements RecyclerViewSearchAdapter.RecyclerViewSearchAdapterListener {

    private View view;
    private EditText edtSearch;
    private SearchFragmentListener searchFragmentListener;

    private ArrayList<Place> mPlaces, mListForLoadMore;
    private RecyclerView mRecyclerViewSearch;
    private RecyclerViewSearchAdapter recyclerViewSearchAdapter;
    private Boolean isSearching = true;

    private boolean userScrolled = true;
    private int pastVisiblesItems, visibleItemCount, totalItemCount;
    private RelativeLayout bottomLayout;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        searchFragmentListener = (SearchFragmentListener) activity;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_search, container, false);
        setView();
        setEvent();
        return view;
    }

    public void setView() {
        edtSearch = (EditText) view.findViewById(R.id.edt_search);
        mRecyclerViewSearch = (RecyclerView) view.findViewById(R.id.recyclerview_search);
        mPlaces = new ArrayList<>();
        mListForLoadMore = new ArrayList<>();
        recyclerViewSearchAdapter = new
                RecyclerViewSearchAdapter(getActivity().getApplicationContext(),
                mListForLoadMore, this);

        mRecyclerViewSearch.setAdapter(recyclerViewSearchAdapter);
        mRecyclerViewSearch.setLayoutManager(new
                LinearLayoutManager(getActivity().getApplicationContext()));
        bottomLayout = (RelativeLayout) view.findViewById(R.id.loading_search);
    }

    public void setSearchList(ArrayList<Place> places) {
        if (isSearching) {
            mPlaces.clear();
            mListForLoadMore.clear();
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
            recyclerViewSearchAdapter.notifyDataSetChanged();
        }
    }

    public void setEvent() {
        edtSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (charSequence.length() > 0) {
                    String text = charSequence.toString().replaceAll("\\s+", " ");
                    if (text.length() > 0 && !text.equals(" ")) {
                        isSearching = true;
                        searchFragmentListener.findPlace(text);
                    }
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (editable.length() <= 0 || editable.toString().equals(" ")) {
                    isSearching = false;
                    mPlaces.clear();
                    mListForLoadMore.clear();
                    recyclerViewSearchAdapter.notifyDataSetChanged();
                } else {
                    Log.d("Luan", "the hell");
                }


            }
        });

        mRecyclerViewSearch.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if (newState == AbsListView.OnScrollListener.SCROLL_STATE_TOUCH_SCROLL) {
                    userScrolled = true;
                }
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                LinearLayoutManager linearLayoutManager = (LinearLayoutManager)
                        mRecyclerViewSearch.getLayoutManager();

                visibleItemCount = linearLayoutManager.getChildCount();
                totalItemCount = linearLayoutManager.getItemCount();
                pastVisiblesItems = linearLayoutManager.findFirstVisibleItemPosition();

                if (userScrolled &&
                        (visibleItemCount + pastVisiblesItems == totalItemCount)
                        && totalItemCount < mPlaces.size()) {
                    userScrolled = true;
                    updateRecyclerView();

                }

            }
        });
    }

    public void updateRecyclerView() {
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
                recyclerViewSearchAdapter.notifyDataSetChanged();
                bottomLayout.setVisibility(View.GONE);

            }
        }, 1000);
    }

    @Override
    public void getSelectedSearchItem(Place place) {
            searchFragmentListener.getSelectedSearchItem(place);
    }

    public interface SearchFragmentListener {
        void findPlace(String clue);
        void getSelectedSearchItem(Place place);
    }
}
