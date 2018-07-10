package com.cn.ly.component_base.mvp;

import android.os.Bundle;

import java.lang.ref.WeakReference;

public abstract class BasePresenter<V extends IBaseView> implements IBasePresenter<V> {

    private WeakReference<V> mViewRef;

    @Override
    public void attachView(V view) {
        mViewRef = new WeakReference<>(view);
    }

    @Override
    public void detachView() {
        if (mViewRef != null) {
            mViewRef.clear();
            mViewRef = null;
        }
    }

    protected boolean isViewAttached() {
        return mViewRef != null && mViewRef.get() != null;
    }

    protected V getView() {
        return mViewRef == null ? null : mViewRef.get();
    }

    public abstract void onCreat();

    public abstract void loadData();

    public void onSaveInstanceState(Bundle outState) {

    }

    public void onStart() {
    }

    public void onResume() {
    }

    public void onPause() {

    }

    public void onStop() {

    }

    public void onDestroy() {

    }
}
