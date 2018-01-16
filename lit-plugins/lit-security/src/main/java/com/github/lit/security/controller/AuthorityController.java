package com.github.lit.security.controller;

import com.github.lit.commons.context.ResultConst;
import com.github.lit.dictionary.tool.DictionaryTools;
import com.github.lit.plugin.context.PluginConst;
import com.github.lit.security.context.SecurityConst;
import com.github.lit.security.model.Authority;
import com.github.lit.security.model.AuthorityQo;
import com.github.lit.security.service.AuthorityService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;

/**
 * User : liulu
 * Date : 2017/11/19 16:42
 * version $Id: AuthorityController.java, v 0.1 Exp $
 */
@Controller
@RequestMapping(PluginConst.URL_PREFIX + "/authority")
public class AuthorityController {

    @Resource
    private AuthorityService authorityService;


    @RequestMapping("/list")
    public String list(AuthorityQo qo, Model model) {
        model.addAttribute(ResultConst.RESULT, authorityService.findPageList(qo));
        model.addAttribute(SecurityConst.AUTHORITY_TYPE, DictionaryTools.findChildByRootKey(SecurityConst.AUTHORITY_TYPE));
        return "authority";
    }

    @RequestMapping("/tree")
    public String tree(Model model) {
        model.addAttribute(ResultConst.RESULT, authorityService.findAuthorityTree());
        return "";
    }

    @RequestMapping("/{roleId}/list")
    public String findByRoleId(@PathVariable Long roleId, Model model) {
        model.addAttribute(ResultConst.RESULT, authorityService.findByRoleId(roleId));
        return "";
    }

    @RequestMapping("/get")
    public String get(Long id, Model model) {
        model.addAttribute(ResultConst.RESULT, authorityService.findById(id));
        return "";
    }

    @RequestMapping("/add")
    public String add(Authority authority, Model model) {
        model.addAttribute(ResultConst.RESULT, authorityService.insert(authority));
        return "";
    }

    @RequestMapping("/update")
    public String update(Authority authority, Model model) {
        authorityService.update(authority);
        return "";
    }

    @RequestMapping("/delete")
    public String delete(Long[] ids) {
        authorityService.delete(ids);
        return "";
    }


}
