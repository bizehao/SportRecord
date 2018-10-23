package com.bzh.sportrecord.base.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.widget.Toast;
import com.bzh.sofialibrary.Sofia;
import com.bzh.sportrecord.R;
import com.bzh.sportrecord.base.view.BaseView;
import com.bzh.sportrecord.utils.AppManager;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import dagger.android.DaggerDialogFragment;
import dagger.android.support.DaggerAppCompatActivity;

/**
 * 基础 activity
 *
 */
public abstract class BaseActivity extends DaggerAppCompatActivity implements BaseView {

    protected Unbinder unBinder;
    /**
     * 获取布局ID
     *
     * @return
     */
    protected abstract int getContentViewLayoutID();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        beforeInit(); //初始化之前
        if (getContentViewLayoutID() != 0) {
            setContentView(getContentViewLayoutID());
            initView(savedInstanceState); //初始化
            initSystemBarTint(); //状态栏
        }
        AppManager appManager = AppManager.getAppManager();
        appManager.addActivity(this);
    }

    @Override
    public void setContentView(int layoutResID) {
        super.setContentView(layoutResID);
        unBinder = ButterKnife.bind(this); //设置 ButterKnife
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (unBinder != null && unBinder != Unbinder.EMPTY) {
            unBinder.unbind();
            unBinder = null;
        }
    }

    /**
     * 界面初始化前期准备
     */
    protected void beforeInit() { };

    /**
     * 初始化布局以及View控件
     */
    protected abstract void initView(Bundle savedInstanceState);


    /**
     * 设置状态栏颜色
     */
    /*protected void initSystemBarTint() {
        Window window = getWindow();
        if (true) {
            // 设置状态栏全透明
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                System.out.println("================");
                //设置悬浮透明状态栏
                window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
                window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
                window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
                window.setStatusBarColor(Color.TRANSPARENT);
                //window.setStatusBarColor(ContextCompat.getColor(this,R.color.colorPrimary));
                //window.setNavigationBarColor(Color.TRANSPARENT);//虚拟键盘颜色

            } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                System.out.println("++++++++++++++++++");
                getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            }
            return;
        }
        // 沉浸式状态栏
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            //5.0以上使用原生方法
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(setStatusBarColor());
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            //4.4-5.0使用三方工具类，有些4.4的手机有问题，这里为演示方便，不使用沉浸式
//            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            SystemBarTintManager tintManager = new SystemBarTintManager(this);
            tintManager.setStatusBarTintEnabled(true);
            tintManager.setStatusBarTintColor(setStatusBarColor());
        }
    }*/

   /**
    * 子类可以重写改变状态栏颜色
    **/
    /*protected int setStatusBarColor() {
        return R.color.blue;
    }*/

    /**
     * 设置状态栏颜色
     */
    protected void initSystemBarTint() {
     Sofia.with(this)
                .statusBarBackground(ContextCompat.getColor(this, R.color.colorPrimary))
                .navigationBarBackground(ContextCompat.getDrawable(this, R.color.colorNavigation));
    }

    private Toast mToast;

    /**
     * 消息框
     *
     * @param desc
     */
    protected void showToast(String desc) {
        if (mToast == null) {
            mToast = Toast.makeText(this.getApplicationContext(), desc, Toast.LENGTH_SHORT);
        } else {
            mToast.setText(desc);
        }
        mToast.show();
    }

    @Override
    public void showErrorMsg(String errorMsg) {
        showToast(errorMsg);
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
