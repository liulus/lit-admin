package com.github.lit.service.impl;

import com.github.lit.model.CorporationVo;
import com.github.lit.repository.OrgRepository;
import com.github.lit.repository.entity.Organization;
import com.github.lit.service.CorporationService;
import com.github.lit.util.UserUtils;
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
    private OrgRepository orgRepository;

    @Override
    public Long saveCorporation(CorporationVo.Info corporationInfo) {
        Organization rootOrg = orgRepository.selectByProperty(Organization::getParentId, 0L);
        // 没有初始化过, 新增
        if (rootOrg == null) {
            rootOrg = BeanUtils.convert(corporationInfo, new Organization());
            rootOrg.setParentId(0L);
            rootOrg.setLevelIndex(UserUtils.nextLevelIndex("", Collections.emptyList()));

            orgRepository.insert(rootOrg);
            return rootOrg.getId();
        }

        Organization upOrg = BeanUtils.convert(Organization.class, corporationInfo);
        upOrg.setId(rootOrg.getId());
        upOrg.setParentId(0L);
        upOrg.setLevelIndex(null);
        orgRepository.updateSelective(upOrg);

        return rootOrg.getId();
    }

    @Override
    public CorporationVo.Info getCorporationInfo() {
        Organization rootOrg = orgRepository.selectByProperty(Organization::getParentId, 0L);
        if (rootOrg == null) {
            return null;
        }
        return BeanUtils.convert(CorporationVo.Info.class, rootOrg);
    }
}
