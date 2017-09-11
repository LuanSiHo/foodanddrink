package com.example.hosiluan.foodanddrink.settingactivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

import com.example.hosiluan.foodanddrink.R;

import java.util.ArrayList;
import java.util.Locale;

public class SettingActivity extends AppCompatActivity implements
        RecyclerViewSettingAdapter.RecyclerViewSettingAdapterListener,
        RecyclerViewLanguageAdapter.RecyclerViewLanguageAdapterListener {

    public static final String APP_LANGUAGE = "app language";
    public static final String CHOOSED_LANGUAGE = "choosed language";
    public static final String LANG_CHANGED = "lang changed";
    public static final String LANG_STATE = "lang state";
    private RecyclerView mSettingRecyclerView, mRecyclerViewLang;
    private RecyclerViewSettingAdapter mRecyclerViewSettingAdapter;
    private ArrayList<String> mSettingList, mLangList;
    private ImageButton imgBtnBack;
    private RecyclerViewLanguageAdapter recyclerViewLanguageAdapter;
    private int chooseLangPos = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        setView();
        setEvent();
    }

    public void setView() {

        imgBtnBack = (ImageButton) findViewById(R.id.img_btn_back_setting);

        mSettingRecyclerView = (RecyclerView) findViewById(R.id.recyclerview_setting);
        mSettingList = new ArrayList<>();

        mRecyclerViewSettingAdapter = new RecyclerViewSettingAdapter(getApplicationContext()
                , mSettingList, this);

        mSettingRecyclerView.setAdapter(mRecyclerViewSettingAdapter);
        mSettingRecyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));

        String[] settingResourceList = getResources().getStringArray(R.array.array_setting);
        for (int i = 0; i < settingResourceList.length; i++) {
            mSettingList.add(settingResourceList[i]);
        }
        mRecyclerViewSettingAdapter.notifyDataSetChanged();

        mLangList = new ArrayList<>();
        mLangList.add("Tiếng Việt");
        mLangList.add("English");
        recyclerViewLanguageAdapter = new RecyclerViewLanguageAdapter(
                getApplicationContext(), mLangList, this);
    }

    public void setEvent() {
        imgBtnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    @Override
    public void getSelectedSettingItem(int position) {

        SharedPreferences sharedPreferences = getSharedPreferences(APP_LANGUAGE, MODE_PRIVATE);
        String lang = sharedPreferences.getString(CHOOSED_LANGUAGE, null);
        if (lang == null){
            lang = "en";
        }

        LayoutInflater layoutInflater = this.getLayoutInflater();
        final View dialogView = layoutInflater.inflate(R.layout.item_choose_language, null);

        final AlertDialog alertDialog;
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setView(dialogView);

        mRecyclerViewLang = (RecyclerView) dialogView.findViewById(R.id.recyclerview_language);
        mRecyclerViewLang.setAdapter(recyclerViewLanguageAdapter);
        mRecyclerViewLang.setLayoutManager(new LinearLayoutManager(this));

        recyclerViewLanguageAdapter.setSelectedLangPos(lang);

        alertDialog = builder.create();
        alertDialog.show();

        Button btnOk = (Button) dialogView.findViewById(R.id.btn_ok_choose_lang);
        btnOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (chooseLangPos >= 0) {
                    switch (chooseLangPos) {
                        case 0: {
                            changeLanguage("vi");
                            SharedPreferences sharedPreferences = getSharedPreferences(APP_LANGUAGE, MODE_PRIVATE);
                            SharedPreferences.Editor editor = sharedPreferences.edit();
                            editor.putString(CHOOSED_LANGUAGE, "vi");
                            editor.putInt(LANG_STATE, 1);
                            editor.apply();
                            break;
                        }
                        case 1: {
                            changeLanguage("en");
                            SharedPreferences sharedPreferences = getSharedPreferences(APP_LANGUAGE, MODE_PRIVATE);
                            SharedPreferences.Editor editor = sharedPreferences.edit();
                            editor.putString(CHOOSED_LANGUAGE, "en");
                            editor.putInt(LANG_STATE, 1);
                            editor.apply();
                            break;
                        }
                    }
                    alertDialog.dismiss();
                    finish();
                }else {
                    alertDialog.dismiss();
                }

            }
        });

        Button btnCancel = (Button) dialogView.findViewById(R.id.btn_cancel_choose_lang);
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                finish();
                alertDialog.dismiss();
            }
        });


    }

    public void changeLanguage(String language) {
        Locale locale = new Locale(language);
        Configuration configuration = new Configuration();
        configuration.locale = locale;
        getBaseContext().getResources().updateConfiguration(
                configuration, getBaseContext().getResources().getDisplayMetrics());
    }

    @Override
    public void getSelectedLanguageItem(int position) {
        chooseLangPos = position;
    }

}
