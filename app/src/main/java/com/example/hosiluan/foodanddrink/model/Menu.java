package com.example.hosiluan.foodanddrink.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Ho Si Luan on 8/25/2017.
 */

public class Menu implements Parcelable {
    @SerializedName("id")
    @Expose
    private int mId;
    @SerializedName("name")
    @Expose
    private String mName;
    @SerializedName("place")
    @Expose
    private int mPlace;

    public Menu(int mId, String mName, int mPlace) {
        this.mId = mId;
        this.mName = mName;
        this.mPlace = mPlace;
    }

    protected Menu(Parcel in) {
        mId = in.readInt();
        mName = in.readString();
        mPlace = in.readInt();
    }

    public static final Creator<Menu> CREATOR = new Creator<Menu>() {
        @Override
        public Menu createFromParcel(Parcel in) {
            return new Menu(in);
        }

        @Override
        public Menu[] newArray(int size) {
            return new Menu[size];
        }
    };

    public int getmId() {
        return mId;
    }

    public void setmId(int mId) {
        this.mId = mId;
    }

    public String getmName() {
        return mName;
    }

    public void setmName(String mName) {
        this.mName = mName;
    }

    public int getmPlace() {
        return mPlace;
    }

    public void setmPlace(int mPlace) {
        this.mPlace = mPlace;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(mId);
        parcel.writeString(mName);
        parcel.writeInt(mPlace);
    }
}
