package com.github.lit.user.controller;

import com.github.lit.plugin.core.constant.PluginConst;
import com.github.lit.support.annotation.ViewName;
import com.github.lit.support.page.Page;
import com.github.lit.support.page.PageUtils;
import com.github.lit.support.util.BeanUtils;
import com.github.lit.user.model.User;
import com.github.lit.user.model.UserQo;
import com.github.lit.user.model.UserVo;
import com.github.lit.user.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

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

    @GetMapping
    @ViewName("user")
    public Page<UserVo.List> userList(UserQo qo) {
        Page<User> pageList = userService.findPageList(qo);
        return PageUtils.convert(pageList, UserVo.List.class, null);
    }

    @GetMapping("/{id}")
    public User get(@PathVariable Long id) {
        return userService.findById(id);
    }

    @PostMapping
    public Long add(UserVo.Add add) {
        return userService.insert(BeanUtils.convert(add, new User()));
    }

    @PutMapping
    public void update(UserVo.Update update) {
        userService.update(BeanUtils.convert(update, new User()));
    }

    @DeleteMapping
    public void delete(Long[] ids) {
        userService.delete(ids);
    }


}
