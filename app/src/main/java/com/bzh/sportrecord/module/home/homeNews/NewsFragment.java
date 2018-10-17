package com.bzh.sportrecord.module.home.homeNews;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.bzh.sportrecord.App;
import com.bzh.sportrecord.R;
import com.bzh.sportrecord.base.fragment.BaseFragment;
import com.bzh.sportrecord.model.Talk;
import com.bzh.sportrecord.module.talk.WebSocketChatClient;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

public class NewsFragment extends BaseFragment {

    private static final String TAG = "NewsFragment";

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
