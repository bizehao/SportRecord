package com.bzh.sportrecord.module.home.homeSport;

import android.content.Intent;
import android.util.Log;

import com.bzh.sportrecord.App;
import com.bzh.sportrecord.model.Msgs;
import com.bzh.sportrecord.model.Talk;
import com.bzh.sportrecord.module.home.homeNews.NewsFragment;
import com.bzh.sportrecord.utils.ObservableHandler;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.java_websocket.WebSocket;
import org.java_websocket.client.WebSocketClient;
import org.java_websocket.framing.Framedata;
import org.java_websocket.handshake.Handshakedata;
import org.java_websocket.handshake.ServerHandshake;
import org.java_websocket.server.WebSocketServer;

import java.net.URI;
import java.util.List;
import java.util.Map;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;

/**
 * @author 毕泽浩
 * @Description:
 * @time 2018/9/27 17:35
 */
public class WebSocketChatClient extends WebSocketClient {

    private static final String TAG = "SportFragment";

    public WebSocketChatClient(URI serverUri) {
        super(serverUri);
    }

    @Override
    public void onOpen(ServerHandshake handshakedata) {
        Talk talk = new Talk();
        talk.setCode("100");
        talk.setReceiver("服务器");
        talk.setMessage("连接服务器");
        Gson gson = new GsonBuilder().create();
        String talkJson = gson.toJson(talk, Talk.class);
        send(talkJson);
        Log.d(TAG, "WebSocket打开成功" + handshakedata.getHttpStatusMessage());
        Log.d(TAG, "WebSocket打开成功" + handshakedata.getHttpStatus());
    }

    @Override
    public void onMessage(String message) {
        Observable<String> observable = Observable.create(new ObservableOnSubscribe<String>() {
            @Override
            public void subscribe(ObservableEmitter<String> emitter) throws Exception {
                emitter.onNext(message);
            }
        });
        if (SportFragment.getObserver() != null) {
            observable.subscribe(SportFragment.getObserver());
        } else {
            Map<String, List<Msgs>> map = App.getMap();
            List<Msgs> list = map.get("100");
            list.add(new Msgs("张三", "张三" + message));
        }

        if (NewsFragment.getObserver() != null) {
            observable.subscribe(NewsFragment.getObserver());
        } else {
            Map<String, List<Msgs>> map = App.getMap();
            List<Msgs> list = map.get("200");
            list.add(new Msgs("李四", "李四" + message));
        }

    }

    @Override
    public void onClose(int code, String reason, boolean remote) {
        Log.d(TAG, "WebSocket关闭成功");
    }

    @Override
    public void onError(Exception ex) {
        Log.d(TAG, "WebSocket链接错误");
        Log.d(TAG, "" + ex.getMessage());
    }

}
