package com.bzh.sportrecord.module.login;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.bzh.sportrecord.R;
import com.bzh.sportrecord.base.activity.BaseActivity;
import com.bzh.sportrecord.module.login.loginInLogin.LoginFragment;

import butterknife.BindView;

public class LoginActivity extends BaseActivity {

    @BindView(R.id.login_fragment)
    FrameLayout mFrameLayout;

    @Override
    protected int getContentViewLayoutID() {
        return R.layout.activity_login;
    }

    @Override
    protected void inject() {

    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        LoginFragment fragment = new LoginFragment();
        replaceFragment(fragment);
    }

    @Override
    public void showErrorMsg(String errorMsg) {

    }

    @Override
    public void showNormal() {

    }

    @Override
    public void showError() {

    }

    @Override
    public void showLoading() {

    }

    @Override
    public void shutDownLoading() {

    }

    /**
     * 添加登录fragment
     * @param fragment
     */
    public void replaceFragment(Fragment fragment){
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.login_fragment,fragment).commit();
    }
}
