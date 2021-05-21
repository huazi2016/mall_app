package com.mall.demo.dao;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.mall.demo.bean.MallBo;
import com.mall.demo.bean.UserBo;

/**
 * time   : 2021/5/20
 * desc   :
 */
@Database(entities = {UserBo.class, MallBo.class}, version = 1, exportSchema = false)
public abstract class AppDatabase extends RoomDatabase {
    public abstract UserDao userDao();
    public abstract MallDao mallDao();
}
