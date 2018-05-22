package com.jiangxfei.mymvp.rx.net;

/**
 * @className HttpResult
 * @描述 网络返回格式类
 * @作者lml
 * @日期2017/1/19 15:27
 * @修改日期
 * @版本
 */
public class HttpResult<T> {

    private int code; //响应码
    private String msg; //返回提示语
    private T data;//数据
    private Object title;

    public HttpResult() {
    }


    public HttpResult(int code, String msg, T data) {
        this.code = code;
        this.msg = msg;
        this.data = data;
    }

    public HttpResult(int code, String msg, T data, Object title) {
        this.code = code;
        this.msg = msg;
        this.data = data;
        this.title = title;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public Object getTitle() {
        return title;
    }

    public void setTitle(Object title) {
        this.title = title;
    }
}
