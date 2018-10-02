package com.bzh.sportrecord.module.talk.talkMessage;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.Toast;
import com.bumptech.glide.Glide;
import com.bzh.sportrecord.R;
import com.bzh.sportrecord.base.activity.BaseActivity;
import com.bzh.sportrecord.module.talk.fixtures.MessagesFixtures;
import com.bzh.sportrecord.module.talk.model.Message;
import com.stfalcon.chatkit.commons.ImageLoader;
import com.stfalcon.chatkit.messages.MessageInput;
import com.stfalcon.chatkit.messages.MessagesList;
import com.stfalcon.chatkit.messages.MessagesListAdapter;
import java.util.ArrayList;
import java.util.Date;

import butterknife.BindView;

public class MessageActivity extends BaseActivity
        implements MessageInput.InputListener, MessageInput.AttachmentsListener, MessageInput.TypingListener,
        MessagesListAdapter.SelectionListener, MessagesListAdapter.OnLoadMoreListener{

    @BindView(R.id.messagesList)
    MessagesList messagesList;

    @BindView(R.id.input)
    MessageInput input;

    @BindView(R.id.message_toolbar)
    Toolbar mToolbar;

    private static final int TOTAL_MESSAGES_COUNT = 100;

    protected final String senderId = "0";
    protected ImageLoader imageLoader;
    protected MessagesListAdapter<Message> messagesAdapter;
    private int selectionCount;
    private Date lastLoadedDate;

    @Override
    protected int getContentViewLayoutID() {
        return R.layout.activity_message;
    }

    @Override
    protected void inject() {

    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        mToolbar.setTitle("我的消息");
        setSupportActionBar(mToolbar);
        if (mToolbar != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
        imageLoader = new ImageLoader() {
            @Override
            public void loadImage(ImageView imageView, String url) {
                Glide.with(MessageActivity.this).load(url).into(imageView);
            }
        };
        input.setInputListener(this);
        input.setTypingListener(this);
        input.setAttachmentsListener(this);
        messagesAdapter = new MessagesListAdapter<>(senderId, imageLoader);
        messagesAdapter.enableSelectionMode(this);
        messagesAdapter.setLoadMoreListener(this);
        messagesAdapter.registerViewClickListener(R.id.messageUserAvatar,
                new MessagesListAdapter.OnMessageViewClickListener<Message>() {
                    @Override
                    public void onMessageViewClick(View view, Message message) {
                        Toast.makeText(MessageActivity.this, message.getUser().getName() + " avatar click", Toast.LENGTH_SHORT).show();
                    }
                });
        messagesList.setAdapter(messagesAdapter);
    }

    public static void open(Context context) {
        context.startActivity(new Intent(context, MessageActivity.class));
    }

    @Override
    public boolean onSubmit(CharSequence input) {//点击提交按钮发送信息
        System.out.println("发送");
        messagesAdapter.addToStart(MessagesFixtures.getTextMessage(input.toString()), true);
        return true;
    }

    @Override
    public void onAddAttachments() {//点击更多按钮

    }

    @Override
    public void onStartTyping() { //开始输入
    }

    @Override
    public void onStopTyping() { //停止输入
    }

    @Override
    public void onLoadMore(int page, int totalItemsCount) { //下拉加载更多
        if (totalItemsCount < TOTAL_MESSAGES_COUNT) {
            loadMessages();
        }
    }

    @Override
    public void onSelectionChanged(int count) { //长按信息框
        this.selectionCount = count;
    }

    @Override
    protected void onStart() {
        super.onStart();
        messagesAdapter.addToStart(MessagesFixtures.getTextMessage(), true);
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
                ArrayList<Message> messages = MessagesFixtures.getMessages(lastLoadedDate);
                lastLoadedDate = messages.get(messages.size() - 1).getCreatedAt();
                messagesAdapter.addToEnd(messages, false);
            }
        }, 1000);
    }
}
