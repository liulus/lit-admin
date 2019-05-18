package com.github.lit.user.controller;

import com.github.lit.support.page.PageResult;
import com.github.lit.user.model.UserQo;
import com.github.lit.user.model.UserVo;
import com.github.lit.user.service.UserService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * @author liulu
 * @version v1.0
 * date 2019-05-01
 */
@RestController
@RequestMapping("/api/user")
public class UserController {

    @Resource
    private UserService userService;


    @PostMapping("/register")
    public Long register(@RequestBody UserVo.Register register) {

        return userService.register(register);
    }


    @GetMapping("/list")
    public PageResult<UserVo.List> findUserPage(UserQo userQo) {

        return userService.findPageList(userQo);
    }


    @PostMapping
    public Long add(@RequestBody UserVo.Add add) {
        return userService.insert(add);
    }


    @PutMapping
    public void update (UserVo.Update update) {
        userService.update(update);
    }

    @DeleteMapping("/{userId}")
    public void delete (@PathVariable("userId") Long userId) {
        userService.delete(userId);
    }






}
