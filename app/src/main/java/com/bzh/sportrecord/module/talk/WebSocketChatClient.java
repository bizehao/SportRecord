package com.bzh.sportrecord.module.talk;

import android.os.Handler;
import android.util.Log;
import com.bzh.sportrecord.App;
import com.bzh.sportrecord.greenDao.FriendsInfoHandler;
import com.bzh.sportrecord.greenDao.MessageInfoHandler;
import com.bzh.sportrecord.greenModel.FriendsInfo;
import com.bzh.sportrecord.greenModel.MessageInfo;
import com.bzh.sportrecord.model.Talk;
import com.google.gson.Gson;
import org.java_websocket.client.WebSocketClient;
import org.java_websocket.handshake.ServerHandshake;
import java.net.URI;

/**
 * @author 毕泽浩
 * @Description:
 * @time 2018/9/27 17:35
 */
public class WebSocketChatClient extends WebSocketClient {

    private static final String TAG = "SportFragment";

    private Gson gson;

    public WebSocketChatClient(URI serverUri) {
        super(serverUri);
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

        switch (talk.getCode()) {
            case "200":
                //消息存储到数据库
                MessageInfo messageInfo = new MessageInfo(talk.getId(), talk.getSender(),
                        talk.getReceiver(), talk.getTime(), talk.getMessage(), false);
                MessageInfoHandler.insert(messageInfo);
                break;
            case "300":
                //将头像更新数据库中
                FriendsInfo friendsInfo = FriendsInfoHandler.selectByUsername(talk.getSender());
                friendsInfo.setHeadportrait(talk.getMessage());
                FriendsInfoHandler.update(friendsInfo);
                break;
        }

    }

    @Override
    public void onClose(int code, String reason, boolean remote) {
        Log.d(TAG, "WebSocket关闭成功");
        WebSocketChatClient ws = App.getWebSocket();
        if (App.getLoginSign()) {
            new Thread(ws::reconnect).start();
        }
    }

    @Override
    public void onError(Exception ex) {
        Log.d(TAG, "WebSocket链接错误");
        Log.d(TAG, "" + ex.getMessage());
    }

    public void df(){

    }

}
