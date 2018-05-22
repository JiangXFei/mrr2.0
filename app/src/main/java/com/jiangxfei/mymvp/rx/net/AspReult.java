package com.jiangxfei.mymvp.rx.net;

/**
 * @author: JiangXFei
 * @date: 2018/1/23 0023
 * @content:
 */

public class AspReult<T> {
    private String status; //响应码
    private String message; //返回提示语
    private T data;//数据
    private String datacount;


    public AspReult() {
    }



    public AspReult(String status, String message, T data, String datacount) {
        this.status = status;
        this.message = message;
        this.data = data;
        this.datacount = datacount;
    }

    public String getDatacount() {
        return datacount;
    }

    public void setDatacount(String datacount) {
        this.datacount = datacount;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
