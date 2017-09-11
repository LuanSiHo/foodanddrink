package com.example.hosiluan.foodanddrink.menuactivity.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.hosiluan.foodanddrink.R;
import com.example.hosiluan.foodanddrink.model.MenuDetail;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by Ho Si Luan on 8/25/2017.
 */

public class RecyclerViewMenuAdapter  extends RecyclerView.Adapter<RecyclerViewMenuAdapter.Holder>{

    private Context mContext;
    private ArrayList<MenuDetail> mMenuDetails;

    public RecyclerViewMenuAdapter(Context mContext, ArrayList<MenuDetail> mMenuDetails) {
        this.mContext = mContext;
        this.mMenuDetails = mMenuDetails;
    }

    @Override
    public Holder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new Holder(LayoutInflater.from(mContext).inflate(R.layout.item_menu_detail,parent,false));
    }

    @Override
    public void onBindViewHolder(Holder holder, int position) {
        String image = mMenuDetails.get(position).getmImage();
        String tail = image.substring(image.length() - 3);
        switch (tail){
            case "jpg":{
                Picasso.with(mContext).load(image).into(holder.imgMenuDetail);
                break;
            }
            case "png":{
                Picasso.with(mContext).load(R.drawable.no_image).into(holder.imgMenuDetail);
                break;
            }
        }
        holder.tvPrice.setText(mMenuDetails.get(position).getmPrice());
        holder.tvName.setText(mMenuDetails.get(position).getmName());
    }

    @Override
    public int getItemCount() {
        return mMenuDetails.size();
    }

    class Holder extends RecyclerView.ViewHolder{
        ImageView imgMenuDetail;
        TextView tvName,tvPrice;

        public Holder(View itemView) {
            super(itemView);

            imgMenuDetail = (ImageView) itemView.findViewById(R.id.img_menu_detail);
            tvName = (TextView) itemView.findViewById(R.id.tv_menu_detail_name);
            tvPrice = (TextView) itemView.findViewById(R.id.tv_menu_detail_price);

        }
    }
}
