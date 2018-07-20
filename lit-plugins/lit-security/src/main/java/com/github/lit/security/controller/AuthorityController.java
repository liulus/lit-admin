package com.github.lit.security.controller;

import com.github.lit.plugin.core.constant.PluginConst;
import com.github.lit.security.model.Authority;
import com.github.lit.security.model.AuthorityQo;
import com.github.lit.security.model.AuthorityVo;
import com.github.lit.security.service.AuthorityService;
import com.github.lit.spring.web.annotation.ViewName;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

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


    @GetMapping
    @ViewName("authority")
    public List<Authority> list(AuthorityQo qo) {
        return authorityService.findPageList(qo);
    }

    @GetMapping("/tree")
    public List<AuthorityVo> tree() {
        return authorityService.findAuthorityTree();
    }

    @GetMapping("/{id}")
    public Authority get(@PathVariable Long id) {
        return authorityService.findById(id);
    }

    @PostMapping
    public Long add(Authority authority) {
        return authorityService.insert(authority);
    }

    @PutMapping
    public void update(Authority authority) {
        authorityService.update(authority);
    }

    @DeleteMapping
    public void delete(Long[] ids) {
        authorityService.delete(ids);
    }


}
