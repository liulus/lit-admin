package com.lit.commons.page;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;

/**
 * User : liulu
 * Date : 2017-2-17 19:43
 * version $Id: PageList.java, v 0.1 Exp $
 */
@Getter
@Setter
@NoArgsConstructor
public class PageList<E> extends ArrayList<E> {

    private static final long serialVersionUID = -3248132653480964900L;
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
    private int totalRecord;


    public PageList(Collection<? extends E> c) {
        super(c);
    }

    public PageList(Collection<? extends E> c, int pageSize, int pageNum) {
        super(c);
        this.pageSize = pageSize;
        this.pageNum = pageNum;
    }

    public PageList(int pageSize, int pageNum, int totalRecord) {
        super(pageSize);
        this.pageSize = pageSize;
        this.pageNum = pageNum;
        this.totalRecord = totalRecord;
    }

    public PageList(Collection<? extends E> c, int pageSize, int pageNum, int totalRecord) {
        super(c);
        this.pageSize = pageSize;
        this.pageNum = pageNum;
        this.totalRecord = totalRecord;
    }

    /**
     * 获取总页数
     *
     * @return
     */
    public int getTotalPage() {
        return (totalRecord - 1) / pageSize + 1;
    }

    public boolean isFirstPage() {
        return pageNum == 1;
    }

    public boolean isLastPage() {
        return pageNum == getTotalPage();
    }

    @Override
    public String toString() {
        return "PageList{" +
                "pageSize=" + pageSize +
                ", pageNum=" + pageNum +
                ", totalRecord=" + totalRecord +
                '}' + Arrays.toString(super.toArray());
    }
}
