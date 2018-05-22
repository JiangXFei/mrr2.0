package com.jiangxfei.mymvp.rx;

import android.content.Context;

import com.jiangxfei.mymvp.config.Constants;
import com.jiangxfei.mymvp.util.StringUtil;
import com.jiangxfei.mymvp.application.MyApplication;
import com.jiangxfei.mymvp.config.APIService;
import com.jiangxfei.mymvp.config.APIUrl;
import com.jiangxfei.mymvp.rx.net.NetUtils;
import com.jiangxfei.mymvp.rx.net.OnLoadCallback;
import com.jiangxfei.mymvp.rx.net.SSLHelper;
import com.jiangxfei.mymvp.util.LogUtil;
import com.jiangxfei.mymvp.util.SharedPreferencesUtil;
import com.jiangxfei.mymvp.util.SystemoutUtils;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLSession;

import okhttp3.Cache;
import okhttp3.CacheControl;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;


/**
 * @className RetrofitUtils
 * @描述 Retofit网络请求工具类
 * @作者lml
 * @日期2017/1/23 9:36
 * @修改日期
 * @版本
 */
public class RetrofitUtils {

    private static int ks = 2;
    private static final int READ_TIMEOUT = 60;//读取超时时间,单位  秒
    private static final int CONN_TIMEOUT = 12;//连接超时时间,单位  秒
    private static Retrofit mRetrofit;
    private static volatile RetrofitUtils instance;
    private static Context mContext;

    public RetrofitUtils(Context context, String url) {
        mContext = context;
        if (StringUtil.isNotNull(url)) {
            if (url.contains("https")) {
                Constants.isHTTPS = true;
            } else {
                Constants.isHTTPS = false;
            }
            mRetrofit = new Retrofit.Builder()
                    .client(getOkHttpClient())
                    .baseUrl(url)
                    .addConverterFactory(GsonConverterFactory.create())
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .build();
        }

    }

    /**
     * @描述 单例
     * @返回
     * @参数
     * @作者 lml
     * @time 2017/1/19  14:32
     */
    public static RetrofitUtils getInstance(Context context, String url) {
        if (instance == null) {
            synchronized (RetrofitUtils.class) {
                if (instance == null) {
                    instance = new RetrofitUtils(context, url);
                    return instance;
                }
            }
        } else {
            instance = new RetrofitUtils(context, url);
        }
        return instance;
    }

    /**
     * 初始化OkHttp
     *
     * @return
     */
    private OkHttpClient getOkHttpClient() {
        //cache url :data/data/<package-name>/cache/response
        //参数1：服务器证书文件的InputStream
        //参数2：本地证书文件inputstream
        //参数3：密码
        SSLHelper.SSLParams sslParams = null;
        sslParams = SSLHelper.getSslSocketFactory(null, null, null);
//        if(Constants.isHTTPS){
//            sslParams = SSLHelper.getSslSocketFactory(null,null,null);
//        }else {
//            try {
//                sslParams = SSLHelper.getSslSocketFactory(new InputStream[] {mContext.getAssets().open("bks/truststore.bks")}, mContext.getAssets().open("bks/client.bks"),"123456");
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//        }
        File httpCacheDirectory = new File(mContext.getCacheDir(), "responses");
        int cacheSize = 10 * 1024 * 1024; // 10 MiB
        Cache cache = new Cache(httpCacheDirectory, cacheSize);
        OkHttpClient.Builder okHttpClient = new OkHttpClient().newBuilder();

//        OkHttpClient okHttpClient1 = new OkHttpClient.Builder()
//                .readTimeout(READ_TIMEOUT, TimeUnit.MINUTES)//设置读取时间为一分钟
//                .retryOnConnectionFailure(true)//设置出现错误进行重新连接
//                .connectTimeout(CONN_TIMEOUT, TimeUnit.SECONDS)//设置连接时间为12s
//                .addInterceptor(getHttpLoggingInterceptor())
////                .addInterceptor(new ParamsInterceptor())//配置参数拦截器
//                .addInterceptor(getInterceptor())//缓存拦截器
//                .writeTimeout(10, TimeUnit.SECONDS)
//                .cache(cache)
//                .sslSocketFactory(sslParams.sSLSocketFactory, sslParams.trustManager)
//                .hostnameVerifier(
//                        new HostnameVerifier() {
//                            @Override
//                            public boolean verify(String hostname, SSLSession session) {
//                                return true;
//                            }
//                        })
//                .build();
        okHttpClient.connectTimeout(CONN_TIMEOUT, TimeUnit.SECONDS);
        okHttpClient.writeTimeout(10, TimeUnit.SECONDS);
        okHttpClient.readTimeout(READ_TIMEOUT, TimeUnit.MINUTES);
        okHttpClient.interceptors().add(getHttpLoggingInterceptor());
        okHttpClient.interceptors().add(getInterceptor());
        okHttpClient.interceptors().add(getCookie());
        okHttpClient.interceptors().add(setCookie());
        okHttpClient.hostnameVerifier(
                new HostnameVerifier() {
                    @Override
                    public boolean verify(String hostname, SSLSession session) {
                        return true;
                    }
                }
        );
        okHttpClient.retryOnConnectionFailure(true);
        okHttpClient.sslSocketFactory(sslParams.sSLSocketFactory, sslParams.trustManager);
        return okHttpClient.build();
    }

