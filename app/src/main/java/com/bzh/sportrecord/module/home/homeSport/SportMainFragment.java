package com.bzh.sportrecord.module.home.homeSport;

import android.os.Bundle;

import com.bzh.sportrecord.R;
import com.bzh.sportrecord.base.fragment.BaseFragment;
import com.bzh.sportrecord.module.doing.DoActivity;

import javax.inject.Inject;

import butterknife.OnClick;

/**
 * @author 毕泽浩
 * @Description: //主页面
 * @time 2018/10/25 13:08
 */
public class SportMainFragment extends BaseFragment {

    @Inject
    public SportMainFragment() {
    }

    @Override
    protected int getContentViewLayoutID() {
        return R.layout.fragment_sport_main;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {

    }

    @OnClick(R.id.fsm_begin)
    public void begin(){
        DoActivity.open(getActivity());
    }
}
