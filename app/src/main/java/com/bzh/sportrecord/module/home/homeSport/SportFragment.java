package com.bzh.sportrecord.module.home.homeSport;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;

import com.bzh.sportrecord.R;
import com.bzh.sportrecord.base.fragment.BaseFragment;

import javax.inject.Inject;

import butterknife.BindView;

public class SportFragment extends BaseFragment {

    @BindView(R.id.sport_tab)
    TabLayout mTabLayout;

    @BindView(R.id.sport_pager)
    ViewPager mViewPager;

    @Inject
    public SportCensusFragment censusFragment;

    @Inject
    public SportMainFragment mainFragment;

    @Inject
    public SportRatioFragment ratioFragment;

    private Fragment[] fragments = new Fragment[3];
    private String[] fragmentNames = new String[3];

    @Inject
    public SportFragment() {
    }

    @Override
    protected int getContentViewLayoutID() {
        return R.layout.fragment_sport;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        fragments[0] = censusFragment;
        fragments[1] = mainFragment;
        fragments[2] = ratioFragment;
        fragmentNames[0] = "统计";
        fragmentNames[1] = "主页";
        fragmentNames[2] = "排行榜";
        FragmentManager manager = getChildFragmentManager();
        mViewPager.setAdapter(new FragmentPagerAdapter(manager) {
            @Override
            public Fragment getItem(int i) {
                return fragments[i];
            }

            @Override
            public int getCount() {
                return fragments.length;
            }

            @Nullable
            @Override
            public CharSequence getPageTitle(int position) {
                return fragmentNames[position];
            }
        });
        mViewPager.setCurrentItem(1);
        mTabLayout.setupWithViewPager(mViewPager);
    }

}
