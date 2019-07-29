package com.github.lit.user.controller;

import com.github.lit.support.util.bean.BeanUtils;
import com.github.lit.user.model.Organization;
import com.github.lit.user.model.OrganizationVo;
import com.github.lit.user.service.OrganizationService;
import org.springframework.web.bind.annotation.*;

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
