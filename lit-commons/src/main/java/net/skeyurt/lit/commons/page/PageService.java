package net.skeyurt.lit.commons.page;

/**
 * User : liulu
 * Date : 2017-2-15 21:11
 * version $Id: PageService.java, v 0.1 Exp $
 */
public class PageService {

    /**
     * 分页信息线程变量
     */
    private static ThreadLocal<Pager> LOCAL_PAGER = new ThreadLocal<>();

    public static void setPager(int pageSize, int pageNum) {
        setPager(pageSize, pageNum, true);
    }

    public static void setPager(int pageSize, int pageNum, boolean isCount) {
        LOCAL_PAGER.set(new Pager(pageSize, pageNum, isCount));
    }

    /**
     * 储存分页信息
     *
     * @param pager 分页对象
     */
    public static void setPager(Pager pager) {
        setPager(pager.getPageSize(), pager.getPageNum(), pager.isCount());
    }

    /**
     * 取出分页信息对象
     *
     * @return
     */
    public static Pager getPager() {
        return getPager(true);
    }

    /**
     * 取出分页信息对象
     *
     * @return
     */
    public static Pager getPager(boolean isClear) {
        Pager pager = LOCAL_PAGER.get();
        //获取数据时清除
        if (isClear) {
            LOCAL_PAGER.remove();
        }
        return pager;
    }

}
