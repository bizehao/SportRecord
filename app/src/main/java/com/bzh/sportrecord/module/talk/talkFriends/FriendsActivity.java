package com.bzh.sportrecord.module.talk.talkFriends;

import android.content.Context;
import android.content.Intent;
import android.support.v4.content.ContextCompat;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.text.InputType;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.bzh.sportrecord.App;
import com.bzh.sportrecord.R;
import com.bzh.sportrecord.base.activity.BaseActivity;
import com.bzh.sportrecord.data.model.FriendsInfo;
import com.bzh.sportrecord.model.Friend;
import com.bzh.sportrecord.module.talk.model.User;
import com.bzh.sportrecord.module.talk.talkMessage.MessageActivity;
import com.bzh.sportrecord.ui.adapter.FriendsRecycleViewAdapter;
import com.bzh.sportrecord.ui.dialog.FriendsDiaFrag;
import com.bzh.sportrecord.ui.dialog.RedFriendDiaFrag;
import com.bzh.sportrecord.ui.widget.SideLetterBar;
import com.bzh.sportrecord.utils.PinyinUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import butterknife.BindView;

public class FriendsActivity extends BaseActivity {

    @BindView(R.id.friends_toolbar)
    Toolbar mToolbar;

    @BindView(R.id.friend_recycle_view)
    RecyclerView mRecyclerView;

    @BindView(R.id.tv_letter_overlay)
    TextView letter;

    @BindView(R.id.side_letter_bar)
    SideLetterBar sideLetterBar; //字母索引

    List<Friend> friends; //好友集合
    List<Friend> screenFriends = new ArrayList<>(); //筛选后的好友
    FriendsRecycleViewAdapter adapter;

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
        mToolbar.setOverflowIcon(ContextCompat.getDrawable(this, R.mipmap.menu_add));
        setSupportActionBar(mToolbar);
        if (mToolbar != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
        friends = new ArrayList<>();
        //请求获取所有好友信息
        List<FriendsInfo> list = new ArrayList<>();
        for (int i = 0; i < list.size(); i++) {
            FriendsInfo friendsInfo = list.get(i);
            friends.add(new Friend(friendsInfo.getUsername(), friendsInfo.getRemarkname(), friendsInfo.getHeadportrait(), PinyinUtils.getPinYin(friendsInfo.getRemarkname())));
        }
        sort(friends);
        shouUsers(friends);
    }

    //跳转到这儿
    public static void open(Context context) {
        context.startActivity(new Intent(context, FriendsActivity.class));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.friends_menu, menu);
        MenuItem Item = menu.findItem(R.id.search_friend);
        SearchView searchView = (SearchView) Item.getActionView();
        searchView.setQueryHint("输入用户名");//设置默认无内容时的文字提示
        //searchView.setIconified(false);//设置searchView处于展开状态
        //searchView.onActionViewExpanded();// 当展开无输入内容的时候，没有关闭的图标
        //searchView.setSubmitButtonEnabled(true);//显示提交按钮
        searchView.setInputType(InputType.TYPE_CLASS_TEXT);
        searchView.setOnCloseListener(new SearchView.OnCloseListener() {
            @Override
            public boolean onClose() {
                adapter.setFriends(friends);
                adapter.notifyDataSetChanged();
                return false;
            }
        });
        searchView.setOnSearchClickListener(new View.OnClickListener() { //点击搜索按钮事件
            @Override
            public void onClick(View v) {
                adapter.setFriends(screenFriends);
                adapter.notifyDataSetChanged();
            }
        });
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                return true;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                screenFriends.clear();
                String name = s.trim();
                if (s.length() != 0 && !name.equals("")) {
                    adapter.setFriends(screenFriends);
                    adapter.notifyDataSetChanged();
                    Pattern pattern = Pattern.compile(name, Pattern.CASE_INSENSITIVE);
                    if (!name.equals("")) {
                        for (int i = 0; i < friends.size(); i++) {
                            Matcher matcher = pattern.matcher(friends.get(i).getRemarks());
                            if (matcher.find()) {
                                screenFriends.add(friends.get(i));
                            }
                        }
                    }
                }
                sort(screenFriends);
                adapter.setFriends(screenFriends);
                adapter.notifyDataSetChanged();
                return true;
            }
        });

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
            case R.id.add_friend:
                FriendsDiaFrag.newInstance().show(getSupportFragmentManager(), getCallingPackage());
                break;
            case R.id.recommend_friend:
                RedFriendDiaFrag.newInstance().show(getSupportFragmentManager(), getCallingPackage());
        }
        return true;
    }

    //展示好友列表
    public void shouUsers(List<Friend> friends) {
        adapter = new FriendsRecycleViewAdapter(this, friends);
        adapter.setListener(new FriendsRecycleViewAdapter.evenClickListener() {
            @Override
            public void setOnClickListener(View view, Friend friend) {
                User user = new User(friend.getName(),friend.getRemarks(),friend.getImage(),true);
                MessageActivity.open(FriendsActivity.this,user);//跳转到message
            }

            @Override
            public void setOnLongClickListener(View view, Friend friend) {
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
            }
        });
    }

    //刷新界面
    public void refresh() {
        friends.clear();
        List<FriendsInfo> list = new ArrayList<>();
        for (int i = 0; i < list.size(); i++) {
            FriendsInfo friendsInfo = list.get(i);
            friends.add(new Friend(friendsInfo.getName(), friendsInfo.getRemarkname(), friendsInfo.getHeadportrait(), PinyinUtils.getPinYin(friendsInfo.getRemarkname())));
        }

        adapter.setFriends(friends);
        adapter.notifyDataSetChanged();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    //排序
    public void sort(List<Friend> friends) {
        Collections.sort(friends, new Comparator<Friend>() {
            @Override
            public int compare(Friend o1, Friend o2) {
                char oo1 = o1.getPinyin().charAt(0);
                char oo2 = o2.getPinyin().charAt(0);
                if ((oo1 >= 'a' && oo1 <= 'z') || (oo1 >= 'A' && oo1 <= 'Z') && (oo2 >= 'a' && oo2 <= 'z') || (oo2 >= 'A' && oo2 <= 'Z')) {
                    return o1.getPinyin().compareTo(o2.getPinyin());
                } else {
                    return 1;
                }
            }
        });
    }
}
