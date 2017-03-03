package com.lit.commons.page;

import java.util.ArrayList;
import java.util.Collection;

/**
 * User : liulu
 * Date : 2017-2-17 19:43
 * version $Id: PageList.java, v 0.1 Exp $
 */
public class PageList<E> extends ArrayList<E> {

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



    public PageList() {
    }

    public PageList(Collection<? extends E> c) {
        super(c);
    }

    public PageList(Collection<? extends E> c, int pageSize, int pageNum) {
        super(c);
        this.pageSize = pageSize;
        this.pageNum = pageNum;
    }

    public PageList(int pageSize, int pageNum, int recordCount) {
        this.pageSize = pageSize;
        this.pageNum = pageNum;
        this.recordCount = recordCount;
    }

    public PageList(Collection<? extends E> c, int pageSize, int pageNum, int recordCount) {
        super(c);
        this.pageSize = pageSize;
        this.pageNum = pageNum;
        this.recordCount = recordCount;
    }

    public int getPageSize() {
        return pageSize;
    }

    public int getPageNum() {
        return pageNum;
    }

    public int getRecordCount() {
        return recordCount;
    }

    public void setPage(int pageSize, int pageNum) {
        this.pageSize = pageSize;
        this.pageNum = pageNum;
    }

    public void setRecordCount(int recordCount) {
        this.recordCount = recordCount;
    }
}
