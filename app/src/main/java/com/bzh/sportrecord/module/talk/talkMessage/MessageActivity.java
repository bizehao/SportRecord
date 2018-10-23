package com.bzh.sportrecord.module.talk.talkMessage;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bzh.chatkit.commons.ImageLoader;
import com.bzh.chatkit.messages.MessageInput;
import com.bzh.chatkit.messages.MessagesList;
import com.bzh.chatkit.messages.MessagesListAdapter;
import com.bzh.sportrecord.App;
import com.bzh.sportrecord.MainAttrs;
import com.bzh.sportrecord.R;
import com.bzh.sportrecord.base.activity.BaseActivity;
import com.bzh.sportrecord.data.AppDatabase;
import com.bzh.sportrecord.data.model.MessageInfo;
import com.bzh.sportrecord.model.Talk;
import com.bzh.sportrecord.module.home.homePlan.TalkFragment;
import com.bzh.sportrecord.module.talk.WebSocketChatClient;
import com.bzh.sportrecord.module.talk.model.Message;
import com.bzh.sportrecord.module.talk.model.User;
import com.google.gson.Gson;

import java.io.ByteArrayInputStream;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import javax.inject.Inject;

import butterknife.BindView;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public class MessageActivity extends BaseActivity {

    @BindView(R.id.messagesList)
    MessagesList messagesList;

    @BindView(R.id.input)
    MessageInput input;

    @BindView(R.id.message_toolbar)
    Toolbar mToolbar;

    @Inject
    Gson gson;

    @Inject
    WebSocketChatClient webSocketChatClient;

    @Inject
    MainAttrs mainAttrs;

    private MessageViewModel messageViewModel;
    protected ImageLoader imageLoader;
    protected MessagesListAdapter<Message> messagesAdapter;
    private int selectionCount;
    private Date lastLoadedDate;
    private Bitmap mBitmap; //对方的头像图片
    private User friend; //接受者(目前聊天的朋友)
    private User own; //发送者(当前用户)

    @Override
    protected int getContentViewLayoutID() {
        return R.layout.activity_message;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void initView(Bundle savedInstanceState) {
        Intent intent = getIntent();
        User user = (User) intent.getSerializableExtra("user");
        mToolbar.setTitle(user.getName());
        App.setFriend(user.getId());
        setSupportActionBar(mToolbar);
        if (mToolbar != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
        friend = user;
        own = new User(App.getUsername(), App.getUsername(), App.getImg(), true);
        messageViewModel = ViewModelProviders.of(this).get(MessageViewModel.class);
        messageViewModel.getMessages().observe(this, messageInfos -> {
            if (messageInfos.size() > 0) {
                for (MessageInfo messageInfo : messageInfos) {
                    messageHandler(messageInfo);
                }
            }
        });
        messageViewModel.getNoReadMessages().observe(this, messageInfo -> {
            messageHandler(messageInfo);
        });
        imageLoader = new ImageLoader() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override //设置头像
            public void loadImage(ImageView imageView, @Nullable String url, @Nullable Object payload) {
                if (url == null) {
                    Glide.with(MessageActivity.this).load(R.mipmap.no_login_user).into(imageView);
                } else {
                    if (mBitmap == null) {
                        //解码
                        Base64.Decoder decoder = Base64.getDecoder();
                        byte[] bytes = decoder.decode(url);
                        mBitmap = BitmapFactory.decodeStream(new ByteArrayInputStream(bytes));
                    }
                    Glide.with(MessageActivity.this).load(mBitmap).into(imageView);
                }
            }
        };
        messagesAdapter = new MessagesListAdapter<>(App.getUsername(), imageLoader);//App.getUsername()
        App app = (App) getApplication();
        input.setInputListener(new MessageInput.InputListener() {//发送事件
            @SuppressWarnings("CheckResult")
            @Override
            public boolean onSubmit(CharSequence input) {
                if (webSocketChatClient.isOpen()) {
                    String id = Long.toString(UUID.randomUUID().getLeastSignificantBits());
                    Long talkID = UUID.randomUUID().getLeastSignificantBits();
                    Date time = new Date(System.currentTimeMillis());
                    Message message = new Message(id, own, input.toString(), time);
                    messagesAdapter.addToStart(message, true);
                    Talk talk = new Talk();
                    talk.setCode("200");
                    talk.setId(talkID);
                    talk.setSender(own.getId());
                    talk.setReceiver(friend.getId());
                    talk.setMessage(input.toString());
                    talk.setTime(time);
                    String talkJson = gson.toJson(talk, Talk.class);
                    webSocketChatClient.send(talkJson);
                    //自己发送的消息存入数据库
                    MessageInfo messageInfo = new MessageInfo(talk, true);
                    AppDatabase.getAppDatabase().messageInfoDao().insert(messageInfo);
                    mainAttrs.setOwnSendMsg(messageInfo);
                }
                return true;
            }
        });


        messagesAdapter.enableSelectionMode(new MessagesListAdapter.SelectionListener() {
            @Override
            public void onSelectionChanged(int count) { //长按信息框
                selectionCount = count;
            }
        });
        messagesAdapter.setLoadMoreListener(new MessagesListAdapter.OnLoadMoreListener() {
            @Override
            public void onLoadMore(int page, int totalItemsCount) { //下拉加载更多
                loadMessages();

            }
        });
        messagesList.setAdapter(messagesAdapter);
        webSocketChatClient.setMessagehandler(talk -> {
            messageViewModel.setNoReadMessages(new MessageInfo(talk, false));
        });
    }

    public static void open(Context context, User user) {
        Intent intent = new Intent(context, MessageActivity.class);
        intent.putExtra("user", user);
        context.startActivity(intent);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                //finish();
                break;
        }
        return true;
    }

    @Override
    public void onBackPressed() { //监听返回按键
        if (selectionCount == 0) {
            super.onBackPressed();
        } else {
            messagesAdapter.unselectAllItems(); //取消所有选择的信息
        }
    }

    protected void loadMessages() { //下拉加载更多的处理
        new Handler().postDelayed(new Runnable() { //imitation of internet connection
            @Override
            public void run() {
            }
        }, 1000);
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        App.setFriend(null);
    }

    @SuppressWarnings("CheckResult")
    public void messageHandler(MessageInfo messageInfo) {
        Observable.create((ObservableOnSubscribe<Message>) emitter -> {
            Message message;
            if (App.getFriend() != null && messageInfo.getSender().equals(friend.getId())) {
                mainAttrs.setClearZeroName(friend.getId());
                if (!messageInfo.isReadSign()) {
                    messageInfo.setReadSign(true); //更改读取状态
                    AppDatabase.getAppDatabase().messageInfoDao().update(messageInfo);
                }
                message = new Message(messageInfo.getId().toString(), friend, messageInfo.getMessage(), messageInfo.getTime());
            } else {
                message = new Message(messageInfo.getId().toString(), own, messageInfo.getMessage(), messageInfo.getTime());
            }
            emitter.onNext(message);
        }).subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(message -> {
                    messagesAdapter.addToStart(message, true);
                });
    }

}
