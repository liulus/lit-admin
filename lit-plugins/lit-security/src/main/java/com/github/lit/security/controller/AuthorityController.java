package com.github.lit.security.controller;

import com.github.lit.commons.context.ResultConst;
import com.github.lit.plugin.context.PluginConst;
import com.github.lit.security.entity.Authority;
import com.github.lit.security.service.AuthorityService;
import com.github.lit.security.vo.AuthorityVo;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
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
    public String list(AuthorityVo vo, Model model) {
        model.addAttribute(ResultConst.RESULT, authorityService.queryPageList(vo));
        return "authority";
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
