package com.jiangxfei.mymvp.rx.net;

import java.util.List;

/**
 * Created by JiangXFei on 2018/2/2.
 */

public class DataPage<T> {

    /**
     * currentPage : 1
     * datas : []
     * pageSize : 20
     * totalLine : 9
     * totalPage : 1
     */

    private int currentPage;
    private int pageSize;
    private int totalLine;
    private int totalPage;
    private List<T> datas;

    public int getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(int currentPage) {
        this.currentPage = currentPage;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public int getTotalLine() {
        return totalLine;
    }

    public void setTotalLine(int totalLine) {
        this.totalLine = totalLine;
    }

    public int getTotalPage() {
        return totalPage;
    }

    public void setTotalPage(int totalPage) {
        this.totalPage = totalPage;
    }

    public List<T> getDatas() {
        return datas;
    }

    public void setDatas(List<T> datas) {
        this.datas = datas;
    }
}
