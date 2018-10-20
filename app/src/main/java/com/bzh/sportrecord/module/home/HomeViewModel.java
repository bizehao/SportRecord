package com.bzh.sportrecord.module.home;

import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;

import java.util.List;

import timber.log.Timber;

/**
 * @author 毕泽浩
 * @Description:
 * @time 2018/10/19 14:01
 */
public class HomeViewModel extends ViewModel {
    // Create a LiveData with a String
    private MutableLiveData<String> mCurrentName;

    public MutableLiveData<String> getmCurrentName() {
        if (mCurrentName == null) {
            mCurrentName = new MutableLiveData<>();
        }
        return mCurrentName;
    }

    public void setmCurrentName(String value){
        if(mCurrentName != null){
            mCurrentName.setValue(value);
        }
    }

    @Override
    protected void onCleared() {
        super.onCleared();

    }
}
