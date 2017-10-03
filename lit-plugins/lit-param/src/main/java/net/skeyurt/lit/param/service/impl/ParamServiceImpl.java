package net.skeyurt.lit.param.service.impl;

import net.skeyurt.lit.jdbc.JdbcTools;
import net.skeyurt.lit.param.service.ParamService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * User : liulu
 * Date : 17-9-17 下午2:53
 * version $Id: ParamServiceImpl.java, v 0.1 Exp $
 */
@Service
public class ParamServiceImpl implements ParamService {


    @Resource
    private JdbcTools jdbcTools;


}
