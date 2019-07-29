package com.github.lit.param.service.impl;

import com.github.lit.param.model.SysParam;
import com.github.lit.param.model.SysParamQo;
import com.github.lit.param.service.ParamService;
import com.github.lit.support.data.SQL;
import com.github.lit.support.data.domain.Page;
import com.github.lit.support.data.jdbc.JdbcRepository;
import com.github.lit.support.exception.BizException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Arrays;
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
    private JdbcRepository jdbcRepository;


    @Override
    public Page<SysParam> findPageList(SysParamQo qo) {
        SQL sql = SQL.baseSelect(SysParam.class);
        if (StringUtils.hasText(qo.getKeyword())) {
            qo.setKeyword("%" + qo.getKeyword() + "%");
            sql.WHERE("(code like :keyword or value like :keyword or remark like :keyword)");
        }
        return jdbcRepository.selectForPageList(sql, qo, SysParam.class);
    }

    @Override
    public SysParam findById(Long id) {
        return jdbcRepository.selectById(SysParam.class, id);
    }

    @Override
    public SysParam findByCode(String code) {
        return jdbcRepository.selectByProperty(SysParam::getCode, code);
    }

    @Override
    public Long insert(SysParam param) {
        checkCode(param.getCode());
        param.setSystem(false);

        jdbcRepository.insert(param);
        return param.getId();
    }

    @Override
    public void update(SysParam param) {
        SysParam oldParam = findById(param.getId());
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
        jdbcRepository.updateSelective(param);
    }

    private void checkCode(String code) {
        SysParam param = findByCode(code);
        if (param != null) {
            throw new BizException("参数 code 已经存在");
        }
    }

    @Override
    public void delete(Long... ids) {
        if (ids == null || ids.length == 0) {
            return;
        }

        List<SysParam> params = jdbcRepository.selectByIds(SysParam.class, Arrays.asList(ids));

        List<Long> validIds = new ArrayList<>(params.size());

        for (SysParam param : params) {
            if (param.getSystem()) {
                throw new BizException(String.format("%s 是系统级参数, 不允许删除", param.getCode()));
            }
            validIds.add(param.getId());
        }
        jdbcRepository.deleteByIds(SysParam.class, validIds);
    }
}
