package com.example.hosiluan.foodanddrink.placedetailactivity;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.hosiluan.foodanddrink.R;
import com.example.hosiluan.foodanddrink.menuactivity.MenuActivity;
import com.example.hosiluan.foodanddrink.model.Menu;
import com.example.hosiluan.foodanddrink.model.Place;
import com.example.hosiluan.foodanddrink.placedetailactivity.adapter.RecyclerViewChoiceAdapter;
import com.example.hosiluan.foodanddrink.placedetailactivity.fragment.EmptyPlaceDetailFragment;
import com.example.hosiluan.foodanddrink.placedetailactivity.fragment.MenuListFragment;
import com.example.hosiluan.foodanddrink.showdirectionactivity.ShowDirectionActivity;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import static com.example.hosiluan.foodanddrink.mainactivity.MainActivity.BUNDLE_FROM_MAIN_TO_PLACE_DETAIL;
import static com.example.hosiluan.foodanddrink.mainactivity.MainActivity.OBJECT_FROM_MAIN_TO_PLACE_DETAIL;
import static com.example.hosiluan.foodanddrink.placeactivity.PlaceActivity.BUNDLE_FROM_PLACE_TO_PLACE_DETAIL;
import static com.example.hosiluan.foodanddrink.placeactivity.PlaceActivity.OBJECT_FROM_PLACE_TO_PLACE_DETAIL;

