package com.example.hosiluan.foodanddrink.mainactivity.adapter;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.hosiluan.foodanddrink.R;
import com.example.hosiluan.foodanddrink.model.TypeOfPlace;
import com.makeramen.roundedimageview.RoundedTransformationBuilder;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Transformation;

import java.util.ArrayList;

/**
 * Created by Ho Si Luan on 8/19/2017.
 */

public class RecyclerviewMainAdapter extends RecyclerView.Adapter<RecyclerviewMainAdapter.MyViewHolder> {

    private Context mContext;
    private ArrayList<TypeOfPlace> arrayList;
    private RecyclerViewMainAdapterListener recyclerViewMainAdapterListener;

    public RecyclerviewMainAdapter(Context mContext, ArrayList<TypeOfPlace> arrayList, RecyclerViewMainAdapterListener recyclerViewMainAdapterListener) {
        this.mContext = mContext;
        this.arrayList = arrayList;
        this.recyclerViewMainAdapterListener = recyclerViewMainAdapterListener;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.menu_item, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {
        TypeOfPlace menuItem = arrayList.get(position);
        holder.textView.setText(menuItem.getTypeofplace());

        TypedArray images = mContext.getResources().obtainTypedArray(R.array.array_menu_image);
        Transformation transformation = new RoundedTransformationBuilder()
                .borderWidthDp(1)
                .cornerRadiusDp(30)
                .oval(false)
                .build();
        Picasso.with(mContext).load(images.getResourceId(position,-1)).transform(transformation).into(holder.imgBigImage);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                recyclerViewMainAdapterListener.getSelectedTypeOfPlace(arrayList.get(position));
            }
        });
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        TextView textView;
        ImageView imgBigImage;

        public MyViewHolder(View itemView) {
            super(itemView);
            textView = (TextView) itemView.findViewById(R.id.text);
            imgBigImage = (ImageView) itemView.findViewById(R.id.img_menu);

        }
    }

     public interface  RecyclerViewMainAdapterListener{
        void getSelectedTypeOfPlace(TypeOfPlace typeOfPlace);
    }
}
