package com.jiangxfei.mymvp.app.home.adapter;

import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.jiangxfei.mymvp.R;
import com.jiangxfei.mymvp.app.home.bean.UserBean;

import java.util.List;

/**
 * @author: JiangXFei
 * @date: 2018/1/9 0009
 * @content:
 */

public class RecyLoadAdapter extends BaseQuickAdapter<UserBean, BaseViewHolder> {

    public RecyLoadAdapter(int layoutResId, @Nullable List<UserBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, UserBean item) {
        helper.setText(R.id.tv1, item.getUid());
        helper.setText(R.id.tv2, item.getUsername());
        helper.setText(R.id.tv3, item.getRealname());
    }
}
