package com.cn.ly.component_base.utils;

import java.util.List;

/**
 * Created by ff on 2018/5/24.
 */

public interface PermissionListener {
    void onGranted();

    void onGranted(List<String> deniedPermissions);
}
