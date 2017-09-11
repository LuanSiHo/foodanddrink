package com.example.hosiluan.foodanddrink.choosecity;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

import com.example.hosiluan.foodanddrink.model.City;
import com.example.hosiluan.foodanddrink.R;

import java.util.ArrayList;

/**
 * Created by Ho Si Luan on 8/20/2017.
 */

public class ListViewCityAdapter extends ArrayAdapter {
    private Context mContext;
    private ArrayList<City> cities;
    private ListViewCityAdapterListener listViewCityAdapterListener;
    private int selectedIndex = -1;

    public ListViewCityAdapter(@NonNull Context context, @LayoutRes int resource,
                               @NonNull ArrayList<City> objects, ListViewCityAdapterListener
                                       listViewCityAdapterListener) {
        super(context, resource, objects);
        mContext = context;
        cities = objects;
        this.listViewCityAdapterListener = listViewCityAdapterListener;
    }

    @NonNull
    @Override
    public View getView(final int position, @Nullable View convertView,
                        @NonNull final ViewGroup parent) {

        final Holder holder;
        if (convertView == null) {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.item_city, parent, false);
            holder = new Holder();

            holder.cityName = (TextView) convertView.findViewById(R.id.tv_city_name);
            holder.checkBox = (CheckBox) convertView.findViewById(R.id.checkbox_city);

            convertView.setTag(holder);
        } else {

            holder = (Holder) convertView.getTag();
        }

        final City city = cities.get(position);
        holder.cityName.setText(city.getName());

        if (selectedIndex == position){
            holder.checkBox.setChecked(true);
            holder.checkBox.setVisibility(View.VISIBLE);
            listViewCityAdapterListener.getSelectedCity(city);
        }else {
            holder.checkBox.setChecked(false);
            holder.checkBox.setVisibility(View.INVISIBLE);
        }
        return convertView;
    }

    class Holder {
        TextView cityName;
        CheckBox checkBox;
    }

    public void setSelectedIndex(int index){
        selectedIndex = index;
    }

    interface ListViewCityAdapterListener {
        void getSelectedCity(City city);
    }


}
