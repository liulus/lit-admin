package com.github.lit.user.controller;

import com.github.lit.plugin.core.event.user.LoginEvent;
import com.github.lit.support.event.Event;
import com.github.lit.user.context.LoginMessageProvider;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * User : liulu
 * Date : 2017/8/12 11:16
 * version $Id: LoginController.java, v 0.1 Exp $
 */
@Controller
@RequestMapping("/user")
@NoArgsConstructor
public class LoginController {

    private LoginMessageProvider loginMessageProvider;

    @Autowired(required = false)
    public LoginController(LoginMessageProvider loginMessageProvider) {
        this.loginMessageProvider = loginMessageProvider;
    }

    @GetMapping("/register")
    @Secured("ROLE_ADMIN")
    public String register() {

        return "default_register";
    }

    @PostMapping("/register")
    public String doRegister() {

        return "";
    }


    @GetMapping("/login")
    public String login(Model model) {
        if (loginMessageProvider != null) {
            model.addAttribute("message", loginMessageProvider.getMessage());
        }
        return "default-login";
    }

    @PostMapping("/login")
    @Secured("ROLE_ADMIN")
    public String doLogin(String userName, String password, Model model) {

        System.out.println(userName + "--" + password);
//        userService.login
        return "redirect:/user/pass";
    }

    @RequestMapping("/pass")
    @Event(LoginEvent.class)
    public String pass() {
        System.out.println("----------------");
        return "redirect:/plugin/user";
    }


}
