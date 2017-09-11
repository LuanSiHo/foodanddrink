package com.example.hosiluan.foodanddrink.settingactivity;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import com.example.hosiluan.foodanddrink.R;

import java.util.ArrayList;

/**
 * Created by Ho Si Luan on 9/7/2017.
 */

public class RecyclerViewLanguageAdapter extends RecyclerView.Adapter {

    private Context mContext;
    private ArrayList<String> mLanguageList;
    private RecyclerViewLanguageAdapterListener recyclerViewLanguageAdapterListener;
    private int lastCheckedPosition = 2;

    public RecyclerViewLanguageAdapter(Context mContext, ArrayList<String> mLanguageList,
                                       RecyclerViewLanguageAdapterListener
                                               recyclerViewLanguageAdapterListener) {
        this.mContext = mContext;
        this.mLanguageList = mLanguageList;
        this.recyclerViewLanguageAdapterListener = recyclerViewLanguageAdapterListener;
    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new MyHolder(LayoutInflater.from(mContext).inflate(R.layout.item_language, parent, false));
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, final int position) {
        final MyHolder myHolder = (MyHolder) holder;
        myHolder.tvLanguage.setText(mLanguageList.get(position));
        ((MyHolder) holder).checkboxLanguage.setTag(new Integer(position));
        if (position == lastCheckedPosition) {
            myHolder.checkboxLanguage.setChecked(true);
        } else {
            myHolder.checkboxLanguage.setChecked(false);
        }

        myHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                recyclerViewLanguageAdapterListener.getSelectedLanguageItem(position);
                lastCheckedPosition = position;
                notifyItemRangeChanged(0, mLanguageList.size());
            }
        });
    }

    public void setSelectedLangPos(String lang) {
        switch (lang){
            case "vi":{
                lastCheckedPosition = 0;
                break;
            }
            case "en":{
                lastCheckedPosition = 1;
                break;
            }
        }
    }

    @Override
    public int getItemCount() {
        return mLanguageList.size();
    }

    class MyHolder extends RecyclerView.ViewHolder {

        TextView tvLanguage;
        CheckBox checkboxLanguage;

        public MyHolder(View itemView) {
            super(itemView);
            tvLanguage = (TextView) itemView.findViewById(R.id.tv_item_language);
            checkboxLanguage = (CheckBox) itemView.findViewById(R.id.checkbox_language);
        }
    }

    public interface RecyclerViewLanguageAdapterListener {
        void getSelectedLanguageItem(int position);
    }
}
