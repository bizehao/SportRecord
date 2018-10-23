package com.bzh.sportrecord.module.talk.talkFriends;

import android.arch.lifecycle.ViewModelProviders;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import com.bzh.sportrecord.App;
import com.bzh.sportrecord.R;
import com.bzh.sportrecord.api.RetrofitHelper;
import com.bzh.sportrecord.data.AppDatabase;
import com.bzh.sportrecord.data.model.FriendsInfo;
import com.bzh.sportrecord.model.ApiUserInfos;
import com.bzh.sportrecord.model.ApiaddFriends;
import com.bzh.sportrecord.ui.adapter.AddFriendsRecycleViewAdapter;
import com.bzh.sportrecord.ui.widget.LoadingView;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import dagger.android.DaggerDialogFragment;
import dagger.android.support.DaggerAppCompatDialogFragment;
import io.reactivex.Observable;

/**
 * @author 毕泽浩
 * @Description: 推荐好友弹框
 * @time 2018/10/9 9:32
 */
public class RedFriendDiaFrag extends DaggerAppCompatDialogFragment {
    Unbinder unBinder;
    @BindView(R.id.recfriends_close)
    ImageButton imageButton;
    @BindView(R.id.recfriends_recycleView)
    RecyclerView mRecyclerView;
    @BindView(R.id.recfriends_loading)
    LoadingView loadingView;
    @BindView(R.id.recfriends_refresh)
    SwipeRefreshLayout refreshLayout;

    @Inject
    RetrofitHelper retrofitHelper;

    AddFriendsRecycleViewAdapter adapter;
    private FriendsViewModel friendsViewModel;

    public static RedFriendDiaFrag newInstance() {
        return new RedFriendDiaFrag();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(DialogFragment.STYLE_NORMAL, R.style.bran_online_supervise_dialog);
        friendsViewModel = ViewModelProviders.of(getActivity()).get(FriendsViewModel.class);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.dialog_recfriends, container, false);
        unBinder = ButterKnife.bind(this, view);
        imageButton.setOnClickListener(new View.OnClickListener() { // 关闭弹框
            @Override
            public void onClick(View v) {
                if (loadingView.getVisibility() == View.VISIBLE) {
                    loadingView.setVisibility(View.INVISIBLE);
                }
                onDestroyView();
            }
        });
        shouUsers(null);
        getFriends();
        refreshLayout.setColorSchemeColors(Color.GREEN, Color.YELLOW, Color.RED);
        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getFriends();
                refreshLayout.setRefreshing(false);
                /*new Handler().postDelayed(new Runnable() {//延时加载
                    public void run() {

                    }
                }, 2000);*/
            }
        });

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
    public void shouUsers(List<ApiUserInfos.DataBean> friends) {
        adapter = new AddFriendsRecycleViewAdapter(getActivity(), friends);
        adapter.setListener(new AddFriendsRecycleViewAdapter.evenClickListener() { //点击添加好友
            @Override
            public void setOnClickListener(ApiUserInfos.DataBean dataBean, int position) {
                String name = dataBean.getUsername();
                Observable<ApiaddFriends> observable = retrofitHelper.getServer().addFriends(App.getUsername(), name, "");
                retrofitHelper.successHandler(observable, new RetrofitHelper.callBack() {
                    @Override
                    public <T> void run(T t) {
                        ApiaddFriends apiaddFriends = (ApiaddFriends) t;
                        if (apiaddFriends.isData()) {
                            FriendsInfo friendsInfo = new FriendsInfo(dataBean.getUsername(),
                                    dataBean.getName(),
                                    dataBean.getHeadportrait(),
                                    dataBean.getDescript(),
                                    dataBean.getAddress(),
                                    dataBean.getMotto(),
                                    dataBean.getUsername());
                            AppDatabase.getAppDatabase().friendsInfoDao().insert(friendsInfo);//将新添加的好友缓存进数据库里
                            adapter.onRefreshView(position);//动态刷新这一行数据
                        }
                        AlertDialog dialog = new AlertDialog.Builder(getActivity()).create();
                        dialog.setTitle("提示：");//设置对话框标题
                        dialog.setMessage(apiaddFriends.getRequestMessage());//设置文字显示内容
                        dialog.show();//显示对话框
                    }
                });
            }
        });
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.setAdapter(adapter);
    }

    //获取推荐好友
    public void getFriends() {
        retrofitHelper.successHandler(retrofitHelper.getServer().getRecUser(App.getUsername()), new RetrofitHelper.callBack() {
            @Override
            public <T> void run(T t) {
                ApiUserInfos apiUserInfos = (ApiUserInfos) t;
                List<ApiUserInfos.DataBean> dataBean = apiUserInfos.getData();
                List<FriendsInfo> list = AppDatabase.getAppDatabase().friendsInfoDao().loadAll();
                for (int i = 0; i < list.size(); i++) {
                    for (int j = 0; j < dataBean.size(); j++) {
                        if (list.get(i).getUsername().equals(dataBean.get(j).getUsername()) && !dataBean.get(j).isExit()) {
                            System.out.println(dataBean.get(j).getUsername());
                            dataBean.get(j).setExit(true);
                        }
                    }
                }
                adapter.setFriends(dataBean);
                adapter.notifyDataSetChanged();
            }
        });

    }
}
