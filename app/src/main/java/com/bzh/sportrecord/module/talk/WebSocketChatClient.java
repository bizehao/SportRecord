package com.bzh.sportrecord.module.talk;

import android.util.Log;

import com.bzh.sportrecord.App;
import com.bzh.sportrecord.greenDao.DaoSession;
import com.bzh.sportrecord.greenModel.MessageInfo;
import com.bzh.sportrecord.model.Talk;
import com.bzh.sportrecord.module.home.homePlan.PlanFragment;
import com.bzh.sportrecord.module.talk.talkMessage.MessageActivity;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.java_websocket.client.WebSocketClient;
import org.java_websocket.handshake.ServerHandshake;

import java.net.URI;
import java.util.List;
import java.util.Map;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * @author 毕泽浩
 * @Description:
 * @time 2018/9/27 17:35
 */
public class WebSocketChatClient extends WebSocketClient {

    private static final String TAG = "SportFragment";

    private static Gson gson;

    private Observable<Talk> observable;

    public WebSocketChatClient(URI serverUri) {
        super(serverUri);
    }

    @Override
    public void onOpen(ServerHandshake handshakedata) {
        gson = new GsonBuilder().create();
        Talk talk = new Talk();
        talk.setCode("100");
        talk.setSender(App.getUsername());
        talk.setReceiver("服务器");
        talk.setMessage("连接服务器成功");
        String talkJson = gson.toJson(talk, Talk.class);
        send(talkJson);
    }

    @Override
    public void onMessage(String message) {
        Talk talk = gson.fromJson(message,Talk.class);
        observable = Observable.create(new ObservableOnSubscribe<Talk>() {
            @Override
            public void subscribe(ObservableEmitter<Talk> emitter) throws Exception {
                emitter.onNext(talk);
            }
        });

        switch (talk.getCode()){
            case "200":
                if(MessageActivity.getObserver() != null && MessageActivity.getReceiver().equals(talk.getSender())){ //消息推送(直接到消息框)
                    observable.subscribeOn(Schedulers.newThread())
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribe(MessageActivity.getObserver());
                }else {
                    if(PlanFragment.getObserver() != null){ //会话显示(未读条数)
                        observable.subscribeOn(Schedulers.newThread())
                                .observeOn(AndroidSchedulers.mainThread())
                                .subscribe(PlanFragment.getObserver());
                    }
                }
                //存储到数据库
                MessageInfo messageInfo = new MessageInfo();
                messageInfo.setId(null);
                messageInfo.setUsername(talk.getReceiver());
                messageInfo.setSender(talk.getSender());
                messageInfo.setDateTime(talk.getTime());
                messageInfo.setMessage(talk.getMessage());
                messageInfo.setReadSign(false);
                DaoSession daoSession = App.getDaoSession();
                daoSession.getMessageInfoDao().insert(messageInfo);
                break;
        }

        /*Map<String, List<Talk>> map = App.getMap();
        List<Talk> list = map.get("200");
        list.add(talk);*/
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
