package com.bzh.sportrecord.module.login.loginInRegister;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.AppCompatButton;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.bzh.sportrecord.R;
import com.bzh.sportrecord.base.fragment.BaseFragment;
import com.bzh.sportrecord.module.login.loginInLogin.LoginContract;
import com.bzh.sportrecord.module.login.loginInLogin.LoginFragment;
import com.bzh.sportrecord.utils.RegisterCheck;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

public class RegisterFragment extends BaseFragment implements RegisterContract.View {

    @BindView(R.id.register_return)
    TextView mImageButton;

    @BindView(R.id.register_username)
    TextInputLayout username;

    @BindView(R.id.register_username_text)
    TextInputEditText usernameText;

    @BindView(R.id.register_password)
    TextInputLayout password;

    @BindView(R.id.register_password_text)
    TextInputEditText passwordText;

    @BindView(R.id.register_again_password)
    TextInputLayout againPassword;

    @BindView(R.id.register_again_password_text)
    TextInputEditText againPasswordText;

    @BindView(R.id.register_email)
    TextInputLayout email;

    @BindView(R.id.register_email_text)
    TextInputEditText emailText;

    @BindView(R.id.register_name)
    TextInputLayout name;

    @BindView(R.id.register_name_text)
    TextInputEditText nameText;

    @BindView(R.id.register_descript)
    TextInputLayout descript;

    @BindView(R.id.register_descript_text)
    TextInputEditText descriptText;

    @BindView(R.id.register_address)
    TextInputLayout address;

    @BindView(R.id.register_address_text)
    TextInputEditText addressText;

    @BindView(R.id.register_motto)
    TextInputLayout motto;

    @BindView(R.id.register_motto_text)
    TextInputEditText mottoText;

    @BindView(R.id.register_sign_up)
    AppCompatButton signUp;

    @Inject
    RegisterContract.Presenter mRegisterPresenter;

    @Override
    protected int getContentViewLayoutID() {
        return R.layout.fragment_register;
    }

    @Override
    protected void initView() {

    }

    @OnClick(R.id.register_return)
    public void registerReturnClick(View view) {
        LoginFragment loginFragment = new LoginFragment();
        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.login_fragment, loginFragment).commit();
    }

    @Override
    public Map<String, String> getAllUserInfo() {
        Map<String, String> map = new HashMap<>();
        map.put("username", usernameText.getText().toString());
        map.put("password", passwordText.getText().toString());
        map.put("againPassword", againPasswordText.getText().toString());
        map.put("email", emailText.getText().toString());
        map.put("name", nameText.getText().toString());
        map.put("descript", descriptText.getText().toString());
        map.put("address", addressText.getText().toString());
        map.put("motto", mottoText.getText().toString());
        return map;
    }

    @OnClick(R.id.register_sign_up)
    public void signUp() {
        username.setErrorEnabled(false);
        password.setErrorEnabled(false);
        againPassword.setErrorEnabled(false);
        email.setErrorEnabled(false);
        name.setErrorEnabled(false);
        descript.setErrorEnabled(false);
        address.setErrorEnabled(false);
        motto.setErrorEnabled(false);
        boolean a = new RegisterCheck(username, usernameText, "用户名").isNull().checkLength(6, 15).getCheck();
        boolean b = new RegisterCheck(password, passwordText, "密码").isNull().checkLength(6, 15).getCheck();
        boolean c = new RegisterCheck(againPassword, againPasswordText, "密码").isNull().checkLength(6, 15).passWordFit(passwordText.getText().toString()).getCheck();
        boolean d = new RegisterCheck(email, emailText, "邮箱").isNull().emailFormat().getCheck();
        boolean e = new RegisterCheck(name, nameText, "名称").special(0, 6).getCheck();
        boolean f = new RegisterCheck(descript, descriptText, "描述").special(0, 200).getCheck();
        boolean g = new RegisterCheck(address, addressText, "地址").special(0, 50).getCheck();
        boolean h = new RegisterCheck(motto, mottoText, "个性签名").special(0, 50).getCheck();
        if (a && b && c && d && e && f && g && h) {
            showToast("验证通过");
        } else {
            showToast("验证失败");
        }
        usernameText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

    }
}
