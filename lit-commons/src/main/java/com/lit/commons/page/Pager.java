package com.lit.commons.page;

import java.io.Serializable;

/**
 * User : liulu
 * Date : 2016-10-5 11:03
 */
public class Pager implements Serializable {

    private static final long serialVersionUID = -2502137541842239335L;

    /** 每页记录数 */
    private int pageSize = 20;

    /** 当前页 */
    private int pageNum = 1;

    /** 是否查询总记录数 */
    private boolean isCount = true;

    private String keyWord;

    public Pager() {
    }

    public Pager(int pageSize, int pageNum) {
        this.pageSize = pageSize;
        this.pageNum = pageNum;
    }

    public Pager(int pageSize, int pageNum, boolean isCount) {
        this.pageSize = pageSize;
        this.pageNum = pageNum;
        this.isCount = isCount;
    }

    public int getOffset() {
        return pageSize * (pageNum - 1);
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

    public boolean getIsCount() {
        return isCount;
    }

    public void setIsCount(boolean count) {
        isCount = count;
    }

    public String getKeyWord() {
        return keyWord;
    }

    public void setKeyWord(String keyWord) {
        this.keyWord = keyWord;
    }

}
