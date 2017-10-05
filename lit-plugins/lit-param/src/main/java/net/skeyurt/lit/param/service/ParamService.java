package net.skeyurt.lit.param.service;

import net.skeyurt.lit.param.entity.Param;
import net.skeyurt.lit.param.vo.ParamVo;

import java.util.List;

/**
 * User : liulu
 * Date : 17-9-17 下午2:53
 * version $Id: ParamService.java, v 0.1 Exp $
 */
public interface ParamService {

    List<Param> queryPageList(ParamVo qo);

    Param findById(Long id);

    Param findByCode(String code);

    void insert(Param param);

    void update(Param param);

    void delete(Long... ids);
}
