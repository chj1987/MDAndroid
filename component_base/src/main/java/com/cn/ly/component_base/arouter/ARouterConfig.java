package com.cn.ly.component_base.arouter;

import android.app.Application;

import com.alibaba.android.arouter.launcher.ARouter;

public class ARouterConfig {
    public static void init(Application application, boolean isDebug) {
        if (isDebug) {
            ARouter.openLog();
            ARouter.openDebug();
        }
        ARouter.init(application);
    }
}
