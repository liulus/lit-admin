package com.github.lit.security.service.impl;

import com.github.lit.jdbc.JdbcTools;
import com.github.lit.jdbc.enums.Logic;
import com.github.lit.plugin.exception.AppException;
import com.github.lit.security.entity.Authority;
import com.github.lit.security.entity.Role;
import com.github.lit.security.entity.RoleAuthority;
import com.github.lit.security.service.AuthorityService;
import com.github.lit.security.service.RoleService;
import com.github.lit.security.vo.AuthorityVo;
import com.google.common.base.Strings;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

/**
 * User : liulu
 * Date : 2017/11/19 16:41
 * version $Id: AuthorityServiceImpl.java, v 0.1 Exp $
 */
@Service
@Transactional
public class AuthorityServiceImpl implements AuthorityService {

    @Resource
    private JdbcTools jdbcTools;

    @Resource
    private RoleService roleService;


    @Override
    public List<Authority> queryPageList(AuthorityVo vo) {

        return jdbcTools.queryPageList(Authority.class, vo);
    }

    @Override
    public List<Authority> findByRoleId(Long roleId) {
        Role role = roleService.findById(roleId);
        if (role == null) {
            return Collections.emptyList();
        }
        return jdbcTools.createSelect(Authority.class)
                .join(RoleAuthority.class)
                .on(Authority.class, "roleId", Logic.EQ, RoleAuthority.class, "roleId")
                .and(RoleAuthority.class, "roleId", Logic.EQ, role.getRoleId())
                .list();
    }

    @Override
    public Authority findById(Long authorityId) {
        return jdbcTools.get(Authority.class, authorityId);
    }

    @Override
    public Authority findByCode(String authorityCode) {
        return jdbcTools.findByProperty(Authority.class, "authorityCode", authorityCode);
    }

    @Override
    public Long insert(Authority authority) {
        checkAuthorityCode(authority.getAuthorityCode());
        return (Long) jdbcTools.insert(authority);
    }

    @Override
    public void update(Authority authority) {

        Authority oldAuthority = findById(authority.getAuthorityId());
        if (!Objects.equals(oldAuthority.getAuthorityCode(), authority.getAuthorityCode())) {
            checkAuthorityCode(authority.getAuthorityCode());
        }
        jdbcTools.update(authority);
    }

    private void checkAuthorityCode(String authorityCode) {
        if (Strings.isNullOrEmpty(authorityCode)) {
            throw new AppException("权限码不能为空!");
        }
        int count = jdbcTools.createSelect(Authority.class).where("authorityCode", authorityCode).count();
        if (count > 0) {
            throw new AppException("权限码已经存在!");
        }
    }

    @Override
    public int delete(Long[] ids) {
        if (ids == null || ids.length == 0) {
            return 0;
        }

        return jdbcTools.deleteByIds(Authority.class, ids);
    }


}
