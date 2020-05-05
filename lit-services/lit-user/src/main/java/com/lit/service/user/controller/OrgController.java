package com.lit.service.user.controller;

import com.lit.service.user.model.Organization;
import com.lit.service.user.model.OrganizationVo;
import com.lit.service.user.service.OrganizationService;
import com.lit.support.util.bean.BeanUtils;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @author liulu
 * @version v1.0
 * date 2019-05-02
 */
@RestController
@RequestMapping("/api/org")
public class OrgController {

    @Resource
    private OrganizationService organizationService;

    @GetMapping("/list")
    public OrganizationVo.Detail orgTree() {
        return organizationService.buildOrgTree();
    }


    @PostMapping
    public Long add(@RequestBody OrganizationVo.Add add) {
        return organizationService.insert(BeanUtils.convert(add, new Organization()));
    }

    @PutMapping
    public void update(@RequestBody OrganizationVo.Update menuVo) {
        organizationService.update(BeanUtils.convert(menuVo, new Organization()));
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        organizationService.delete(new Long[]{id});
    }

}
