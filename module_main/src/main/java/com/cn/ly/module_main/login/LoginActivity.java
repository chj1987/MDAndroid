package com.cn.ly.module_main.login;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityOptionsCompat;
import android.transition.Explode;
import android.view.View;
import android.widget.Button;

import com.cn.ly.component_base.base.BaseActivity;
import com.cn.ly.component_base.utils.StateBarUtils;
import com.cn.ly.module_main.R;
import com.cn.ly.module_main.R2;

import butterknife.BindView;
import butterknife.OnClick;


public class LoginActivity extends BaseActivity<LoginActivity, LoginPresent> {
    @BindView(R2.id.btn_login)
    Button mBtnLogin;

    @BindView(R2.id.fab)
    FloatingActionButton mFab;

    @Override
    protected boolean isSwipeBack() {
        return true;
    }

    @Override
    protected int initLayoutId() {
        return R.layout.activity_login;
    }

    @Override
    protected LoginPresent createPresenter() {
        return new LoginPresent();
    }

    @Override
    public Context getContext() {
        return this;
    }

    @Override
    protected void init(Bundle savedInstanceState) {
        StateBarUtils.setStateBarTranslucent(this);
    }

    @OnClick({R2.id.btn_login, R2.id.fab})
    public void click(View view) {
        Explode explode = new Explode();
        explode.setDuration(500);

        getWindow().setEnterTransition(null);
        getWindow().setExitTransition(null);
        ActivityOptionsCompat activityOptionsCompat = ActivityOptionsCompat.makeSceneTransitionAnimation(LoginActivity.this
                , mFab
                , mFab.getTransitionName());
        Intent intent = new Intent(LoginActivity.this, RegistActivity.class);
        startActivity(intent, activityOptionsCompat.toBundle());
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        mFab.setVisibility(View.GONE);
    }

    @Override
    protected void onResume() {
        super.onResume();
        mFab.setVisibility(View.VISIBLE);
    }
}
