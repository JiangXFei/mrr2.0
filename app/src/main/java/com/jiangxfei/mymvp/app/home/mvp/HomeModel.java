package com.jiangxfei.mymvp.app.home.mvp;

import android.content.Context;

import com.jiangxfei.mymvp.app.home.bean.UserBean;
import com.jiangxfei.mymvp.rx.RetrofitUtils;
import com.jiangxfei.mymvp.rx.net.HttpResult;
import com.jiangxfei.mymvp.rx.net.OnLoadCallback;
import com.jiangxfei.mymvp.util.LogUtil;

import java.util.List;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * @author: JiangXFei
 * @date: 2018/1/9 0009
 * @content:
 */

public class HomeModel implements HomeConstants.LoadAllModel {
    @Override
    public void loadAll(Context mContext, OnLoadCallback callback) {
        RetrofitUtils.getApiService(mContext)
                .loadAll()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<HttpResult<List<UserBean>>>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(HttpResult<List<UserBean>> listHttpResult) {
                        callback.onSuccess(listHttpResult.getData(), listHttpResult.getMsg(), listHttpResult.getCode());
                    }

                    @Override
                    public void onError(Throwable e) {
                        LogUtil.e(e.toString());
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }
}
