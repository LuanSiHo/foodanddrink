package com.example.hosiluan.foodanddrink.mainactivity.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.hosiluan.foodanddrink.R;
import com.example.hosiluan.foodanddrink.model.Place;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by Ho Si Luan on 9/8/2017.
 */

public class RecyclerViewSearchAdapter extends RecyclerView.Adapter {
    private Context mContext;
    private ArrayList<Place> mPlaces;
    private RecyclerViewSearchAdapterListener recyclerViewSearchAdapterListener;

    public RecyclerViewSearchAdapter(Context mContext, ArrayList<Place> mPlaces,
                                     RecyclerViewSearchAdapterListener
                                             recyclerViewSearchAdapterListener) {
        this.mContext = mContext;
        this.mPlaces = mPlaces;
        this.recyclerViewSearchAdapterListener = recyclerViewSearchAdapterListener;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new MyHolder(LayoutInflater.from(mContext).inflate(R.layout.item_place,
                parent, false));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        final Place place = mPlaces.get(position);
        final RecyclerViewSearchAdapter.MyHolder viewHolder = (MyHolder) holder;

        viewHolder.tvName.setText(place.getmName());
        viewHolder.tvAddress.setText(place.getmAddress());
        viewHolder.tvTimeRange.setText(place.getmTimeRange());
        viewHolder.tvPriceRange.setText(place.getmPriceRange());
        Picasso.with(mContext).load(place.getmImageUrl()).into(viewHolder.imgPlace);

        if (mPlaces.get(position).getmDistance() != null) {
            viewHolder.tvDistance.setText(String.valueOf(mPlaces
                    .get(position)
                    .getmDistance()
                    .getText()));
        }
        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                recyclerViewSearchAdapterListener.getSelectedSearchItem(mPlaces.get(position));
            }
        });
    }

    @Override
    public int getItemCount() {
        return mPlaces.size();
    }

    class MyHolder extends RecyclerView.ViewHolder {

        TextView tvName, tvAddress, tvTimeRange, tvPriceRange, tvDistance;
        ImageView imgPlace;

        public MyHolder(View itemView) {
            super(itemView);
            tvName = (TextView) itemView.findViewById(R.id.tv_place_name);
            tvAddress = (TextView) itemView.findViewById(R.id.tv_place_address);
            tvTimeRange = (TextView) itemView.findViewById(R.id.tv_place_time_range);
            tvPriceRange = (TextView) itemView.findViewById(R.id.tv_place_price_range);
            tvDistance = (TextView) itemView.findViewById(R.id.tv_place_distance);
            imgPlace = (ImageView) itemView.findViewById(R.id.img_place);
        }
    }

    public  interface  RecyclerViewSearchAdapterListener{
        void getSelectedSearchItem(Place place);
    }
}
