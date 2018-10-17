package com.bzh.sportrecord.module.home.homePlan;

import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.view.LayoutInflater;
import android.view.animation.Animation;
import android.widget.ImageView;
import com.bumptech.glide.Glide;
import com.bzh.chatkit.commons.ImageLoader;
import com.bzh.chatkit.dialogs.DialogsList;
import com.bzh.chatkit.dialogs.DialogsListAdapter;
import com.bzh.sportrecord.App;
import com.bzh.sportrecord.R;
import com.bzh.sportrecord.base.fragment.BaseFragment;
import com.bzh.sportrecord.greenModel.FriendsInfo;
import com.bzh.sportrecord.greenModel.MessageInfo;
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
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import javax.inject.Inject;
import butterknife.BindView;
import io.reactivex.functions.Consumer;

public class PlanFragment extends BaseFragment implements PlanContract.View {

    @BindView(R.id.dialogsList)
    DialogsList dialogsList;

    @Inject
    PlanContract.Presenter mPlanPresenter;

    private DialogsListAdapter<Dialog> dialogsAdapter;
    private ImageLoader imageLoader;
    private Bitmap mBitmap;
    private static Consumer<Talk> lastMsgObserver; //观察者
    private static Consumer<String> msgCountConsumer; //消息清空观察者
    private User receiver;
    private Map<String, Dialog> dialogsMap; //存放当前用户的会话

    private PageLayout mPageLayout;

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
        if (App.getLoginSign()) {
            dialogsMap = new LinkedHashMap<>();
            SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getActivity());
            String dialogsMapJson = sharedPreferences.getString(App.getUsername(), null);
            System.out.println(dialogsMapJson);
            if (dialogsMapJson != null) {
                //从本地读取列表信息
                Gson gson = App.getGsonInstance();
                dialogsMap = new LinkedHashMap<>();
                JsonObject obj = new JsonParser().parse(dialogsMapJson).getAsJsonObject();
                Set<Map.Entry<String, JsonElement>> entrySet = obj.entrySet();
                for (Map.Entry<String, JsonElement> entry : entrySet) {
                    String entryKey = entry.getKey();
                    System.out.println(entryKey);
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

            List<MessageInfo> messageInfos = mPlanPresenter.getMessageInfo();
            if (messageInfos.size() > 0) {
                for (int i = 0; i < messageInfos.size(); i++) {
                    MessageInfo messageInfo = messageInfos.get(i);
                    String id = Long.toString(UUID.randomUUID().getLeastSignificantBits());
                    addDialog(id, messageInfo.getSender(), messageInfo.getMessage(), messageInfo.getTime(), false);
                }
            }
            dialogsList.setAdapter(dialogsAdapter);
            initLastMsgObserver();
            initMsgCountObserver();
        } else {
            //加载出错过度页面
            mPageLayout = new PageLayout.Builder(getActivity())
                    .initPage(getActivity().findViewById(R.id.ll_default))
                    .setCustomView(LayoutInflater.from(getActivity()).inflate(R.layout.layout_custom, null))
                    .setOnRetryListener(new PageLayout.OnRetryClickListener() {
                        @Override
                        public void onRetry() {
                            getActivity().finish();
                            LoginActivity.open(getActivity());

                            /*new Handler().post(new Runnable() {
                                @Override
                                public void run() {
                                    mPageLayout.hide();
                                }
                            });*/
                        }
                    })
                    .create();
            mPageLayout.showError();
        }
    }

    @Override
    public Animation onCreateAnimation(int transit, boolean enter, int nextAnim) {
        return super.onCreateAnimation(transit, enter, nextAnim);
    }

    //初始化更新或添加last消息的观察者
    public void initLastMsgObserver() {
        lastMsgObserver = new Consumer<Talk>() {
            @Override
            public void accept(Talk talk) throws Exception {
                String id = Long.toString(UUID.randomUUID().getLeastSignificantBits());
                if (talk.getSender().equals(App.getUsername())) {
                    addDialog(id, talk.getReceiver(), talk.getMessage(), talk.getTime(), true);
                } else {
                    addDialog(id, talk.getSender(), talk.getMessage(), talk.getTime(), false);
                }
            }
        };
    }

    //初始化清空未读消息条数的观察者
    public void initMsgCountObserver() {
        msgCountConsumer = new Consumer<String>() {
            @Override
            public void accept(String name) throws Exception {
                if (dialogsMap.containsKey(name)) {
                    Dialog dialog = dialogsMap.get(name);
                    dialog.setUnreadCount(0);
                    dialogsAdapter.updateItemById(dialog);
                }
            }
        };
    }

    //获取更新或添加last消息的观察者
    public static Consumer<Talk> getLastMsgObserver() {
        return lastMsgObserver;
    }

    //获取清空未读消息条数的观察者
    public static Consumer<String> getMsgObserver() {
        return msgCountConsumer;
    }

    //添加会话
    public void addDialog(String id, String friendName, String messsage, Date time, boolean isOneself) {
        Dialog dialog;
        if (!dialogsMap.containsKey(friendName)) {
            FriendsInfo friendsInfo = mPlanPresenter.getFriendsInfo(friendName);
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
            dialogsAdapter.addItem(dialog);
        } else {
            dialog = dialogsMap.get(friendName);
            if (isOneself) {
                dialog.setUnreadCount(dialog.getUnreadCount());
            } else {
                dialog.setUnreadCount(dialog.getUnreadCount() + 1);
            }
            Message message = dialog.getLastMessage();
            message.setText(messsage);
            message.setCreatedAt(time);
            dialogsAdapter.updateItemById(dialog);
        }
        int position = dialogsAdapter.getDialogPosition(dialog);
        dialogsAdapter.moveItem(position, 0);
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
        System.out.println("保存数据");
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
