package com.mall.demo.bean;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

/**
 * time   : 2021/5/20
 * desc   :
 */
@Entity(tableName = "mall")
public class MallBo {
    @PrimaryKey
    public int mid;
    @ColumnInfo(name = "username")
    public String username;
    @ColumnInfo(name = "name")
    public String name;
    @ColumnInfo(name = "subtitle")
    public String subtitle;
    @ColumnInfo(name = "img")
    public String img;
    @ColumnInfo(name = "price")
    public String price;
    @ColumnInfo(name = "order")
    public String order;
    @ColumnInfo(name = "content")
    public String content;
}
