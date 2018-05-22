
package com.jiangxfei.mymvp.util;

import android.text.TextUtils;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

/**
 * 处理字符工具类
 */
public class StringUtil {
    static String[] pinyins = {
            "A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M", "N", "O", "P", "Q",
            "R", "S", "T", "U", "V", "W", "X", "Y", "Z", "#"
    };

    final static int BUFFER_SIZE = 4096;

    /*
     * // /** // * 将字符串中的半角字符替换成全角
     */
    public static String toDBC(String input) {
        char[] c = input.toCharArray();
        for (int i = 0; i < c.length; i++) {
            if (c[i] == 12288) {
                c[i] = (char) 32;
                continue;
            }
            if (c[i] > 65280 && c[i] < 65375)
                c[i] = (char) (c[i] - 65248);
        }
        return new String(c);
    }

    /**
     * ASCII表中可见字符从!开始，偏移位值为33(Decimal)
     */
    static final char DBC_CHAR_START = 33; // 半角!

    /**
     * ASCII表中可见字符到~结束，偏移位值为126(Decimal)
     */
    static final char DBC_CHAR_END = 126; // 半角~

    /**
     * 全角对应于ASCII表的可见字符从！开始，偏移值为65281
     */
    static final char SBC_CHAR_START = 65281; // 全角！

    /**
     * 全角对应于ASCII表的可见字符到～结束，偏移值为65374
     */
    static final char SBC_CHAR_END = 65374; // 全角～

    /**
     * ASCII表中除空格外的可见字符与对应的全角字符的相对偏移
     */
    static final int CONVERT_STEP = 65248; // 全角半角转换间隔

    /**
     * 全角空格的值，它没有遵从与ASCII的相对偏移，必须单独处理
     */
    static final char SBC_SPACE = 12288; // 全角空格 12288

    /**
     * 半角空格的值，在ASCII中为32(Decimal)
     */
    static final char DBC_SPACE = ' '; // 半角空格

    // 文件名称
    public static String getFileName(String url) {
        String fileName = null;
        if (!TextUtils.isEmpty(url)) {
            fileName = url.substring(url.lastIndexOf("/") + 1);
        }
        return fileName;

    }

    public static String convertToCoinFormat(String value) {
        if (!TextUtils.isEmpty(value)) {
            BigDecimal total = new BigDecimal(value);
            DecimalFormat df = new DecimalFormat("###,##0.00");
            return df.format(total);
        }
        return "";
    }

    public static String changeDataYear(String date) {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy");
        try {
            return formatter.format(formatter.parse(date));
        } catch (Exception e) {
            e.printStackTrace();
            return "";

        }

    }

    public static String changeDataMonth(String date) {
        SimpleDateFormat formatter = new SimpleDateFormat("MM");
        try {
            return formatter.format(formatter.parse(date));
        } catch (Exception e) {
            e.printStackTrace();
            return "";

        }

    }


    public static String changeDataYearMonth(String date) {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM");
        try {
            return formatter.format(formatter.parse(date));
        } catch (Exception e) {
            e.printStackTrace();
            return "";

        }

    }

    public static String changeDataDay(String date) {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        try {
            return formatter.format(formatter.parse(date));
        } catch (Exception e) {
            e.printStackTrace();
            return "";

        }

    }

    public static String changeDataSecond(String date) {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            return formatter.format(formatter.parse(date));
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }

