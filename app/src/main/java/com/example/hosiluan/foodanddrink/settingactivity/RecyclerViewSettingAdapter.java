package com.example.hosiluan.foodanddrink.settingactivity;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.hosiluan.foodanddrink.R;

import java.util.ArrayList;

/**
 * Created by Ho Si Luan on 9/7/2017.
 */

public class RecyclerViewSettingAdapter extends RecyclerView.Adapter {

    private Context mContext;
    private ArrayList<String> mSettingList;
    private RecyclerViewSettingAdapterListener recyclerViewSettingAdapterListener;

    public RecyclerViewSettingAdapter(Context mContext, ArrayList<String> mSettingList,
                                      RecyclerViewSettingAdapterListener
                                              recyclerViewSettingAdapterListener) {
        this.mContext = mContext;
        this.mSettingList = mSettingList;
        this.recyclerViewSettingAdapterListener = recyclerViewSettingAdapterListener;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        return new Holder(LayoutInflater.from(mContext).inflate(R.layout.item_setting, parent, false));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        Holder holder1 = (Holder) holder;
        TypedArray images = mContext.getResources().obtainTypedArray(R.array.array_setting_image);
        holder1.tvSetting.setText(mSettingList.get(position));
        holder1.imgIconSetting.setImageResource(images.getResourceId(position, -1));

        TypedArray imagesGo = mContext.getResources().obtainTypedArray(R.array.array_setting_image_go);
        holder1.imgIconGo.setImageResource(imagesGo.getResourceId(position, -1));
        holder1.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                recyclerViewSettingAdapterListener.getSelectedSettingItem(position);
            }
        });

    }

    @Override
    public int getItemCount() {
        return mSettingList.size();
    }

    class Holder extends RecyclerView.ViewHolder {

        TextView tvSetting,tvLanguage;
        ImageView imgIconSetting, imgIconGo;

        public Holder(View itemView) {
            super(itemView);
            imgIconSetting = (ImageView) itemView.findViewById(R.id.img_setting);
            tvSetting = (TextView) itemView.findViewById(R.id.tv_setting);
            tvLanguage = (TextView) itemView.findViewById(R.id.tv_language);
            imgIconGo = (ImageView) itemView.findViewById(R.id.img_setting_go);
        }
    }

    public interface RecyclerViewSettingAdapterListener {
        void getSelectedSettingItem(int position);
    }
}
