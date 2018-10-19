package com.bzh.sportrecord.greenDao;

import com.bzh.sportrecord.App;
import com.bzh.sportrecord.greenModel.FriendsInfo;
import com.bzh.sportrecord.greenModel.MessageInfo;

import org.greenrobot.greendao.query.WhereCondition;

import java.util.List;

/**
 * @author 毕泽浩
 * @Description: FriendsInfo表的处理
 * @time 2018/10/18 9:19
 */
public class FriendsInfoHandler {

    private static FriendsInfoDao friendsInfoDao;

    //初始化
    private static void init(){
        if(friendsInfoDao == null){
            friendsInfoDao = App.getDaoSession().getFriendsInfoDao();
        }
    }

    //增添
    public static void insert(FriendsInfo friendsInfo){
        init();
        friendsInfoDao.insert(friendsInfo);
    }

    //更新
    public static void update(FriendsInfo friendsInfo){
        init();
        friendsInfoDao.update(friendsInfo);
    }

    //查询通过用户名
    public static FriendsInfo selectByUsername(String username){
        return friendsInfoDao.queryBuilder().where(FriendsInfoDao.Properties.Username.eq(username)).unique();
    }

    //查询单个通过条件
    public static FriendsInfo selectByCondition(WhereCondition cond, WhereCondition... condMore){
        init();
        return friendsInfoDao.queryBuilder()
                .where(cond,condMore)
                .unique();
    }

}
