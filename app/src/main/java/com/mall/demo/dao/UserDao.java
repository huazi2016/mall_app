package com.mall.demo.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import com.mall.demo.bean.UserBo;

import java.util.List;

/**
 * time   : 2021/5/20
 * desc   :
 */
@Dao
public interface UserDao {

    @Query("SELECT * FROM user")
    List<UserBo> getAll();

    @Insert
    void insertAll(UserBo... users);

    @Delete
    void delete(UserBo user);
}
