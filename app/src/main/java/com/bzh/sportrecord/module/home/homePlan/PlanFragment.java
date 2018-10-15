package com.bzh.sportrecord.module.home.homePlan;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bzh.chatkit.commons.ImageLoader;
import com.bzh.chatkit.dialogs.DialogsList;
import com.bzh.chatkit.dialogs.DialogsListAdapter;
import com.bzh.sportrecord.App;
import com.bzh.sportrecord.R;
import com.bzh.sportrecord.base.fragment.BaseFragment;
import com.bzh.sportrecord.greenDao.DaoSession;
import com.bzh.sportrecord.greenDao.FriendsInfoDao;
import com.bzh.sportrecord.greenDao.MessageInfoDao;
import com.bzh.sportrecord.greenModel.FriendsInfo;
import com.bzh.sportrecord.greenModel.MessageInfo;
import com.bzh.sportrecord.model.Talk;
import com.bzh.sportrecord.module.talk.model.Dialog;
import com.bzh.sportrecord.module.talk.model.Message;
import com.bzh.sportrecord.module.talk.model.User;
import com.bzh.sportrecord.module.talk.talkMessage.MessageActivity;

import java.io.ByteArrayInputStream;
import java.util.ArrayList;
import java.util.Base64;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;

import butterknife.BindView;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;

public class PlanFragment extends BaseFragment {

    @BindView(R.id.dialogsList)
    DialogsList dialogsList;

    private DialogsListAdapter<Dialog> dialogsAdapter;
    private ImageLoader imageLoader;
    private Bitmap mBitmap;
    private static Observer<Talk> observer; //观察者
    private static Consumer<String> msgConsumer; //消息清空观察者
    private User receiver;
    private Map<String, Dialog> dialogsMap = new HashMap<>(); //存放当前用户的会话
    private DaoSession daoSession;

    @Override
    protected int getContentViewLayoutID() {
        return R.layout.fragment_talk;
    }

    @Override
    protected void initView() {
        //从数据库里获取未读消息
        daoSession = App.getDaoSession();
        MessageInfoDao messageInfoDao = daoSession.getMessageInfoDao();

        List<MessageInfo> messageInfos = messageInfoDao.queryBuilder()
                .where(MessageInfoDao.Properties.Username.eq(App.getUsername()), MessageInfoDao.Properties.ReadSign.eq(false))
                .list();

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
                MessageActivity.open(Objects.requireNonNull(getActivity()), user);
                dialog.setUnreadCount(0);
                dialogsAdapter.updateItemById(dialog);
            }
        });
        dialogsAdapter.setOnDialogLongClickListener(new DialogsListAdapter.OnDialogLongClickListener<Dialog>() {
            @Override
            public void onDialogLongClick(Dialog dialog) { //长按对话
                System.out.println("长按对话框");
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

        if (messageInfos.size() > 0) {
            for (int i = 0; i < messageInfos.size(); i++) {
                MessageInfo messageInfo = messageInfos.get(i);
                String id = Long.toString(UUID.randomUUID().getLeastSignificantBits());
                if (!dialogsMap.containsKey(messageInfo.getSender())) {
                    FriendsInfoDao friendsInfoDao = daoSession.getFriendsInfoDao();
                    FriendsInfo friendsInfo = friendsInfoDao.queryBuilder().where(FriendsInfoDao.Properties.Username.eq(messageInfo.getSender())).unique();
                    receiver = new User(friendsInfo.getUsername(), friendsInfo.getRemarkname(), friendsInfo.getHeadportrait(), true);
                    ArrayList<User> users = new ArrayList<>();
                    users.add(receiver);
                    Message message = new Message(id, receiver, messageInfo.getMessage());
                    Dialog dialog = new Dialog(id, receiver.getName(), receiver.getAvatar(), users, message, 1);
                    dialogsMap.put(messageInfo.getSender(), dialog);
                    dialogsAdapter.addItem(dialog);


                } else {
                    Dialog dialog = dialogsMap.get(messageInfo.getSender());
                    dialog.setUnreadCount(dialog.getUnreadCount() + 1);
                    Message message = dialog.getLastMessage();
                    message.setText(messageInfo.getMessage());
                    dialogsAdapter.updateItemById(dialog);
                }
            }
        }

        dialogsList.setAdapter(dialogsAdapter);

        observer = new Observer<Talk>() { //观察者
            @Override
            public void onSubscribe(Disposable d) {
            }

            @Override
            public void onNext(Talk talk) {
                String id = Long.toString(UUID.randomUUID().getLeastSignificantBits());
                if (!dialogsMap.containsKey(talk.getSender())) {
                    FriendsInfoDao dao = daoSession.getFriendsInfoDao();
                    FriendsInfo friendsInfo = dao.queryBuilder().where(FriendsInfoDao.Properties.Username.eq(talk.getSender())).unique();
                    receiver = new User(friendsInfo.getUsername(), friendsInfo.getRemarkname(), friendsInfo.getHeadportrait(), true);
                    ArrayList<User> users = new ArrayList<>();
                    users.add(receiver);
                    Message message = new Message(id, receiver, talk.getMessage());
                    Dialog dialog = new Dialog(id, receiver.getName(), receiver.getAvatar(), users, message, 1);
                    dialogsMap.put(talk.getSender(), dialog);
                    dialogsAdapter.addItem(dialog);
                } else {
                    Dialog dialog = dialogsMap.get(talk.getSender());
                    dialog.setUnreadCount(dialog.getUnreadCount() + 1);
                    Message message = dialog.getLastMessage();
                    message.setText(talk.getMessage());
                    dialogsAdapter.updateItemById(dialog);
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

        msgConsumer = new Consumer<String>() { //清空
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

    @Override
    protected void inject() {

    }

    //获取订阅者
    public static Observer<Talk> getObserver() {
        return observer;
    }

    //获取订阅者
    public static Consumer<String> getMsgObserver() {
        return msgConsumer;
    }


}
