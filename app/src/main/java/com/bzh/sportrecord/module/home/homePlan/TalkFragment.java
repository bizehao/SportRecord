package com.bzh.sportrecord.module.home.homePlan;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.Bundle;
import android.os.Looper;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.view.LayoutInflater;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bzh.chatkit.commons.ImageLoader;
import com.bzh.chatkit.dialogs.DialogsList;
import com.bzh.chatkit.dialogs.DialogsListAdapter;
import com.bzh.sportrecord.App;
import com.bzh.sportrecord.MainAttrs;
import com.bzh.sportrecord.R;
import com.bzh.sportrecord.base.fragment.BaseFragment;
import com.bzh.sportrecord.data.AppDatabase;
import com.bzh.sportrecord.data.model.FriendsInfo;
import com.bzh.sportrecord.data.model.MessageInfo;
import com.bzh.sportrecord.model.Talk;
import com.bzh.sportrecord.module.login.LoginActivity;
import com.bzh.sportrecord.module.talk.WebSocketChatClient;
import com.bzh.sportrecord.module.talk.model.Dialog;
import com.bzh.sportrecord.module.talk.model.Message;
import com.bzh.sportrecord.module.talk.model.User;
import com.bzh.sportrecord.module.talk.talkMessage.MessageActivity;
import com.bzh.sportrecord.ui.widget.PageLayout;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.ByteArrayInputStream;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Collection;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

import javax.inject.Inject;

