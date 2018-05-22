package com.jiangxfei.mymvp.config;

import com.jiangxfei.mymvp.app.home.bean.UserBean;
import com.jiangxfei.mymvp.rx.net.HttpResult;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.http.GET;

/**
 * Created by 上官林超 on 2017/7/28.
 * todo 创建URL 连接
 */

public interface APIService {
    @GET("loadAll")
    Observable<HttpResult<List<UserBean>>> loadAll();
}


