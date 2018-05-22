package com.jiangxfei.mymvp.app.home.mvp;

import android.content.Context;

import com.jiangxfei.mymvp.app.home.bean.UserBean;
import com.jiangxfei.mymvp.rx.net.BaseView;
import com.jiangxfei.mymvp.rx.net.OnLoadCallback;

import java.util.List;

/**
 * @author: JiangXFei
 * @date: 2018/1/9 0009
 * @content:
 */

public class HomeConstants {

    public interface LoadAllModel {
       void loadAll(Context mContext, OnLoadCallback callback);
    }

    public interface LoadAllView extends BaseView {
        void loadOnSuccess(List<UserBean> list);
    }

    public interface LoadAllPersenter {
        void loadAll();
    }
}
