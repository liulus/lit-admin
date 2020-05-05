package com.lit.service.menu.controller;

import com.lit.service.menu.model.Menu;
import com.lit.service.menu.model.MenuVo;
import com.lit.service.menu.service.MenuService;
import com.lit.support.util.bean.BeanUtils;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
