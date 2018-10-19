package com.bzh.sportrecord.ui.dialog;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import com.bzh.sportrecord.App;
import com.bzh.sportrecord.R;
import com.bzh.sportrecord.api.DataManager;
import com.bzh.sportrecord.data.model.FriendsInfo;
import com.bzh.sportrecord.model.ApiUserInfos;
import com.bzh.sportrecord.model.ApiaddFriends;
import com.bzh.sportrecord.module.talk.talkFriends.FriendsActivity;
import com.bzh.sportrecord.ui.adapter.AddFriendsRecycleViewAdapter;
import com.bzh.sportrecord.ui.widget.LoadingView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import io.reactivex.Observable;

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
                    loadingView.setVisibility(View.INVISIBLE);
                }
                onDestroyView();
            }
        });
        mSearchView.setSubmitButtonEnabled(true);//显示提交按钮
        mSearchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                loadingView.setVisibility(View.VISIBLE);
                String name = s.trim();
                DataManager dataManager = DataManager.getInstance();
                Observable<ApiUserInfos> observable = dataManager.getUserInfos(name);
                dataManager.successHandler(observable, new DataManager.callBack() {
                    @Override
                    public <T> void run(T t) {
                        ApiUserInfos apiUserInfos = (ApiUserInfos) t;
                        List<ApiUserInfos.DataBean> dataBean = apiUserInfos.getData();
                       //DaoSession daoSession = App.getDaoSession(); //数据库获取缓存数据
                       // List<FriendsInfo> list = daoSession.getFriendsInfoDao().loadAll();
                        /*for (int i=0; i<list.size(); i++){
                            for(int j=0; j<dataBean.size(); j++){
                                if(list.get(i).getUsername().equals(dataBean.get(j).getUsername())){
                                    dataBean.get(j).setExit(true);
                                }
                            }
                        }*/
                        adapter.setFriends(dataBean);
                        adapter.notifyDataSetChanged();
                        loadingView.setVisibility(View.INVISIBLE);
                    }
                });
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                return false;
            }
        });
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
    public void shouUsers(List<ApiUserInfos.DataBean> friends) {
        adapter = new AddFriendsRecycleViewAdapter(getActivity(), friends);
        adapter.setListener(new AddFriendsRecycleViewAdapter.evenClickListener() { //点击添加好友
            @Override
            public void setOnClickListener(ApiUserInfos.DataBean dataBean,int position) {
                String name = dataBean.getUsername();
                DataManager dataManager = DataManager.getInstance();
                Observable<ApiaddFriends> observable = dataManager.addFriends(App.getUsername(),name,"");
                dataManager.successHandler(observable, new DataManager.callBack() {
                    @Override
                    public <T> void run(T t) {
                        ApiaddFriends apiaddFriends = (ApiaddFriends) t;
                        if(apiaddFriends.isData()){
                            FriendsInfo friendsInfo = new FriendsInfo(null,dataBean.getUsername(),
                                    dataBean.getName(),
                                    dataBean.getHeadportrait(),
                                    dataBean.getDescript(),
                                    dataBean.getAddress(),
                                    dataBean.getMotto(),
                                    dataBean.getUsername());
                            //DaoSession daoSession = App.getDaoSession(); //数据库获取缓存数据
                            //daoSession.getFriendsInfoDao().insert(friendsInfo);//将新添加的好友缓存进数据库里
                            adapter.onRefreshView(position);//动态刷新这一行数据
                            FriendsActivity df = (FriendsActivity)getActivity();
                            df.refresh(); //刷新好友界面
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

}
