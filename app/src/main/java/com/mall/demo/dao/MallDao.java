package com.mall.demo.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.mall.demo.bean.MallBo;

import java.util.List;

/**
 * time   : 2021/5/20
 * desc   :
 */
@Dao
public interface MallDao {

    @Query("SELECT * FROM mall")
    List<MallBo> getAllInfo();

    @Query("SELECT * FROM mall WHERE username IN (:usernames)")
    List<MallBo> loadAllByUserNames(String[] usernames);

    //@Update(entity = MallBo.class)
    //void updateState(String username, String order);

    @Insert
    void insertAll(MallBo... malls);

    @Delete
    void delete(MallBo mall);

}
