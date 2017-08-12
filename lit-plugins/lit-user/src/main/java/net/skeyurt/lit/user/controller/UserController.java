package net.skeyurt.lit.user.controller;

import net.skeyurt.lit.user.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;

/**
 * User : liulu
 * Date : 2017/8/12 11:16
 * version $Id: UserController.java, v 0.1 Exp $
 */
@Controller
@RequestMapping("/plugin/user")
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

        return "default_login";
    }

    @PostMapping("/login")
    public String doLogin() {

        return "";
    }







}
