package com.example.hosiluan.foodanddrink.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Ho Si Luan on 8/20/2017.
 */

public class TypeOfPlace implements Parcelable {
    @SerializedName("id")
    @Expose
    private int id;

    @SerializedName("typeofplace")
    @Expose
    private String typeofplace;

    public TypeOfPlace(int id, String typeofplace) {
        this.id = id;
        this.typeofplace = typeofplace;
    }

    protected TypeOfPlace(Parcel in) {
        id = in.readInt();
        typeofplace = in.readString();
    }

    public static final Creator<TypeOfPlace> CREATOR = new Creator<TypeOfPlace>() {
        @Override
        public TypeOfPlace createFromParcel(Parcel in) {
            return new TypeOfPlace(in);
        }

        @Override
        public TypeOfPlace[] newArray(int size) {
            return new TypeOfPlace[size];
        }
    };

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTypeofplace() {
        return typeofplace;
    }

    public void setTypeofplace(String typeofplace) {
        this.typeofplace = typeofplace;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(id);
        parcel.writeString(typeofplace);
    }
}
