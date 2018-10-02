package com.bzh.sportrecord.module.talk.talkFriends;

import android.content.Context;
import android.content.Intent;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.bzh.sportrecord.R;
import com.bzh.sportrecord.base.activity.BaseActivity;
import com.bzh.sportrecord.model.Friend;
import com.bzh.sportrecord.module.talk.talkMessage.MessageActivity;
import com.bzh.sportrecord.ui.CustomDiaFrag;
import com.bzh.sportrecord.ui.adapter.FriendsRecycleViewAdapter;
import com.bzh.sportrecord.ui.widget.FriendsDiaFrag;
import com.bzh.sportrecord.ui.widget.SideLetterBar;
import com.bzh.sportrecord.utils.PinyinUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import butterknife.BindView;

public class FriendsActivity extends BaseActivity {

    @BindView(R.id.friends_toolbar)
    Toolbar mToolbar;

    @BindView(R.id.friend_recycle_view)
    RecyclerView mRecyclerView;

    @BindView(R.id.tv_letter_overlay)
    TextView letter;

    @BindView(R.id.side_letter_bar)
    SideLetterBar sideLetterBar;

    @Override
    protected int getContentViewLayoutID() {
        return R.layout.activity_friends;
    }

    @Override
    protected void inject() {

    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        mToolbar.setTitle("我的联系人");
        mToolbar.setOverflowIcon(ContextCompat.getDrawable(this,R.mipmap.menu_add));
        setSupportActionBar(mToolbar);
        if (mToolbar != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
        List<Friend> friends = new ArrayList<>();
        String name;
        for (int i = 0; i < 20; i++) {
            if (i < 5) {
                name = "张三";
            } else if (i < 10) {
                name = "李四";
            } else if (i < 15) {
                name = "王五";
            } else {
                name = "阿毛";
            }

            friends.add(new Friend(name + i, PinyinUtils.getPinYin(name)));
        }
        Collections.sort(friends, new Comparator<Friend>() {
            @Override
            public int compare(Friend o1, Friend o2) {
                return o1.getPinyin().compareTo(o2.getPinyin());
            }
        });
        FriendsRecycleViewAdapter adapter = new FriendsRecycleViewAdapter(friends, this);
        adapter.setListener(new FriendsRecycleViewAdapter.evenClickListener() {
            @Override
            public void setOnClickListener(View view) {
                showToast("跳转到消息界面");
            }

            @Override
            public void setOnLongClickListener(View view) {
                showToast("暂时没思路");
            }
        });
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.setAdapter(adapter);
        sideLetterBar.setOverlay(letter);
        sideLetterBar.setOnLetterChangedListener(new SideLetterBar.OnLetterChangedListener() {  //右边字母序列时间
            @Override
            public void onLetterChanged(String letter) {
                int position = adapter.getLetterPosition(letter);
                layoutManager.scrollToPositionWithOffset(position, 0);
                layoutManager.setStackFromEnd(true);
            }
        });
    }

    //跳转到这儿
    public static void open(Context context) {
        context.startActivity(new Intent(context, FriendsActivity.class));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.friends_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
            case R.id.add_friend:
                FriendsDiaFrag.newInstance().show(getSupportFragmentManager(),getCallingPackage());
                break;
        }
        return true;
    }
}
