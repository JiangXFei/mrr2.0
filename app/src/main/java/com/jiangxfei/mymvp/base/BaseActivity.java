package com.jiangxfei.mymvp.base;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.KeyEvent;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.jiangxfei.mymvp.R;
import com.jiangxfei.mymvp.util.ExitUtil;

/**
 * @author: JiangXFei
 * @date: 2018/1/8 0008
 * @content:
 */

public abstract class BaseActivity extends Activity {
    protected Activity mActivity;
    protected Context mContext;
    private long firstTime;
    private long spaceTime;
    private long secondTime;
    protected ImageView imgMore;
    protected ImageView imgBack;
    protected TextView tvTitle;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = this;
        mActivity = this;
        ExitUtil.getInstance().addActivity(this);
        initView(savedInstanceState);
        initData();
        initDo(savedInstanceState);
    }

    /**
     * 初始化布局，控件
     *
     * @param savedInstanceState
     */
    protected abstract void initView(Bundle savedInstanceState);

    /**
     * 初始化数据
     */
    protected abstract void initData();


    /**
     * 初始化业务逻辑
     *
     * @param savedInstanceState
     */
    protected abstract void initDo(Bundle savedInstanceState);

    /**
     * 初始化带返回按钮的头部
     */
    public void initBackTilte() {
        imgMore = findViewById(R.id.img_right_01);
        imgBack = findViewById(R.id.img_left_01);
        tvTitle = findViewById(R.id.tv_center_01);
    }
    public void initBackTilte(String title) {
        imgMore = findViewById(R.id.img_right_01);
        imgBack = findViewById(R.id.img_left_01);
        tvTitle = findViewById(R.id.tv_center_01);
        tvTitle.setText(title);
    }
    /**
     * 双击退出
     */
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        String currentActivityName = this.getClass().getSimpleName();
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0
                && currentActivityName.equals("HomeActivity")) {
            firstTime = System.currentTimeMillis();
            spaceTime = firstTime - secondTime;
            secondTime = firstTime;
            if (spaceTime > 2000) {
                Toast.makeText(mContext, "点击第二次退出", Toast.LENGTH_SHORT).show();
                return false;
            } else {
                ExitUtil.getInstance().exit();
            }
        }
        return super.onKeyDown(keyCode, event);
    }
}
