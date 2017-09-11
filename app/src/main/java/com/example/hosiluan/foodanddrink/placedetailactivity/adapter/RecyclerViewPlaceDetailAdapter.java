package com.example.hosiluan.foodanddrink.placedetailactivity.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.hosiluan.foodanddrink.R;
import com.example.hosiluan.foodanddrink.model.Menu;

import java.util.ArrayList;

/**
 * Created by Ho Si Luan on 8/25/2017.
 */

public class RecyclerViewPlaceDetailAdapter extends
        RecyclerView.Adapter<RecyclerViewPlaceDetailAdapter.Holder> {

    private Context mContext;
    private ArrayList<Menu> mMenus;
    private RecyclerViewPlaceDetailAdapterListener recyclerViewPlaceDetailAdapterListener;

    public RecyclerViewPlaceDetailAdapter(Context mContext, ArrayList<Menu> mMenus,
                                          RecyclerViewPlaceDetailAdapterListener
                                                  recyclerViewPlaceDetailAdapterListener) {
        this.mContext = mContext;
        this.mMenus = mMenus;
        this.recyclerViewPlaceDetailAdapterListener = recyclerViewPlaceDetailAdapterListener;
    }

    @Override
    public Holder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new Holder(LayoutInflater.from(mContext).inflate(R.layout.item_place_menu, parent, false));
    }

    @Override
    public void onBindViewHolder(final Holder holder, final int position) {
        holder.tvMenuName.setText(mMenus.get(position).getmName());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                recyclerViewPlaceDetailAdapterListener.getSelectedMenuItem(mMenus.get(position));
            }
        });
    }

    @Override
    public int getItemCount() {
        return mMenus.size();
    }

    class Holder extends RecyclerView.ViewHolder {

        TextView tvMenuName;

        public Holder(View itemView) {
            super(itemView);
            tvMenuName = (TextView) itemView.findViewById(R.id.tv_menu_name);
        }
    }

    public  interface RecyclerViewPlaceDetailAdapterListener {
        void getSelectedMenuItem(Menu menu);
    }
}