import butterknife.BindView;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public class TalkFragment extends BaseFragment {

    @BindView(R.id.dialogsList)
    DialogsList dialogsList;
    @BindView(R.id.is_online)
    FrameLayout mFrameLayout;
    @Inject
    WebSocketChatClient webSocketChatClient;
    @Inject
    Gson gson;
    @Inject
    MainAttrs mainAttrs;
    private DialogsListAdapter<Dialog> dialogsAdapter;
    private ImageLoader imageLoader;
    private Bitmap mBitmap;
    private User receiver;
    private Map<String, Dialog> dialogsMap = new LinkedHashMap<>();
    //存放当前用户的会话
    private TalkViewModel mTalkViewModel;
    private PageLayout mPageLayout;

    @Inject
    public TalkFragment() {
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mTalkViewModel = ViewModelProviders.of(this).get(TalkViewModel.class);
    }

    @Override
    protected int getContentViewLayoutID() {
        return R.layout.fragment_talk;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        mainAttrs.getLoginSign().observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(@Nullable Boolean aBoolean) {
                if (aBoolean != null && aBoolean) {
                    online();
                } else {
                    noOnline();
                }
            }
        });
    }

    //在线的处理
    public void online() {
        if (mPageLayout != null) {
            mPageLayout.hide();
        }
        mTalkViewModel.getMessageInfos().observe(this, messageInfos -> {
            if (messageInfos.size() > 0) {
                for (int i = 0; i < messageInfos.size(); i++) {
                    addDialog(messageInfos.get(i));
                }
            }
        });
        mTalkViewModel.getMessageInfoLiveData().observe(this, messageInfo -> {
            addDialog(messageInfo);
        });
        mainAttrs.getClearZeroName().observe(this, s -> {
            Dialog dialog = dialogsMap.get(s);
            if (dialog != null && dialog.getUnreadCount() != 0) {
                dialog.setUnreadCount(0);
                dialogsAdapter.updateItemById(dialog);
            }
        });
        mainAttrs.getOwnSendMsg().observe(this, messageInfo -> {
            addDialogOfOwn(messageInfo);
        });
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getActivity());
        String dialogsMapJson = sharedPreferences.getString(App.getUsername(), null);
        /*if (dialogsMapJson != null) {
            //从本地读取列表信息
            Gson gson = App.getGsonInstance();
            dialogsMap = new LinkedHashMap<>();
            JsonObject obj = new JsonParser().parse(dialogsMapJson).getAsJsonObject();
            Set<Map.Entry<String, JsonElement>> entrySet = obj.entrySet();
            for (Map.Entry<String, JsonElement> entry : entrySet) {
                String entryKey = entry.getKey();
                JsonObject value = (JsonObject) entry.getValue();
                dialogsMap.put(entryKey, gson.fromJson(value, Dialog.class));
            }
        }*/
        imageLoader = new ImageLoader() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void loadImage(ImageView imageView, @Nullable String url, @Nullable Object payload) {
                if (url == null) {
                    Glide.with(getActivity()).load(R.mipmap.no_login_user).into(imageView);
                } else {
                    Base64.Decoder decoder = Base64.getDecoder();
                    byte[] bytes = decoder.decode(url);
                    mBitmap = BitmapFactory.decodeStream(new ByteArrayInputStream(bytes));
                    Glide.with(getActivity()).load(mBitmap).into(imageView);
                }
            }
        };
        dialogsAdapter = new DialogsListAdapter<>(R.layout.item_custom_dialog, imageLoader);

        dialogsAdapter.setOnDialogClickListener(new DialogsListAdapter.OnDialogClickListener<Dialog>() {
            @Override
            public void onDialogClick(Dialog dialog) { //点击进入对话
                User user = dialog.getUsers().get(0);
                MessageActivity.open(getActivity(), user);
            }
        });
        dialogsAdapter.setOnDialogLongClickListener(new DialogsListAdapter.OnDialogLongClickListener<Dialog>() {
            @Override
            public void onDialogLongClick(Dialog dialog) { //长按对话
                showToast(dialog.getDialogName());
            }
        });
        dialogsAdapter.setOnSlidingMenuClickListener(new DialogsListAdapter.OnSlidingMenuClickListener<Dialog>() {
            @Override
            public void onSetTopClick(Dialog dialog) { //将某一项置顶
                int position = dialogsAdapter.getDialogPosition(dialog);
                dialogsAdapter.moveItem(position, 0);
            }

            @Override
            public void onDeleteClick(Dialog dialog) { //删除某一项
                dialogsAdapter.deleteById(dialog.getId());
                dialogsMap.remove(dialog.getUsers().get(0).getId());
            }
        });
        //Collection<Dialog> dialogCollection = dialogsMap.values();
        //dialogsAdapter.setItems(new ArrayList<>(dialogCollection));

        List<MessageInfo> messageInfos = new ArrayList<>();//mPlanPresenter.getMessageInfo();
        if (messageInfos.size() > 0) {
            for (int i = 0; i < messageInfos.size(); i++) {
                MessageInfo messageInfo = messageInfos.get(i);
                String id = Long.toString(UUID.randomUUID().getLeastSignificantBits());
                //addDialog(id, messageInfo.getSender(), messageInfo.getMessage(), messageInfo.getTime(), false);
            }
        }
        dialogsList.setAdapter(dialogsAdapter);
        App app = (App) getActivity().getApplication();
        webSocketChatClient.setDialogHandler(talk -> {
            mTalkViewModel.setMessageInfoLiveData(new MessageInfo(talk, false));
        });
    }

    //不在线的处理
    public void noOnline() {
        //加载出错过度页面
        mPageLayout = new PageLayout.Builder(getActivity())
                .initPage(mFrameLayout)
                .setCustomView(LayoutInflater.from(getActivity()).inflate(R.layout.layout_custom, null))
                .setOnRetryListener(new PageLayout.OnRetryClickListener() {
                    @Override
                    public void onRetry() {
                        LoginActivity.open(getActivity());
                    }
                })
                .create();
        mPageLayout.showError();
    }

    //添加会话多个
    @SuppressWarnings("CheckResult")
    public void addDialog(MessageInfo messageInfo) {
        if (!dialogsMap.containsKey(messageInfo.getSender())) {
            Observable.create((ObservableOnSubscribe<Dialog>) emitter -> {
                String id = String.valueOf(UUID.randomUUID().getLeastSignificantBits());
                FriendsInfo friendsInfo = AppDatabase.getAppDatabase().friendsInfoDao().findByUsername(messageInfo.getSender());
                receiver = new User(friendsInfo.getUsername(), friendsInfo.getRemarkname(), friendsInfo.getHeadportrait(), true);
                ArrayList<User> users = new ArrayList<>();
                users.add(receiver);
                Message message = new Message(messageInfo.getId().toString(), receiver, messageInfo.getMessage(), messageInfo.getTime());
                Dialog dialog;
                if (App.getFriend() != null && App.getFriend().equals(messageInfo.getSender())) {
                    dialog = new Dialog(id, receiver.getName(), receiver.getAvatar(), users, message, 0);
                } else {
                    int count = messageInfo.getCount() == 0 ? 1 : messageInfo.getCount();
                    dialog = new Dialog(id, receiver.getName(), receiver.getAvatar(), users, message, count);
                }
                dialogsMap.put(friendsInfo.getUsername(), dialog);
                emitter.onNext(dialog);
            }).observeOn(AndroidSchedulers.mainThread()).subscribeOn(Schedulers.io()).subscribe(dialog -> {
                dialogsAdapter.addItem(dialog);
                int position = dialogsAdapter.getDialogPosition(dialog);
                dialogsAdapter.moveItem(position, 0);
            });
        } else {
            Observable.create((ObservableOnSubscribe<Dialog>) emitter -> {
                Dialog dialog = dialogsMap.get(messageInfo.getSender());
                if (App.getFriend() != null && App.getFriend().equals(messageInfo.getSender())) {
                    dialog.setUnreadCount(0);
                } else {
                    dialog.setUnreadCount(dialog.getUnreadCount() + 1);
                }
                Message message = dialog.getLastMessage();
                message.setText(messageInfo.getMessage());
                message.setCreatedAt(messageInfo.getTime());
                emitter.onNext(dialog);
            }).observeOn(AndroidSchedulers.mainThread()).subscribeOn(Schedulers.io()).subscribe(dialog -> {
                dialogsAdapter.updateItemById(dialog);
                int position = dialogsAdapter.getDialogPosition(dialog);
                dialogsAdapter.moveItem(position, 0);
            });
        }
    }

    //添加会话自己发送
    @SuppressWarnings("CheckResult")
    public void addDialogOfOwn(MessageInfo messageInfo) {
        if (!dialogsMap.containsKey(messageInfo.getReceiver())) {
            Observable.create((ObservableOnSubscribe<Dialog>) emitter -> {
                String id = String.valueOf(UUID.randomUUID().getLeastSignificantBits());
                FriendsInfo friendsInfo = AppDatabase.getAppDatabase().friendsInfoDao().findByUsername(messageInfo.getReceiver());
                receiver = new User(friendsInfo.getUsername(), friendsInfo.getRemarkname(), friendsInfo.getHeadportrait(), true);
                ArrayList<User> users = new ArrayList<>();
                users.add(receiver);
                Message message = new Message(messageInfo.getId().toString(), receiver, messageInfo.getMessage(), messageInfo.getTime());
                Dialog dialog = new Dialog(id, receiver.getName(), receiver.getAvatar(), users, message, 0);
                ;
                dialogsMap.put(friendsInfo.getUsername(), dialog);
                emitter.onNext(dialog);
            }).observeOn(AndroidSchedulers.mainThread()).subscribeOn(Schedulers.io()).subscribe(dialog -> {
                dialogsAdapter.addItem(dialog);
                int position = dialogsAdapter.getDialogPosition(dialog);
                dialogsAdapter.moveItem(position, 0);
            });
        } else {
            Observable.create((ObservableOnSubscribe<Dialog>) emitter -> {
                Dialog dialog = dialogsMap.get(messageInfo.getReceiver());
                dialog.setUnreadCount(0);
                Message message = dialog.getLastMessage();
                message.setText(messageInfo.getMessage());
                message.setCreatedAt(messageInfo.getTime());
                emitter.onNext(dialog);
            }).observeOn(AndroidSchedulers.mainThread()).subscribeOn(Schedulers.io()).subscribe(dialog -> {
                dialogsAdapter.updateItemById(dialog);
                int position = dialogsAdapter.getDialogPosition(dialog);
                dialogsAdapter.moveItem(position, 0);
            });
        }
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        saveData();
    }

    /**
     * 保存临时数据
     */
    private void saveData() {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getActivity());
        SharedPreferences.Editor edit = sharedPreferences.edit();
        List<Dialog> dialogs = dialogsAdapter.getItems();
        Map<String, Dialog> dialogMap = new LinkedHashMap<>();
        for (Dialog dialog : dialogs) {
            dialogMap.put(dialog.getUsers().get(0).getId(), dialog);
        }
        String dialogsMapJson = gson.toJson(dialogMap);
        edit.putString(App.getUsername(), dialogsMapJson);
        edit.apply();
    }


}
