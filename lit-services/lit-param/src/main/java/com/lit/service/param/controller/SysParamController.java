package com.lit.service.param.controller;

import com.lit.service.core.constant.AuthorityConst;
import com.lit.service.param.model.SysParam;
import com.lit.service.param.model.SysParamQo;
import com.lit.service.param.service.ParamService;
import com.lit.support.data.domain.Page;
import org.springframework.security.access.annotation.Secured;
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
 * date 2019-05-01
 */
@RestController
@RequestMapping("/api/param")
public class SysParamController {

    @Resource
    private ParamService paramService;

    @GetMapping("/list")
    public Page<SysParam> findParamPage(SysParamQo qo) {
        return paramService.findPageList(qo);
    }

    @PostMapping
    public Long add(@RequestBody SysParam sysParam) {
        return paramService.insert(sysParam);
    }

    @PutMapping
    @Secured(AuthorityConst.ADD_DICTIONARY)
    public void update(@RequestBody SysParam sysParam) {
        paramService.update(sysParam);
    }

    @DeleteMapping("/{id}")
    public void deleteDictionary(@PathVariable Long id) {
        paramService.delete(id);
    }






}
