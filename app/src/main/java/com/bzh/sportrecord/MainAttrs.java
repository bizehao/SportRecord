package com.bzh.sportrecord;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;

/**
 * @author 毕泽浩
 * @Description:
 * @time 2018/10/20 7:12
 */
public class MainAttrs {

    private MutableLiveData<Boolean> loginSign; //登录状态

    private MutableLiveData<Boolean> networkSign; //网络状态

    public MutableLiveData<Boolean> getLoginSign() {
        if(loginSign == null){
            loginSign = new MutableLiveData<>();
            loginSign.setValue(false);
        }
        return loginSign;
    }

    public MutableLiveData<Boolean> getNetworkSign() {
        if(networkSign == null){
            networkSign = new MutableLiveData<>();
        }
        return networkSign;
    }

    public void setLoginSign(boolean loginSign) {
        this.loginSign.setValue(loginSign);
    }

    public void setNetworkSign(boolean networkSign) {
        this.networkSign.setValue(networkSign);
    }
}
