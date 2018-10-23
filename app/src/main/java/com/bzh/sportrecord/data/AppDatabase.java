package com.bzh.sportrecord.data;

import android.arch.persistence.db.SupportSQLiteDatabase;
import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.arch.persistence.room.TypeConverters;
import android.arch.persistence.room.migration.Migration;
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
@Database(entities = {MessageInfo.class, FriendsInfo.class}, version = 1, exportSchema = false)
@TypeConverters(DateConverter.class)
public abstract class AppDatabase extends RoomDatabase {

    private static AppDatabase appDatabase;

    //获取数据库对象
    public static void initAppDatabase(Context context) {
        if (appDatabase == null) {
            appDatabase = Room.databaseBuilder(context.getApplicationContext(),
                    AppDatabase.class, "SportRecord")
                    //添加数据库变动迁移
                    //.addMigrations(AppDatabase.MIGRATION_3_4)
                    //允许在主线程中执行操作
                    .allowMainThreadQueries()
                    .build();
        }
    }

    /**
     * 获取数据库实例
     * @return
     */
    public static AppDatabase getAppDatabase(){
        return appDatabase;
    }


    //数据库变动添加Migration
    public static final Migration MIGRATION_3_4 = new Migration(3, 4) {
        @Override
        public void migrate(SupportSQLiteDatabase database) {
            //数据库的具体变动，我是在之前的user表中添加了新的column，名字是age。
            //类型是integer，不为空，默认值是0
            database.execSQL("ALTER TABLE user "
                    + " ADD COLUMN age INTEGER NOT NULL DEFAULT 0");
        }
    };

    public abstract MessageInfoDao messageInfoDao();

    public abstract FriendsInfoDao friendsInfoDao();
}
