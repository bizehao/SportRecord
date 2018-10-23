package com.bzh.sportrecord.module.talk.talkFriends;

import android.arch.lifecycle.ViewModelProviders;
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

import com.bzh.sportrecord.R;
import com.bzh.sportrecord.base.activity.BaseActivity;
import com.bzh.sportrecord.data.model.FriendsInfo;
import com.bzh.sportrecord.model.Friend;
import com.bzh.sportrecord.module.talk.model.User;
import com.bzh.sportrecord.module.talk.talkMessage.MessageActivity;
import com.bzh.sportrecord.ui.adapter.FriendsRecycleViewAdapter;
import com.bzh.sportrecord.ui.widget.SideLetterBar;

import java.util.ArrayList;
import java.util.List;
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
    FriendsRecycleViewAdapter adapter;
    FriendsViewModel friendsViewModel;
    List<FriendsInfo> newFriendsInfos = new ArrayList<>();

    @Override
    protected int getContentViewLayoutID() {
        return R.layout.activity_friends;
    }


    @Override
    protected void initView(Bundle savedInstanceState) {
        mToolbar.setTitle("我的联系人");
        mToolbar.setOverflowIcon(ContextCompat.getDrawable(this, R.mipmap.menu_add));
        setSupportActionBar(mToolbar);
        if (mToolbar != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
        friendsViewModel = ViewModelProviders.of(this).get(FriendsViewModel.class);
        friendsViewModel.getFriends().observe(this, friendsInfos -> {
            adapter.setFriends(friendsInfos);
            adapter.notifyDataSetChanged();
        });
        friendsViewModel.getScreenFriends().observe(this, friendsInfos -> {
            adapter.setFriends(friendsInfos);
            adapter.notifyDataSetChanged();
        });

        adapter = new FriendsRecycleViewAdapter(this, null);
        adapter.setListener(new FriendsRecycleViewAdapter.evenClickListener() {
            @Override
            public void setOnClickListener(View view, Friend friend) {
                User user = new User(friend.getName(), friend.getRemarks(), friend.getImage(), true);
                MessageActivity.open(FriendsActivity.this, user);//跳转到message
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
                friendsViewModel.setScreenFriends(friendsViewModel.getFriends().getValue());
                return false;
            }
        });
        searchView.setOnSearchClickListener(new View.OnClickListener() { //点击搜索按钮事件
            @Override
            public void onClick(View v) {
                friendsViewModel.setScreenFriends(newFriendsInfos);
            }
        });
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                return true;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                newFriendsInfos.clear();
                List<FriendsInfo> friendsInfos = friendsViewModel.getFriends().getValue();
                String name = s.trim();
                if (s.length() != 0 && !name.equals("")) {
                    Pattern pattern = Pattern.compile(name, Pattern.CASE_INSENSITIVE);
                    for (int i = 0; i < friendsInfos.size(); i++) {
                        System.out.println(i);
                        FriendsInfo friendsInfo = friendsInfos.get(i);
                        Matcher matcher = pattern.matcher(friendsInfo.getRemarkname());
                        if (matcher.find()) {
                            newFriendsInfos.add(friendsInfo);
                        }
                    }
                }
                friendsViewModel.setScreenFriends(newFriendsInfos);
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

    @Override
    protected void onResume() {
        super.onResume();
    }

}
