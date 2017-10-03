package net.skeyurt.lit.param.service.impl;

import com.google.common.base.Strings;
import net.skeyurt.lit.jdbc.JdbcTools;
import net.skeyurt.lit.jdbc.enums.Logic;
import net.skeyurt.lit.jdbc.sta.Select;
import net.skeyurt.lit.param.entity.Param;
import net.skeyurt.lit.param.qo.ParamQo;
import net.skeyurt.lit.param.service.ParamService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * User : liulu
 * Date : 17-9-17 下午2:53
 * version $Id: ParamServiceImpl.java, v 0.1 Exp $
 */
@Service
public class ParamServiceImpl implements ParamService {


    @Resource
    private JdbcTools jdbcTools;


    @Override
    public List<Param> queryPageList(ParamQo qo) {

        Select<Param> select = jdbcTools.createSelect(Param.class);

        if (!Strings.isNullOrEmpty(qo.getKeyWord())) {
            select.andWithBracket("paramCode", Logic.LIKE, qo.getBlurKeyWord())
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
}
