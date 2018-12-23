package com.github.lit.param.service;

import com.github.lit.param.model.SysParam;
import com.github.lit.param.model.SysParamQo;
import com.github.lit.support.page.Page;

/**
 * User : liulu
 * Date : 17-9-17 下午2:53
 * version $Id: ParamService.java, v 0.1 Exp $
 */
public interface ParamService {

    Page<SysParam> findPageList(SysParamQo qo);

    SysParam findById(Long id);

    SysParam findByCode(String code);

    void insert(SysParam param);

    void update(SysParam param);

    void delete(Long... ids);
}
