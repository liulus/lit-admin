package net.skeyurt.lit.param.service;

import net.skeyurt.lit.param.entity.Param;
import net.skeyurt.lit.param.qo.ParamQo;

import java.util.List;

/**
 * User : liulu
 * Date : 17-9-17 下午2:53
 * version $Id: ParamService.java, v 0.1 Exp $
 */
public interface ParamService {

    List<Param> queryPageList(ParamQo qo);
}
