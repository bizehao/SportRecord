package com.bzh.sportrecord.data.dao;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.bzh.sportrecord.data.model.MessageInfo;

import java.util.List;

/**
 * @author 毕泽浩
 * @Description:
 * @time 2018/10/19 11:23
 */
@Dao
public interface MessageInfoDao {

    @Query("select * from messageinfo")
    LiveData<List<MessageInfo>> getAll();

    @Query("select * from messageinfo")
    List<MessageInfo> loadAll();

    @Query("select * from messageinfo where receiver = :friend or sender = :friend order by time")
    List<MessageInfo> findByFriend(String friend);

    @Query("select * from messageinfo where id = :id")
    MessageInfo findById(Long id);

    @Query("select * from messageinfo where sender = :sender and readSign = :readSign")
    LiveData<List<MessageInfo>> findBySender(String sender, boolean readSign);

    @Query("select * from messageinfo where receiver = :receiver")
    MessageInfo findByReceiver(String receiver);

    @Query("select * from messageinfo a inner join (select max(time) time, count(*) count from messageinfo where readSign = :readSign GROUP BY sender) b \n" +
            "on a.time = b.time")
    List<MessageInfo> findAllNoRead(boolean readSign);

    @Update
    void update(MessageInfo... messageInfo);

    @Update
    void update(List<MessageInfo> messageInfo);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(MessageInfo... messageInfo);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(List<MessageInfo> messageInfo);

    @Delete
    void delete(MessageInfo... messageInfo);

    @Delete
    void delete(List<MessageInfo> messageInfo);
}
