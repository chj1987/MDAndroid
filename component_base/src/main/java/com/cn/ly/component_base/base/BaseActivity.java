package com.cn.ly.component_base.base;

import android.app.Activity;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Looper;
import android.os.MessageQueue;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;

import com.cn.ly.component_base.mvp.BasePresenter;
import com.cn.ly.component_base.mvp.IBaseView;
import com.cn.ly.component_base.utils.ActivityUtils;
import com.cn.ly.component_base.utils.PermissionListener;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.Unbinder;


import static com.cn.ly.component_base.utils.Preconditions.checkNotNull;

public abstract class BaseActivity<V extends IBaseView, P extends BasePresenter> extends AppCompatActivity implements IBaseView {
    private static final int CODE_REQUEST_PERMISSION = 1;
    private static PermissionListener mPermissionListener;
    private Unbinder mBind;
    private P mPresenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (initBundle(getIntent().getExtras())) {
            this.setContentView(this.initLayoutId());

//            if (isSwipeBack()) {
//                SwipeBackLayout swipeBackLayout = getSwipeBackLayout();
//                swipeBackLayout.setEdgeTrackingEnabled(SwipeBackLayout.EDGE_LEFT);
//            } else {
//                setSwipeBackEnable(false);
//            }

            mBind = ButterKnife.bind(this);

            //ActivityUtils.addActivity(this);

            init(savedInstanceState);

            mPresenter = getPresenter();
            if (mPresenter == null) {
                mPresenter = createPresenter();
            }
            mPresenter.attachView(getBaseView());

            mPresenter.onCreat();
            //延迟加载数据
            Looper.myQueue().addIdleHandler(new MessageQueue.IdleHandler() {
                @Override
                public boolean queueIdle() {
                    mPresenter.loadData();
                    return false;
                }
            });
        } else {
            finish();
        }
    }

    protected boolean isSwipeBack() {
        return false;
    }

    protected boolean initBundle(Bundle bundle) {
        return true;
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        mPresenter.onSaveInstanceState(outState);
        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onStart() {
        mPresenter.onStart();
        super.onStart();
    }

    @Override
    protected void onResume() {
        mPresenter.onResume();
        super.onResume();
    }

    @Override
    protected void onPause() {
        mPresenter.onPause();
        super.onPause();
    }

    @Override
    protected void onStop() {
        mPresenter.onStop();
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        mPresenter.onDestroy();
        mBind.unbind();
        ActivityUtils.removeAllActivity();
        super.onDestroy();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case CODE_REQUEST_PERMISSION:
                if (grantResults.length > 0) {
                    List<String> deniedPermissions = new ArrayList<>();
                    for (int i = 0; i < grantResults.length; i++) {
                        int result = grantResults[i];
                        if (result != PackageManager.PERMISSION_GRANTED) {
                            String permissition = permissions[i];
                            deniedPermissions.add(permissition);
                        }
                    }

                    if (deniedPermissions.isEmpty()) {
                        mPermissionListener.onGranted();
                    } else {
                        mPermissionListener.onGranted(deniedPermissions);
                    }
                }
                break;
            default:
                break;
        }
    }

    /**
     * @param permissions
     * @param listener
     */
    public static void requestPermissions(String[] permissions, PermissionListener listener) {
        Activity activity = checkNotNull(ActivityUtils.getTopActivity());
        if (activity == null) {
            return;
        }
        mPermissionListener = listener;
        List<String> permissionList = new ArrayList<>();
        for (String permission : permissions) {
            // 没有授权
            if (ContextCompat.checkSelfPermission(activity, permission) != PackageManager.PERMISSION_GRANTED) {
                permissionList.add(permission);
            }
        }
        if (!permissionList.isEmpty()) {
            ActivityCompat.requestPermissions(activity
                    , permissionList.toArray(new String[permissionList.size()])
                    , CODE_REQUEST_PERMISSION);
        } else {
            mPermissionListener.onGranted();
        }
    }

    private V getBaseView() {
        return (V) this;
    }

    protected abstract P createPresenter();

    private P getPresenter() {
        return mPresenter;
    }

    protected void init(Bundle savedInstanceState) {
    }

    protected abstract int initLayoutId();

}
