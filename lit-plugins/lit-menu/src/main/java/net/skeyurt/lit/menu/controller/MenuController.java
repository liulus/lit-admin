package net.skeyurt.lit.menu.controller;

import lombok.extern.slf4j.Slf4j;
import net.skeyurt.lit.commons.context.ResultConst;
import net.skeyurt.lit.menu.service.MenuService;
import net.skeyurt.lit.menu.vo.MenuVo;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;

/**
 * User : liulu
 * Date : 2017/7/13 19:46
 * version $Id: MenuController.java, v 0.1 Exp $
 */
@Controller
@RequestMapping("/plugin/menu")
@Slf4j
public class MenuController {

    @Resource
    private MenuService menuService;


    @RequestMapping({"", "/list"})
    public String menuList(MenuVo vo, Model model) {
        model.addAttribute(ResultConst.RESULT, menuService.queryPageList(vo));
        return "menu";
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
