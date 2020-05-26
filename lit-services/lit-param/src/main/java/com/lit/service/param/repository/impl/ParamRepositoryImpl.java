package com.lit.service.param.repository.impl;

import com.lit.service.param.model.SysParam;
import com.lit.service.param.model.SysParamQo;
import com.lit.service.param.repository.ParamRepository;
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
public class ParamRepositoryImpl extends AbstractJdbcRepository<SysParam> implements ParamRepository {

    @Override
    protected SQL buildPageSQL(Pageable condition) {
        SysParamQo qo = (SysParamQo) condition;
        SQL sql = baseSelectSQL();
        if (StringUtils.hasText(qo.getKeyword())) {
            qo.setKeyword("%" + qo.getKeyword() + "%");
            sql.WHERE("(code like :keyword or value like :keyword or remark like :keyword)");
        }
        return sql;
    }
}