    /**
     * unix时间戳转换为dateFormat
     *
     * @param beginDate
     * @return
     */
    public static String timestampToDate(String beginDate) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String sd = sdf.format(new Date(Long.parseLong(beginDate)));
        return sd;
    }

    /**
     * 时间戳转换成日期格式字符串
     *
     * @param seconds 精确到秒的字符串
     * @return
     */
    public static String timeStamp2Date(String seconds, String format) {
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        String sd = sdf.format(new Date(Long.parseLong(seconds)));
        return sd;
    }

    public static String timeStamp2Date(String seconds) {
        SimpleDateFormat sdf = new SimpleDateFormat();
        String sd = sdf.format(new Date(Long.parseLong(seconds)));
        return sd;
    }

    /**
     * 时间戳转换成日期格式字符串
     *
     * @param seconds 精确到秒的字符串
     * @return
     */
    public static String longStamp2Date(long seconds, String format) {
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        String sd = sdf.format(new Date(seconds));
        return sd;
    }


    /**
     * String  float
     *
     * @return
     */
    public static float strToFloat(String format) {
        BigDecimal b = new BigDecimal(format);
        float f1 = b.setScale(2, BigDecimal.ROUND_HALF_UP).floatValue();

        return f1;
    }


    /**
     * 日期格式字符串转换成时间戳
     *
     * @param format 如：yyyy-MM-dd HH:mm:ss
     * @return
     */
    public static String date2TimeStamp(String date_str, String format) {

        return null;
    }

    /**
     * 将InputStream转换成某种字符编码的String
     *
     * @param in
     * @param encoding
     * @return
     * @throws Exception
     */
    public static String InputStreamTOString(InputStream in, String encoding) throws Exception {

        ByteArrayOutputStream outStream = new ByteArrayOutputStream();
        byte[] data = new byte[BUFFER_SIZE];
        int count = -1;
        while ((count = in.read(data, 0, BUFFER_SIZE)) != -1)
            outStream.write(data, 0, count);

        data = null;
        return new String(outStream.toByteArray(), encoding);
    }

    /**
     * 将字符串单个字符后加指定功能
     *
     * @param
     * @return
     */
    public static String makeBreak(String str, String pattern) {
        StringBuffer out = new StringBuffer();
        char[] cs = str.toCharArray();
        for (int i = 0; i < cs.length; i++) {
            if (i < cs.length - 1) {
                out.append(cs[i]).append(pattern);
            } else {
                out.append(cs[i]);
            }
        }
        return out.toString();
    }

    /**
     * 比较两个字符串是否相等
     *
     * @param str1
     * @param str2
     * @return
     */
    public static final boolean isEqual(String str1, String str2) {
        if (str1 == null || "".equals(str1.trim())) {
            return false;
        }
        if (str2 == null || "".equals(str2.trim())) {
            return false;
        }
        return str1.equals(str2);
    }

    /**
     * 不为空
     *
     * @param s
     * @return
     */
    public static boolean isNotNull(String s) {
        return s != null && !"".equals(s.trim());
    }

    /**
     * 为空
     *
     * @param s
     * @return
     */
    public static boolean isNull(String s) {
        return s == null || "".equals(s.trim());
    }

    // 获得当前日期
    public static String getLocaleDateS() {

        // 初始当前年份
        Calendar d = Calendar.getInstance(Locale.CHINA);
        // 创建一个日历引用d，通过静态方法getInstance() 从指定时区 Locale.CHINA 获得一个日期实例
        Date myDate = new Date();
        // 创建一个Date实例
        d.setTime(myDate);
        // 设置日历的时间，把一个新建Date实例myDate传入
        String yearStr = String.valueOf(d.get(Calendar.YEAR)) + "-"
                + String.valueOf(d.get(Calendar.MONTH) + 1) + "-"
                + String.valueOf(d.get(Calendar.DAY_OF_MONTH));

//        return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.CHINA).format(new Date(System
//                .currentTimeMillis()));
        return new SimpleDateFormat("yyyy-MM-dd", Locale.CHINA).format(new Date(System
                .currentTimeMillis()));

    }

    // 获得当前日期
    public static String getLocaleDate() {

        // 初始当前年份
        Calendar d = Calendar.getInstance(Locale.CHINA);
        // 创建一个日历引用d，通过静态方法getInstance() 从指定时区 Locale.CHINA 获得一个日期实例
        Date myDate = new Date();
        // 创建一个Date实例
        d.setTime(myDate);
        // 设置日历的时间，把一个新建Date实例myDate传入
        String yearStr = String.valueOf(d.get(Calendar.YEAR)) + "-"
                + String.valueOf(d.get(Calendar.MONTH) + 1) + "-"
                + String.valueOf(d.get(Calendar.DAY_OF_MONTH));

//        return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.CHINA).format(new Date(System
//                .currentTimeMillis()));
        return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.CHINA).format(new Date(System
                .currentTimeMillis()));

    }

    public static boolean isHttp(String str) {
        if (isNotNull(str)) {
            String httpString = str.substring(0, 4);
            String[] httpSplit = str.split(":");
            if (httpString.equals("http") &&
                    (httpSplit[0].equals("http") || httpSplit[0].equals("https"))) {
                return true;
            }
        }
        return false;
    }
}
