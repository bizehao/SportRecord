package com.bzh.sportrecord.module.home;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.ColorStateList;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.FileProvider;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.ashokvarma.bottomnavigation.BottomNavigationBar;
import com.ashokvarma.bottomnavigation.BottomNavigationItem;
import com.bumptech.glide.Glide;
import com.bzh.sportrecord.App;
import com.bzh.sportrecord.BuildConfig;
import com.bzh.sportrecord.R;
import com.bzh.sportrecord.api.DataManager;
import com.bzh.sportrecord.base.activity.BaseActivity;
import com.bzh.sportrecord.module.home.homeNews.NewsFragment;
import com.bzh.sportrecord.module.home.homePlan.PlanFragment;
import com.bzh.sportrecord.module.home.homeSport.SportFragment;
import com.bzh.sportrecord.module.login.LoginActivity;
import com.bzh.sportrecord.module.talk.talkFriends.FriendsActivity;
import com.bzh.sportrecord.utils.CommonUtil;
import com.bzh.sportrecord.utils.FileUtil;

import java.io.File;
import java.util.Objects;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

import static com.bzh.sportrecord.utils.FileUtil.getRealFilePathFromUri;

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

    //调用照相机返回图片文件
    private File tempFile;
    // 1: qq, 2: weixin
    private int type = 1;

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
        setSupportActionBar(mToolbar);
        View navigationHeadView = mNavigationView.getHeaderView(0);
        mCircleImageView = navigationHeadView.findViewById(R.id.user_icon);
        mTextViewName = navigationHeadView.findViewById(R.id.user_name);
        mTextViewMotto = navigationHeadView.findViewById(R.id.user_motto);
        mCircleImageView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) { //选择图像
                uploadHeadImage();
                return true;
            }
        });
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
        Glide.with(HomeActivity.this).load(ContextCompat.getDrawable(HomeActivity.this, R.mipmap.button_sport)).into(mFloatingActionButton);
        mFloatingActionButton.setOnClickListener(new View.OnClickListener() { //悬浮按钮点击跳转到好友列表
            @Override
            public void onClick(View v) {
                showToast("运动吧");
            }
        });
        mBottomNavigationBar.setTabSelectedListener(new BottomNavigationBar.OnTabSelectedListener() {
            @Override
            public void onTabSelected(int position) {
                if (position == 0) {
                    Glide.with(HomeActivity.this).load(ContextCompat.getDrawable(HomeActivity.this, R.mipmap.button_sport)).into(mFloatingActionButton);
                    mFloatingActionButton.setOnClickListener(new View.OnClickListener() { //悬浮按钮点击跳转到好友列表
                        @Override
                        public void onClick(View v) {
                            showToast("运动吧");
                        }
                    });
                }
                if (position == 1) {
                    Glide.with(HomeActivity.this).load(ContextCompat.getDrawable(HomeActivity.this, R.mipmap.button_sport)).into(mFloatingActionButton);
                    mFloatingActionButton.setOnClickListener(new View.OnClickListener() { //悬浮按钮点击跳转到好友列表
                        @Override
                        public void onClick(View v) {
                            showToast("新闻记录");
                        }
                    });
                }
                if (position == 2) {
                    Glide.with(HomeActivity.this).load(ContextCompat.getDrawable(HomeActivity.this, R.mipmap.friends)).into(mFloatingActionButton);
                    mFloatingActionButton.setOnClickListener(new View.OnClickListener() { //悬浮按钮点击跳转到好友列表
                        @Override
                        public void onClick(View v) {
                            FriendsActivity.open(HomeActivity.this);
                        }
                    });
                }
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

    //跳转到这儿
    public static void open(Context context){
        Intent intent = new Intent(context, HomeActivity.class);
        context.startActivity(intent); //跳转到home页面

    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onResume() {
        super.onResume();
        if(App.getWhetherVerify()){
            if (App.getLoginSign()) {
                mNavigationView.getMenu().findItem(R.id.nav_login).setVisible(false);
                mNavigationView.getMenu().findItem(R.id.nav_loginout).setVisible(true);
                //加载用户信息
                mPresenter.loadData(App.getUsername());
                App.setWhetherVerify(false);//设置验证状态为下次不用验证
            } else {
                mNavigationView.getMenu().findItem(R.id.nav_login).setVisible(true);
                mNavigationView.getMenu().findItem(R.id.nav_loginout).setVisible(false);
                Glide.with(this).load(ContextCompat.getDrawable(getApplication(), R.mipmap.no_login_user)).into(mCircleImageView);
                mTextViewName.setText("未登录");
                mTextViewMotto.setText("");
                App.setWhetherVerify(true);
            }
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
    public void setHeadPortrait(Bitmap bitmap) {
        Glide.with(this).load(bitmap).into(mCircleImageView);
    }

    @Override
    public void setHeadName(String name) {
        mTextViewName.setText(name);
    }

    @Override
    public void setHeadMotto(String motto) {
        mTextViewMotto.setText(motto);
    }

    /**
     * 上传头像
     */
    private void uploadHeadImage() {
        View view = LayoutInflater.from(HomeActivity.this).inflate(R.layout.dialog_imgchoose, null);
        TextView btnCarema = view.findViewById(R.id.btn_camera); //拍照
        TextView btnPhoto = view.findViewById(R.id.btn_photo); //相册
        TextView btnCancel = view.findViewById(R.id.btn_cancel); //取消
        final PopupWindow popupWindow = new PopupWindow(view, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        popupWindow.setBackgroundDrawable(ContextCompat.getDrawable(HomeActivity.this, android.R.color.transparent));
        popupWindow.setOutsideTouchable(true);
        View parent = LayoutInflater.from(HomeActivity.this).inflate(R.layout.activity_home, null);
        popupWindow.showAtLocation(parent, Gravity.BOTTOM, 0, 0);
        //popupWindow在弹窗的时候背景半透明
        final WindowManager.LayoutParams params = getWindow().getAttributes();
        params.alpha = 0.5f;
        getWindow().setAttributes(params);
        popupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                params.alpha = 1.0f;
                getWindow().setAttributes(params);
            }
        });
        btnCarema.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //权限判断
                if (ContextCompat.checkSelfPermission(HomeActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                        != PackageManager.PERMISSION_GRANTED) {
                    //申请WRITE_EXTERNAL_STORAGE权限
                    ActivityCompat.requestPermissions(HomeActivity.this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                            CommonUtil.WRITE_EXTERNAL_STORAGE_REQUEST_CODE);
                } else {
                    //跳转到调用系统相机
                    gotoCamera();
                }
                popupWindow.dismiss();
            }
        });
        btnPhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //权限判断
                if (ContextCompat.checkSelfPermission(HomeActivity.this, Manifest.permission.READ_EXTERNAL_STORAGE)
                        != PackageManager.PERMISSION_GRANTED) {
                    //申请READ_EXTERNAL_STORAGE权限
                    ActivityCompat.requestPermissions(HomeActivity.this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                            CommonUtil.READ_EXTERNAL_STORAGE_REQUEST_CODE);
                } else {
                    //跳转到相册
                    gotoPhoto();
                }
                popupWindow.dismiss();
            }
        });
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popupWindow.dismiss();
            }
        });
    }

    /**
     * 外部存储权限申请返回
     */
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == CommonUtil.WRITE_EXTERNAL_STORAGE_REQUEST_CODE) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // Permission Granted
                gotoCamera();
            }
        } else if (requestCode == CommonUtil.READ_EXTERNAL_STORAGE_REQUEST_CODE) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // Permission Granted
                gotoPhoto();
            }
        }
    }

    /**
     * 跳转到相册
     */
    private void gotoPhoto() {
        Log.d("evan", "*****************打开图库********************");
        //跳转到调用系统图库
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(Intent.createChooser(intent, "请选择图片"), CommonUtil.REQUEST_PICK);
    }


    /**
     * 跳转到照相机
     */
    private void gotoCamera() {
        Log.d("evan", "*****************打开相机********************");
        //创建拍照存储的图片文件
        tempFile = new File(FileUtil.checkDirPath(Environment.getExternalStorageDirectory().getPath() + "/image/"), System.currentTimeMillis() + ".jpg");

        //跳转到调用系统相机
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            //设置7.0中共享文件，分享路径定义在xml/file_paths.xml
            intent.setFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
            Uri contentUri = FileProvider.getUriForFile(HomeActivity.this, BuildConfig.APPLICATION_ID + ".fileProvider", tempFile);
            intent.putExtra(MediaStore.EXTRA_OUTPUT, contentUri);
        } else {
            intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(tempFile));
        }
        startActivityForResult(intent, CommonUtil.REQUEST_CAPTURE);
    }

    /**
     * 调用某个活动后的返回处理
     *
     * @param requestCode
     * @param resultCode
     * @param intent
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
        switch (requestCode) {
            case CommonUtil.REQUEST_CAPTURE: //调用系统相机返回
                if (resultCode == RESULT_OK) {
                    gotoClipActivity(Uri.fromFile(tempFile));
                }
                break;
            case CommonUtil.REQUEST_PICK:  //调用系统相册返回
                if (resultCode == RESULT_OK) {
                    Uri uri = intent.getData();
                    gotoClipActivity(uri);
                }
                break;
            case CommonUtil.REQUEST_CROP_PHOTO:  //剪切图片返回
                if (resultCode == RESULT_OK) {
                    final Uri uri = intent.getData();
                    if (uri == null) {
                        return;
                    }
                    String cropImagePath = getRealFilePathFromUri(getApplicationContext(), uri);
                    Bitmap bitMap = BitmapFactory.decodeFile(cropImagePath);

                    if (type == 1) {
                        //上传图片
                        DataManager dataManager = DataManager.getInstance();
                        File file = new File(cropImagePath);
                        // 创建 RequestBody，用于封装构建RequestBody
                        RequestBody requestFile = RequestBody.create(MediaType.parse("multipart/form-data"), file);
                        // MultipartBody.Part  和后端约定好Key，这里的partName是用image
                        MultipartBody.Part body = MultipartBody.Part.createFormData("headPortrait", file.getName(), requestFile);
                        RequestBody username = RequestBody.create(MediaType.parse("text/x-markdown"), "lisi");
                        dataManager.successHandler(dataManager.uploadPng(username, body), new DataManager.callBack() {
                            @Override
                            public <T> void run(T t) {
                                mCircleImageView.setImageBitmap(bitMap);
                            }
                        });
                    } else {
                        mCircleImageView.setImageBitmap(bitMap);
                    }
                }
                break;
        }
    }


    /**
     * 打开截图界面
     */
    public void gotoClipActivity(Uri uri) {
        if (uri == null) {
            return;
        }
        Intent intent = new Intent();
        intent.setClass(this, ClipImageActivity.class);
        intent.putExtra("type", type);
        intent.setData(uri);
        startActivityForResult(intent, CommonUtil.REQUEST_CROP_PHOTO);
    }
}
