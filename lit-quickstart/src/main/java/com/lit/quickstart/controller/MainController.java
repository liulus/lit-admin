package com.lit.quickstart.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * User : liulu
 * Date : 2016-10-27 20:11
 * version $Id: MainController.java, v 0.1 Exp $
 */
@Controller
public class MainController {

    @RequestMapping("/")
    public String index() {
        return "main";
    }


}
