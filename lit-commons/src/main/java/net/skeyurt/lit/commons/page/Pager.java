package net.skeyurt.lit.commons.page;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

/**
 * User : liulu
 * Date : 2016-10-5 11:03
 */
@Setter
@NoArgsConstructor
public class Pager implements Serializable {

    private static final long serialVersionUID = -2502137541842239335L;

    /**
     * 每页记录数
     */
    private int pageSize = 20;

    /**
     * 当前页
     */
    private int pageNum = 1;

    /**
     * 是否查询总记录数
     */
    @Getter
    private boolean count = true;

    @Getter
    private String keyWord;

    public Pager(int pageSize, int pageNum) {
        this.pageSize = pageSize;
        this.pageNum = pageNum;
    }

    public Pager(int pageSize, int pageNum, boolean count) {
        this.pageSize = pageSize;
        this.pageNum = pageNum;
        this.count = count;
    }

    public int getOffset() {
        return getPageSize() * (getPageNum() - 1);
    }

    public int getPageSize() {
        return pageSize < 1 ? 20 : pageSize;
    }

    public int getPageNum() {
        return pageNum < 1 ? 1 : pageNum;
    }

}
