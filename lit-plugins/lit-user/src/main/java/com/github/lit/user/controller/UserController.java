package com.github.lit.user.controller;

import com.github.lit.commons.bean.BeanUtils;
import com.github.lit.plugin.context.PluginConst;
import com.github.lit.plugin.web.ViewName;
import com.github.lit.user.model.User;
import com.github.lit.user.model.UserQo;
import com.github.lit.user.model.UserVo;
import com.github.lit.user.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

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

    @GetMapping
    @ViewName("user")
    public List<UserVo.List> userList(UserQo qo) {
        List<User> pageList = userService.findPageList(qo);
        return BeanUtils.convert(UserVo.List.class, pageList);
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
