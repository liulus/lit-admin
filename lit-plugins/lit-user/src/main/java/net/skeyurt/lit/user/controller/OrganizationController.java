package net.skeyurt.lit.user.controller;

import net.skeyurt.lit.plugin.context.PluginConst;
import net.skeyurt.lit.user.service.OrganizationService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

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
    private OrganizationService orgService;

    @RequestMapping({"/list", ""})
    public String orgList() {

        return "";
    }



}
