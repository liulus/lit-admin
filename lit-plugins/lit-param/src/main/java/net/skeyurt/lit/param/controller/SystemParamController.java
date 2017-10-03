package net.skeyurt.lit.param.controller;

import net.skeyurt.lit.param.service.SystemParamService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;

/**
 * User : liulu
 * Date : 17-9-17 下午2:55
 * version $Id: SystemParamController.java, v 0.1 Exp $
 */
@Controller
@RequestMapping("/plugin/param")
public class SystemParamController {


    @Resource
    private SystemParamService systemParamService;


}
