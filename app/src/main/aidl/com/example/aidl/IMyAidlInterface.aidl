// IMyAidlInterface.aidl
package com.example.aidl;

import com.example.aidl.UserInfo;

// Declare any non-default types here with import statements

interface IMyAidlInterface {


    int  add(int i,int j);
    String getUserInfo(in UserInfo userinfo);
    void   getaList(out String[] list);
    void   setaList(in String[] list);
    void   gettList(inout String[] list);

    /**
     * Demonstrates some basic types that you can use as parameters
     * and return values in AIDL.
     */
    void basicTypes(int anInt, long aLong, boolean aBoolean, float aFloat,
            double aDouble, String aString);
}
