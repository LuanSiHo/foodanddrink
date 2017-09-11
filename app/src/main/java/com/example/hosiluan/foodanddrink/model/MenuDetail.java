package com.example.hosiluan.foodanddrink.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Ho Si Luan on 8/25/2017.
 */

public class MenuDetail implements Parcelable{

    @SerializedName("id")
    @Expose
    private int mId;
    @SerializedName("name")
    @Expose
    private String mName;
    @SerializedName("image")
    @Expose
    private String mImage;
    @SerializedName("price")
    @Expose
    private String mPrice;
    @SerializedName("menu")
    @Expose
    private int mMenu;

    public MenuDetail(int mId, String mName, String mImage, String mPrice, int mMenu) {
        this.mId = mId;
        this.mName = mName;
        this.mImage = mImage;
        this.mPrice = mPrice;
        this.mMenu = mMenu;
    }

    protected MenuDetail(Parcel in) {
        mId = in.readInt();
        mName = in.readString();
        mImage = in.readString();
        mPrice = in.readString();
        mMenu = in.readInt();
    }

    public static final Creator<MenuDetail> CREATOR = new Creator<MenuDetail>() {
        @Override
        public MenuDetail createFromParcel(Parcel in) {
            return new MenuDetail(in);
        }

        @Override
        public MenuDetail[] newArray(int size) {
            return new MenuDetail[size];
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

    public String getmImage() {
        return mImage;
    }

    public void setmImage(String mImage) {
        this.mImage = mImage;
    }

    public String getmPrice() {
        return mPrice;
    }

    public void setmPrice(String mPrice) {
        this.mPrice = mPrice;
    }

    public int getmMenu() {
        return mMenu;
    }

    public void setmMenu(int mMenu) {
        this.mMenu = mMenu;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(mId);
        parcel.writeString(mName);
        parcel.writeString(mImage);
        parcel.writeString(mPrice);
        parcel.writeInt(mMenu);
    }
}
