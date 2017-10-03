package net.skeyurt.lit.param.controller;

import net.skeyurt.lit.commons.context.ResultConst;
import net.skeyurt.lit.param.entity.Param;
import net.skeyurt.lit.param.qo.ParamQo;
import net.skeyurt.lit.param.service.ParamService;
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
@RequestMapping("/plugin/param")
public class ParamController {


    @Resource
    private ParamService paramService;

    @RequestMapping({"/list", ""})
    public String list(ParamQo qo, Model model) {

        List<Param> dictionaries = paramService.queryPageList(qo);
        model.addAttribute(ResultConst.RESULT, dictionaries);

        return "param";
    }
}
