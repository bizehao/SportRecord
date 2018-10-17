package com.bzh.sportrecord.module.login.loginInRegister;

import android.os.Bundle;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.AppCompatButton;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.bzh.sportrecord.R;
import com.bzh.sportrecord.base.fragment.BaseFragment;
import com.bzh.sportrecord.module.login.loginInLogin.LoginFragment;
import com.bzh.sportrecord.ui.dialog.CustomDiaFrag;
import com.bzh.sportrecord.utils.DensityUtils;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;

public class RegisterFragment extends BaseFragment implements RegisterContract.View {

    private static final String TAG = "RegisterFragment";

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

    private boolean usernameSign = true,
            passwordSign = true, againPasswordSign = true, emailSign = true, nameSign = true,
            descriptSign = true, addressSign = true, mottoSign = true; //各个输入框的表示

    @Override
    protected int getContentViewLayoutID() {
        return R.layout.fragment_register;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        usernameText.addTextChangedListener(new ClassOfTextWatcher(usernameText, username));
        passwordText.addTextChangedListener(new ClassOfTextWatcher(passwordText, password));
        againPasswordText.addTextChangedListener(new ClassOfTextWatcher(againPasswordText, againPassword));
        emailText.addTextChangedListener(new ClassOfTextWatcher(emailText, email));
        nameText.addTextChangedListener(new ClassOfTextWatcher(nameText, name));
        descriptText.addTextChangedListener(new ClassOfTextWatcher(descriptText, descript));
        addressText.addTextChangedListener(new ClassOfTextWatcher(addressText, address));
    }

    @Override
    protected void inject() {
        fragmentComponent.inject(this);
    }

