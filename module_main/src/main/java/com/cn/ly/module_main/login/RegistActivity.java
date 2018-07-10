package com.cn.ly.module_main.login;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.CardView;
import android.transition.Transition;
import android.transition.TransitionInflater;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.view.animation.AccelerateInterpolator;
import android.widget.Button;

import com.cn.ly.component_base.base.BaseActivity;
import com.cn.ly.component_base.utils.StateBarUtils;
import com.cn.ly.module_main.MainActivity;
import com.cn.ly.module_main.R;
import com.cn.ly.module_main.R2;
import butterknife.BindView;
import butterknife.OnClick;


public class RegistActivity extends BaseActivity<RegistActivity, RegistPresenter> {
    @BindView(R2.id.cv_add)
    CardView mCvContent;

    @BindView(R2.id.fab)
    FloatingActionButton mFab;

    @BindView(R2.id.btn_regist)
    Button mBtnRegist;

    @Override
    protected int initLayoutId() {
        return R.layout.activity_regist;
    }

    @Override
    protected RegistPresenter createPresenter() {
        return new RegistPresenter();
    }


    @Override
    public Context getContext() {
        return this;
    }

    @Override
    protected void init(Bundle savedInstanceState) {
        StateBarUtils.setStateBarTranslucent(this);
        enterAnimation();
    }

    private void enterAnimation() {
        Transition transition = TransitionInflater.from(this).inflateTransition(R.transition.fabtransition);
        getWindow().setSharedElementEnterTransition(transition);

        transition.addListener(new Transition.TransitionListener() {
            @Override
            public void onTransitionStart(Transition transition) {
                mCvContent.setVisibility(View.GONE);
            }

            @Override
            public void onTransitionEnd(Transition transition) {
                transition.removeListener(this);
                revealShowAnimation();
            }

            @Override
            public void onTransitionCancel(Transition transition) {

            }

            @Override
            public void onTransitionPause(Transition transition) {

            }

            @Override
            public void onTransitionResume(Transition transition) {

            }
        });
    }

    private void revealShowAnimation() {
        Animator animator = ViewAnimationUtils.createCircularReveal(mCvContent
                , mCvContent.getWidth() / 2
                , 0
                , mFab.getWidth() / 2
                , mCvContent.getHeight());
        animator.setDuration(500);
        animator.setInterpolator(new AccelerateInterpolator());

        animator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
            }

            @Override
            public void onAnimationStart(Animator animation) {
                mCvContent.setVisibility(View.VISIBLE);
                super.onAnimationStart(animation);
            }
        });

        animator.start();
    }


    @Override
    public void onBackPressed() {
        revealClossAnimation();
    }

    private void revealClossAnimation() {
        Animator animator = ViewAnimationUtils.createCircularReveal(mCvContent
                , mCvContent.getWidth() / 2
                , 0
                , mCvContent.getHeight()
                , mFab.getWidth() / 2);
        animator.setDuration(1000);
        animator.setInterpolator(new AccelerateInterpolator());
        animator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                mCvContent.setVisibility(View.INVISIBLE);
                super.onAnimationEnd(animation);
                mFab.setImageResource(R.mipmap.ic_add);
                RegistActivity.super.onBackPressed();
            }

            @Override
            public void onAnimationStart(Animator animation) {
                super.onAnimationStart(animation);
            }
        });
        animator.start();
    }

    @OnClick({R2.id.fab, R2.id.btn_regist})
    public void click(View view) {
        if (view.getId() == R.id.fab) {
            revealClossAnimation();
        } else if (view.getId() == R.id.btn_regist) {
            startActivity(new Intent(RegistActivity.this, MainActivity.class));
        }
    }
}
