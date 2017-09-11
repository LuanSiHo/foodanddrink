package com.example.hosiluan.foodanddrink.mainactivity.adapter;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.hosiluan.foodanddrink.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import static com.example.hosiluan.foodanddrink.loginactivity.LoginActivity.LOGIN_TOKEN;

/**
 * Created by Ho Si Luan on 9/4/2017.
 */

public class RecyclerViewMeAdapter extends RecyclerView.Adapter {

    private Context mContext;
    private ArrayList<String> mTypeList;
    private RecyclerViewMeAdapterListener recyclerViewMeAdapterListener;

    public RecyclerViewMeAdapter(Context mContext, ArrayList<String> mTypeList,
                                 RecyclerViewMeAdapterListener recyclerViewMeAdapterListener) {
        this.mContext = mContext;
        this.mTypeList = mTypeList;
        this.recyclerViewMeAdapterListener = recyclerViewMeAdapterListener;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new MyHolder(LayoutInflater.from(mContext).inflate(R.layout.item_me, parent, false));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        final MyHolder myHolder = (MyHolder) holder;
        myHolder.tvType.setText(mTypeList.get(position));
        TypedArray imageType = mContext.getResources().obtainTypedArray(R.array.array_me_image);
        myHolder.imgGo.setImageResource(R.drawable.ic_send);
        Picasso.with(mContext).load(imageType.getResourceId(position, -1)).into(myHolder.imgType);

        myHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                recyclerViewMeAdapterListener.getSelectedItem(position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mTypeList.size();
    }

    class MyHolder extends RecyclerView.ViewHolder {

        TextView tvType;
        ImageView imgType, imgGo;

        public MyHolder(View itemView) {
            super(itemView);
            tvType = (TextView) itemView.findViewById(R.id.tv_type_me);
            imgType = (ImageView) itemView.findViewById(R.id.img_type_me);
            imgGo = (ImageView) itemView.findViewById(R.id.img_go_me);
        }
    }

    public interface RecyclerViewMeAdapterListener{
        void getSelectedItem(int position);
    }
}
