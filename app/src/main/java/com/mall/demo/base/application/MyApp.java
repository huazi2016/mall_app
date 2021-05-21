package com.mall.demo.base.application;

import androidx.appcompat.app.AppCompatDelegate;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.blankj.utilcode.util.SPUtils;
import com.blankj.utilcode.util.Utils;
import com.mall.demo.base.utils.Constant;
import com.kingja.loadsir.core.LoadSir;
import com.mall.demo.base.callback.ErrorCallback;
import com.mall.demo.dao.AppDatabase;
import com.tencent.mmkv.MMKV;

import org.litepal.LitePal;
import org.litepal.LitePalApplication;

/**
 * Created with Android Studio.
 * Description: Base Application
 *
 * @author: Wangjianxian
 * @date: 2019/12/18
 * Time: 21:26
 */
public class MyApp extends LitePalApplication {

    private static MyApp mContext;
    public static AppDatabase room;

    @Override
    public void onCreate() {
        super.onCreate();
        mContext = this;
        LitePal.initialize(this);
        Utils.init(this);
        initMode();
        //初始化轻量级存储框架-mmkv
        MMKV.initialize(getApplicationContext());
        LoadSir.beginBuilder()
                .addCallback(new ErrorCallback())
                .commit();
        initDataBase();
        room = Room.databaseBuilder(getApplicationContext(), AppDatabase.class, "my_db").build();
    }

    /**
     * init Room
     */
    private void initDataBase() {
        Room.databaseBuilder(getApplicationContext(), AppDatabase.class,
                "database-name").build();
    }

    private void initMode() {
        boolean isNightMode = SPUtils.getInstance(Constant.CONFIG_SETTINGS).getBoolean
                (Constant.KEY_NIGHT_MODE, false);
        AppCompatDelegate.setDefaultNightMode(isNightMode ? AppCompatDelegate.MODE_NIGHT_YES :
                AppCompatDelegate.MODE_NIGHT_NO);
    }

    public static synchronized MyApp getContext(){
        return mContext;
    }
}
