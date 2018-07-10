package com.cn.ly.component_base.widget;

import com.cn.ly.component_base.mvp.IBaseView;

import io.reactivex.subscribers.ResourceSubscriber;

public abstract class CommonSubscriber<T> extends ResourceSubscriber<T> {
    private IBaseView view;


    public CommonSubscriber(IBaseView view) {
        this.view = view;
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    public void onComplete() {

    }

    @Override
    public void onError(Throwable t) {

    }
}
