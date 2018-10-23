package com.bzh.sportrecord.module.home.homeNews;

import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.TextView;

import com.bzh.sportrecord.R;
import com.bzh.sportrecord.base.fragment.BaseFragment;
import com.bzh.sportrecord.module.home.HomeViewModel;

import javax.inject.Inject;

import butterknife.BindView;

public class NewsFragment extends BaseFragment {

    private static final String TAG = "NewsFragment";
    private HomeViewModel mHomeViewModel;
    @BindView(R.id.fragmentTwo)
    TextView mMapView;

    @Inject
    public NewsFragment() {
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mHomeViewModel = ViewModelProviders.of(getActivity()).get(HomeViewModel.class);
        mHomeViewModel.getmCurrentName().observe(this,s -> {
            mMapView.setText(s);
        });
    }

    @Override
    protected int getContentViewLayoutID() {
        return R.layout.fragment_map;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {

    }

}
