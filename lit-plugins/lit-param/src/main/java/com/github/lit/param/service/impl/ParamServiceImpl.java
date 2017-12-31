package com.github.lit.param.service.impl;

import com.github.lit.jdbc.JdbcTools;
import com.github.lit.jdbc.enums.Logic;
import com.github.lit.jdbc.statement.Select;
import com.github.lit.param.entity.Param;
import com.github.lit.param.service.ParamService;
import com.github.lit.param.vo.ParamVo;
import com.github.lit.plugin.exception.AppException;
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
    public List<Param> queryPageList(ParamVo qo) {

        Select<Param> select = jdbcTools.createSelect(Param.class);

        if (!Strings.isNullOrEmpty(qo.getKeyWord())) {
            select.and().parenthesis()
                    .condition("paramCode", Logic.LIKE, qo.getBlurKeyWord())
                    .or("paramValue", Logic.LIKE, qo.getBlurKeyWord())
                    .or("memo", Logic.LIKE, qo.getBlurKeyWord())
                    .end();
        }

        if (qo.getSystem() != null) {
            select.and("system", qo.getSystem());
        }

        if (!Strings.isNullOrEmpty(qo.getParamCode())) {
            select.and("paramCode", qo.getParamCode());
        }

        return select.page(qo).list();
    }

    @Override
    public Param findById(Long id) {
        return jdbcTools.get(Param.class, id);
    }

    @Override
    public Param findByCode(String code) {
        return jdbcTools.findByProperty(Param.class, "paramCode", code);
    }

    @Override
    public void insert(Param param) {
        Param oldParam = findByCode(param.getParamCode());

        if (oldParam != null) {
            throw new AppException("参数 code 已经存在!");
        }

        jdbcTools.insert(param);
    }

    @Override
    public void update(Param param) {
        Param oldParam = findById(param.getParamId());

        if (!Objects.equals(param.getParamCode(), oldParam.getParamCode())) {
            Param exist = findByCode(param.getParamCode());
            if (exist != null){
                throw new AppException("参数 code 已经存在!");
            }
        }
        jdbcTools.update(param);
    }

    @Override
    public void delete(Long... ids) {
        if (ids == null || ids.length == 0) {
            return;
        }
        List<Long> validIds = new ArrayList<>(ids.length);
        for (Long id : ids) {
            Param param = findById(id);
            if (param == null) {
                continue;
            }
            if (param.getSystem()) {
                throw new AppException(String.format("%s 是系统级字典, 不允许删除 !", param.getParamCode()));
            }
            validIds.add(id);
        }
        jdbcTools.deleteByIds(Param.class, validIds.toArray(new Serializable[validIds.size()]));
    }
}
