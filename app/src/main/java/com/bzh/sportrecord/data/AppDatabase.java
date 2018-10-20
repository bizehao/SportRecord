package com.bzh.sportrecord.data;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.arch.persistence.room.TypeConverters;
import android.content.Context;

import com.bzh.sportrecord.data.converter.DateConverter;
import com.bzh.sportrecord.data.dao.FriendsInfoDao;
import com.bzh.sportrecord.data.dao.MessageInfoDao;
import com.bzh.sportrecord.data.model.FriendsInfo;
import com.bzh.sportrecord.data.model.MessageInfo;

/**
 * @author 毕泽浩
 * @Description: room数据库
 * @time 2018/10/19 11:31
 */
@Database(entities = {MessageInfo.class, FriendsInfo.class}, version = 3, exportSchema = false)
@TypeConverters(DateConverter.class)
public abstract class AppDatabase extends RoomDatabase {

    private static AppDatabase appDatabase;

    //获取数据库对象
    public static AppDatabase getAppDatabase(Context context) {
        if (appDatabase == null) {
            appDatabase = Room.databaseBuilder(context.getApplicationContext(),
                    AppDatabase.class, "SportRecord")
                    .allowMainThreadQueries()
                    .build();
        }
        return appDatabase;
    }

    public abstract MessageInfoDao messageInfoDao();

    public abstract FriendsInfoDao friendsInfoDao();
}