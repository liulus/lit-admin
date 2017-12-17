package com.github.lit.security.service.impl;

import com.github.lit.jdbc.JdbcTools;
import com.github.lit.plugin.exception.AppException;
import com.github.lit.security.entity.Role;
import com.github.lit.security.service.RoleService;
import com.github.lit.security.vo.RoleVo;
import com.google.common.base.Strings;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Objects;

/**
 * User : liulu
 * Date : 2017/11/19 16:42
 * version $Id: RoleServiceImpl.java, v 0.1 Exp $
 */
@Service
public class RoleServiceImpl implements RoleService {

    @Resource
    private JdbcTools jdbcTools;

    @Override
    public List<Role> findPageList(RoleVo roleVo) {

        return jdbcTools.queryPageList(Role.class, roleVo);
    }

    @Override
    public Role findById(Long roleId) {
        return jdbcTools.get(Role.class, roleId);
    }

    @Override
    public Role findByCode(String roleCode) {
        return jdbcTools.findByProperty(Role.class, "roleCode", roleCode);
    }

    @Override
    public Long insert(Role role) {
        checkRoleCode(role.getRoleCode());
        return (Long) jdbcTools.insert(role);
    }

    @Override
    public void update(Role role) {
        Role oldRole = findById(role.getRoleId());
        if (!Objects.equals(oldRole.getRoleCode(), role.getRoleCode())) {
            checkRoleCode(role.getRoleCode());
        }
        jdbcTools.update(role);
    }

    private void checkRoleCode(String roleCode) {
        if (Strings.isNullOrEmpty(roleCode)) {
            throw new AppException("角色码不能为空!");
        }
        int count = jdbcTools.createSelect(Role.class).where("roleCode", roleCode).count();
        if (count > 0) {
            throw new AppException("角色码已经存在!");
        }
    }

    @Override
    public int delete(Long[] ids) {
        if (ids == null || ids.length == 0) {
            return 0;
        }

        return jdbcTools.deleteByIds(Role.class, ids);
    }


}
