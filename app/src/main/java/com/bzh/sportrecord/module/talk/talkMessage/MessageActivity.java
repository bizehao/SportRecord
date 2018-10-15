package com.bzh.sportrecord.module.talk.talkMessage;

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
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bzh.chatkit.commons.ImageLoader;
import com.bzh.chatkit.messages.MessageInput;
import com.bzh.chatkit.messages.MessagesList;
import com.bzh.chatkit.messages.MessagesListAdapter;
import com.bzh.sportrecord.App;
import com.bzh.sportrecord.R;
import com.bzh.sportrecord.base.activity.BaseActivity;
import com.bzh.sportrecord.greenDao.DaoSession;
import com.bzh.sportrecord.greenDao.MessageInfoDao;
import com.bzh.sportrecord.greenModel.MessageInfo;
import com.bzh.sportrecord.model.Friend;
import com.bzh.sportrecord.model.Talk;
import com.bzh.sportrecord.module.home.homePlan.PlanFragment;
import com.bzh.sportrecord.module.talk.WebSocketChatClient;
import com.bzh.sportrecord.module.talk.model.Dialog;
import com.bzh.sportrecord.module.talk.model.Message;
import com.bzh.sportrecord.module.talk.model.User;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.ByteArrayInputStream;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import butterknife.BindView;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public class MessageActivity extends BaseActivity {

    @BindView(R.id.messagesList)
    MessagesList messagesList;

    @BindView(R.id.input)
    MessageInput input;

    @BindView(R.id.message_toolbar)
    Toolbar mToolbar;

    private static final int TOTAL_MESSAGES_COUNT = 5;

    protected ImageLoader imageLoader;
    protected MessagesListAdapter<Message> messagesAdapter;
    private int selectionCount;
    private Date lastLoadedDate;

    private static Observer<Talk> observer; //观察者
    private static WebSocketChatClient webSocketChatClient; //websocket
    private Gson gson = new GsonBuilder().create(); //gson
    private Bitmap mBitmap; //对方的头像图片
    private static User receiver; //接受者(目前聊天的朋友)
    private User sender; //发送者(当前用户)
    private DaoSession daoSession;

    @Override
    protected int getContentViewLayoutID() {
        return R.layout.activity_message;
    }

    @Override
    protected void inject() {

    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void initView(Bundle savedInstanceState) {
        Intent intent = getIntent();
        User user = (User) intent.getSerializableExtra("user");
        mToolbar.setTitle(user.getName());
        setSupportActionBar(mToolbar);
        if (mToolbar != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
        receiver = user;
        sender = new User(App.getUsername(), App.getUsername(), App.getImg(), true);
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
        webSocketChatClient = App.getWebSocket();
        input.setInputListener(new MessageInput.InputListener() {//发送事件
            @Override
            public boolean onSubmit(CharSequence input) {
                String id = Long.toString(UUID.randomUUID().getLeastSignificantBits());
                Message message = new Message(id, sender, input.toString());
                messagesAdapter.addToStart(message, true);
                if (webSocketChatClient.isOpen()) {
                    Talk talk = new Talk();
                    talk.setCode("200");
                    talk.setSender(sender.getId());
                    talk.setReceiver(receiver.getId());
                    talk.setMessage(input.toString());
                    String talkJson = gson.toJson(talk, Talk.class);
                    webSocketChatClient.send(talkJson);
                }
                return true;
            }
        });
        input.setTypingListener(new MessageInput.TypingListener() {
            @Override
            public void onStartTyping() { //开始输入
            }

            @Override
            public void onStopTyping() { //停止输入
            }
        });
        input.setAttachmentsListener(new MessageInput.AttachmentsListener() {
            @Override
            public void onAddAttachments() { //点击更多按钮
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
                if (totalItemsCount < TOTAL_MESSAGES_COUNT) {
                    loadMessages();
                }
            }
        });
        messagesAdapter.registerViewClickListener(R.id.messageUserAvatar,
                new MessagesListAdapter.OnMessageViewClickListener<Message>() {
                    @Override
                    public void onMessageViewClick(View view, Message message) {
                        Toast.makeText(MessageActivity.this, message.getUser().getName() + " avatar click", Toast.LENGTH_SHORT).show();
                    }
                });
        messagesList.setAdapter(messagesAdapter);
        observer = new Observer<Talk>() {
            @Override
            public void onSubscribe(Disposable d) {
            }

            @Override
            public void onNext(Talk talk) {
                if (talk.getSender().equals(receiver.getId())) {
                    String id = Long.toString(UUID.randomUUID().getLeastSignificantBits());
                    Message message = new Message(id, receiver, talk.getMessage());
                    messagesAdapter.addToStart(message, true);
                }
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

    public static void open(Context context, User user) {
        Intent intent = new Intent(context, MessageActivity.class);
        intent.putExtra("user", user);
        context.startActivity(intent);
    }

    @Override
    protected void onStart() {
        super.onStart();

        Observable.create(new ObservableOnSubscribe<ArrayList<Message>>() {
            @Override
            public void subscribe(ObservableEmitter<ArrayList<Message>> emitter) throws Exception {
                ArrayList<Message> messageArrays = new ArrayList<>();
                daoSession = App.getDaoSession();
                MessageInfoDao messageInfoDao = daoSession.getMessageInfoDao();
                List<MessageInfo> messageInfos = messageInfoDao.queryBuilder()
                        .where(MessageInfoDao.Properties.Username.eq(App.getUsername()),
                                MessageInfoDao.Properties.ReadSign.eq(false),
                                MessageInfoDao.Properties.Sender.eq(receiver.getId()))
                        .list();
                if (messageInfos.size() > 0) {
                    for (int i = 0; i < messageInfos.size(); i++) {
                        String id = Long.toString(UUID.randomUUID().getLeastSignificantBits());
                        Message message = new Message(id, receiver, messageInfos.get(i).getMessage());
                        messageArrays.add(message);
                    }
                }
                emitter.onNext(messageArrays);
            }
        }).subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<ArrayList<Message>>() {
                    @Override
                    public void accept(ArrayList<Message> messages) throws Exception {
                        System.out.println("执行");
                        messagesAdapter.addToEnd(messages, true);
                        Observable<String> observable = Observable.create(new ObservableOnSubscribe<String>() {
                            @Override
                            public void subscribe(ObservableEmitter<String> emitter) throws Exception {
                                emitter.onNext(receiver.getId());
                            }
                        });
                        observable.subscribe(PlanFragment.getMsgObserver());
                    }
                });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
        }
        return true;
    }

    @Override
    public void onBackPressed() { //监听返回按键
        System.out.println("返回");
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
                /*ArrayList<Message> messages = MessagesFixtures.getMessages(lastLoadedDate);
                lastLoadedDate = messages.get(messages.size() - 1).getCreatedAt();
                messagesAdapter.addToEnd(messages, false);*/
            }
        }, 1000);
    }

    //获取订阅者
    public static Observer<Talk> getObserver() {
        return observer;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        observer = null;
    }

    //获取当前正在会话的用户
    public static String getReceiver(){
        return receiver.getId();
    }
}
