package com.bzh.sportrecord.module.home.homePlan;

import android.support.design.widget.FloatingActionButton;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bzh.sportrecord.R;
import com.bzh.sportrecord.base.fragment.BaseFragment;
import com.bzh.sportrecord.module.talk.fixtures.DialogsFixtures;
import com.bzh.sportrecord.module.talk.model.Dialog;
import com.bzh.sportrecord.module.talk.talkFriends.FriendsActivity;
import com.bzh.sportrecord.module.talk.talkMessage.MessageActivity;
import com.stfalcon.chatkit.commons.ImageLoader;
import com.stfalcon.chatkit.dialogs.DialogsList;
import com.stfalcon.chatkit.dialogs.DialogsListAdapter;

import java.util.Objects;

import butterknife.BindView;

public class PlanFragment extends BaseFragment implements
        DialogsListAdapter.OnDialogClickListener<Dialog>,
        DialogsListAdapter.OnDialogLongClickListener<Dialog> {

    @BindView(R.id.dialogsList)
    DialogsList dialogsList;

    private DialogsListAdapter<Dialog> dialogsAdapter;
    private ImageLoader imageLoader;

    @Override
    protected int getContentViewLayoutID() {
        return R.layout.fragment_talk;
    }

    @Override
    protected void initView() {
        imageLoader = new ImageLoader() {
            @Override
            public void loadImage(ImageView imageView, String url) {
                System.out.println("加载图片");
                Glide.with(getActivity()).load(url).into(imageView);
            }
        };
        dialogsAdapter = new DialogsListAdapter<>(R.layout.item_custom_dialog, imageLoader);
        //dialogsAdapter = new DialogsListAdapter<>(imageLoader);
        dialogsAdapter.setItems(DialogsFixtures.getDialogs());
        dialogsAdapter.setOnDialogClickListener(this);
        dialogsAdapter.setOnDialogLongClickListener(this);
        dialogsList.setAdapter(dialogsAdapter);
    }

    @Override
    protected void inject() {

    }

    @Override //点击进入对话
    public void onDialogClick(Dialog dialog) {
        MessageActivity.open(Objects.requireNonNull(getActivity()));
    }

    @Override //长按对话
    public void onDialogLongClick(Dialog dialog) {
        System.out.println("长按对话框");
        showToast(dialog.getDialogName());
    }

    @Override
    public void onResume() {
        super.onResume();
        FloatingActionButton floatingActionButton = getActivity().findViewById(R.id.home_float_button);
        Glide.with(getActivity()).load(ContextCompat.getDrawable(getActivity(),R.mipmap.friends)).into(floatingActionButton);
        floatingActionButton.setOnClickListener(new View.OnClickListener() { //悬浮按钮点击跳转到好友列表
            @Override
            public void onClick(View v) {
                FriendsActivity.open(getActivity());
            }
        });
    }
}
