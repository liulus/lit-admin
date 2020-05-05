package com.lit.service.user.service.impl;

import com.lit.service.user.model.CorporationVo;
import com.lit.service.user.model.Organization;
import com.lit.service.user.service.CorporationService;
import com.lit.service.user.util.UserUtils;
import com.lit.support.data.jdbc.JdbcRepository;
import com.lit.support.util.bean.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Collections;

/**
 * @author liulu
 * @version v1.0
 * date 2019-05-02
 */
@Service
@Transactional
public class CorporationServiceImpl implements CorporationService {

    @Resource
    private JdbcRepository jdbcRepository;

    @Override
    public Long saveCorporation(CorporationVo.Info corporationInfo) {
        Organization rootOrg = jdbcRepository.selectByProperty(Organization::getParentId, 0L);
        // 没有初始化过, 新增
        if (rootOrg == null) {
            rootOrg = BeanUtils.convert(corporationInfo, new Organization());
            rootOrg.setParentId(0L);
            rootOrg.setLevelIndex(UserUtils.nextLevelIndex("", Collections.emptyList()));

            jdbcRepository.insert(rootOrg);
            return rootOrg.getId();
        }

        Organization upOrg = BeanUtils.convert(corporationInfo, new Organization());
        upOrg.setId(rootOrg.getId());
        upOrg.setParentId(0L);
        upOrg.setLevelIndex(null);
        jdbcRepository.updateSelective(upOrg);

        return rootOrg.getId();
    }

    @Override
    public CorporationVo.Info getCorporationInfo() {
        Organization rootOrg = jdbcRepository.selectByProperty(Organization::getParentId, 0L);
        if (rootOrg == null) {
            return null;
        }
        return BeanUtils.convert(rootOrg, new CorporationVo.Info());
    }
}
