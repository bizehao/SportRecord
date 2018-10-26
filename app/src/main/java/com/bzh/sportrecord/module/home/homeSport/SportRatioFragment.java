package com.bzh.sportrecord.module.home.homeSport;

import android.os.Bundle;

import com.bzh.sportrecord.R;
import com.bzh.sportrecord.base.fragment.BaseFragment;

import javax.inject.Inject;

/**
 * @author 毕泽浩
 * @Description: //对比排行
 * @time 2018/10/25 13:08
 */
public class SportRatioFragment extends BaseFragment {

    @Inject
    public SportRatioFragment() {
    }

    @Override
    protected int getContentViewLayoutID() {
        return R.layout.fragment_sport_ratio;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {

    }
}
