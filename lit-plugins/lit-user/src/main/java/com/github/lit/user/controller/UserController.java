package com.github.lit.user.controller;

import com.github.lit.commons.context.ResultConst;
import com.github.lit.plugin.context.PluginConst;
import com.github.lit.user.service.UserService;
import com.github.lit.user.vo.UserVo;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;
import java.util.List;

/**
 * User : liulu
 * Date : 2017/8/12 11:16
 * version $Id: UserController.java, v 0.1 Exp $
 */
@Controller
@RequestMapping(PluginConst.URL_PREFIX + "/user")
public class UserController {

    @Resource
    private UserService userService;


    @GetMapping("/register")
    public String register() {

        return "default_register";
    }

    @PostMapping("/register")
    public String doRegister() {

        return "";
    }


    @GetMapping("/login")
    public String login() {
        return "default-login";
    }

    @PostMapping("/login")
    public String doLogin(String userName, String password, Model model) {

        System.out.println(userName + "--" + password);
//        userService.login
        return "redirect:/plugin/user";
    }


    @RequestMapping({"/list", ""})
    public String userList(UserVo vo, Model model) {

        List<UserVo> userVos = userService.queryPageList(vo);
        model.addAttribute(ResultConst.RESULT, userVos);
        return "user";
    }

    @RequestMapping("get")
    public String get(Long id, Model model) {
        UserVo userVo = userService.findById(id);
        model.addAttribute(ResultConst.RESULT, userVo);
        return "";
    }

    @RequestMapping("/add")
    public String add(UserVo userVo, Model model) {
        userService.insert(userVo);
        return "";
    }

    @RequestMapping("/update")
    public String update(UserVo userVo) {
        userService.update(userVo);
        return "";
    }

    @RequestMapping("/delete")
    public String delete(Long... ids) {
        userService.delete(ids);
        return "";
    }


}
