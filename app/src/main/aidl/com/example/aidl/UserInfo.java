package com.example.aidl;

import android.os.Parcel;
import android.os.Parcelable;

public class UserInfo implements Parcelable{

    private String name;
    private String adress;
    private int  age;


    public static final Creator<UserInfo> CREATOR = new Creator<UserInfo>() {
        @Override
        public UserInfo createFromParcel(Parcel in) {
            UserInfo userInfo=new UserInfo();
            userInfo.setName(in.readString());
            userInfo.setAdress(in.readString());
            userInfo.setAge(in.readInt());
            return userInfo;
        }

        @Override
        public UserInfo[] newArray(int size) {
            return new UserInfo[size];
        }
    };

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAdress() {
        return adress;
    }

    public void setAdress(String adress) {
        this.adress = adress;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeString(adress);
        dest.writeInt(age);
    }


}
