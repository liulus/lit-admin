package com.github.lit.user.controller;

import com.github.lit.plugin.core.constant.PluginConst;
import com.github.lit.support.annotation.ViewName;
import com.github.lit.support.page.PageResult;
import com.github.lit.support.page.PageUtils;
import com.github.lit.support.util.BeanUtils;
import com.github.lit.user.model.Organization;
import com.github.lit.user.model.OrganizationQo;
import com.github.lit.user.model.OrganizationVo;
import com.github.lit.user.service.OrganizationService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * User : liulu
 * Date : 17-10-5 上午11:16
 * version $Id: OrganizationController.java, v 0.1 Exp $
 */
@Controller
@RequestMapping(PluginConst.URL_PREFIX + "/org")
public class OrganizationController {


    @Resource
    private OrganizationService organizationService;

    @GetMapping
    @ViewName("organization")
    public PageResult<OrganizationVo.ListRes> orgList(OrganizationQo qo) {
        PageResult<Organization> organizationPage = organizationService.findPageList(qo);
        if (qo.getParentId() != 0L) {
            Organization org = organizationService.findById(qo.getParentId());
            organizationPage.add("returnId", org == null ? 0 : org.getParentId());
        }
        return PageUtils.convert(organizationPage, OrganizationVo.ListRes.class, null);
    }

    @GetMapping("/{id}")
    public OrganizationVo.ListRes get(@PathVariable Long id) {
        return BeanUtils.convert(organizationService.findById(id), new OrganizationVo.ListRes());
    }

    @PostMapping
    public Long add(OrganizationVo.Add add) {
        return organizationService.insert(BeanUtils.convert(add, new Organization()));
    }

    @PutMapping
    public void update(OrganizationVo.Update update) {
        organizationService.update(BeanUtils.convert(update, new Organization()));
    }

    @DeleteMapping
    public void delete(Long[] ids) {
        organizationService.delete(ids);
    }
}
