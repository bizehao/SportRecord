package com.bzh.sportrecord.data.dao;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;
import com.bzh.sportrecord.data.model.MessageInfo;

/**
 * @author 毕泽浩
 * @Description:
 * @time 2018/10/19 11:23
 */
@Dao
public interface MessageInfoDao {

    @Query("select * from messageinfo")
    LiveData<MessageInfo> getAll();

    @Query("select * from messageinfo where id = :id")
    MessageInfo findById(Long id);

    @Query("select * from messageinfo where sender = :sender")
    MessageInfo findBySender(String sender);

    @Query("select * from messageinfo where receiver = :receiver")
    MessageInfo findByReceiver(String receiver);

    @Update
    void update(MessageInfo messageInfo);

    @Insert
    void insert(MessageInfo messageInfo);

    @Delete
    void delete(MessageInfo messageInfo);
}
