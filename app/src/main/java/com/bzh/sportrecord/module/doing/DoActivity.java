package com.bzh.sportrecord.module.doing;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.bzh.sportrecord.R;
import com.bzh.sportrecord.base.activity.BaseActivity;

import butterknife.BindView;

public class DoActivity extends BaseActivity {

    @BindView(R.id.do_toolbar)
    Toolbar mToolbar;

    @Override
    protected int getContentViewLayoutID() {
        return R.layout.activity_do;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        mToolbar.setTitle("运动中");
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    public static void open(Context context) {
        context.startActivity(new Intent(context, DoActivity.class));
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
        }
        return true;
    }
}
