package net.skeyurt.lit.param.service.impl;

import net.skeyurt.lit.jdbc.JdbcTools;
import net.skeyurt.lit.param.service.SystemParamService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * User : liulu
 * Date : 17-9-17 下午2:53
 * version $Id: SystemParamServiceImpl.java, v 0.1 Exp $
 */
@Service
public class SystemParamServiceImpl implements SystemParamService {


    @Resource
    private JdbcTools jdbcTools;


}
