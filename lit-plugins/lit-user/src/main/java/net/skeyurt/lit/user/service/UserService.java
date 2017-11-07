package net.skeyurt.lit.user.service;

import net.skeyurt.lit.user.vo.UserVo;

import java.util.List;

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
     * @return
     */
    UserVo findById(Long id);

    /**
     * 根据用户名查询用户
     *
     * @param username 用户名
     */
    UserVo findByName(String username);


    /**
     * 根据条件查询用户列表
     *
     * @param vo 查询条件
     * @return
     */
    List<UserVo> queryPageList(UserVo vo);

    /**
     * 新增用户
     *
     * @param userVo
     */
    void insert(UserVo userVo);

    /**
     * 修改用户
     *
     * @param userVo
     */
    void update(UserVo userVo);

    /**
     * 删除用户
     *
     * @param ids
     */
    void delete(Long... ids);

}
