package com.cn.ly.component_base.mvp;

public interface IBasePresenter<V extends IBaseView> {
    void attachView(V view);

    void detachView();
}
