package com.github.lit.menu.controller;

import com.github.lit.menu.model.Menu;
import com.github.lit.menu.model.MenuVo;
import com.github.lit.menu.service.MenuService;
import com.github.lit.support.util.BeanUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author liulu
 * @version v1.0
 * date 2019-04-24
 */
@RestController
@RequestMapping("/api")
public class MenuController {

    @Resource
    private MenuService menuService;

    @GetMapping("/menu/tree")
    public List<MenuVo.Detail> menuTree() {
        return menuService.buildMenuTree(false, false);
    }

    @GetMapping("/my/menu")
    public List<MenuVo.Detail> myMenu() {
        return menuService.buildMenuTree(true, true);
    }


    @PostMapping("/menu")
    public Long add(@RequestBody MenuVo.Add add) {
        return menuService.insert(BeanUtils.convert(add, new Menu()));
    }

    @PutMapping("/menu")
    public int update(@RequestBody MenuVo.Update menuVo) {
        return menuService.update(BeanUtils.convert(menuVo, new Menu()));
    }

    @PostMapping("/menu/change/status/{id}")
    public void changeStatus(@PathVariable Long id) {
        menuService.changeStatus(id);
    }

    @DeleteMapping("/menu/{id}")
    public void delete(@PathVariable Long id) {
        menuService.delete(new Long[]{id});
    }


}
