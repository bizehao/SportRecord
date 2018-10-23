package com.bzh.sportrecord.base.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;
import com.bzh.sportrecord.base.view.BaseView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import dagger.android.support.DaggerFragment;

public abstract class BaseFragment extends DaggerFragment implements BaseView {

    private Unbinder unbinder;

    private Toast mToast;

    /**
     * 获取布局ID
     */
    protected abstract int getContentViewLayoutID();

    /**
     * 界面初始化
     */
    protected abstract void initView(Bundle savedInstanceState);

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (getContentViewLayoutID() != 0) {
            return inflater.inflate(getContentViewLayoutID(), container, false);
        } else {
            return super.onCreateView(inflater, container, savedInstanceState);
        }
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        unbinder = ButterKnife.bind(this, view);
        initView(savedInstanceState);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind(); // 解除绑定
    }

    /**
     * 消息框
     *
     * @param desc
     */
    protected void showToast(String desc) {
        if (mToast == null) {
            mToast = Toast.makeText(getActivity(), desc, Toast.LENGTH_SHORT);
        } else {
            mToast.setText(desc);
        }
        mToast.show();
    }

    @Override
    public void showErrorMsg(String errorMsg) {
        showToast(errorMsg);
        //showToast(errorMsg);
    }

    @Override
    public void showNormal() {
        //showToast("显示正常，正常框");
    }

    @Override
    public void showError() {
        //showToast("发生错误，错误框");
    }

    @Override
    public void showLoading() {
        //showToast("开始加载框，加载框");
    }

    @Override
    public void shutDownLoading() {
        //showToast("结束加载框，加载框");
    }
}
