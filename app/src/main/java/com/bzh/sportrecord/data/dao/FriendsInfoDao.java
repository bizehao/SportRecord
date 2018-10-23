package com.bzh.sportrecord.data.dao;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;
import android.support.annotation.NonNull;

import com.bzh.sportrecord.data.model.FriendsInfo;

import java.util.List;

/**
 * @author 毕泽浩
 * @Description:
 * @time 2018/10/19 11:39
 */
@Dao
public interface FriendsInfoDao {
    @Query("select * from friendsinfo")
    LiveData<List<FriendsInfo>> getAll();

    @Query("select * from friendsinfo")
    List<FriendsInfo> loadAll();

    @Query("select * from friendsinfo where username = :username")
    FriendsInfo findByUsername(String username);

    @Update
    void update(FriendsInfo... friendsInfo);

    @Update
    void update(List<FriendsInfo> friendsInfo);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(FriendsInfo... friendsInfo);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(List<FriendsInfo> friendsInfo);

    @Delete
    void delete(FriendsInfo... friendsInfo);

    @Delete
    void delete(List<FriendsInfo> friendsInfo);
}
