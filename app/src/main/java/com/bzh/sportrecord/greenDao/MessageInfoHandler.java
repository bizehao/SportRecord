package com.bzh.sportrecord.greenDao;

import android.widget.LinearLayout;

import com.bzh.sportrecord.App;
import com.bzh.sportrecord.greenModel.MessageInfo;

import org.greenrobot.greendao.query.WhereCondition;

import java.util.List;

/**
 * @author 毕泽浩
 * @Description: MessageInfo表的处理
 * @time 2018/10/18 9:25
 */
public class MessageInfoHandler {

    private static MessageInfoDao messageInfoDao;
    private static AddPostProcessing addPostProcessing;
    private static UpdatePostProcessing updatePostProcessing;

    public static void setAddPostProcessing(AddPostProcessing addPostProcessing) {
        MessageInfoHandler.addPostProcessing = addPostProcessing;
    }

    public static void setUpdatePostProcessing(UpdatePostProcessing updatePostProcessing) {
        MessageInfoHandler.updatePostProcessing = updatePostProcessing;
    }

    //初始化
    private static void init(){
        if(messageInfoDao == null){
            messageInfoDao = App.getDaoSession().getMessageInfoDao();
        }
    }

    //增添
    public static void insert(MessageInfo messageInfo){
        init();
        messageInfoDao.insert(messageInfo);
    }

    //增添 回调
    public static void insertProcessing(MessageInfo messageInfo){
        init();
        messageInfoDao.insert(messageInfo);
        if(addPostProcessing != null){
            addPostProcessing.run(messageInfo);
        }
    }

    //查询通过id
    public static MessageInfo selectById(Long id){
        init();
        return messageInfoDao.load(id);
    }

    //查询多个通过条件
    public static List<MessageInfo> selectByCondition(WhereCondition cond, WhereCondition... condMore){
        init();
        return messageInfoDao.queryBuilder()
                .where(cond,condMore)
                .list();
    }

    //更新
    public static void update(MessageInfo messageInfo){
        init();
        messageInfoDao.update(messageInfo);
    }

    //更新 回调
    public static void updateProcessing(MessageInfo messageInfo){
        init();
        messageInfoDao.update(messageInfo);
        if(updatePostProcessing != null){
            updatePostProcessing.run(messageInfo);
        }
    }

    //回调接口 增添信息
    public interface AddPostProcessing{
        void run(MessageInfo messageInfo);
    }

    //回调接口 更新信息
    public interface UpdatePostProcessing{
        void run(MessageInfo messageInfo);
    }
}
