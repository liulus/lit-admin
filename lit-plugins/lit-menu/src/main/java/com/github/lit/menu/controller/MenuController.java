package com.github.lit.menu.controller;

import com.github.lit.commons.bean.BeanUtils;
import com.github.lit.menu.model.Menu;
import com.github.lit.menu.model.MenuQo;
import com.github.lit.menu.model.MenuVo;
import com.github.lit.menu.service.MenuService;
import com.github.lit.plugin.context.PluginConst;
import com.github.lit.plugin.web.ViewName;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

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


    @GetMapping
    @ViewName("menu")
    public List<MenuVo.Detail> menuList(MenuQo qo, Model model) {
        if (qo.getParentId() == null) {
            qo.setParentId(0L);
        }
        if (qo.getParentId() != 0L) {
            Menu menu = menuService.findById(qo.getParentId());
            model.addAttribute("returnId", menu == null ? 0 : menu.getParentId());
        }

        List<Menu> menus = menuService.findPageList(qo);

        return BeanUtils.convert(MenuVo.Detail.class, menus);
    }

    /**
     * 移动菜单,改变父节点
     *
     * @param parentId
     * @param ids
     * @return
     */
    @RequestMapping("/move")
    public String move(Long parentId, Long[] ids) {
        if (parentId == null) {
            parentId = 0L;
        }
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

    @GetMapping("/{id}")
    public Menu get(@PathVariable Long id) {
        return menuService.findById(id);
    }

    @PostMapping
    public void add(MenuVo.Add add) {
        menuService.insert(BeanUtils.convert(add, new Menu()));
    }

    @PutMapping
    public String update(MenuVo.Update update) {
        menuService.update(BeanUtils.convert(update, new Menu()));
        return "";
    }

    @DeleteMapping
    public void delete(Long[] ids) {
        menuService.delete(ids);
    }


}
