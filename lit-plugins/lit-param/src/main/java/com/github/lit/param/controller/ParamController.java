package com.github.lit.param.controller;

import com.github.lit.commons.context.ResultConst;
import com.github.lit.param.entity.Param;
import com.github.lit.param.service.ParamService;
import com.github.lit.param.vo.ParamVo;
import com.github.lit.plugin.context.PluginConst;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

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


    @Resource
    private ParamService paramService;

    @RequestMapping({"/list", ""})
    public String list(ParamVo qo, Model model) {

        List<Param> dictionaries = paramService.queryPageList(qo);
        model.addAttribute(ResultConst.RESULT, dictionaries);

        return "param";
    }

    @RequestMapping("/get")
    public String get(Long id, Model model) {

        model.addAttribute(ResultConst.RESULT, paramService.findById(id));

        return "";
    }

    @RequestMapping("/add")
    public String add(Param param, Model model) {
        paramService.insert(param);
        return "";
    }

    @RequestMapping("/update")
    public String update(Param param, Model model) {

        paramService.update(param);
        return "";
    }

    @RequestMapping("/delete")
    public String delete(Long... ids) {
        paramService.delete(ids);
        return "";
    }
}
