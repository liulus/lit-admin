package com.github.lit.user.service;

import com.github.lit.support.page.Page;
import com.github.lit.user.model.User;
import com.github.lit.user.model.UserQo;

/**
 * User : liulu
 * Date : 2017/8/12 11:20
 * version $Id: UserService.java, v 0.1 Exp $
 */
public interface UserService {

    /**
     * 根据用户 Id 查询用户
     *
     * @param id 用户Id
     * @return User
     */
    User findById(Long id);

    /**
     * 根据用户名查询用户
     *
     * @param username 用户名
     * @return user
     */
    User findByName(String username);


    /**
     * 根据条件查询用户列表
     *
     * @param qo 查询条件
     * @return 用户列表
     */
    Page<User> findPageList(UserQo qo);

    /**
     * 新增用户
     *
     * @param user user
     */
    Long insert(User user);

    /**
     * 修改用户
     *
     * @param user user
     */
    void update(User user);

    /**
     * 删除用户
     *
     * @param ids ids
     */
    void delete(Long[] ids);

}