public class PlaceDetailActivity extends AppCompatActivity implements
        PlaceDetailPresenter.PlaceDetailPresenterListener,
        MenuListFragment.MenuListFragmentListener,
        RecyclerViewChoiceAdapter.RecyclerviewChoiceAdapterListener {

    public static final String OBJECT_FROM_DETAIL_TO_MENU = "object from detail to menu";
    public static final String BUNDLE_FROM_DETAIL_TO_MENU = "bundle from detail to menu";
    public static final String LIST_FROM_PLACE_DETAIL_TO_MENU_LIST_FRAGMENT
            = "list from detail to menu fragment";
    public static final String OBJECCT_FROM_PLACE_DETAIL_TO_SHOW_DIRECT
            = "object from detail to direct";
    public static final String BUNDLE_FROM_DETAIL_TO_DIRECT = "bundle from detail to direct";
    private TextView tvTitle, tvName, tvAddress;
    private ImageView imgPlaceDetail;
    private ImageButton imgBtnBack;
    private RecyclerView  mRecyclerViewChoice;

    private ArrayList<String> mChoices;
    private RecyclerViewChoiceAdapter recyclerViewChoiceAdapter;
    private PlaceDetailPresenter placeDetailPresenter;
    private Place mPlace;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_place_detail);
        setView();
        setEvent();
        recieveBundleFromPlaceActivity();
        receiveBundleFromMainActivity();
    }

    public void setView() {
        tvAddress = (TextView) findViewById(R.id.tv_place_detail_address);
        tvName = (TextView) findViewById(R.id.tv_place_detail_name);
        tvTitle = (TextView) findViewById(R.id.tv_title_place_detail);
        imgPlaceDetail = (ImageView) findViewById(R.id.img_place_detail);
        imgBtnBack = (ImageButton) findViewById(R.id.img_btn_back_place_detail_activity);

        mRecyclerViewChoice = (RecyclerView) findViewById(R.id.recyclerview_choice);

        String[] strings = getResources().getStringArray(R.array.array_choice);
        mChoices = new ArrayList<>();

        for (int i = 0; i < strings.length; i++) {
            mChoices.add(strings[i]);
        }

        recyclerViewChoiceAdapter = new RecyclerViewChoiceAdapter(getApplicationContext(),
                mChoices, this);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext()
                , LinearLayoutManager.HORIZONTAL, false);
        mRecyclerViewChoice.setLayoutManager(linearLayoutManager);
        mRecyclerViewChoice.setAdapter(recyclerViewChoiceAdapter);
        mRecyclerViewChoice.stopScroll();

        placeDetailPresenter = new PlaceDetailPresenter(this);
    }

    public void setEvent() {
        imgBtnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

    }

    public void recieveBundleFromPlaceActivity() {
        Intent intent = getIntent();
        Bundle bundle = intent.getParcelableExtra(BUNDLE_FROM_PLACE_TO_PLACE_DETAIL);
        if (bundle != null){
            mPlace = bundle.getParcelable(OBJECT_FROM_PLACE_TO_PLACE_DETAIL);
            tvTitle.setText(mPlace.getmName());
            tvName.setText(mPlace.getmName());
            tvAddress.setText(mPlace.getmAddress());
            Picasso.with(getApplicationContext()).load(mPlace.getmImageUrl()).into(imgPlaceDetail);
            getMenu(mPlace.getmId());
        }
    }
    public void receiveBundleFromMainActivity(){
        Intent intent = getIntent();
        Bundle bundle = intent.getParcelableExtra(BUNDLE_FROM_MAIN_TO_PLACE_DETAIL);
        if (bundle != null){
            mPlace = bundle.getParcelable(OBJECT_FROM_MAIN_TO_PLACE_DETAIL);
            tvTitle.setText(mPlace.getmName());
            tvName.setText(mPlace.getmName());
            tvAddress.setText(mPlace.getmAddress());
            Picasso.with(getApplicationContext()).load(mPlace.getmImageUrl()).into(imgPlaceDetail);
            getMenu(mPlace.getmId());
        }

    }



    public void getMenu(int place) {
        placeDetailPresenter.getMenu(place);
    }

    @Override
    public void getPlaceDetail(List<Menu> menus) {
        if (menus.size() == 0) {
            EmptyPlaceDetailFragment emptyPlaceDetailFragment = new EmptyPlaceDetailFragment();
            setMenuFragment(emptyPlaceDetailFragment);
        } else {
            ArrayList<Menu> menuToFragment = new ArrayList<>();
            for (int i = 0; i < menus.size(); i++) {
                menuToFragment.add(menus.get(i));
            }

            Bundle bundle = new Bundle();
            bundle.putParcelableArrayList(LIST_FROM_PLACE_DETAIL_TO_MENU_LIST_FRAGMENT
                    , menuToFragment);

            MenuListFragment menuListFragment = new MenuListFragment();
            menuListFragment.setArguments(bundle);
            setMenuFragment(menuListFragment);
        }
    }


    public void setMenuFragment(Fragment menuFragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.menu_layout, menuFragment);
        fragmentTransaction.commit();
    }

    @Override
    public void insertSavedPlaceResult(String result) {
        Log.d("Luan",result);
        Toast.makeText(this, result, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onConnectionFailer() {
        Toast.makeText(this, "Something wrong. Please check your internet connection", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void getSelectedMenuItem(Menu menu) {
        Intent intent = new Intent(PlaceDetailActivity.this, MenuActivity.class);
        Bundle bundle = new Bundle();
        bundle.putParcelable(OBJECT_FROM_DETAIL_TO_MENU, menu);
        intent.putExtra(BUNDLE_FROM_DETAIL_TO_MENU, bundle);
        startActivity(intent);
    }

    @Override
    public void getSelectedChoice(int position) {
        switch (position) {
            case 0: {
                Intent intent = new Intent(PlaceDetailActivity.this, ShowDirectionActivity.class);
                Bundle bundle = new Bundle();
                bundle.putParcelable(OBJECCT_FROM_PLACE_DETAIL_TO_SHOW_DIRECT,mPlace);
                intent.putExtra(BUNDLE_FROM_DETAIL_TO_DIRECT,bundle);
                startActivity(intent);
                break;
            }
            case 1: {
                placeDetailPresenter.insertSavedPlace(mPlace);
                break;
            }

            case 2: {

                String message = mPlace.getmName() + "\n" + mPlace.getmAddress() + "\n" + "https://www.deliverynow.vn/" + mPlace.getmUrl();
                Intent share = new Intent(Intent.ACTION_SEND);
                share.setType("text/plain");
                share.putExtra(Intent.EXTRA_TEXT, message);
                startActivity(Intent.createChooser(share, "Share"));
                break;
            }
        }
    }
}
