package com.mall.demo.base.application;

import android.app.Application;

import com.tencent.mmkv.MMKV;

/**
 * Created with Android Studio.
 * Description: Base Application
 *
 * @author: Wangjianxian
 * @date: 2019/12/18
 * Time: 21:26
 */
public class MyApp extends Application {

    private static MyApp mContext;

    @Override
    public void onCreate() {
        super.onCreate();
        mContext = this;
        //初始化轻量级存储框架-mmkv
        MMKV.initialize(getApplicationContext());
    }

    public static synchronized MyApp getContext(){
        return mContext;
    }
}
