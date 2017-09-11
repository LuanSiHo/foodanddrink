package com.example.hosiluan.foodanddrink.placeactivity.adapter;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.hosiluan.foodanddrink.R;
import com.example.hosiluan.foodanddrink.model.Place;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by Ho Si Luan on 8/20/2017.
 */

public class RecyclerViewPlaceAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {


    private Context mContext;
    private ArrayList<Place> mPlaces;
    private RecyclerViewPlaceAdapterListener recyclerViewPlaceAdapterListener;

    public RecyclerViewPlaceAdapter(Context mContext,
                                    ArrayList<Place> mPlaces
            , RecyclerViewPlaceAdapterListener recyclerViewPlaceAdapterListener) {
        this.mContext = mContext;
        this.mPlaces = mPlaces;
        this.recyclerViewPlaceAdapterListener = recyclerViewPlaceAdapterListener;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(mContext).inflate(R.layout.item_place, parent, false);
        return new Holder(view);
    }


    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, final int position) {
        Place place = mPlaces.get(position);
        Holder viewHolder = (Holder) holder;
        viewHolder.tvName.setText(place.getmName());
        viewHolder.tvAddress.setText(place.getmAddress());
        viewHolder.tvTimeRange.setText(place.getmTimeRange());
        viewHolder.tvPriceRange.setText(place.getmPriceRange());

        if (mPlaces.get(position).getmDistance() != null){
            viewHolder.tvDistance.setText(String.valueOf(mPlaces.get(position).getmDistance().getText()));
        }


        Picasso.with(mContext).load(place.getmImageUrl()).into(viewHolder.imgPlace);

        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                recyclerViewPlaceAdapterListener.getSelectedItem(mPlaces.get(position));
            }
        });

    }


    @Override
    public int getItemCount() {
        return mPlaces.size();
    }

    class Holder extends RecyclerView.ViewHolder {
        TextView tvName, tvAddress, tvTimeRange, tvPriceRange, tvDistance;
        ImageView imgPlace;

        public Holder(View itemView) {
            super(itemView);
            tvName = (TextView) itemView.findViewById(R.id.tv_place_name);
            tvAddress = (TextView) itemView.findViewById(R.id.tv_place_address);
            tvTimeRange = (TextView) itemView.findViewById(R.id.tv_place_time_range);
            tvPriceRange = (TextView) itemView.findViewById(R.id.tv_place_price_range);
            tvDistance = (TextView) itemView.findViewById(R.id.tv_place_distance);
            imgPlace = (ImageView) itemView.findViewById(R.id.img_place);
        }
    }

    public interface RecyclerViewPlaceAdapterListener {
        void getSelectedItem(Place place);
    }
}
