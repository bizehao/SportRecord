package com.bzh.sportrecord;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;

import com.bzh.sportrecord.data.AppDatabase;
import com.bzh.sportrecord.data.model.MessageInfo;

import javax.inject.Inject;

/**
 * @author 毕泽浩
 * @Description:
 * @time 2018/10/20 7:12
 */
public class MainAttrs {

    private MutableLiveData<Boolean> loginSign; //登录状态

    private MutableLiveData<Boolean> networkSign; //网络状态

    private MutableLiveData<String> clearZeroName; //需要清零的

    private MutableLiveData<MessageInfo> ownSendMsg; //自己发送的消息

    private LiveData<Integer> noReadCount; //未读信息条数

    public MainAttrs() {
        noReadCount = AppDatabase.getAppDatabase().messageInfoDao().getNoReadCount(false);
        if (loginSign == null) {
            loginSign = new MutableLiveData<>();
            loginSign.setValue(false);
        }
        if (networkSign == null) {
            networkSign = new MutableLiveData<>();
        }
        if (clearZeroName == null) {
            clearZeroName = new MutableLiveData<>();
        }
        if (ownSendMsg == null) {
            ownSendMsg = new MutableLiveData<>();
        }
    }

    public LiveData<Boolean> getLoginSign() {

        return loginSign;
    }

    public LiveData<Boolean> getNetworkSign() {

        return networkSign;
    }

    public LiveData<String> getClearZeroName() {
        return clearZeroName;
    }

    public LiveData<MessageInfo> getOwnSendMsg() {
        return ownSendMsg;
    }

    public void setOwnSendMsg(MessageInfo ownSendMsg) {
        this.ownSendMsg.postValue(ownSendMsg);
    }

    public void setClearZeroName(String clearZeroName) {
        this.clearZeroName.postValue(clearZeroName);
    }

    public void setLoginSign(boolean loginSign) {
        this.loginSign.setValue(loginSign);
    }

    public void setNetworkSign(boolean networkSign) {
        this.networkSign.postValue(networkSign);
    }

    public LiveData<Integer> getNoReadCount() {
        return noReadCount;
    }
}
