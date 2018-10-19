package com.bzh.sportrecord.module.home;

import android.arch.lifecycle.DefaultLifecycleObserver;
import android.arch.lifecycle.LifecycleOwner;
import android.support.annotation.NonNull;

import timber.log.Timber;

/**
 * @author 毕泽浩
 * @Description:
 * @time 2018/10/19 13:26
 */
public class test implements DefaultLifecycleObserver {

    @Override
    public void onStart(@NonNull LifecycleOwner owner) {
        System.out.println("你好啊");
        Timber.d("测试一下");
    }
}
