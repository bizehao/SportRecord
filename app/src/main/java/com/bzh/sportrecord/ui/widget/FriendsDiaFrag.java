package com.bzh.sportrecord.ui.widget;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.bzh.sportrecord.R;
import com.bzh.sportrecord.model.Friend;
import com.bzh.sportrecord.ui.adapter.AddFriendsRecycleViewAdapter;
import com.bzh.sportrecord.ui.adapter.FriendsRecycleViewAdapter;
import com.bzh.sportrecord.utils.PinyinUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * @author 毕泽浩
 * @Description: 新增好友弹框
 * @time 2018/9/22 18:41
 */
public class FriendsDiaFrag extends DialogFragment {

    Unbinder unBinder;
    @BindView(R.id.addfriends_close)
    ImageButton imageButton;
    @BindView(R.id.addfriends_recycleView)
    RecyclerView mRecyclerView;
    @BindView(R.id.addfriends_searchView)
    SearchView mSearchView;
    @BindView(R.id.addfriends_loading)
    LoadingView loadingView;
    AddFriendsRecycleViewAdapter adapter;
    List<Friend> friends;
    List<Friend> screenFriends = new ArrayList<>(); //筛选后的好友

    public static FriendsDiaFrag newInstance() {
        return new FriendsDiaFrag();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(DialogFragment.STYLE_NORMAL, R.style.bran_online_supervise_dialog);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.dialog_addfriends, container, false);
        unBinder = ButterKnife.bind(this, view);
        imageButton.setOnClickListener(new View.OnClickListener() { // 关闭弹框
            @Override
            public void onClick(View v) {
                if(loadingView.getVisibility() == View.VISIBLE){
                    loadingView.setVisibility(View.GONE);
                }
                onDestroyView();
            }
        });
        mSearchView.setSubmitButtonEnabled(true);//显示提交按钮
        mSearchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                screenFriends.clear();
                String name = s.trim();
                Pattern pattern = Pattern.compile(name, Pattern.CASE_INSENSITIVE);
                if (!name.equals("")) {
                    for (int i = 0; i < friends.size(); i++) {
                        Matcher matcher = pattern.matcher(friends.get(i).getName());
                        if (matcher.find()) {
                            screenFriends.add(friends.get(i));
                        }
                    }
                }
                adapter.setFriends(screenFriends);
                adapter.notifyDataSetChanged();
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                return false;
            }
        });
        friends = new ArrayList<>();
        String name;
        for (int i = 0; i < 20; i++) {
            if (i < 5) {
                name = "张三五";
            } else if (i < 10) {
                name = "李三";
            } else if (i < 15) {
                name = "王三五";
            } else {
                name = "阿三";
            }

            friends.add(new Friend(name+i));
        }
        shouUsers(null);
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (unBinder != null && unBinder != Unbinder.EMPTY) {
            unBinder.unbind();
            unBinder = null;
        }
    }

    //展示好友列表
    public void shouUsers(List<Friend> friends) {
        adapter = new AddFriendsRecycleViewAdapter(getActivity(), friends);
        adapter.setListener(new AddFriendsRecycleViewAdapter.evenClickListener() {
            @Override
            public void setOnClickListener(View view) {
                System.out.println("添加好友");
            }
        });
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.setAdapter(adapter);
    }

}
