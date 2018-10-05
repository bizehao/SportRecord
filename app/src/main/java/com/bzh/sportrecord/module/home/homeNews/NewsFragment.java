package com.bzh.sportrecord.module.home.homeNews;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bzh.sportrecord.App;
import com.bzh.sportrecord.R;
import com.bzh.sportrecord.base.fragment.BaseFragment;
import com.bzh.sportrecord.model.Msgs;
import com.bzh.sportrecord.model.Talk;
import com.bzh.sportrecord.module.home.homeSport.WebSocketChatClient;
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

    private static Observer<String> observer;

    private static WebSocketChatClient webSocketChatClient;

    @Override
    protected int getContentViewLayoutID() {
        return R.layout.test;
    }

    @Override
    protected void initView() {
        webSocketChatClient = App.getWebSocket();
        Map<String, List<Msgs>> map = App.getMap();
        List<Msgs> list = map.get("200");
        if (list != null){
            for (Msgs msgs : list){
                textView.setText(msgs.getMessage());
            }
            list.clear();
        }
        observer = new Observer<String>() {
            @Override
            public void onSubscribe(Disposable d) {
                System.out.println("开始"+d);
            }

            @Override
            public void onNext(String s) {
                System.out.println(s);
                textView.setText(s);
            }

            @Override
            public void onError(Throwable e) {
                System.out.println(e.getMessage());
            }

            @Override
            public void onComplete() {
                System.out.println("处理完毕");
            }
        };
    }

    @Override
    protected void inject() {
    }

    @OnClick(R.id.ws_button)
    public void buttonClick() {
        String message = editText.getText().toString();
        Talk talk = new Talk();
        talk.setReceiver("李四");
        talk.setMessage(message);
        Gson gson = new GsonBuilder().create();
        String talkJson = gson.toJson(talk, Talk.class);
        webSocketChatClient.send(talkJson);
    }

    public static Observer<String> getObserver(){
        return observer;
    }

}
