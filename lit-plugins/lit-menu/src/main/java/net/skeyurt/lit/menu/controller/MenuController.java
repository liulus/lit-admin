package net.skeyurt.lit.menu.controller;

import lombok.extern.slf4j.Slf4j;
import net.skeyurt.lit.commons.context.ResultConst;
import net.skeyurt.lit.dictionary.tool.DictionaryTools;
import net.skeyurt.lit.menu.context.MenuConst;
import net.skeyurt.lit.menu.service.MenuService;
import net.skeyurt.lit.menu.vo.MenuVo;
import net.skeyurt.lit.plugin.context.PluginConst;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;
import java.util.List;

/**
 * User : liulu
 * Date : 2017/7/13 19:46
 * version $Id: MenuController.java, v 0.1 Exp $
 */
@Controller
@RequestMapping(PluginConst.URL_PREFIX + "/menu")
@Slf4j
public class MenuController {

    @Resource
    private MenuService menuService;


    @RequestMapping({"/list", ""})
    public String menuList(MenuVo vo, Model model) {
        List<MenuVo> menuVos = menuService.queryPageList(vo);
        model.addAttribute(ResultConst.RESULT, menuVos);
        model.addAttribute("menuType", DictionaryTools.findChildByRootKey(MenuConst.MENU_TYPE));
        return "menu";
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
    public String childList(MenuVo vo, @PathVariable Long parentId, Model model) {

        vo.setParentId(parentId);
        List<MenuVo> menuVos = menuService.queryPageList(vo);
        model.addAttribute(ResultConst.RESULT, menuVos);
        model.addAttribute("menuType", DictionaryTools.findChildByRootKey(MenuConst.MENU_TYPE));
        return "menu";
    }

    /**
     * 返回上级按钮
     *
     * @param menuId
     * @param model
     * @return
     */
    @RequestMapping("/back/{menuId}")
    public String back(@PathVariable Long menuId, Model model) {

        MenuVo menuVo = menuService.findById(menuId);
        if (menuVo != null && menuVo.getParentId() != null) {
            return "redirect:/plugin/menu/" + menuVo.getParentId();
        }

        return "redirect:/plugin/menu";
    }

    /**
     * 移动菜单,改变父节点
     *
     * @param parentId
     * @param ids
     * @return
     */
    @RequestMapping("/move")
    public String move(Long parentId, Long... ids) {
        menuService.moveMenu(parentId, ids);
        return "";
    }

    /**
     * 同级向上移动菜单
     *
     * @param menuId
     * @return
     */
    @RequestMapping("/move/up")
    public String moveUp(Long menuId) {
        menuService.move(menuId, true);
        return "";
    }

    /**
     * 同级向下移动菜单
     *
     * @param menuId
     * @return
     */
    @RequestMapping("/move/down")
    public String moveDown(Long menuId) {
        menuService.move(menuId, false);
        return "";
    }

    /**
     * 启用菜单
     *
     * @param menuId
     * @return
     */
    @RequestMapping("/enable")
    public String enable(Long menuId) {
        menuService.changeStatus(menuId, true);
        return "";
    }

    /**
     * 禁用菜单
     *
     * @param menuId
     * @return
     */
    @RequestMapping("/disable")
    public String disable(Long menuId) {
        menuService.changeStatus(menuId, false);
        return "";
    }

    @RequestMapping("/get")
    public String get(Long id, Model model) {
        model.addAttribute(ResultConst.RESULT, menuService.findById(id));
        return "";
    }

    @RequestMapping("/add")
    public String add(MenuVo vo, Model model) {
        menuService.add(vo);
        return "";
    }

    @RequestMapping("/update")
    public String update(MenuVo vo, Model model) {
        menuService.update(vo);
        return "";
    }

    @RequestMapping("/delete")
    public String delete(Long... ids) {
        menuService.delete(ids);
        return "";
    }


}
