package com.example.hosiluan.foodanddrink.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Ho Si Luan on 8/20/2017.
 */

public class Place implements Parcelable {
    @SerializedName("id")
    @Expose
    private int mId;
    @SerializedName("name")
    @Expose
    private String mName;
    @SerializedName("url")
    @Expose
    private String mUrl;
    @SerializedName("imageurl")
    @Expose
    private String mImageUrl;
    @SerializedName("address")
    @Expose
    private String mAddress;
    @SerializedName("lon")
    @Expose
    private double mLon;
    @SerializedName("lat")
    @Expose
    private double mlat;
    @SerializedName("timerange")
    @Expose
    private String mTimeRange;
    @SerializedName("pricerange")
    @Expose
    private String mPriceRange;
    @SerializedName("area")
    @Expose
    private int mArea;
    @SerializedName("typeofplace")
    @Expose
    private int mTypeOfPlace;

    private Distance mDistance;

    public Place(int mId, String mName, String mUrl, String mImageUrl, String mAddress, double mLon,
                 double mlat, String mTimeRange, String mPriceRange, int mArea, int mTypeOfPlace) {

        this.mId = mId;
        this.mName = mName;
        this.mUrl = mUrl;
        this.mImageUrl = mImageUrl;
        this.mAddress = mAddress;
        this.mLon = mLon;
        this.mlat = mlat;
        this.mTimeRange = mTimeRange;
        this.mPriceRange = mPriceRange;
        this.mArea = mArea;
        this.mTypeOfPlace = mTypeOfPlace;
    }

    public Distance getmDistance() {
        return mDistance;
    }

    public void setmDistance(Distance mDistance) {
        this.mDistance = mDistance;
    }

    protected Place(Parcel in) {
        mId = in.readInt();
        mName = in.readString();
        mUrl = in.readString();
        mImageUrl = in.readString();
        mAddress = in.readString();
        mLon = in.readDouble();
        mlat = in.readDouble();
        mTimeRange = in.readString();
        mPriceRange = in.readString();
        mArea = in.readInt();
        mTypeOfPlace = in.readInt();
    }

    public static final Creator<Place> CREATOR = new Creator<Place>() {
        @Override
        public Place createFromParcel(Parcel in) {
            return new Place(in);
        }

        @Override
        public Place[] newArray(int size) {
            return new Place[size];
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

    public String getmUrl() {
        return mUrl;
    }

    public void setmUrl(String mUrl) {
        this.mUrl = mUrl;
    }

    public String getmImageUrl() {
        return mImageUrl;
    }

    public void setmImageUrl(String mImageUrl) {
        this.mImageUrl = mImageUrl;
    }

    public String getmAddress() {
        return mAddress;
    }

    public void setmAddress(String mAddress) {
        this.mAddress = mAddress;
    }

    public double getmLon() {
        return mLon;
    }

    public void setmLon(double mLon) {
        this.mLon = mLon;
    }

    public double getMlat() {
        return mlat;
    }

    public void setMlat(double mlat) {
        this.mlat = mlat;
    }

    public String getmTimeRange() {
        return mTimeRange;
    }

    public void setmTimeRange(String mTimeRange) {
        this.mTimeRange = mTimeRange;
    }

    public String getmPriceRange() {
        return mPriceRange;
    }

    public void setmPriceRange(String mPriceRange) {
        this.mPriceRange = mPriceRange;
    }

    public int getmArea() {
        return mArea;
    }

    public void setmArea(int mArea) {
        this.mArea = mArea;
    }

    public int getmTypeOfPlace() {
        return mTypeOfPlace;
    }

    public void setmTypeOfPlace(int mTypeOfPlace) {
        this.mTypeOfPlace = mTypeOfPlace;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(mId);
        parcel.writeString(mName);
        parcel.writeString(mUrl);
        parcel.writeString(mImageUrl);
        parcel.writeString(mAddress);
        parcel.writeDouble(mLon);
        parcel.writeDouble(mlat);
        parcel.writeString(mTimeRange);
        parcel.writeString(mPriceRange);
        parcel.writeInt(mArea);
        parcel.writeInt(mTypeOfPlace);
    }
}
