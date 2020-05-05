package com.lit.service.security.controller;

import com.lit.service.core.constant.PluginConst;
import com.lit.service.security.model.Authority;
import com.lit.service.security.model.AuthorityQo;
import com.lit.service.security.model.AuthorityVo;
import com.lit.service.security.service.AuthorityService;
import com.lit.support.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;

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
    public Page<Authority> list(AuthorityQo qo) {
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
