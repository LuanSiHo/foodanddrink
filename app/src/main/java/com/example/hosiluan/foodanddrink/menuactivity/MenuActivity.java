package com.example.hosiluan.foodanddrink.menuactivity;

import android.content.Intent;
import android.os.Parcelable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.hosiluan.foodanddrink.R;
import com.example.hosiluan.foodanddrink.menuactivity.fragment.EmptyMenuFragment;
import com.example.hosiluan.foodanddrink.menuactivity.fragment.MenuDetailFragment;
import com.example.hosiluan.foodanddrink.model.Menu;
import com.example.hosiluan.foodanddrink.model.MenuDetail;

import java.util.ArrayList;
import java.util.List;

import static com.example.hosiluan.foodanddrink.placedetailactivity.PlaceDetailActivity.BUNDLE_FROM_DETAIL_TO_MENU;
import static com.example.hosiluan.foodanddrink.placedetailactivity.PlaceDetailActivity.OBJECT_FROM_DETAIL_TO_MENU;

public class MenuActivity extends AppCompatActivity implements MenuPresenter.MenuPresenterListener {


    public static final String EMPTY_MENU_DETAIL_FRAGMENT = "empty menu detail fragment";
    public static final String MENU_DETAIL_FRAGMENT = "menu detail fragment";
    public static final String MENU_DETAIL_LIST = "object  from menuactivity to fragment";
    private MenuPresenter mMenuPresenter;

    private TextView tvTitle;
    private ImageButton imgBtnBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
        setView();
        setEvent();
        receiveBundleFromPlaceDetailAcitivity();
    }


    public void setView() {
        mMenuPresenter = new MenuPresenter(this);
        tvTitle = (TextView) findViewById(R.id.tv_menu_title);
        imgBtnBack = (ImageButton) findViewById(R.id.img_btn_menu_back);

    }

    public void receiveBundleFromPlaceDetailAcitivity() {
        Intent intent = getIntent();
        Bundle bundle = intent.getBundleExtra(BUNDLE_FROM_DETAIL_TO_MENU);
        Menu menu = bundle.getParcelable(OBJECT_FROM_DETAIL_TO_MENU);
        tvTitle.setText(menu.getmName());

        mMenuPresenter.getMenuDetail(menu.getmId());
    }

    public void setEvent(){
        imgBtnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    public void setMenuDetailFragment(Fragment menuFragment,String TAG) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.menu_detail_layout, menuFragment,TAG);
        fragmentTransaction.commit();
    }

    @Override
    public void getSelectedMenuDetail(List<MenuDetail> menuDetails) {
        if (menuDetails.size() == 0){
            EmptyMenuFragment emptyMenuFragment = new EmptyMenuFragment();
            setMenuDetailFragment(emptyMenuFragment,EMPTY_MENU_DETAIL_FRAGMENT);
        }else {
            ArrayList<MenuDetail> menuDetailsToFragment = new ArrayList<>();
            for (int i = 0; i < menuDetails.size();i++){
                menuDetailsToFragment.add(menuDetails.get(i));
            }

            MenuDetailFragment  menuDetailFragment = new MenuDetailFragment();
            Bundle bundle = new Bundle();
            bundle.putParcelableArrayList(MENU_DETAIL_LIST,menuDetailsToFragment);
            menuDetailFragment.setArguments(bundle);
            setMenuDetailFragment(menuDetailFragment,MENU_DETAIL_FRAGMENT);

        }
    }

    @Override
    public void onConnectionFailer() {
        Toast.makeText(this, "Something wrong. Please check your internet connection", Toast.LENGTH_SHORT).show();

    }
}

