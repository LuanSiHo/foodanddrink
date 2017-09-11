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
 * Created by Ho Si Luan on 9/3/2017.
 */

public class RecyclerViewSaveAdapter extends RecyclerView.Adapter {

    private Context mContext;
    private ArrayList<Place> mPlaces;
    private ArrayList<Place> mSelectedList = new ArrayList<>();
    private RecyclerViewSaveAdapterListener recyclerViewSaveAdapterListener;
    private Boolean isLongClicked = false;
    private Boolean unHighLight = true;
    private ArrayList<String> highlightPosition = new ArrayList<>();

    public RecyclerViewSaveAdapter(Context mContext, ArrayList<Place> mPlaces,
                                   RecyclerViewSaveAdapterListener recyclerViewSaveAdapterListener) {
        this.mContext = mContext;
        this.mPlaces = mPlaces;
        this.recyclerViewSaveAdapterListener = recyclerViewSaveAdapterListener;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new MyViewHolder(LayoutInflater.from(mContext).inflate(R.layout.item_place,
                parent, false));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        final Place place = mPlaces.get(position);
        final RecyclerViewSaveAdapter.MyViewHolder viewHolder = (MyViewHolder) holder;

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

        if (unHighLight) {
            holder.itemView.setBackgroundColor(holder.itemView.getResources()
                    .getColor(R.color.whiteColor));
        }

        viewHolder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                view.setBackgroundColor(view.getResources().getColor(R.color.colorOrangeLight));
                setIsLongClicked(true);
                mSelectedList.add(mPlaces.get(position));
                recyclerViewSaveAdapterListener.onItemLongClick();
                return true;
            }
        });
        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isLongClicked) {
                    if (checkHighlightPositon(position)) {
                        view.setBackgroundColor(view.getResources().getColor(R.color.whiteColor));
                        mSelectedList.remove(mPlaces.get(position));
                        highlightPosition.remove(String.valueOf(position));
                    } else {
                        view.setBackgroundColor(view.getResources().getColor(R.color.colorOrangeLight));
                        mSelectedList.add(mPlaces.get(position));
                        highlightPosition.add(String.valueOf(position));
                    }
                } else {
                    recyclerViewSaveAdapterListener.getSelectedItem(mPlaces.get(position));
                }
            }
        });
    }


    public boolean checkHighlightPositon(int position) {
        for (int i = 0; i < highlightPosition.size(); i++) {
            if (Integer.parseInt(highlightPosition.get(i)) == position) {
                return true;
            }
        }
        return false;
    }

    public void removeAllSelectedItem() {
        for (int i = 0; i < mSelectedList.size(); i++) {
            mSelectedList.remove(i);

        }
        for (int i = 0; i < highlightPosition.size(); i++) {
            highlightPosition.remove(i);
        }

    }

    public void setUnHighLight() {
        unHighLight = true;
    }

    public void setIsLongClicked(Boolean isLongClicked) {
        this.isLongClicked = isLongClicked;
    }


    public ArrayList<Place> getSelectedList(){
        return mSelectedList;
    }

    @Override
    public int getItemCount() {
        return mPlaces.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {

        TextView tvName, tvAddress, tvTimeRange, tvPriceRange, tvDistance;
        ImageView imgPlace;

        public MyViewHolder(View itemView) {
            super(itemView);
            tvName = (TextView) itemView.findViewById(R.id.tv_place_name);
            tvAddress = (TextView) itemView.findViewById(R.id.tv_place_address);
            tvTimeRange = (TextView) itemView.findViewById(R.id.tv_place_time_range);
            tvPriceRange = (TextView) itemView.findViewById(R.id.tv_place_price_range);
            tvDistance = (TextView) itemView.findViewById(R.id.tv_place_distance);
            imgPlace = (ImageView) itemView.findViewById(R.id.img_place);
        }
    }

    public interface RecyclerViewSaveAdapterListener {
        void getSelectedItem(Place place);

        void onItemLongClick();
    }
}
