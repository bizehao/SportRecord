package com.bzh.sportrecord;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;

import com.bzh.sportrecord.base.activity.BaseActivity;
import com.bzh.sportrecord.di.component.ActivityComponent;
import com.bzh.sportrecord.di.module.ActivityModule;
import com.bzh.sportrecord.module.home.HomeActivity;
import com.bzh.sportrecord.test.Person;

import javax.inject.Inject;


public class MainActivity extends BaseActivity {

    @Override
    protected int getContentViewLayoutID() {
        return R.layout.activity_main;
    }

    @Override
    protected void beforeInit() {
        super.beforeInit();
        setTheme(R.style.AppTheme_Launcher);
    }

    @Override
    protected void inject() {

    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        startActivity(new Intent(MainActivity.this, HomeActivity.class));
        finish();
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
}
