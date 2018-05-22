package com.jiangxfei.mymvp;

import android.Manifest;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.jiangxfei.mymvp.app.home.HomeActivity;
import com.jiangxfei.mymvp.base.BaseActivity;

import java.util.List;
import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import pub.devrel.easypermissions.EasyPermissions;

/**
 * @author JiangXFei
 */
public class WelcomeActivity extends BaseActivity
        implements EasyPermissions.PermissionCallbacks {

    private int LOCATIONCODE = 0x0001;
    private static final int TIME = 3;

    private int systemId = 1;
    private String[] perms = {Manifest.permission.ACCESS_COARSE_LOCATION};

    @BindView(R.id.btn_text)
    Button btnText;

    @Override
    protected void initView(Bundle savedInstanceState) {
        setContentView(R.layout.activity_welcome);
        ButterKnife.bind(this);
        initPrimiss();
    }

    @Override
    protected void initData() {
    }

    @Override
    protected void initDo(Bundle savedInstanceState) {

    }

    /**
     * 权限处理
     */
    private void initPrimiss() {
        if (EasyPermissions.hasPermissions(this, perms)) {
            initWelcome();
        } else {
            EasyPermissions.requestPermissions(this, "需要您的位置权限。",
                    LOCATIONCODE, perms);
        }
    }


    /**
     * 权限请求结果
     *
     * @param requestCode
     * @param permissions
     * @param grantResults
     */
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this);
    }

    /**
     * 权限请求成功
     *
     * @param requestCode
     * @param perms
     */
    @Override
    public void onPermissionsGranted(int requestCode, @NonNull List<String> perms) {
        initWelcome();
    }

    /**
     * 权限请求失败
     *
     * @param requestCode
     * @param perms
     */
    @Override
    public void onPermissionsDenied(int requestCode, @NonNull List<String> perms) {
        Toast.makeText(mContext, "没有定位权限，将无法获取到您的位置！", Toast.LENGTH_LONG).show();
    }

    /**
     * 初始化闪屏动画
     */
    private void initWelcome() {
        Observable
                .interval(0, 1, TimeUnit.SECONDS)
                .take(TIME)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Long>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(Long aLong) {
                        btnText.setText(TIME - aLong + "s");
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {
                        startActivity(new Intent(mContext, HomeActivity.class));
                        finish();
                    }
                });
    }


    @OnClick(R.id.btn_text)
    public void onViewClicked(View v) {
        switch (v.getId()) {
            case R.id.btn_text:
                //用户按钮点击事件
                startActivity(new Intent(mContext, HomeActivity.class));
                break;
        }
    }
}
