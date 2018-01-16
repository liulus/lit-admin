package com.github.lit.security.controller;

import com.github.lit.commons.context.ResultConst;
import com.github.lit.plugin.context.PluginConst;
import com.github.lit.security.model.Role;
import com.github.lit.security.model.RoleQo;
import com.github.lit.security.service.RoleService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;

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

    @RequestMapping("/list")
    public String list(RoleQo roleQo, Model model) {
        model.addAttribute(ResultConst.RESULT, roleService.findPageList(roleQo));
        return "role";
    }

    @RequestMapping("/bind/authority")
    public String bindAuthority(Long roleId, Long[] authorityIds) {
        roleService.bindAuthority(roleId, authorityIds);
        return "";
    }

    @RequestMapping("/get")
    public String get(Long id, Model model) {
        model.addAttribute(ResultConst.RESULT, roleService.findById(id));
        return "";
    }

    @RequestMapping("/add")
    public String add(Role role, Model model) {
        roleService.insert(role);
        return "";
    }

    @RequestMapping("/update")
    public String update(Role role, Model model) {

        roleService.update(role);
        return "";
    }

    @RequestMapping("/delete")
    public String delete(Long... ids) {
        roleService.delete(ids);
        return "";
    }

}
