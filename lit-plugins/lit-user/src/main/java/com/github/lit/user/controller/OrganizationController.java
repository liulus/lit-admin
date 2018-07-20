package com.github.lit.user.controller;

import com.github.lit.bean.BeanUtils;
import com.github.lit.plugin.core.constant.PluginConst;
import com.github.lit.spring.web.annotation.ViewName;
import com.github.lit.user.model.Organization;
import com.github.lit.user.model.OrganizationQo;
import com.github.lit.user.model.OrganizationVo;
import com.github.lit.user.service.OrganizationService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

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
    public List<OrganizationVo.List> orgList(OrganizationQo qo, Model model) {
        if (qo.getParentId() != 0L) {
            Organization org = organizationService.findById(qo.getParentId());
            model.addAttribute("returnId", org == null ? 0 : org.getParentId());
        }
        return BeanUtils.convert(OrganizationVo.List.class, organizationService.findPageList(qo));
    }

    @GetMapping("/{id}")
    public OrganizationVo.List get(@PathVariable Long id) {
        return BeanUtils.convert(organizationService.findById(id), new OrganizationVo.List());
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
