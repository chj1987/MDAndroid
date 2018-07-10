package com.cn.ly.component_base.utils;

import android.content.Context;
import android.os.Environment;

import com.cn.ly.component_base.base.BaseApplication;
import com.orhanobut.logger.Logger;

import java.io.File;

/**
 * Created by ff on 2018/5/22.
 */

public class FileUtils {
    private static final String TAG = "FileUtils";

    private static final String ROOT_PATH = Environment.getExternalStorageDirectory()
            + File.separator
            + BaseApplication.getAppliactionContext().getPackageName();

    private static final String CACHE = "/cache/";

    /**
     * 在初始化时创建APP所需要的基础文件夹
     * 在6.0以上版本时需要进行权限申请
     *
     * @param context 上下文
     */
    public static void init(Context context) {
        Logger.d(TAG, "Root path is " + ROOT_PATH);
        createFileDir(context, CACHE);
    }

    /**
     * 创建文件夹
     * 在6.0以上版本时需要进行权限申请
     *
     * @param context     上下文
     * @param fileDirName 文件夹名字
     */
    public static void createFileDir(Context context, String fileDirName) {

        File rootDir = new File(ROOT_PATH);
        boolean isRootSuccess = false;
        if (!rootDir.exists()) {
            isRootSuccess = rootDir.mkdirs();
        }

        File fileDir;
        if (isExistSDCard()) {
            fileDir = new File(ROOT_PATH + fileDirName);
        } else {
            fileDir = new File(getInternalPath(context) + fileDirName);
        }

        boolean isFileSuccess = false;
        if (!fileDir.exists()) {
            isFileSuccess = fileDir.mkdirs();
        }

        Logger.d(TAG, "is root dir create success? " + isRootSuccess);
        Logger.d(TAG, "is file dir create success? " + isFileSuccess);

    }

    /**
     * 在没有sdcard时获取内部存储路径
     *
     * @return
     */
    public static String getInternalPath(Context context) {
        Logger.d(TAG, "internal path is " + context.getFilesDir().getPath());
        return context.getFilesDir().getPath() + context.getPackageName();
    }

    /**
     * 检测是否SDCard是否存在
     *
     * @return true：存在 false：不存在
     */
    public static boolean isExistSDCard() {
        return Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED);
    }

}
