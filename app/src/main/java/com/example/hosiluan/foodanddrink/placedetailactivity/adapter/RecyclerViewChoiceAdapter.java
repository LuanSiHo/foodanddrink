package com.example.hosiluan.foodanddrink.placedetailactivity.adapter;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.hosiluan.foodanddrink.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by Ho Si Luan on 8/25/2017.
 */

public class RecyclerViewChoiceAdapter extends RecyclerView.Adapter<RecyclerViewChoiceAdapter.MyHolder> {

    private Context mContext;
    private ArrayList<String> mChoices;
    private RecyclerviewChoiceAdapterListener recyclerviewChoiceAdapterListener;

    public RecyclerViewChoiceAdapter(Context mContext, ArrayList<String> mChoices,
                                     RecyclerviewChoiceAdapterListener recyclerviewChoiceAdapterListener) {
        this.mContext = mContext;
        this.mChoices = mChoices;
        this.recyclerviewChoiceAdapterListener = recyclerviewChoiceAdapterListener;
    }

    @Override
    public MyHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new MyHolder(LayoutInflater.from(mContext).inflate(R.layout.item_choice_place_detail, parent, false));
    }

    @Override
    public void onBindViewHolder(MyHolder holder, final int position) {
        holder.tvChoice.setText(mChoices.get(position));
        TypedArray image = mContext.getResources().obtainTypedArray(R.array.array_choice_image);
        Picasso.with(mContext).load(image.getResourceId(position, -1)).into(holder.imgChoice);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                recyclerviewChoiceAdapterListener.getSelectedChoice(position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mChoices.size();
    }

    class MyHolder extends RecyclerView.ViewHolder {

        ImageView imgChoice;
        TextView tvChoice;

        public MyHolder(View itemView) {
            super(itemView);
            imgChoice = (ImageView) itemView.findViewById(R.id.img_choice);
            tvChoice = (TextView) itemView.findViewById(R.id.tv_choice);

        }
    }

    public interface RecyclerviewChoiceAdapterListener {
        void getSelectedChoice(int position);
    }
}
