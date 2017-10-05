package net.skeyurt.lit.user.service.impl;

import com.google.common.base.Strings;
import net.skeyurt.lit.commons.exception.AppCheckedException;
import net.skeyurt.lit.jdbc.JdbcTools;
import net.skeyurt.lit.jdbc.enums.Logic;
import net.skeyurt.lit.jdbc.sta.Select;
import net.skeyurt.lit.user.entity.Organization;
import net.skeyurt.lit.user.service.OrganizationService;
import net.skeyurt.lit.user.util.UserUtils;
import net.skeyurt.lit.user.vo.OrganizationVo;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * User : liulu
 * Date : 17-10-3 下午4:29
 * version $Id: OrganizationServiceImpl.java, v 0.1 Exp $
 */
@Service
@Transactional
public class OrganizationServiceImpl implements OrganizationService {

    @Resource
    private JdbcTools jdbcTools;


    @Override
    public List<Organization> queryPageList(OrganizationVo vo) {
        return buildSelect(vo).page(vo).list();
    }

    @Override
    public Organization findById(Long orgId) {
        return jdbcTools.get(Organization.class, orgId);
    }

    @Override
    public Organization findByCode(String orgCode) {
        return jdbcTools.findByProperty(Organization.class, "orgCode", orgCode);
    }

    @Override
    public void insert(Organization organization) {

        String parentSerialNum = "";

        Organization oldOrg = findByCode(organization.getOrgCode());
        if (oldOrg != null) {
            throw new AppCheckedException("机构号已经存在!");
        }

        // 处理 orgLevel
        if (organization.getParentId() == null) {
            organization.setOrgLevel(1);
        } else {
            Organization parentOrg = findById(organization.getParentId());
            if (parentOrg == null) {
                throw new AppCheckedException("父机构信息丢失!");
            }
            parentSerialNum = parentOrg.getSerialNum();
            organization.setOrgLevel(parentOrg.getOrgLevel() + 1);
        }

        // 处理 serialNum
        List<String> serialNums = jdbcTools.createSelect(Organization.class)
                .include("serialNum")
                .where("parentId", organization.getParentId())
                .list(String.class);

        organization.setSerialNum(UserUtils.nextSerialNum(parentSerialNum, serialNums));

        jdbcTools.insert(organization);
    }

    @Override
    public void update(Organization organization) {

        Organization oldOrg = findById(organization.getOrgId());
        if (!Objects.equals(oldOrg.getOrgCode(), organization.getOrgCode())) {
            Organization checkOrg = findByCode(organization.getOrgCode());
            if (checkOrg != null) {
                throw new AppCheckedException("机构号已经存在!");
            }
        }
        // 不允许更新的属性
        organization.setOrgLevel(null);
        organization.setSerialNum(null);

        jdbcTools.update(organization);
    }

    @Override
    public void delete(Long... ids) {
        if (ids == null || ids.length == 0) {
            return;
        }
        List<Long> validIds = new ArrayList<>(ids.length);
        for (Long id : ids) {
            Organization organization = findById(id);
            if (organization == null) {
                continue;
            }
            int count = buildSelect(OrganizationVo.builder().parentId(organization.getOrgId()).build()).count();
            if (count > 0) {
                throw new AppCheckedException(String.format("请先删除 %s 的子机构数据 !", organization.getOrgName()));
            }
            validIds.add(id);
        }
        jdbcTools.deleteByIds(Organization.class, validIds.toArray(new Serializable[validIds.size()]));
    }

    private Select<Organization> buildSelect(OrganizationVo vo) {
        Select<Organization> select = jdbcTools.createSelect(Organization.class).where("parentId", vo.getParentId());

        if (!Strings.isNullOrEmpty(vo.getKeyWord())) {
            select.andWithBracket("orgCode", Logic.LIKE, vo.getBlurKeyWord())
                    .or("orgName", Logic.LIKE, vo.getBlurKeyWord())
                    .or("memo", Logic.LIKE, vo.getBlurKeyWord())
                    .end();
        }

        if (!Strings.isNullOrEmpty(vo.getOrgCode())) {
            select.and("orgCode", vo.getOrgCode());
        }

        return select;
    }

}
