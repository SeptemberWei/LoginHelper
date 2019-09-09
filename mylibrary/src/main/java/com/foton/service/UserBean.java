package com.foton.service;

import android.os.Parcel;
import android.os.Parcelable;

public class UserBean implements Parcelable {
    public String userName;

    public UserBean() {

    }

    public UserBean(Parcel in) {
        this.userName = in.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.userName);
    }

    public void readFromParcel(Parcel in){
        this.userName = in.readString();
    }

    public static final Creator<UserBean> CREATOR = new Creator<UserBean>() {
        @Override
        public UserBean createFromParcel(Parcel in) {

            return new UserBean(in);
        }

        @Override
        public UserBean[] newArray(int size) {
            return new UserBean[size];
        }
    };

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }
}
