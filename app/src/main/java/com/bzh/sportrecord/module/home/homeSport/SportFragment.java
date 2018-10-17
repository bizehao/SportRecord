package com.bzh.sportrecord.module.home.homeSport;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.bzh.sportrecord.R;
import com.bzh.sportrecord.base.fragment.BaseFragment;

import butterknife.BindView;
import butterknife.OnClick;

public class SportFragment extends BaseFragment {

    private static final String TAG = "SportFragment";

    @BindView(R.id.ws_edittext)
    EditText editText;

    @BindView(R.id.ws_button)
    Button button;

    @BindView(R.id.ws_textview)
    TextView textView;

    @Override
    protected int getContentViewLayoutID() {
        return R.layout.test;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {

    }

    @Override
    protected void inject() {
    }

    @OnClick(R.id.ws_button)
    public void buttonClick() {

    }

}
