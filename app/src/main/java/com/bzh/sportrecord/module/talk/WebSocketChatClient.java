package com.bzh.sportrecord.module.talk;

import android.content.Context;
import android.os.Handler;
import android.util.Log;

import com.bzh.sportrecord.App;
import com.bzh.sportrecord.MainAttrs;
import com.bzh.sportrecord.data.AppDatabase;
import com.bzh.sportrecord.data.model.FriendsInfo;
import com.bzh.sportrecord.data.model.MessageInfo;
import com.bzh.sportrecord.model.Talk;
import com.google.gson.Gson;

import org.java_websocket.client.WebSocketClient;
import org.java_websocket.handshake.ServerHandshake;

import java.net.URI;

import javax.inject.Inject;

import timber.log.Timber;

/**
 * @author 毕泽浩
 * @Description:
 * @time 2018/9/27 17:35
 */
public class WebSocketChatClient extends WebSocketClient {

    private static final String TAG = "SportFragment";

    private MainAttrs mainAttrs;

    private Gson gson;

    private DialogHandler dialogHandler; //会话处理

    private MessageHandler messagehandler; //消息处理

    public WebSocketChatClient(URI serverUri,Gson gson,MainAttrs mainAttrs) {
        super(serverUri);
        this.gson = gson;
        this.mainAttrs = mainAttrs;
    }

    @Override
    public void onOpen(ServerHandshake handshakedata) {
        System.out.println("webSocket连接成功");
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
        System.out.println(message);
        Talk talk = gson.fromJson(message, Talk.class);
        System.out.println(talk);
        switch (talk.getCode()) {
            case "200":
                //消息存储到数据库
                MessageInfo messageInfo = new MessageInfo(talk,false);
                AppDatabase database = AppDatabase.getAppDatabase();
                database.messageInfoDao().insert(messageInfo);

                //会话处理
                if (dialogHandler != null) {
                    dialogHandler.handler(talk);
                } else {
                    Timber.d("会话界面未初始化");
                }
                //消息处理
                if (messagehandler != null) {
                    messagehandler.handler(talk);
                } else {
                    Timber.d("消息界面未初始化");
                }
                break;
            case "300":
                //将头像更新数据库中
                /*FriendsInfo friendsInfo = FriendsInfoHandler.selectByUsername(talk.getSender());
                friendsInfo.setHeadportrait(talk.getMessage());
                FriendsInfoHandler.update(friendsInfo);*/
                break;
        }

    }

    @Override
    public void onClose(int code, String reason, boolean remote) {
        Log.d(TAG, "WebSocket关闭成功");
        System.out.println("关闭哈哈");
        if (mainAttrs.getLoginSign().getValue() != null && mainAttrs.getLoginSign().getValue()) {
            System.out.println("执行重试连接");
            new Thread(this::reconnect).start();
        }
    }

    @Override
    public void onError(Exception ex) {
        Log.d(TAG, "WebSocket链接错误");
        Log.d(TAG, "" + ex.getMessage());
    }

    public void setDialogHandler(DialogHandler dialogHandler) {
        this.dialogHandler = dialogHandler;
    }

    public void setMessagehandler(MessageHandler messagehandler) {
        this.messagehandler = messagehandler;
    }

    //会话处理
    public interface DialogHandler {
        void handler(Talk talk);
    }

    //消息处理
    public interface MessageHandler {
        void handler(Talk talk);
    }

}
