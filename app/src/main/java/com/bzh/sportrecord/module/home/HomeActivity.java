package com.bzh.sportrecord.module.home;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.TextView;

import com.ashokvarma.bottomnavigation.BottomNavigationBar;
import com.ashokvarma.bottomnavigation.BottomNavigationItem;
import com.bumptech.glide.Glide;
import com.bzh.sportrecord.App;
import com.bzh.sportrecord.R;
import com.bzh.sportrecord.api.DataManager;
import com.bzh.sportrecord.base.activity.BaseActivity;
import com.bzh.sportrecord.module.home.homeNews.NewsFragment;
import com.bzh.sportrecord.module.home.homePlan.PlanFragment;
import com.bzh.sportrecord.module.home.homeSport.SportFragment;
import com.bzh.sportrecord.module.home.homeSport.WebSocketChatClient;
import com.bzh.sportrecord.module.login.LoginActivity;

import java.net.URI;
import java.net.URISyntaxException;
import java.time.Instant;
import java.util.Objects;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;

public class HomeActivity extends BaseActivity implements HomeContract.View {

    @BindView(R.id.home_toolbar)
    Toolbar mToolbar;

    @BindView(R.id.home_drawer_layout)
    DrawerLayout mDrawerLayout;

    @BindView(R.id.home_float_button)
    FloatingActionButton mFloatingActionButton;

    @BindView(R.id.nav_view)
    NavigationView mNavigationView;

    @BindView(R.id.home_bottom_bar)
    BottomNavigationBar mBottomNavigationBar;

    CircleImageView mCircleImageView;

    TextView mTextViewName;

    TextView mTextViewMotto;

    @Inject
    HomeContract.Presenter mPresenter;

    private Fragment[] fragments = new Fragment[3];
    private int[] colors = new int[3];

    @Override
    protected int getContentViewLayoutID() {
        return R.layout.activity_home;
    }

