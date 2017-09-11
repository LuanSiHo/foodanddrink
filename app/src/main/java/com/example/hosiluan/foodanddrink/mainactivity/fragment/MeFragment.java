package com.example.hosiluan.foodanddrink.mainactivity.fragment;

import android.app.Activity;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.hosiluan.foodanddrink.CircleImage;
import com.example.hosiluan.foodanddrink.R;
import com.example.hosiluan.foodanddrink.mainactivity.adapter.RecyclerViewMeAdapter;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Locale;

import static android.content.Context.MODE_PRIVATE;
import static com.example.hosiluan.foodanddrink.profilesettingactivity.ProfileSettingActivity.decodeBitmapFromString;
import static com.example.hosiluan.foodanddrink.loginactivity.LoginActivity.FB_USER_NAME;
import static com.example.hosiluan.foodanddrink.loginactivity.LoginActivity.LOGIN_TOKEN;
import static com.example.hosiluan.foodanddrink.loginactivity.LoginActivity.USER_ID;
import static com.example.hosiluan.foodanddrink.loginactivity.LoginActivity.USER_PROFILE_PIC;
import static com.example.hosiluan.foodanddrink.settingactivity.SettingActivity.APP_LANGUAGE;
import static com.example.hosiluan.foodanddrink.settingactivity.SettingActivity.CHOOSED_LANGUAGE;

/**
 * Created by Ho Si Luan on 9/2/2017.
 */

public class MeFragment extends Fragment implements RecyclerViewMeAdapter.RecyclerViewMeAdapterListener {
    private View view;
    private RecyclerViewMeAdapter recyclerViewMeAdapter;
    private RecyclerView recyclerViewMe;
    private ArrayList<String> mTypeList;
    private SharedPreferences sharedPreferences;
    private TextView tvUserName;
    private CircleImage imgProfileImage;

    private MeFragmentListener meFragmentListener;




    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        meFragmentListener = (MeFragmentListener) activity;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_me, container, false);
        setView();
        setUserInfo();
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        setUserInfo();
    }

    public void setView() {

        tvUserName = (TextView) view.findViewById(R.id.tv_user_name);
        imgProfileImage = (CircleImage) view.findViewById(R.id.img_user_profile);

        recyclerViewMe = (RecyclerView) view.findViewById(R.id.recyclerview_me);
        String[] typearray = getResources().getStringArray(R.array.array_me);

        mTypeList = new ArrayList<>();
        for (int i = 0; i < typearray.length; i++) {
            mTypeList.add(typearray[i]);
        }

        recyclerViewMeAdapter = new RecyclerViewMeAdapter(getActivity()
                .getApplicationContext(), mTypeList, this);

        recyclerViewMe.setAdapter(recyclerViewMeAdapter);
        recyclerViewMe.setLayoutManager(new LinearLayoutManager(getActivity().getApplicationContext()));
    }

    public void setUserInfo(){
        sharedPreferences = getActivity().getSharedPreferences(LOGIN_TOKEN,MODE_PRIVATE);
        String userName = sharedPreferences.getString(FB_USER_NAME,null);
        String id = sharedPreferences.getString(USER_ID,null);

        tvUserName.setText(userName);

        String encodedString = sharedPreferences.getString(USER_PROFILE_PIC, null);
        if (encodedString == null || encodedString.length() == 0) {
            Picasso.with(getActivity().getApplicationContext())
                    .load("https://graph.facebook.com/" + id + "/picture?width=500&height=500")
                    .into(imgProfileImage);
        } else {
            Bitmap bitmap = decodeBitmapFromString(encodedString);
            imgProfileImage.setImageBitmap(bitmap);
        }
    }

    @Override
    public void getSelectedItem(int position) {
        meFragmentListener.getSelectedMeFragmentItem(position);
    }

   public interface MeFragmentListener {
        void getSelectedMeFragmentItem(int position);
    }

}

