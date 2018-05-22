package com.jiangxfei.mymvp.app.home.mvp;

import android.content.Context;
import android.widget.Toast;

import com.jiangxfei.mymvp.app.home.bean.UserBean;
import com.jiangxfei.mymvp.rx.net.OnLoadCallback;

import java.util.List;

/**
 * @author: JiangXFei
 * @date: 2018/1/9 0009
 * @content:
 */

public class HomePersenter implements HomeConstants.LoadAllPersenter {
    private Context mContext;

    private HomeConstants.LoadAllModel mLoadAllModel;
    private HomeConstants.LoadAllView mLoadAllView;

    public HomePersenter(Context mContext, HomeConstants.LoadAllView mLoadAllView) {
        this.mContext = mContext;
        this.mLoadAllView = mLoadAllView;
        this.mLoadAllModel = new HomeModel();
    }

    @Override
    public void loadAll() {
        mLoadAllModel.loadAll(mContext, new OnLoadCallback() {
            @Override
            public void onSuccess(Object o, int resultCode) {

            }

            @Override
            public void onSuccess(Object o, String message, int resultCode) {
                Toast.makeText(mContext, message, Toast.LENGTH_SHORT).show();
                List<UserBean> userList = (List<UserBean>) o;
                if (userList.size() > 0) {
                    mLoadAllView.loadOnSuccess(userList);
                }
            }

            @Override
            public void onFailure(String msg, int resultCode) {

            }
        });
    }
}
