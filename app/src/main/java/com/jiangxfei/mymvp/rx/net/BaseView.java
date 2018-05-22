package com.jiangxfei.mymvp.rx.net;

/**
 * @className BaseView
 * @描述 公共View
 * @作者lml
 * @日期2017/1/23 11:28
 * @修改日期
 * @版本
 */
public interface BaseView {
    void setData(Object o);

    void showLoading();

    void hideLoading();

    void message(String str);

}
