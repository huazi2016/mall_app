package com.mall.demo.bean;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import org.jetbrains.annotations.NotNull;

/**
 * time   : 2021/5/20
 * desc   :
 */
@Entity(tableName = "user")
public class UserBo {
    @PrimaryKey(autoGenerate = true)
    @NotNull
    @ColumnInfo(name = "uid")
    public int uid;
    @ColumnInfo(name = "username")
    public String username;
    @ColumnInfo(name = "password")
    public String pwd;
}
