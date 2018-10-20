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
import com.bzh.sportrecord.R;
import com.bzh.sportrecord.base.fragment.BaseFragment;
import com.bzh.sportrecord.data.AppDatabase;
import com.bzh.sportrecord.data.model.FriendsInfo;
import com.bzh.sportrecord.data.model.MessageInfo;
import com.bzh.sportrecord.model.Talk;
import com.bzh.sportrecord.module.login.LoginActivity;
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

    private DialogsListAdapter<Dialog> dialogsAdapter;
    private ImageLoader imageLoader;
    private Bitmap mBitmap;
    private static Consumer<MessageInfo> lastMsgObserver; //添加消息更新消息观察者
    private static Consumer<MessageInfo> msgCountConsumer; //消息清空观察者
    private User receiver;
    private Map<String, Dialog> dialogsMap; //存放当前用户的会话
    private TalkViewModel mTalkViewModel;
    private PageLayout mPageLayout;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mTalkViewModel = ViewModelProviders.of(this).get(TalkViewModel.class);
        mTalkViewModel.getMessageInfoLiveData().observe(this, messageInfo -> {
            String id = Long.toString(UUID.randomUUID().getLeastSignificantBits());
            if (messageInfo.getSender().equals(App.getUsername())) {
                addDialog(id, messageInfo.getReceiver(), messageInfo.getMessage(), messageInfo.getTime(), true);
            } else {
                addDialog(id, messageInfo.getSender(), messageInfo.getMessage(), messageInfo.getTime(), false);
            }
        });
    }

    @Override
    protected int getContentViewLayoutID() {
        return R.layout.fragment_talk;
    }

    @Override
    protected void inject() {
        fragmentComponent.inject(this);
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        App.getMainAttrs().getLoginSign().observe(this, new Observer<Boolean>() {
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

    /*@Subscribe(threadMode = ThreadMode.MAIN, sticky = true)
    public void onMessageEvent(Talk talk) {
        System.out.println("执行一次");
        String id = Long.toString(UUID.randomUUID().getLeastSignificantBits());
        if (talk.getSender().equals(App.getUsername())) {
            addDialog(id, talk.getReceiver(), talk.getMessage(), talk.getTime(), true);
        } else {
            addDialog(id, talk.getSender(), talk.getMessage(), talk.getTime(), false);
        }
        EventBus.getDefault().removeStickyEvent(Talk.class);
    }*/

    //在线的处理
    public void online() {
        if (mPageLayout != null) {
            mPageLayout.hide();
        }
        dialogsMap = new LinkedHashMap<>();
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getActivity());
        String dialogsMapJson = sharedPreferences.getString(App.getUsername(), null);
        if (dialogsMapJson != null) {
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
        }
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
            }
        });
        Collection<Dialog> dialogCollection = dialogsMap.values();
        dialogsAdapter.setItems(new ArrayList<>(dialogCollection));

        List<MessageInfo> messageInfos = new ArrayList<>();//mPlanPresenter.getMessageInfo();
        if (messageInfos.size() > 0) {
            for (int i = 0; i < messageInfos.size(); i++) {
                MessageInfo messageInfo = messageInfos.get(i);
                String id = Long.toString(UUID.randomUUID().getLeastSignificantBits());
                addDialog(id, messageInfo.getSender(), messageInfo.getMessage(), messageInfo.getTime(), false);
            }
        }
        dialogsList.setAdapter(dialogsAdapter);
        App app = (App) getActivity().getApplication();
        app.getWebSocket().setDialogHandler(talk -> {
            mTalkViewModel.setMessageInfoLiveData(new MessageInfo(talk));
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

    //初始化更新或添加last消息的观察者
    public void initLastMsgObserver() {
        lastMsgObserver = new Consumer<MessageInfo>() {
            @Override
            public void accept(MessageInfo messageInfo) throws Exception {
                String id = Long.toString(UUID.randomUUID().getLeastSignificantBits());
                if (messageInfo.getSender().equals(App.getUsername())) {
                    addDialog(id, messageInfo.getReceiver(), messageInfo.getMessage(), messageInfo.getTime(), true);
                } else {
                    addDialog(id, messageInfo.getSender(), messageInfo.getMessage(), messageInfo.getTime(), false);
                }
            }
        };
    }

    //初始化清空未读消息条数的观察者
    public void initMsgCountObserver() {
        msgCountConsumer = new Consumer<MessageInfo>() {
            @Override
            public void accept(MessageInfo messageInfo) throws Exception {
                if (dialogsMap.containsKey(messageInfo.getSender())) {
                    Dialog dialog = dialogsMap.get(messageInfo.getSender());
                    int val = dialog.getUnreadCount() - 1;
                    if (val >= 0) {
                        dialog.setUnreadCount(val);
                    } else {
                        dialog.setUnreadCount(0);
                    }
                    dialogsAdapter.updateItemById(dialog);
                }
            }
        };
    }

    //获取更新或添加last消息的观察者
    public static Consumer<MessageInfo> getLastMsgObserver() {
        return lastMsgObserver;
    }

    //获取清空未读消息条数的观察者
    public static Consumer<MessageInfo> getMsgObserver() {
        return msgCountConsumer;
    }

    //添加会话
    @SuppressWarnings("CheckResult")
    public void addDialog(String id, String friendName, String messsage, Date time, boolean isOneself) {
        if (!dialogsMap.containsKey(friendName)) {
            Observable.create((ObservableOnSubscribe<Dialog>) emitter -> {
                Dialog dialog;
                FriendsInfo friendsInfo = new FriendsInfo(); //mPlanPresenter.getFriendsInfo(friendName);
                receiver = new User(friendsInfo.getUsername(), friendsInfo.getRemarkname(), friendsInfo.getHeadportrait(), true);
                ArrayList<User> users = new ArrayList<>();
                users.add(receiver);
                Message message = new Message(id, receiver, messsage, time);
                if (isOneself) {
                    dialog = new Dialog(id, receiver.getName(), receiver.getAvatar(), users, message, 0);
                } else {
                    dialog = new Dialog(id, receiver.getName(), receiver.getAvatar(), users, message, 1);
                }
                dialogsMap.put(friendName, dialog);
                emitter.onNext(dialog);
            }).observeOn(AndroidSchedulers.mainThread()).subscribeOn(Schedulers.io()).subscribe(dialog -> {
                dialogsAdapter.addItem(dialog);
                int position = dialogsAdapter.getDialogPosition(dialog);
                dialogsAdapter.moveItem(position, 0);
            });
        } else {
            Observable.create((ObservableOnSubscribe<Dialog>) emitter -> {
                Dialog dialog = dialogsMap.get(friendName);
                if (isOneself) {
                    dialog.setUnreadCount(dialog.getUnreadCount());
                } else {
                    dialog.setUnreadCount(dialog.getUnreadCount() + 1);
                }
                Message message = dialog.getLastMessage();
                message.setText(messsage);
                message.setCreatedAt(time);
                emitter.onNext(dialog);
            }).observeOn(AndroidSchedulers.mainThread()).subscribeOn(Schedulers.io()).subscribe(dialog -> {
                System.out.println(dialog);
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
        Gson gson = App.getGsonInstance();
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
