package com.lit.commons.page;

import java.util.List;

/**
 * User : liulu
 * Date : 2016-10-1 16:35
 */
public class PageResult<T> {

    /**
     * 每页记录数
     */
    private int pageSize;

    /**
     * 当前页
     */
    private int pageNum;

    /**
     * 总记录数
     */
    private int recordCount;


    private List<T> data;

    public PageResult() {
    }

    public PageResult(List<T> data) {
        this.data = data;
    }


    public List<T> getData() {
        return data;
    }

    public void setData(List<T> data) {
        this.data = data;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public int getPageNum() {
        return pageNum;
    }

    public void setPageNum(int pageNum) {
        this.pageNum = pageNum;
    }

    public int getRecordCount() {
        return recordCount;
    }

    public void setRecordCount(int recordCount) {
        this.recordCount = recordCount;
    }

    public int getPageCount() {
        return (recordCount + pageSize - 1) / pageSize;
    }
}
