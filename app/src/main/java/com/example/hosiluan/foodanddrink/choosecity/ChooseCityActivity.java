package com.example.hosiluan.foodanddrink.choosecity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.hosiluan.foodanddrink.model.City;
import com.example.hosiluan.foodanddrink.R;
import com.example.hosiluan.foodanddrink.retrofit.remote.ApiUtils;
import com.example.hosiluan.foodanddrink.retrofit.remote.CityService;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ChooseCityActivity extends AppCompatActivity implements
        ListViewCityAdapter.ListViewCityAdapterListener,
        ChooseCityPresenter.ChooseCityPresenterListener {

    private ListView listViewCity;
    private ListViewCityAdapter listViewCityAdapter;
    private ArrayList<City> cities = new ArrayList<City>();

    private TextView tvDone;
    private ImageButton imgbtnBack;
    private City selectedCity;


    private ChooseCityPresenter chooseCityPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_city);
        setView();
        getCityList();
        setEvent();
    }

    public void receiveCityNameFromMainActivity() {
        Intent intent = getIntent();
        String cityName = intent.getStringExtra("city_name");
        for (int i = 0; i < cities.size(); i++) {
            if (cities.get(i).getName().equals(cityName)) {
                listViewCityAdapter.setSelectedIndex(i);
            }
        }
    }

    private void setEvent() {
        listViewCity.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                listViewCityAdapter.setSelectedIndex(i);
                listViewCityAdapter.notifyDataSetChanged();
            }
        });

        tvDone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                Bundle bundle = new Bundle();
                bundle.putParcelable("selected_city", selectedCity);
                intent.putExtra("selected_city_bundle", bundle);
                setResult(1, intent);
                finish();
            }
        });

        imgbtnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    public void setView() {
        tvDone = (TextView) findViewById(R.id.tv_done);
        imgbtnBack = (ImageButton) findViewById(R.id.img_btn_back);
        listViewCity = (ListView) findViewById(R.id.lv_city);
        listViewCityAdapter = new ListViewCityAdapter(this, R.layout.item_city,
                new ArrayList<City>(), this);
        chooseCityPresenter = new ChooseCityPresenter(this);
    }

    public void getCityList() {
        chooseCityPresenter.getCityList();
    }

    @Override
    public void getSelectedCity(City city) {
        selectedCity = city;
    }

    @Override
    public void getCityListResult(ArrayList<City> cities) {
        this.cities = cities;
        receiveCityNameFromMainActivity();
        listViewCityAdapter.clear();
        for (int i = 0; i < cities.size(); i++) {
            listViewCityAdapter.addAll(cities.get(i));
        }
        listViewCity.setAdapter(listViewCityAdapter);
    }

    @Override
    public void onConnectionFailer() {
        Toast.makeText(this, "Something wrong. Please check your internet connection", Toast.LENGTH_SHORT).show();
    }
}
