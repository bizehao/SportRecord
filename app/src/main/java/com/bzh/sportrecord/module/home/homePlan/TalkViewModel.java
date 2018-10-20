package com.bzh.sportrecord.module.home.homePlan;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;

import com.bzh.sportrecord.data.model.MessageInfo;
import com.bzh.sportrecord.module.talk.WebSocketChatClient;

/**
 * @author 毕泽浩
 * @Description:
 * @time 2018/10/19 22:33
 */
public class TalkViewModel extends ViewModel {

    private MutableLiveData<MessageInfo> messageInfoLiveData;

    public MutableLiveData<MessageInfo> getMessageInfoLiveData() {
        if(messageInfoLiveData == null){
            messageInfoLiveData = new MutableLiveData<>();
        }
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
