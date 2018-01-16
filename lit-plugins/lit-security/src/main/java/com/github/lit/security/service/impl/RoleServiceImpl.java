package com.github.lit.security.service.impl;

import com.github.lit.jdbc.JdbcTools;
import com.github.lit.jdbc.enums.Logic;
import com.github.lit.plugin.exception.AppException;
import com.github.lit.security.model.Authority;
import com.github.lit.security.model.Role;
import com.github.lit.security.model.RoleAuthority;
import com.github.lit.security.model.RoleQo;
import com.github.lit.security.service.RoleService;
import com.google.common.base.Strings;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

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
    public List<Role> findPageList(RoleQo roleQo) {

        return jdbcTools.createSelect(Role.class).page(roleQo).list();
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

    @Override
    public void bindAuthority(Long roleId, Long[] authorityIds) {
        Role role = findById(roleId);
        if (role == null) {
            return;
        }
        List<Long> ids = jdbcTools.createSelect(Authority.class)
                .include("authorityId")
                .where("authorityId", Logic.IN, (Object[]) authorityIds)
                .list(Long.class);
        List<Long> oldIds = jdbcTools.createSelect(RoleAuthority.class)
                .include("authorityId")
                .where("roleId", roleId)
                .list(Long.class);
        List<Long> deleteIds = oldIds.stream().filter(id -> !ids.contains(id)).collect(Collectors.toList());
        if (!CollectionUtils.isEmpty(deleteIds)) {
            jdbcTools.createDelete(RoleAuthority.class)
                    .where("roleId", roleId)
                    .and("authorityId", Logic.IN, deleteIds.toArray())
                    .execute();
        }
        List<Long> insertIds = ids.stream().filter(id -> !oldIds.contains(id)).collect(Collectors.toList());
        insertIds.forEach(id -> {
            RoleAuthority roleAuthority = RoleAuthority.builder()
                    .roleId(role.getRoleId())
                    .authorityId(id)
                    .build();
            jdbcTools.insert(roleAuthority);
        });
    }


}
