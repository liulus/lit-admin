package com.github.lit.param.service.impl;

import com.github.lit.commons.exception.BizException;
import com.github.lit.jdbc.JdbcTools;
import com.github.lit.jdbc.statement.select.Select;
import com.github.lit.param.model.Param;
import com.github.lit.param.model.ParamQo;
import com.github.lit.param.service.ParamService;
import com.google.common.base.Strings;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * User : liulu
 * Date : 17-9-17 下午2:53
 * version $Id: ParamServiceImpl.java, v 0.1 Exp $
 */
@Service
@Transactional
public class ParamServiceImpl implements ParamService {


    @Resource
    private JdbcTools jdbcTools;


    @Override
    public List<Param> queryPageList(ParamQo qo) {

        Select<Param> select = jdbcTools.select(Param.class);

        if (!Strings.isNullOrEmpty(qo.getKeyword())) {
            select.and()
                    .bracket(Param::getCode).like(qo.getKeyword())
                    .or(Param::getValue).like(qo.getKeyword())
                    .or(Param::getMemo).like(qo.getKeyword())
                    .end();
        }

        if (!Strings.isNullOrEmpty(qo.getCode())) {
            select.and(Param::getCode).equalsTo(qo.getCode());
        }

        return select.page(qo).list();
    }

    @Override
    public Param findById(Long id) {
        return jdbcTools.get(Param.class, id);
    }

    @Override
    public Param findByCode(String code) {
        return jdbcTools.select(Param.class).where(Param::getCode).equalsTo(code).single();
    }

    @Override
    public void insert(Param param) {
        checkCode(param.getCode());
        param.setSystem(false);

        jdbcTools.insert(param);
    }

    @Override
    public void update(Param param) {
        Param oldParam = findById(param.getParamId());
        if (oldParam == null) {
            return;
        }
        if (!Objects.equals(param.getCode(), oldParam.getCode())) {
            if (oldParam.getSystem()) {
                throw new BizException(String.format("%s 是系统级参数, 不允许修改", oldParam.getCode()));
            }
            checkCode(param.getCode());
        }
        param.setSystem(null);
        jdbcTools.update(param);
    }

    private void checkCode(String code) {
        Param param = findByCode(code);
        if (param != null) {
            throw new BizException("参数 code 已经存在");
        }
    }

    @Override
    public void delete(Long... ids) {
        if (ids == null || ids.length == 0) {
            return;
        }
        List<Param> params = jdbcTools.select(Param.class)
                .where(Param::getParamId).in((Object[]) ids)
                .list();

        List<Long> validIds = new ArrayList<>(params.size());

        for (Param param : params) {
            if (param.getSystem()) {
                throw new BizException(String.format("%s 是系统级参数, 不允许删除", param.getCode()));
            }
            validIds.add(param.getParamId());
        }
        jdbcTools.deleteByIds(Param.class, validIds.toArray(new Serializable[validIds.size()]));
    }
}
