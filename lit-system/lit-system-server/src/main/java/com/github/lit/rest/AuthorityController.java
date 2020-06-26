package com.github.lit.rest;

import com.github.lit.constant.PluginConst;
import com.github.lit.model.AuthorityQo;
import com.github.lit.model.AuthorityVo;
import com.github.lit.repository.entity.Authority;
import com.github.lit.service.AuthorityService;
import com.lit.support.data.domain.Page;
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
