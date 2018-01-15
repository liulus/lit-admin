package com.github.lit.menu.controller;

import com.github.lit.commons.bean.BeanUtils;
import com.github.lit.commons.context.ResultConst;
import com.github.lit.dictionary.entity.Dictionary;
import com.github.lit.dictionary.tool.DictionaryTools;
import com.github.lit.menu.context.MenuConst;
import com.github.lit.menu.model.Menu;
import com.github.lit.menu.model.MenuQo;
import com.github.lit.menu.model.MenuVo;
import com.github.lit.menu.service.MenuService;
import com.github.lit.plugin.context.PluginConst;
import com.google.common.collect.Maps;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

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
    public String menuList(MenuQo qo, Model model) {
        List<Menu> menus = menuService.findPageList(qo);
        processResult(model, menus);
        return "menu";
    }

    /**
     * 查询父菜单下的子菜单列表
     *
     * @param qo       查询条件
     * @param parentId 父菜单Id
     * @param model    视图
     * @return String
     */
    @RequestMapping("/{parentId}")
    public String childList(MenuQo qo, @PathVariable Long parentId, Model model) {

        qo.setParentId(parentId);
        List<Menu> menus = menuService.findPageList(qo);
        processResult(model, menus);
        return "menu";
    }

    private void processResult(Model model, List<Menu> menus) {
        List<Dictionary> dictionaries = DictionaryTools.findChildByRootKey(MenuConst.MENU_TYPE);

        Map<String, Dictionary> dictionaryMap = Maps.uniqueIndex(dictionaries, Dictionary::getDictKey);
        List<MenuVo> menuVos = BeanUtils.convert(MenuVo.class, menus, (menuVo, menu) -> {
            Dictionary dictionary = dictionaryMap.get(menu.getMenuType());
            menuVo.setMenuTypeStr(dictionary == null ? "" : dictionary.getDictValue());
        });

        model.addAttribute(ResultConst.RESULT, menuVos);
        model.addAttribute("menuType", dictionaries);
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

        Menu menu = menuService.findById(menuId);
        if (menu != null && menu.getParentId() != null) {
            return PluginConst.REDIRECT + PluginConst.URL_PREFIX + "/menu/" + menu.getParentId();
        }

        return PluginConst.REDIRECT + PluginConst.URL_PREFIX + "/menu";
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
