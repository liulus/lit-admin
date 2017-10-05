package net.skeyurt.lit.user.controller;

import net.skeyurt.lit.commons.context.ResultConst;
import net.skeyurt.lit.dictionary.tool.DictionaryTools;
import net.skeyurt.lit.plugin.context.PluginConst;
import net.skeyurt.lit.user.context.UserConst;
import net.skeyurt.lit.user.entity.Organization;
import net.skeyurt.lit.user.service.OrganizationService;
import net.skeyurt.lit.user.vo.OrganizationVo;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

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

    @RequestMapping({"/list", ""})
    public String orgList(OrganizationVo vo, Model model) {
        List<Organization> orgs = organizationService.queryPageList(vo);
        model.addAttribute(ResultConst.RESULT, orgs);
        model.addAttribute("orgType", DictionaryTools.findChildByRootKey(UserConst.ORGANIZATION_TYPE));
        return "organization";
    }

    /**
     * 查询父菜单下的子菜单列表
     *
     * @param vo
     * @param parentId
     * @param model
     * @return
     */
    @RequestMapping("/{parentId}")
    public String childList(OrganizationVo vo, @PathVariable Long parentId, Model model) {

        vo.setParentId(parentId);
        List<Organization> orgs = organizationService.queryPageList(vo);
        model.addAttribute(ResultConst.RESULT, orgs);
        model.addAttribute("orgType", DictionaryTools.findChildByRootKey(UserConst.ORGANIZATION_TYPE));
        return "organization";
    }

    /**
     * 返回上级按钮
     *
     * @param orgId
     * @param model
     * @return
     */
    @RequestMapping("/back/{orgId}")
    public String back(@PathVariable Long orgId, Model model) {

        Organization org = organizationService.findById(orgId);
        if (org != null && org.getParentId() != null) {
            return PluginConst.REDIRECT + PluginConst.URL_PREFIX + "/org/" + org.getParentId();
        }

        return PluginConst.REDIRECT + PluginConst.URL_PREFIX + "/org";
    }

    @RequestMapping("get")
    public String get(Long id, Model model) {
        Organization organization = organizationService.findById(id);
        model.addAttribute(ResultConst.RESULT, organization);
        return "";
    }

    @RequestMapping("/add")
    public String add(Organization organization, Model model) {
        organizationService.insert(organization);
        return "";
    }

    @RequestMapping("/update")
    public String update(Organization organization) {
        organizationService.update(organization);
        return "";
    }

    @RequestMapping("/delete")
    public String delete(Long... ids) {
        organizationService.delete(ids);
        return "";
    }
}
