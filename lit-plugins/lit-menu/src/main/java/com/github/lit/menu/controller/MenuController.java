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
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

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
        if (qo.getParentId() != 0L) {
            Menu menu = menuService.findById(qo.getParentId());
            model.addAttribute("returnId", menu == null ? 0 : menu.getParentId());
        }

        List<Menu> menus = menuService.findPageList(qo);

        return BeanUtils.convert(MenuVo.Detail.class, menus);
    }

    @GetMapping("/tree")
    public List<MenuVo.Tree> menuTree() {
        List<Menu> allMenus = menuService.findAll();
        List<MenuVo.Tree> rootMenus = allMenus.stream()
                .filter(menu -> menu.getParentId() == 0L)
                .map(menu -> BeanUtils.convert(menu, new MenuVo.Tree()))
                .collect(Collectors.toList());
        Map<Long, List<MenuVo.Tree>> menuMap = allMenus.stream()
                .filter(menu -> menu.getParentId() != 0L)
                .map(menu -> BeanUtils.convert(menu, new MenuVo.Tree()))
                .collect(Collectors.groupingBy(MenuVo.Tree::getParentId));
        setChildMenu(rootMenus, menuMap);

        return rootMenus;
    }

    private void setChildMenu(List<MenuVo.Tree> parentMenus, Map<Long, List<MenuVo.Tree>> menuMap) {
        if (CollectionUtils.isEmpty(parentMenus)) {
            return;
        }
        for (MenuVo.Tree menu : parentMenus) {
            List<MenuVo.Tree> children = menuMap.get(menu.getId());
            if (!CollectionUtils.isEmpty(children)) {
                menu.setChildren(children);
                setChildMenu(children, menuMap);
            }
        }
    }

    /**
     * 移动菜单,改变父节点
     *
     * @param parentId
     * @param ids
     * @return
     */
    @PostMapping("/move")
    public String move(Long parentId, Long[] ids) {
        if (parentId == null) {
            parentId = 0L;
        }
        menuService.moveMenu(parentId, ids);
        return "";
    }

    /**
     * 启用菜单
     *
     * @param id
     * @return
     */
    @PostMapping("/enable")
    public String enable(Long id) {
        menuService.changeStatus(id, true);
        return "";
    }

    /**
     * 禁用菜单
     *
     * @param id
     * @return
     */
    @PostMapping("/disable")
    public String disable(Long id) {
        menuService.changeStatus(id, false);
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
    public void update(MenuVo.Update update) {
        menuService.update(BeanUtils.convert(update, new Menu()));
    }

    @DeleteMapping
    public void delete(Long[] ids) {
        menuService.delete(ids);
    }


}
