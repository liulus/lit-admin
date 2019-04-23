package com.github.lit.user.service.impl;

import com.github.lit.plugin.core.model.LoginUser;
import com.github.lit.support.exception.BizException;
import com.github.lit.support.jdbc.JdbcRepository;
import com.github.lit.support.page.PageResult;
import com.github.lit.user.model.User;
import com.github.lit.user.model.UserQo;
import com.github.lit.user.service.UserService;
import com.github.lit.user.util.UserUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.Objects;

/**
 * User : liulu
 * Date : 2017/8/12 11:20
 * version $Id: UserServiceImpl.java, v 0.1 Exp $
 */
@Service
@Transactional
public class UserServiceImpl implements UserService {


    @Resource
    private JdbcRepository jdbcRepository;

    @Override
    public User findById(Long id) {
        return jdbcRepository.selectById(User.class, id);
    }

    @Override
    public User findByName(String username) {
        return jdbcRepository.selectByProperty(User::getUserName, username);
    }

    @Override
    public PageResult<User> findPageList(UserQo qo) {
        LoginUser loginUser = UserUtils.getLoginUser();

        if (loginUser != null && loginUser.hasOrg()) {
            qo.setSerialNum(loginUser.getLevelIndex());
            if (StringUtils.isEmpty(qo.getOrgCode())) {
                qo.setOrgCode(loginUser.getOrgCode());
            }
        }
        return jdbcRepository.selectPageList(User.class, qo);
    }

    @Override
    public Long insert(User user) {
        checkUserName(user.getUserName());
        checkEmail(user.getEmail());
        checkMobilePhone(user.getMobileNum());

        // 设置默认值
        user.setLock(false);
        user.setCreator(UserUtils.getLoginUser().getUserName());
        user.setPassword(UserUtils.encode("123456"));
        jdbcRepository.insert(user);
        return user.getId();
    }

    @Override
    public void update(User user) {

        User oldUser = findById(user.getId());

        if (!Objects.equals(oldUser.getUserName(), user.getUserName())) {
            checkUserName(user.getUserName());
        }
        if (!Objects.equals(oldUser.getEmail(), user.getEmail())) {
            checkEmail(user.getEmail());
        }
        if (!Objects.equals(oldUser.getMobileNum(), user.getMobileNum())) {
            checkMobilePhone(user.getMobileNum());
        }

        jdbcRepository.updateSelective(user);
    }

    private void checkUserName(String userName) {

        if (StringUtils.isEmpty(userName)) {
            return;
        }
        int count = jdbcRepository.countByProperty(User::getUserName, userName);
        if (count >= 1) {
            throw new BizException("该用户名被使用");
        }
    }

    private void checkEmail(String email) {
        if (StringUtils.isEmpty(email)) {
            return;
        }
        int count = jdbcRepository.countByProperty(User::getEmail, email);
        if (count >= 1) {
            throw new BizException("该邮箱已被使用");
        }
    }

    private void checkMobilePhone(String mobileNum) {
        if (StringUtils.isEmpty(mobileNum)) {
            return;
        }
        int count = jdbcRepository.countByProperty(User::getMobileNum, mobileNum);
        if (count >= 1) {
            throw new BizException("该手机号已被使用");
        }
    }

    @Override
    public void delete(Long[] ids) {
        jdbcRepository.deleteByIds(User.class, Arrays.asList(ids));
    }


}
