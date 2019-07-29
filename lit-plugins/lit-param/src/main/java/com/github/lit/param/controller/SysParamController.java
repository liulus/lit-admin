package com.github.lit.param.controller;

import com.github.lit.param.model.SysParam;
import com.github.lit.param.model.SysParamQo;
import com.github.lit.param.service.ParamService;
import com.github.lit.plugin.core.constant.AuthorityConst;
import com.github.lit.support.data.domain.Page;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

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
