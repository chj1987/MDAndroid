package com.cn.ly.module_main;

import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alibaba.android.arouter.launcher.ARouter;
import com.ashokvarma.bottomnavigation.BottomNavigationBar;
import com.ashokvarma.bottomnavigation.BottomNavigationItem;
import com.cn.ly.component_base.base.BaseActivity;
import com.cn.ly.component_base.utils.StateBarUtils;
import com.cn.ly.module_main.login.LoginActivity;

import java.lang.reflect.Field;

import butterknife.BindView;

public class MainActivity extends BaseActivity<MainActivity, MainPresenter> implements NavigationView.OnNavigationItemSelectedListener, BottomNavigationBar.OnTabSelectedListener {
    @BindView(R2.id.toolbar)
    Toolbar mToolBar;

    @BindView(R2.id.bottom_bar)
    BottomNavigationBar mBottomBar;


    @BindView(R2.id.drawerlayout)
    DrawerLayout mDrawerLayout;

    @BindView(R2.id.nav_view)
    NavigationView mNavigationView;

    @BindView(R2.id.tv)
    TextView tv;

    private ActionBarDrawerToggle mToggle;
    private int statusBarHeight;
    private ImageView mIvHead;

    @Override
    protected int initLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    protected MainPresenter createPresenter() {
        return new MainPresenter();
    }

    @Override
    public Context getContext() {
        return this;
    }

    @Override
    protected void init(Bundle savedInstanceState) {
        initStatuView();

        initToolBar();

        initBottomBar();

        initLeftMenu();
    }

    private void initLeftMenu() {
        mNavigationView.setNavigationItemSelectedListener(this);
        View view = mNavigationView.inflateHeaderView(R.layout.navition_head);
        mIvHead = (ImageView) view.findViewById(R.id.iv_head);
        mIvHead.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });
    }

    private void initBottomBar() {
        mBottomBar.setMode(BottomNavigationBar.MODE_FIXED)
                .setBackgroundStyle(BottomNavigationBar.BACKGROUND_STYLE_RIPPLE);

        mBottomBar.addItem(new BottomNavigationItem(R.mipmap.ic_home_select, "首页")
                .setInactiveIcon(ContextCompat.getDrawable(MainActivity.this, R.mipmap.ic_home_unselect)))
                .addItem(new BottomNavigationItem(R.mipmap.ic_video_select, "视频")
                        .setInactiveIcon(ContextCompat.getDrawable(MainActivity.this, R.mipmap.ic_video_unselect)))
                .addItem(new BottomNavigationItem(R.mipmap.ic_qita_select, "商城")
                        .setInactiveIcon(ContextCompat.getDrawable(MainActivity.this, R.mipmap.ic_qita_unselect)))
                .addItem(new BottomNavigationItem(R.mipmap.ic_my_select, "我的")
                        .setInactiveIcon(ContextCompat.getDrawable(MainActivity.this, R.mipmap.ic_my_unselect)))
                .initialise();
        setBottomNavigationItem(7, 56);

        mBottomBar.setTabSelectedListener(this);
    }

    private void initStatuView() {
        StateBarUtils.setStateBarTranslucent(this);
        //获得状态栏高度
        int resourceId = MainActivity.this.getResources().getIdentifier("status_bar_height", "dimen", "android");
        int stateBarHeight = getResources().getDimensionPixelSize(resourceId);
        //设置margin
        RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) mToolBar.getLayoutParams();
        layoutParams.setMargins(0, stateBarHeight, 0, 0);
        mToolBar.setLayoutParams(layoutParams);

        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
            //将侧边栏顶部延伸至status bar
            mDrawerLayout.setFitsSystemWindows(true);
            //将主页面顶部延伸至status bar;虽默认为false,但经测试,DrawerLayout需显示设置
            mDrawerLayout.setClipToPadding(false);
        }
    }

    private void initToolBar() {
        setSupportActionBar(mToolBar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setHomeButtonEnabled(true);

        mToggle = new ActionBarDrawerToggle(MainActivity.this
                , mDrawerLayout, mToolBar
                , R.string.app_name
                , R.string.app_name);
        mToggle.syncState();
        mDrawerLayout.addDrawerListener(mToggle);
    }

    private void setBottomNavigationItem(int space, int imgLen) {
        float contentLen = 36;
        Class barClass = mBottomBar.getClass();
        Field[] fields = barClass.getDeclaredFields();
        for (int i = 0; i < fields.length; i++) {
            Field field = fields[i];
            field.setAccessible(true);
            if (field.getName().equals("mTabContainer")) {
                try { //反射得到 mTabContainer
                    LinearLayout mTabContainer = (LinearLayout) field.get(mBottomBar);
                    for (int j = 0; j < mTabContainer.getChildCount(); j++) {
                        //获取到容器内的各个 Tab
                        View view = mTabContainer.getChildAt(j);
                        //获取到Tab内的各个显示控件
                        // 获取到Tab内的文字控件
                        TextView labelView = (TextView) view.findViewById(com.ashokvarma.bottomnavigation.R.id.fixed_bottom_navigation_title);
                        //计算文字的高度DP值并设置，setTextSize为设置文字正方形的对角线长度，所以：文字高度（总内容高度减去间距和图片高度）*根号2即为对角线长度，此处用DP值，设置该值即可。
                        labelView.setTextSize(TypedValue.COMPLEX_UNIT_DIP, (float) (Math.sqrt(2) * (contentLen - imgLen - space)));
                        //获取到Tab内的图像控件
                        ImageView iconView = (ImageView) view.findViewById(com.ashokvarma.bottomnavigation.R.id.fixed_bottom_navigation_icon);
                        //设置图片参数，其中，MethodUtils.dip2px()：换算dp值
                        FrameLayout.LayoutParams params = new FrameLayout.LayoutParams((int) (imgLen), (int) (imgLen));
                        params.gravity = Gravity.CENTER;
                        iconView.setLayoutParams(params);
                    }
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        mToggle.onConfigurationChanged(newConfig);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (mToggle.onOptionsItemSelected(item)) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        mDrawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onBackPressed() {
        if (mDrawerLayout.isDrawerOpen(GravityCompat.START)) {
            mDrawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
    }

    @Override
    public void onTabSelected(int position) {
        if (position == 0) {
            ARouter.getInstance()
                    .build("/module/HomeActivity")
                    .navigation();
        }
    }

    @Override
    public void onTabUnselected(int position) {

    }

    @Override
    public void onTabReselected(int position) {

    }
}