    /**
     * 设置返回数据的  Interceptor  判断网络   没网读取缓存
     * CacheControl.FORCE_CACHE; //仅仅使用缓存
     * CacheControl.FORCE_NETWORK;// 仅仅使用网络
     *
     * @return
     */
    public Interceptor getInterceptor() {
        return new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                Request request = chain.request();
                if (!NetUtils.isConnected(MyApplication.getContext())) {//无网络
                    request = request.newBuilder()
                            .cacheControl(CacheControl.FORCE_CACHE)
                            .build();

                }
                Response response = chain.proceed(request);
                if (NetUtils.isConnected(mContext)) {
                    int maxAge = 10;// 在线缓存在6s内可读取
                    String cacheControl = request.cacheControl().toString();

                    response.newBuilder()
                            .removeHeader("Pragma")
                            .removeHeader("Cache-Control")
                            .header("Cache-Control", "public, max-age=" + maxAge)
                            .build();
                } else {

                    // 无网络时，设置超时为1周
                    int maxStale = 60 * 60 * 24 * 7;
                    response.newBuilder()
                            .removeHeader("Pragma")
                            .removeHeader("Cache-Control")
                            .header("Cache-Control", "public, only-if-cached, max-stale=" + maxStale)
                            .build();
                }

                return response;
            }
        };
    }

    /**
     * 获取COOKIE
     */
    public Interceptor getCookie() {
        return new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                // 获取 Cookie
                Response resp = chain.proceed(chain.request());
                List<String> cookies = resp.headers("Set-Cookie");
                String cookieStr = "";
                if (cookies != null && cookies.size() > 0) {
                    for (int i = 0; i < cookies.size(); i++) {
                        cookieStr += cookies.get(i);
                    }
                    SharedPreferencesUtil.putString("cookie", cookieStr);
                }
                return resp;
            }
        };
    }

    /**
     * 设置COOKIE
     */
    public Interceptor setCookie() {
        return new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                // 设置 Cookie
                String cookieStr = SharedPreferencesUtil.getString("cookie", null);
                if (StringUtil.isNotNull(cookieStr)) {
                    return chain.proceed(chain.request().newBuilder().header("Cookie", cookieStr).build());
                }
                return chain.proceed(chain.request());
            }
        };
    }

    /**
     * HttpLog拦截器
     *
     * @return
     */
    private static HttpLoggingInterceptor getHttpLoggingInterceptor() {
        HttpLoggingInterceptor httpLoggingInterceptor = new HttpLoggingInterceptor(new HttpLoggingInterceptor.Logger() {

            @Override
            public void log(String message) {
                //打印json日志
                LogUtil.log("----json----", message);
            }
        });
        httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        return httpLoggingInterceptor;
    }

    //上传图片
    public void post_file(final String url, final Map<String, Object> map, File file, final OnLoadCallback callBack) {
        OkHttpClient client = new OkHttpClient();

        // form 表单形式上传
        MultipartBody.Builder requestBody = new MultipartBody.Builder().setType(MultipartBody.FORM);
        if (map != null) {
            // map 里面是请求中所需要的 key 和 value
            for (Map.Entry entry : map.entrySet()) {
                requestBody.addFormDataPart(String.valueOf(entry.getKey()), String.valueOf(entry.getValue()));
            }
        }
        if (file != null) {
            // MediaType.parse() 里面是上传的文件类型。
            //    RequestBody body = RequestBody.create(MediaType.parse("image/png"), file);
            RequestBody body = RequestBody.create(MediaType.parse("application/octet-stream"), file);
            String filename = file.getName();
            SystemoutUtils.systemOut("---文件名称--" + filename);
            // 参数分别为， 请求key ，文件名称 ， RequestBody
//            requestBody.addPart(
//                    Headers.of("Content-Disposition", "form-data; name=\"image\""),
//                    RequestBody.create(MEDIA_TYPE_PNG, new File("website/static/logo-square.png")))
//                    .build();
            requestBody.addFormDataPart("file", filename, body);
        }
        Request request = new Request.Builder().url(url).post(requestBody.build()).build();
        // readTimeout("请求超时时间" , 时间单位);
        //.readTimeout(5000, TimeUnit.MILLISECONDS)
        client.newBuilder()
                .addInterceptor(getInterceptor())
                .addInterceptor(setCookie())
                .build().newCall(request).enqueue(
                new Callback() {
                    @Override
                    public void onFailure(Call call, IOException e) {
                        SystemoutUtils.systemOut("---失败---");
                        callBack.onFailure("fail", 500);
                    }

                    @Override
                    public void onResponse(Call call, Response response) throws IOException {
                        SystemoutUtils.systemOut("-----成功1---------" + response.body().string());
//                        new Thread(new Runnable() {
//                            @Override
//                            public void run() {
//                                SystemoutUtils.systemOut("-----成功1---------" + res);
//                            }
//                        }).start();
                    }
                }
        );
    }

    /**
     * 创建api接口
     *
     * @param cls api接口类
     * @param <T> api接口类型
     * @return
     */
    public <T> T create(Class<T> cls) {
        return mRetrofit.create(cls);
    }

    /**
     * @描述 获得apiservice
     * @返回
     * @参数
     * @作者 lml
     * @time 2017/1/22  10:26
     */
    public static APIService getApiService(Context context) {
        return RetrofitUtils.getInstance(context, APIUrl.API_URL).create(APIService.class); //创建Rxjava
    }

    public static APIService getApiService(Context context, String url) {
        return RetrofitUtils.getInstance(context, url).create(APIService.class); //创建Rxjava
    }
}
