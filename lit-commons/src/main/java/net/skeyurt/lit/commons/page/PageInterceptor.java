package net.skeyurt.lit.commons.page;

import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.*;

/**
 * User : liulu
 * Date : 2017-2-17 20:56
 * version $Id: PageInterceptor.java, v 0.1 Exp $
 */
@Slf4j
@Aspect
@NoArgsConstructor
public class PageInterceptor {

    @Setter
    private PageSqlHandler pageSqlHandler;

    private String dbName;

    public PageInterceptor (PageSqlHandler pageSqlHandler) {
        this.pageSqlHandler = pageSqlHandler;
    }

    @Around("execution(* org.springframework.jdbc.core.JdbcOperations.query*(..))")
    public Object doPage(ProceedingJoinPoint pjp) throws Throwable {
        Object[] args = pjp.getArgs();
        String querySql = (String) args[0];

        // 获取 sql 的参数
        Object[] sqlParams = null;
        for (Object obj : args) {
            if (obj instanceof Object[]) {
                sqlParams = (Object[]) obj;
            }
        }

        Pager pager = PageService.getPager();
        if (pager == null) {
            log.info("\n sql : {} \nargs : {}\n", querySql, Arrays.toString(sqlParams));
            return pjp.proceed();
        }

        JdbcTemplate target = (JdbcTemplate) pjp.getTarget();
        if (StringUtils.isEmpty(dbName)) {
            dbName = target.getDataSource().getConnection().getMetaData().getDatabaseProductName().toUpperCase();
        }

        List result;
        if (pager.isCount()) {
            String countSql = getPageSqlHandler().getCountSql(querySql, dbName);
            log.info("\n sql : {} \nargs : {}\n", countSql, Arrays.toString(sqlParams));
            Integer totalRecord = target.queryForObject(countSql, sqlParams, Integer.class);
            result = new PageList(pager.getPageSize(), pager.getPageNum(), totalRecord);
            if (Objects.equals(totalRecord, 0)) {
                return result;
            }
        } else {
            result = new ArrayList(pager.getPageSize());
        }

        args[0] = getPageSqlHandler().getPageSql(dbName, querySql, pager.getPageSize(), pager.getPageNum());

        log.info("\n sql : {} \nargs : {}\n", args[0], Arrays.toString(sqlParams));
        //noinspection unchecked
        result.addAll((Collection) pjp.proceed(args));

        return result;
    }

    public PageSqlHandler getPageSqlHandler() {
        if (pageSqlHandler == null) {
            pageSqlHandler = new DefaultPageSqlHandler();
        }
        return pageSqlHandler;
    }

}
