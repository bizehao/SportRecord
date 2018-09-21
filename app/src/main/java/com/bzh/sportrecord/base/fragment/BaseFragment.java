package com.bzh.sportrecord.base.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.bzh.sportrecord.App;
import com.bzh.sportrecord.base.presenter.BasePresenter;
import com.bzh.sportrecord.base.view.BaseView;
import com.bzh.sportrecord.di.component.DaggerActivityComponent;
import com.bzh.sportrecord.di.component.DaggerFragmentComponent;
import com.bzh.sportrecord.di.component.FragmentComponent;
import com.bzh.sportrecord.di.module.FragmentModule;

import javax.inject.Inject;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public abstract class BaseFragment extends Fragment implements BaseView {

    private Unbinder unbinder;

    private Toast mToast;

    protected FragmentComponent fragmentComponent;

    /**
     * 获取布局ID
     */
    protected abstract int getContentViewLayoutID();

    /**
     * 界面初始化
     */
    protected abstract void initView();

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
        fragmentComponent = DaggerFragmentComponent.builder()
                .fragmentModule(new FragmentModule(getActivity(),this))
                .build();
        inject();
        initView();
    }

    /**
     * 注入组件
     */
    protected abstract void inject();

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
            mToast = Toast.makeText(this.getActivity().getApplicationContext(), desc, Toast.LENGTH_SHORT);
        } else {
            mToast.setText(desc);
        }
        mToast.show();
    }

    @Override
    public void showErrorMsg(String errorMsg) {
        System.out.println("自定义错误");
        //showToast(errorMsg);
    }

    @Override
    public void showNormal() {
        System.out.println("正常的显示");
        //showToast("显示正常，正常框");
    }

    @Override
    public void showError() {
        System.out.println("发生错误，错误框");
        //showToast("发生错误，错误框");
    }

    @Override
    public void showLoading() {
        System.out.println("开始加载框，加载框");
        //showToast("开始加载框，加载框");
    }

    @Override
    public void shutDownLoading() {
        System.out.println("结束加载框，加载框");
        //showToast("结束加载框，加载框");
    }
}
