package com.github.lit.repository.impl;

import com.github.lit.model.MenuQo;
import com.github.lit.repository.MenuRepository;
import com.github.lit.repository.entity.Menu;
import com.lit.support.data.SQL;
import com.lit.support.data.domain.Pageable;
import com.lit.support.data.jdbc.AbstractJdbcRepository;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

/**
 * @author liulu
 * @version 1.0
 * created_at 2020/5/26
 */
@Repository
public class MenuRepositoryImpl extends AbstractJdbcRepository<Menu> implements MenuRepository {

    private static final String PARENT_ID_CONDITION = "parent_id = :parentId";

    @Override
    protected SQL buildPageSQL(Pageable condition) {
        MenuQo qo = (MenuQo) condition;
        SQL sql = baseSelectSQL();
        if (qo.getParentId() != null) {
            sql.WHERE(PARENT_ID_CONDITION);
        }
        if (StringUtils.hasText(qo.getKeyword())) {
            qo.setKeyword("%" + qo.getKeyword() + "%");
            sql.WHERE("(code like :keyword or name like :keyword or remark like :keyword)");
        }
        sql.ORDER_BY("order_num");
        return sql;
    }

    @Override
    public Menu findByCodeAndParentId(String code, Long parentId) {
        SQL sql = baseSelectSQL()
                .WHERE(PARENT_ID_CONDITION)
                .WHERE("code = :code");
        Menu params = new Menu();
        params.setParentId(parentId);
        params.setCode(code);
        return selectSingle(sql, params);
    }
}
