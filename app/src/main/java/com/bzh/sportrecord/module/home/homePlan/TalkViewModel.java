package com.bzh.sportrecord.module.home.homePlan;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import com.bzh.sportrecord.data.AppDatabase;
import com.bzh.sportrecord.data.model.MessageInfo;

import java.util.List;

/**
 * @author 毕泽浩
 * @Description:
 * @time 2018/10/19 22:33
 */
public class TalkViewModel extends ViewModel {

    private MutableLiveData<List<MessageInfo>> messageInfos; //首次进入加载的会话
    private MutableLiveData<MessageInfo> messageInfoLiveData; //运行中聊天的会话

    public TalkViewModel() {
        if (messageInfos == null) {
            messageInfos = new MutableLiveData<>();
            messageInfos.setValue(AppDatabase.getAppDatabase().messageInfoDao().findAllNoRead(false));
        }
        if (messageInfoLiveData == null) {
            messageInfoLiveData = new MutableLiveData<>();
        }
    }


    public LiveData<List<MessageInfo>> getMessageInfos() {

        return messageInfos;
    }

    public LiveData<MessageInfo> getMessageInfoLiveData() {

        return messageInfoLiveData;
    }

    public void setMessageInfoLiveData(MessageInfo messageInfo) {
        this.messageInfoLiveData.postValue(messageInfo);
    }

    @Override
    protected void onCleared() {
        super.onCleared();
    }
}
