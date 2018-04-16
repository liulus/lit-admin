package com.github.lit.param.controller;

import com.github.lit.param.model.Param;
import com.github.lit.param.model.ParamQo;
import com.github.lit.param.service.ParamService;
import com.github.lit.plugin.core.constant.PluginConst;
import com.github.lit.plugin.web.ViewName;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * User : liulu
 * Date : 17-9-17 下午2:55
 * version $Id: ParamController.java, v 0.1 Exp $
 */
@Controller
@RequestMapping(PluginConst.URL_PREFIX + "/param")
public class ParamController {

    private static final String REDIRECT_URL = PluginConst.REDIRECT + "/param";

    @Resource
    private ParamService paramService;

    @GetMapping
    @ViewName("param")
    public List<Param> list(ParamQo qo) {
        return paramService.queryPageList(qo);
    }

    @GetMapping("/{id}")
    public Param get(@PathVariable Long id) {
        return paramService.findById(id);
    }

    @PostMapping
    @ViewName(REDIRECT_URL)
    public void add(Param param) {
        paramService.insert(param);
    }

    @PutMapping
    @ViewName(REDIRECT_URL)
    public void update(Param param) {
        paramService.update(param);
    }

    @DeleteMapping
    @ViewName(REDIRECT_URL)
    public void delete(Long... ids) {
        paramService.delete(ids);
    }
}
