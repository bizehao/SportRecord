package com.bzh.sportrecord.utils;

import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;

import com.bzh.sportrecord.module.login.loginInRegister.RegisterFragment;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegisterCheck {
    private String value;

    private TextInputLayout layout;

    private TextInputEditText editText;

    private String message;

    private boolean checkSign = false;

    public RegisterCheck(TextInputLayout layout, TextInputEditText editText, String message) {
        this.value = editText.getText().toString();
        this.layout = layout;
        this.editText = editText;
        this.message = message;
    }


    //字符串是否为空
    public RegisterCheck isNull() {
        if (value != null && !value.equals("") && value.length() != 0) {
            checkSign = true;
        } else {
            showError(layout, message + "不能为空");
            checkSign = false;
        }
        return this;
    }

    //字符串是否在长度范围内
    public RegisterCheck checkLength(int min, int max) {
        if (checkSign) {
            if (!(value.length() >= min && value.length() <= max)) {
                showError(layout, message + "长度应该在" + min + "到" + max + "之间");
                checkSign = false;
            }
        }
        return this;
    }

    //邮箱格式
    public RegisterCheck emailFormat() {
        if (checkSign) {
            String reg = "\\w+@(\\w+\\.){1,3}\\w+";
            Pattern pattern = Pattern.compile(reg);
            Matcher matcher = pattern.matcher(value);
            if (!matcher.matches()) {
                showError(layout, message + "格式不正确");
                checkSign = false;
            }
        }
        return this;
    }

    //密码是否一致
    public RegisterCheck passWordFit(String h) {
        if (checkSign) {
            if (!value.equals(h)) {
                showError(layout, "两次密码不一致");
                checkSign = false;
            }
        }
        return this;
    }

    public RegisterCheck special(int min,int max){
        if(value != null && !value.equals("") && value.length() != 0){
            if (!(value.length() >= min && value.length() <= max)) {
                showError(layout, message + "长度应该在" + min + "到" + max + "之间");
                checkSign = false;
            }else {
                checkSign = true;
            }
        }else {
            checkSign = true;
        }
        return this;
    }

    public boolean getCheck() {
        return checkSign;
    }

    /**
     * 显示错误提示，并获取焦点
     *
     * @param textInputLayout
     * @param error
     */
    private void showError(TextInputLayout textInputLayout, String error) {
        textInputLayout.setError(error);
        textInputLayout.getEditText().setFocusable(true);
        textInputLayout.getEditText().setFocusableInTouchMode(true);
        textInputLayout.getEditText().requestFocus();
    }
}
