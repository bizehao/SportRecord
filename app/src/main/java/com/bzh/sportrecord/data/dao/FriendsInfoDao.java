package com.bzh.sportrecord.data.dao;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;
import com.bzh.sportrecord.data.model.FriendsInfo;

/**
 * @author 毕泽浩
 * @Description:
 * @time 2018/10/19 11:39
 */
@Dao
public interface FriendsInfoDao {
    @Query("select * from friendsinfo")
    LiveData<FriendsInfo> getAll();

    @Query("select * from friendsinfo where id = :id")
    FriendsInfo findById(Long id);

    @Query("select * from friendsinfo where username = :username")
    FriendsInfo findByUsername(String username);

    @Update
    void update(FriendsInfo friendsInfo);

    @Insert
    void insert(FriendsInfo friendsInfo);

    @Delete
    void delete(FriendsInfo friendsInfo);
}
