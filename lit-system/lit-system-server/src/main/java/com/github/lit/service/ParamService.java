package com.github.lit.service;

import com.github.lit.model.SysParamQo;
import com.github.lit.repository.entity.SysParam;
import com.lit.support.data.domain.Page;

/**
 * User : liulu
 * Date : 17-9-17 下午2:53
 * version $Id: ParamService.java, v 0.1 Exp $
 */
public interface ParamService {

    Page<SysParam> findPageList(SysParamQo qo);

    SysParam findById(Long id);

    SysParam findByCode(String code);

    Long insert(SysParam param);

    void update(SysParam param);

    void delete(Long... ids);
}
