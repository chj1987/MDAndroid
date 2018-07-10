package com.cn.ly.component_base.base;

import android.app.Application;
import android.content.Context;
import android.support.multidex.MultiDex;

import com.cn.ly.component_base.BuildConfig;
import com.cn.ly.component_base.arouter.ARouterConfig;
import com.orhanobut.logger.AndroidLogAdapter;
import com.orhanobut.logger.Logger;

public class BaseApplication extends Application {
    private static boolean IS_DEBUG = BuildConfig.DEBUG;
    private static BaseApplication mApp;

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        mApp = this;
        MultiDex.install(this);
    }

    public static BaseApplication getAppliactionContext() {
        return mApp;
    }

    @Override
    public void onCreate() {
        super.onCreate();

        ARouterConfig.init(mApp, BuildConfig.DEBUG);

        Logger.addLogAdapter(new AndroidLogAdapter() {
            @Override
            public boolean isLoggable(int priority, String tag) {
                return BuildConfig.DEBUG;
            }
        });
    }
}
