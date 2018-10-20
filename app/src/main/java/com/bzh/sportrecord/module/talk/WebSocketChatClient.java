package com.bzh.sportrecord.module.talk;

import android.content.Context;
import android.os.Handler;
import android.util.Log;

import com.bzh.sportrecord.App;
import com.bzh.sportrecord.data.AppDatabase;
import com.bzh.sportrecord.data.model.FriendsInfo;
import com.bzh.sportrecord.data.model.MessageInfo;
import com.bzh.sportrecord.model.Talk;
import com.google.gson.Gson;

import org.java_websocket.client.WebSocketClient;
import org.java_websocket.handshake.ServerHandshake;

import java.net.URI;

import timber.log.Timber;

/**
 * @author 毕泽浩
 * @Description:
 * @time 2018/9/27 17:35
 */
public class WebSocketChatClient extends WebSocketClient {

    private static final String TAG = "SportFragment";

    private Context context;

    private Gson gson;

    private DialogHandler dialogHandler; //会话处理

    private MessageHandler messagehandler; //消息处理

    public WebSocketChatClient(URI serverUri) {
        super(serverUri);
    }

    public WebSocketChatClient(Context context,URI serverUri) {
        this(serverUri);
        this.context = context;
    }

    @Override
    public void onOpen(ServerHandshake handshakedata) {
        System.out.println("webSocket连接成功");
        gson = App.getGsonInstance();
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
        Talk talk = gson.fromJson(message, Talk.class);
        System.out.println("=================");
        System.out.println(talk);
        switch (talk.getCode()) {
            case "200":
                //消息存储到数据库
                MessageInfo messageInfo = new MessageInfo(talk);
                AppDatabase database = AppDatabase.getAppDatabase(context);
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
        App app = (App)context;
        WebSocketChatClient ws = app.getWebSocket();
        if (App.getMainAttrs().getLoginSign().getValue() != null && App.getMainAttrs().getLoginSign().getValue()) {
            new Thread(ws::reconnect).start();
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
