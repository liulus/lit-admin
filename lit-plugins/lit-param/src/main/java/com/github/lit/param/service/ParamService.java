package com.github.lit.param.service;

import com.github.lit.param.model.Param;
import com.github.lit.param.model.ParamQo;

import java.util.List;

/**
 * User : liulu
 * Date : 17-9-17 下午2:53
 * version $Id: ParamService.java, v 0.1 Exp $
 */
public interface ParamService {

    List<Param> queryPageList(ParamQo qo);

    Param findById(Long id);

    Param findByCode(String code);

    void insert(Param param);

    void update(Param param);

    void delete(Long... ids);
}
