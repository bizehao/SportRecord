package com.bzh.sportrecord;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.bzh.apilibrary.badge.BGABadgeTextView;
import com.bzh.apilibrary.badge.BGABadgeable;
import com.bzh.apilibrary.badge.BGADragDismissDelegate;
import com.bzh.sportrecord.module.home.HomeActivity;
import com.bzh.sportrecord.ui.widget.PopupList;
import com.bzh.sportrecord.utils.AppManager;
import com.bzh.sportrecord.utils.ToastUtil;
import com.idescout.sql.SqlScoutServer;

import java.util.ArrayList;
import java.util.List;

import mehdi.sakout.fancybuttons.FancyButton;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";

    private FancyButton fancyButton;

    private SqlScoutServer sqlScoutServer;
    private BGABadgeTextView bgaBadgeTextView;
    private List<String> popupMenuItemList = new ArrayList<>();
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ToastUtil.init(this);
        sqlScoutServer = SqlScoutServer.create(this, getPackageName());
        fancyButton = findViewById(R.id.btn_spotify);
        bgaBadgeTextView = findViewById(R.id.bgabadgetextview);
        PopupList popupList = new PopupList(this);
        popupMenuItemList.add("复制");
        popupMenuItemList.add("删除");
        popupMenuItemList.add("分享");
        popupMenuItemList.add("更多");
        fancyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new MaterialDialog.Builder(MainActivity.this)
                        .title("弹框")
                        .content("内容")
                        .positiveText("确定")
                        .negativeText("取消")
                        .show();
            }
        });
        popupList.bind(fancyButton, popupMenuItemList, new PopupList.PopupListListener() {
            @Override
            public boolean showPopupList(View adapterView, View contextView, int contextPosition) {
                return false;
            }

            @Override
            public void onPopupListClick(View contextView, int contextPosition, int position) {
                System.out.println("显示");
                Toast.makeText(MainActivity.this, contextPosition + "," + position, Toast.LENGTH_SHORT).show();
            }
        });
        bgaBadgeTextView.showTextBadge("20");
        bgaBadgeTextView.setDragDismissDelegate(new BGADragDismissDelegate() {
            @Override
            public void onDismiss(BGABadgeable badgeable) {
                ToastUtil.show("气泡消失了");
            }
        });
        startActivity(new Intent(MainActivity.this, HomeActivity.class));
        //AppManager.getAppManager().finishActivity();
        finish();
    }

    public void show() {

    }

    @Override
    protected void onResume() {
        sqlScoutServer.resume();
        super.onResume();
    }

    @Override
    protected void onPause() {
        sqlScoutServer.destroy();
        super.onPause();
    }

    @Override
    protected void onStop() {
        sqlScoutServer.destroy();
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        sqlScoutServer.destroy();
        super.onDestroy();
    }

}
