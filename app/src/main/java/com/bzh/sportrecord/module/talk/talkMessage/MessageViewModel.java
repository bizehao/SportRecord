package com.bzh.sportrecord.module.talk.talkMessage;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;

import com.bzh.sportrecord.App;
import com.bzh.sportrecord.data.AppDatabase;
import com.bzh.sportrecord.data.model.MessageInfo;

import java.util.List;

/**
 * @author 毕泽浩
 * @Description:
 * @time 2018/10/21 11:57
 */
public class MessageViewModel extends ViewModel {
    private MutableLiveData<List<MessageInfo>> messages; //所有消息
    private MutableLiveData<MessageInfo> noReadMessages; //未读消息

    public MessageViewModel() {
        if (messages == null) {
            this.messages = new MutableLiveData<>();
            this.messages.setValue(AppDatabase.getAppDatabase().messageInfoDao().findByFriend(App.getFriend()));
        }
        if (noReadMessages == null) {
            this.noReadMessages = new MutableLiveData<>();
        }
    }

    public LiveData<List<MessageInfo>> getMessages() {
        return messages;
    }

    public LiveData<MessageInfo> getNoReadMessages() {
        return noReadMessages;
    }

    public void setNoReadMessages(MessageInfo noReadMessages) {
        this.noReadMessages.postValue(noReadMessages);
    }
}
