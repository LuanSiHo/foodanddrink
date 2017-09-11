package com.example.hosiluan.foodanddrink.mainactivity.fragment;

import android.app.Activity;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.hosiluan.foodanddrink.CircleImage;
import com.example.hosiluan.foodanddrink.R;
import com.example.hosiluan.foodanddrink.mainactivity.adapter.RecyclerviewMainAdapter;
import com.example.hosiluan.foodanddrink.model.City;
import com.example.hosiluan.foodanddrink.model.TypeOfPlace;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import static android.content.Context.MODE_PRIVATE;
import static com.example.hosiluan.foodanddrink.profilesettingactivity.ProfileSettingActivity.decodeBitmapFromString;
import static com.example.hosiluan.foodanddrink.loginactivity.LoginActivity.LOGIN_TOKEN;
import static com.example.hosiluan.foodanddrink.loginactivity.LoginActivity.USER_PROFILE_PIC;

/**
 * Created by Ho Si Luan on 9/2/2017.
 */

public class HomeFragment extends Fragment implements RecyclerviewMainAdapter.RecyclerViewMainAdapterListener {
    private View view;

    private ArrayList<TypeOfPlace> arrayList;
    private RecyclerviewMainAdapter arrayAdapter;
    private RecyclerView recyclerViewMenu;
    private TextView tvHello, tvCity;
    private CircleImage imgProfilePicture;
    private City city = new City("1", "Hồ Chí Minh");
    private HomeFragmentListener homeFragmentListener;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        homeFragmentListener = (HomeFragmentListener) activity;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_home,container,false);
        Log.d("Luan","hello");
        setView();
        receiveBundleFromMainActivity();
        setEvent();
        return view;
    }

    public void setView(){
        tvHello = (TextView) view.findViewById(R.id.tv_hello);
        imgProfilePicture = (CircleImage) view.findViewById(R.id.imageview_profile_picture);
        tvCity = (TextView) view.findViewById(R.id.tv_city);
        recyclerViewMenu = (RecyclerView) view.findViewById(R.id.recyclerview_menu);
    }

    public void setEvent(){
        tvCity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                homeFragmentListener.startChooseCityActivity(tvCity.getText().toString());
            }
        });
    }

    public void receiveBundleFromMainActivity() {

        Bundle bundle = getArguments();
        String name = bundle.getString("name");
        String id = bundle.getString("id");

        tvHello.append(name);
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences(LOGIN_TOKEN,MODE_PRIVATE);

        String encodedString = sharedPreferences.getString(USER_PROFILE_PIC, null);
        if (encodedString == null || encodedString.length() == 0) {
            Picasso.with(getActivity().getApplicationContext())
                    .load("https://graph.facebook.com/" + id + "/picture?width=500&height=500")
                    .into(imgProfilePicture);
        } else {
            Bitmap bitmap = decodeBitmapFromString(encodedString);
            imgProfilePicture.setImageBitmap(bitmap);
        }
    }

    public void setSelectedCity(Bundle bundle){
        city = bundle.getParcelable("selected_city");
        tvCity.setText(city.getName());
    }

    public void setRecyclerViewMenu(List<TypeOfPlace> typeOfPlaces) {
        arrayList = new ArrayList<>();
        arrayList = (ArrayList<TypeOfPlace>) typeOfPlaces;
        arrayAdapter = new RecyclerviewMainAdapter(getActivity().getApplicationContext(),
                arrayList, this);
        recyclerViewMenu.setAdapter(arrayAdapter);

        final GridLayoutManager gridLayoutManager = new
                GridLayoutManager(getActivity().getApplicationContext(), 6);
        gridLayoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int position) {
                switch (position) {
                    case 0:
                    case 1:
                    case 2:
                        return 2;
                    case 3:
                    case 4:
                        return 3;
                }
                return 0;

            }
        });
        recyclerViewMenu.setLayoutManager(gridLayoutManager);

        arrayList.clear();

        String[] typePlace = getResources().getStringArray(R.array.array_menu_name);
        for (int i = 0; i < typePlace.length; i++){
            TypeOfPlace typeOfPlace = new TypeOfPlace(i + 1,typePlace[i]);
            arrayList.add(typeOfPlace);
        }

        arrayAdapter.notifyDataSetChanged();

    }

    @Override
    public void getSelectedTypeOfPlace(TypeOfPlace typeOfPlace) {
        homeFragmentListener.getSelectedTypeOfPlace(typeOfPlace,city);

    }


    public interface HomeFragmentListener{
        void startChooseCityActivity(String cityName);
        void getSelectedTypeOfPlace(TypeOfPlace typeOfPlace,City  city);
    }
}
