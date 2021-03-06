package com.bzh.sportrecord.module.login.loginInLogin;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputEditText;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.AppCompatButton;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bzh.sportrecord.R;
import com.bzh.sportrecord.base.fragment.BaseFragment;
import com.bzh.sportrecord.module.home.HomePresenter;
import com.bzh.sportrecord.module.login.loginInRegister.RegisterFragment;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

public class LoginFragment extends BaseFragment implements LoginContract.View {

    @BindView(R.id.rout_register)
    TextView mTextView;

    @BindView(R.id.login_user_text)
    TextInputEditText loginUserText;

    @BindView(R.id.password_text)
    TextInputEditText passwordText;

    @BindView(R.id.login_but)
    AppCompatButton loginBut;

    @Inject
    LoginContract.Presenter mLoginPresenter;

    @Override
    protected int getContentViewLayoutID() {
        return R.layout.fragment_login;
    }

    protected void inject(){
        fragmentComponent.inject(this);
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
    }

    @OnClick(R.id.rout_register) //跳转到注册
    public void setmTextViewClick(View view){
        FrameLayout frameLayout = getActivity().findViewById(R.id.login_fragment);
        frameLayout.setPadding(0,0,0,0);
        RegisterFragment registerFragment = new RegisterFragment();
        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.login_fragment,registerFragment).commit();
    }

    @OnClick(R.id.login_but) //点击登录
    public void setmLoginButClick(View view){
        mLoginPresenter.login();
    }

    @Override
    public String getUsername() {
        return loginUserText.getText().toString();
    }

    @Override
    public String getPassword() {
        return passwordText.getText().toString();
    }

}
