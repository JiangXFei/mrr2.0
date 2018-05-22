package com.jiangxfei.mymvp.app.home;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.jiangxfei.mymvp.R;
import com.jiangxfei.mymvp.app.home.adapter.RecyLoadAdapter;
import com.jiangxfei.mymvp.app.home.bean.UserBean;
import com.jiangxfei.mymvp.app.home.mvp.HomeConstants;
import com.jiangxfei.mymvp.app.home.mvp.HomePersenter;
import com.jiangxfei.mymvp.base.BaseActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @author: JiangXFei
 * @date: 2018/1/8 0008
 * @content:
 */

public class HomeActivity extends BaseActivity implements HomeConstants.LoadAllView {

    @BindView(R.id.recy_show)
    RecyclerView recyShow;

    HomeConstants.LoadAllPersenter mLoadAllPersenter;
    private RecyLoadAdapter adapter;
    private List<UserBean> userBeanList;

    @Override
    protected void initView(Bundle savedInstanceState) {
        setContentView(R.layout.activity_home);
        ButterKnife.bind(this);
        mActivity = this;
        mContext = this;
        initBackTilte();
        mLoadAllPersenter = new HomePersenter(mContext, (HomeConstants.LoadAllView) mActivity);
        initRecy();
    }

    private void initRecy() {
        userBeanList = new ArrayList<>();
        adapter = new RecyLoadAdapter(R.layout.item_user, userBeanList);
        recyShow.setLayoutManager(new LinearLayoutManager(mContext));
        recyShow.setAdapter(adapter);
    }

    @Override
    protected void initData() {
        mLoadAllPersenter.loadAll();
    }

    @Override
    protected void initDo(Bundle savedInstanceState) {
        imgBack.setOnClickListener(v -> finish());
    }

    @Override
    public void setData(Object o) {

    }

    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading() {

    }

    @Override
    public void message(String str) {

    }

    @Override
    public void loadOnSuccess(List<UserBean> list) {
        userBeanList.clear();
        userBeanList.addAll(list);
        adapter.notifyDataSetChanged();
    }


}
