package com.bzh.sportrecord.module.home;

import android.arch.lifecycle.LiveData;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.wifi.WifiManager;
import android.util.Log;

import java.lang.ref.WeakReference;
import java.util.WeakHashMap;

import timber.log.Timber;

/**
 * @author 毕泽浩
 * @Description:
 * @time 2018/10/19 14:38
 */
public class HomeLiveData extends LiveData<Integer> {
    private static HomeLiveData homeLiveData;
    private WeakReference<Context> mContextWeakReference;

    private HomeLiveData(Context context) {
        mContextWeakReference = new WeakReference<>(context);
    }

    public static HomeLiveData getInstance(Context context) {
        if (homeLiveData == null) {
            homeLiveData = new HomeLiveData(context);
        }
        return homeLiveData;
    }

    @Override
    protected void onActive() {
        super.onActive();
        registerReceiver();
    }

    @Override
    protected void onInactive() {
        super.onInactive();
        unregisterReceiver();
    }

    private void registerReceiver() {
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(WifiManager.RSSI_CHANGED_ACTION);
        mContextWeakReference.get().registerReceiver(mReceiver, intentFilter);
    }

    private void unregisterReceiver() {
        mContextWeakReference.get().unregisterReceiver(mReceiver);
    }

    private BroadcastReceiver mReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            Timber.d(action);
            if (WifiManager.RSSI_CHANGED_ACTION.equals(action)) {
                int wifiRssi = intent.getIntExtra(WifiManager.EXTRA_NEW_RSSI, -200);
                int wifiLevel = WifiManager.calculateSignalLevel(
                        wifiRssi, 4);
                homeLiveData.setValue(wifiLevel);
            }
        }
    };
}
