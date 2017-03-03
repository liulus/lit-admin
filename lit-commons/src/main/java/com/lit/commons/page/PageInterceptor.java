package com.lit.commons.page;

import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.Collection;

/**
 * User : liulu
 * Date : 2017-2-17 20:56
 * version $Id: PageInterceptor.java, v 0.1 Exp $
 */
@Aspect
@Component
public class PageInterceptor {

    private static final Logger LOGGER = LoggerFactory.getLogger(PageInterceptor.class);

    // 数据库方言
    private String dbName;

    private PageSqlHandler pageSqlHandler;

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
            LOGGER.info("\n sql : {} \nargs : {}\n", querySql, Arrays.toString(sqlParams));
            return pjp.proceed();
        }

        JdbcTemplate target = (JdbcTemplate) pjp.getTarget();
        if (StringUtils.isEmpty(dbName)) {
            dbName = target.getDataSource().getConnection().getMetaData().getDatabaseProductName().toUpperCase();
        }

        args[0] = getPageSqlHandler().getPageSql(querySql, dbName, pager.getPageNum(), pager.getPageSize());

        LOGGER.info("\n sql : {} \nargs : {}\n", args[0], Arrays.toString(sqlParams));
        @SuppressWarnings("unchecked")
        PageList result = new PageList((Collection) pjp.proceed(args), pager.getPageSize(), pager.getPageNum());

        if (pager.getIsCount()) {
            String countSql = getPageSqlHandler().getCountSql(querySql, dbName);
            LOGGER.info("\n sql : {} \nargs : {}\n", countSql, Arrays.toString(sqlParams));
            result.setRecordCount(target.queryForObject(countSql, sqlParams, Integer.class));
        }

        return result;
    }

    private PageSqlHandler getPageSqlHandler() {
        if (pageSqlHandler == null) {
            pageSqlHandler = new DefaultPageSqlHandler();
        }
        return pageSqlHandler;
    }

    public void setPageSqlHandler(PageSqlHandler pageSqlHandler) {
        this.pageSqlHandler = pageSqlHandler;
    }

}
