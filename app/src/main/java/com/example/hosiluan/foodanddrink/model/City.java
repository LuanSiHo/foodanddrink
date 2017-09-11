package com.example.hosiluan.foodanddrink.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Ho Si Luan on 8/20/2017.
 */

public class City implements Parcelable {
    private String id;
    private String name;

    public City(String id, String name) {
        this.id = id;
        this.name = name;
    }

    protected City(Parcel in) {
        id = in.readString();
        name = in.readString();
    }

    public static final Creator<City> CREATOR = new Creator<City>() {
        @Override
        public City createFromParcel(Parcel in) {
            return new City(in);
        }

        @Override
        public City[] newArray(int size) {
            return new City[size];
        }
    };

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(id);
        parcel.writeString(name);
    }
}