    @Override
    protected void beforeInit() {
        super.beforeInit();
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN); //固定页面压制 bug:底部状态栏跟随键盘
    }

    @Override
    protected void inject() {
        activityComponent.inject(this);
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        System.out.println("初始化view成功");
        setSupportActionBar(mToolbar);
        View navigationHeadView = mNavigationView.getHeaderView(0);
        mCircleImageView = navigationHeadView.findViewById(R.id.user_icon);
        mTextViewName = navigationHeadView.findViewById(R.id.user_name);
        mTextViewMotto = navigationHeadView.findViewById(R.id.user_motto);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        //mToolbar.getBackground().mutate().setAlpha(225);
        ActionBarDrawerToggle drawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout, mToolbar, R.string.app_name, R.string.app_name);
        mDrawerLayout.addDrawerListener(drawerToggle);
        drawerToggle.syncState();

        fragments[0] = new SportFragment();
        fragments[1] = new NewsFragment();
        fragments[2] = new PlanFragment();
        colors[0] = R.color.blue;
        colors[1] = R.color.red;
        colors[2] = R.color.colorAccent;

        //侧滑菜单 menu
        //mNavigationView.setCheckedItem(R.id.nav_login);
        mNavigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                Intent instant;
                switch (menuItem.getItemId()) {
                    case R.id.nav_login:
                        showToast("登录");
                        instant = new Intent(HomeActivity.this, LoginActivity.class);
                        startActivity(instant);
                        break;
                    case R.id.nav_loginout:
                        showToast("注销");
                        App.setLoginSign(false);
                        instant = new Intent(HomeActivity.this, LoginActivity.class);
                        startActivity(instant);
                        break;
                    case R.id.nav_nocturnal_pattern:
                        showToast("夜间模式");
                        break;
                    case R.id.nav_feedback_feedback:
                        showToast("意见反馈");
                        break;
                    case R.id.nav_adout_me:
                        showToast("关于我们");
                        break;
                    case R.id.nav_setup:
                        showToast("设置");
                        break;
                    case R.id.nav_sign_out:
                        showToast("退出");
                        break;
                }
                mDrawerLayout.closeDrawers();
                return true;
            }
        });
        //底部菜单
        mBottomNavigationBar.setMode(BottomNavigationBar.MODE_SHIFTING).setBackgroundStyle(BottomNavigationBar.BACKGROUND_STYLE_RIPPLE)
                .addItem(new BottomNavigationItem(R.mipmap.home_sport, "运动").setActiveColorResource(colors[0]))
                .addItem(new BottomNavigationItem(R.mipmap.home_news, "资讯").setActiveColorResource(colors[1]))
                .addItem(new BottomNavigationItem(R.mipmap.home_plan, "个人计划").setActiveColorResource(colors[2]))
                .setFirstSelectedPosition(0).initialise();
        getSupportFragmentManager().beginTransaction().replace(R.id.ttest, fragments[0]).commit();//设置默认的fragment
        mFloatingActionButton.setBackgroundTintList(ColorStateList.valueOf(ContextCompat.getColor(this, colors[0])));//设置默认颜色
        mBottomNavigationBar.setTabSelectedListener(new BottomNavigationBar.OnTabSelectedListener() {
            @Override
            public void onTabSelected(int position) {
                if (position < fragments.length) {
                    mFloatingActionButton.setBackgroundTintList(ColorStateList.valueOf(ContextCompat.getColor(getApplication(), colors[position])));
                    FragmentManager fragmentManager = getSupportFragmentManager();
                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                    Fragment nowFragment = fragmentManager.findFragmentById(R.id.ttest);
                    Fragment nextFragment = fragments[position];
                    if (nextFragment.isAdded()) {
                        fragmentTransaction.hide(nowFragment).show(nextFragment);
                    } else {
                        fragmentTransaction.hide(nowFragment).add(R.id.ttest, nextFragment);
                    }
                    fragmentTransaction.commitAllowingStateLoss();
                }
            }

            @Override
            public void onTabUnselected(int position) {
                //这儿也要操作隐藏，否则Fragment会重叠
                if (fragments != null) {
                    if (position < fragments.length) {
                        FragmentManager fm = getSupportFragmentManager();
                        FragmentTransaction ft = fm.beginTransaction();
                        Fragment fragment = fragments[position];
                        // 隐藏当前的fragment
                        ft.hide(fragment);
                        ft.commitAllowingStateLoss();
                    }
                }
            }

            @Override
            public void onTabReselected(int position) {
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
    }



    @Override
    protected void onResume() {
        super.onResume();
        if (App.getLoginSign()) {
            mNavigationView.getMenu().findItem(R.id.nav_login).setVisible(false);
            mNavigationView.getMenu().findItem(R.id.nav_loginout).setVisible(true);
            //加载用户信息
            mPresenter.loadData(App.getUsername());
        } else {
            mNavigationView.getMenu().findItem(R.id.nav_login).setVisible(true);
            mNavigationView.getMenu().findItem(R.id.nav_loginout).setVisible(false);
            Glide.with(this).load(ContextCompat.getDrawable(getApplication(), R.mipmap.no_login_user)).into(mCircleImageView);
            mTextViewName.setText("未登录");
            mTextViewMotto.setText("");
        }
    }

    @Override // menu
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.toolbar, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                mDrawerLayout.openDrawer(GravityCompat.START);
                break;
        }
        return true;
    }

    @OnClick(R.id.home_float_button) //悬浮按钮
    public void makeByFloatingActionButton(View view) {
        showToast("点击了");
       /* Snackbar.make(view,"1111", Snackbar.LENGTH_SHORT)
                .setAction("222", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        showToast("点击了");
                    }
                }).show();*/
    }

    @Override
    public void setHeadPortrait(int image) {
        Glide.with(this).load(ContextCompat.getDrawable(getApplication(), R.drawable.user_icon)).into(mCircleImageView);
    }

    @Override
    public void setHeadName(String name) {
        mTextViewName.setText(name);
    }

    @Override
    public void setHeadMotto(String motto) {
        mTextViewMotto.setText(motto);
    }
}
