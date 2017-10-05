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
     * 根据条件查询用户列表
     *
     * @param vo 查询条件
     * @return
     */
    List<UserVo> queryPageList(UserVo vo);
}