    @OnClick(R.id.register_return) //跳转到登录页面
    public void registerReturnClick(View view) {
        FrameLayout frameLayout = getActivity().findViewById(R.id.login_fragment);
        frameLayout.setPadding(0, 0, 0, DensityUtils.dp2px(getActivity(), 90));
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

    @OnClick(R.id.register_sign_up) //注册按钮点击事件
    public void signUp() {
        if (usernameText.getText().length() == 0) {
            usernameSign = false;
            textLayoutshowError(username, "用户名不能为空");
        }
        if (passwordText.getText().length() == 0) {
            passwordSign = false;
            textLayoutshowError(password, "密码不能为空");
        }
        if (againPasswordText.getText().length() == 0) {
            againPasswordSign = false;
            textLayoutshowError(againPassword, "密码不能为空");
        }
        if (emailText.getText().length() == 0) {
            emailSign = false;
            textLayoutshowError(email, "邮箱地址不能为空");
        } else {
            String reg = "\\w+@(\\w+\\.){1,3}\\w+";
            Pattern pattern = Pattern.compile(reg);
            Matcher matcher = pattern.matcher(emailText.getText().toString());
            if (!matcher.matches()) {
                emailSign = false;
                textLayoutshowError(email, "邮箱格式不正确");
            }
        }
        if (usernameSign && passwordSign && againPasswordSign && emailSign && nameSign && descriptSign && addressSign && mottoSign) {
            mRegisterPresenter.register();
        }

    }

    private class ClassOfTextWatcher implements TextWatcher {

        private TextInputEditText editText;
        private TextInputLayout layout;

        public ClassOfTextWatcher(View editText, View layout) {
            if (editText instanceof TextInputEditText && layout instanceof TextInputLayout) {
                this.editText = (TextInputEditText) editText;
                this.layout = (TextInputLayout) layout;
            } else {
                throw new ClassCastException("view must be an instance Of TextView");
            }
        }

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
        }

        @Override
        public void afterTextChanged(Editable s) {
            switch (editText.getId()) {
                case R.id.register_username_text:
                    if (s.length() == 0) {
                        usernameSign = false;
                        textLayoutshowError(layout, "用户名不能为空");
                    } else if (s.length() < 6) {
                        usernameSign = false;
                        textLayoutshowError(layout, "用户名长度至少6位");
                    } else if (s.length() > 15) {
                        usernameSign = false;
                        textLayoutshowError(layout, "用户名长度最多15位");
                    } else {
                        usernameSign = true;
                        layout.setErrorEnabled(false);
                    }
                    break;
                case R.id.register_password_text:
                    if (s.length() == 0) {
                        againPasswordSign = false;
                        textLayoutshowError(layout, "密码不能为空");
                    } else if (s.length() < 6) {
                        againPasswordSign = false;
                        textLayoutshowError(layout, "密码长度至少6位");
                    } else if (s.length() > 15) {
                        againPasswordSign = false;
                        textLayoutshowError(layout, "密码长度最多15位");
                    } else if (againPasswordText.getText().length() > 0) {
                        if (editText.getText().toString().equals(againPasswordText.getText().toString())) {
                            againPasswordSign = true;
                            layout.setErrorEnabled(false);
                        } else {
                            againPasswordSign = false;
                            textLayoutshowError(layout, "两次密码不一致");
                        }
                    } else {
                        againPasswordSign = true;
                        layout.setErrorEnabled(false);
                    }
                    break;
                case R.id.register_again_password_text:
                    if (s.length() == 0) {
                        passwordSign = false;
                        textLayoutshowError(layout, "密码不能为空");
                    } else if (s.length() < 6) {
                        passwordSign = false;
                        textLayoutshowError(layout, "密码长度至少6位");
                    } else if (s.length() > 15) {
                        passwordSign = false;
                        textLayoutshowError(layout, "密码长度最多15位");
                    } else if (passwordText.getText().length() > 0) {
                        if (editText.getText().toString().equals(passwordText.getText().toString())) {
                            passwordSign = true;
                            layout.setErrorEnabled(false);
                        } else {
                            passwordSign = false;
                            textLayoutshowError(layout, "两次密码不一致");
                        }
                    } else {
                        layout.setErrorEnabled(false);
                    }
                    break;
                case R.id.register_email_text:
                    if (s.length() == 0) {
                        emailSign = false;
                        textLayoutshowError(layout, "邮箱地址不能为空");
                    } else {
                        emailSign = true;
                        layout.setErrorEnabled(false);
                    }
                    break;
                case R.id.register_name_text:
                    if (s.length() > 15) {
                        nameSign = false;
                        textLayoutshowError(layout, "签名长度最多为15位");
                    } else {
                        nameSign = true;
                        layout.setErrorEnabled(false);
                    }
                    break;
                case R.id.register_descript_text:
                    if (s.length() > 200) {
                        descriptSign = false;
                        textLayoutshowError(layout, "签名长度最多200位");
                    } else {
                        descriptSign = true;
                        layout.setErrorEnabled(false);
                    }
                    break;
                case R.id.register_address_text:
                    if (s.length() > 50) {
                        addressSign = false;
                        textLayoutshowError(layout, "地址最多为50位");
                    } else {
                        addressSign = true;
                        layout.setErrorEnabled(false);
                    }
                    break;
                case R.id.register_motto_text:
                    if (s.length() > 50) {
                        mottoSign = false;
                        textLayoutshowError(layout, "个性最多为50位");
                    } else {
                        mottoSign = true;
                        layout.setErrorEnabled(false);
                    }
                    break;
            }
        }
    }

    /**
     * 显示错误提示，并获取焦点
     *
     * @param textInputLayout
     * @param error
     */
    private void textLayoutshowError(TextInputLayout textInputLayout, String error) {
        textInputLayout.setError(error);
        textInputLayout.getEditText().setFocusable(true);
        textInputLayout.getEditText().setFocusableInTouchMode(true);
        textInputLayout.getEditText().requestFocus();
    }

    @Override
    public void showNormal(){
        CustomDiaFrag.newInstance("提示","你已经注册成功,是否前往登录").show(getFragmentManager(), getTag());
    };

    @Override
    public void showError() {
        CustomDiaFrag.newInstance("提示","该用户名已存在，请重新设置用户名").show(getFragmentManager(), getTag());
    }

    @Override
    public void showErrorMsg(String errorMsg) {
        CustomDiaFrag.newInstance("提示",errorMsg).show(getFragmentManager(), getTag());
    }
}
