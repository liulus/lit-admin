package com.github.lit.security.controller;

import com.github.lit.plugin.core.constant.PluginConst;
import com.github.lit.plugin.web.ViewName;
import com.github.lit.security.model.AuthorityVo;
import com.github.lit.security.model.Role;
import com.github.lit.security.model.RoleQo;
import com.github.lit.security.service.RoleService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * User : liulu
 * Date : 2017/11/19 16:43
 * version $Id: RoleController.java, v 0.1 Exp $
 */
@Controller
@RequestMapping(PluginConst.URL_PREFIX + "/role")
public class RoleController {

    @Resource
    private RoleService roleService;

    @GetMapping
    @ViewName("role")
    public List<Role> list(RoleQo roleQo) {
        return roleService.findPageList(roleQo);
    }

    @GetMapping("/bind/authority")
    @ViewName("bind-authority")
    public List<AuthorityVo.TreeNode> bindAuthority(Long roleId) {
        return roleService.findAuthorityTree(roleId);
    }

    @PostMapping("/bind/authority")
    @ViewName(PluginConst.REDIRECT + "/role")
    public void doBindAuthority(Long roleId, Long[] authorityIds) {
        roleService.bindAuthority(roleId, authorityIds);
    }

    @GetMapping("/{id}")
    public Role get(Long id) {
        return roleService.findById(id);
    }

    @PostMapping
    public Long add(Role role) {
        return roleService.insert(role);
    }

    @PutMapping
    public void update(Role role) {
        roleService.update(role);
    }

    @DeleteMapping("/delete")
    public void delete(Long[] ids) {
        roleService.delete(ids);
    }

}
