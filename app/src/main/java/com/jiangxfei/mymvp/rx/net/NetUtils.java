package com.jiangxfei.mymvp.rx.net;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.provider.Settings;
import android.telephony.TelephonyManager;

/**
 * 类描述：网络
 * 项目名称：android_9cvc
 * 创建人：
 * 创建时间：2015/9/14 15:48
 */
public class NetUtils {
    private NetUtils() {
        /* cannot be instantiated */
        throw new UnsupportedOperationException("cannot be instantiated");
    }

    /**
     * 判断网络是否连接
     *
     * @param context
     * @return
     */
    public static boolean isConnected(Context context) {

        ConnectivityManager connectivity = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        if (null != connectivity) {
            NetworkInfo info = connectivity.getActiveNetworkInfo();
            if (null != info && info.isConnected()) {
                if (info.getState() == NetworkInfo.State.CONNECTED) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * 判断是否是wifi连接
     */
    public static boolean isWifi(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);

        if (cm == null)
            return false;
        return cm.getActiveNetworkInfo().getType() == ConnectivityManager.TYPE_WIFI;

    }

    /**
     * 打开网络设置界面
     */
    public static void openSetting(Context activity) {

        Intent intent;
        //判断手机系统的版本  即API大于10 就是3.0或以上版本
        if (android.os.Build.VERSION.SDK_INT > 10) {
            intent = new Intent(Settings.ACTION_SETTINGS);
        } else {
            intent = new Intent("/");
            ComponentName component = new ComponentName("com.android.settings", "com.android.settings.Settings");
            intent.setComponent(component);
            intent.setAction("android.intent.action.VIEW");
        }
        activity.startActivity(intent);

    }

    // / 没有连接

    public static final String NETWORN_NONE = "";

// / wifi连接

    public static final String NETWORN_WIFI = "wifi";

// / 手机网络数据连接

    public static final String NETWORN_2G = "2G";

    public static final String NETWORN_3G = "3G";

    public static final String NETWORN_4G = "4G";

    public static final String NETWORN_MOBILE = "无";


    /**
     * 获得网络类型
     *
     * @Description:
     * @ClassName:NetUtils
     * @date 2016-5-11 13:39
     * @author lml
     */
    public static String getNetworkState(Context context) {

        ConnectivityManager connManager = (ConnectivityManager) context

                .getSystemService(Context.CONNECTIVITY_SERVICE);

        if (null == connManager)

            return NETWORN_NONE;

        NetworkInfo activeNetInfo = connManager.getActiveNetworkInfo();

        if (activeNetInfo == null || !activeNetInfo.isAvailable()) {

            return NETWORN_NONE;

        }
        // Wifi

        NetworkInfo wifiInfo = connManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);

        if (null != wifiInfo) {

            NetworkInfo.State state = wifiInfo.getState();

            if (null != state)

                if (state == NetworkInfo.State.CONNECTED || state == NetworkInfo.State.CONNECTING) {

                    return NETWORN_WIFI;

                }

        }


        // 网络

        NetworkInfo networkInfo = connManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);

        if (null != networkInfo) {

            NetworkInfo.State state = networkInfo.getState();

            String strSubTypeName = networkInfo.getSubtypeName();

            if (null != state)

                if (state == NetworkInfo.State.CONNECTED || state == NetworkInfo.State.CONNECTING) {

                    switch (activeNetInfo.getSubtype()) {

                        case TelephonyManager.NETWORK_TYPE_GPRS: // 联通2g

                        case TelephonyManager.NETWORK_TYPE_CDMA: // 电信2g

                        case TelephonyManager.NETWORK_TYPE_EDGE: // 移动2g

                        case TelephonyManager.NETWORK_TYPE_1xRTT:

                        case TelephonyManager.NETWORK_TYPE_IDEN:

                            return NETWORN_2G;

                        case TelephonyManager.NETWORK_TYPE_EVDO_A: // 电信3g

                        case TelephonyManager.NETWORK_TYPE_UMTS:

                        case TelephonyManager.NETWORK_TYPE_EVDO_0:

                        case TelephonyManager.NETWORK_TYPE_HSDPA:

                        case TelephonyManager.NETWORK_TYPE_HSUPA:

                        case TelephonyManager.NETWORK_TYPE_HSPA:

                        case TelephonyManager.NETWORK_TYPE_EVDO_B:

                        case TelephonyManager.NETWORK_TYPE_EHRPD:

                        case TelephonyManager.NETWORK_TYPE_HSPAP:

                            return NETWORN_3G;

                        case TelephonyManager.NETWORK_TYPE_LTE:

                            return NETWORN_4G;

                        default://有机型返回16,17

                            //中国移动 联通 电信 三种3G制式

                            if (strSubTypeName.equalsIgnoreCase("TD-SCDMA") || strSubTypeName.equalsIgnoreCase("WCDMA") || strSubTypeName.equalsIgnoreCase("CDMA2000")) {

                                return NETWORN_3G;

                            } else {

                                return NETWORN_MOBILE;

                            }

                    }

                }

        }

        return NETWORN_NONE;


    }
}