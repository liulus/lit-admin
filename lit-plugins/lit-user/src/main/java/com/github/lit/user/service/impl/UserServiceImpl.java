package com.github.lit.user.service.impl;

import com.github.lit.plugin.exception.AppException;
import com.github.lit.user.dao.UserDao;
import com.github.lit.user.model.LoginUser;
import com.github.lit.user.model.User;
import com.github.lit.user.model.UserQo;
import com.github.lit.user.service.UserService;
import com.github.lit.user.util.UserUtils;
import com.google.common.base.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;

/**
 * User : liulu
 * Date : 2017/8/12 11:20
 * version $Id: UserServiceImpl.java, v 0.1 Exp $
 */
@Service
@Transactional
public class UserServiceImpl implements UserService {

    private final UserDao userDao;

    @Autowired
    public UserServiceImpl(UserDao userDao) {
        this.userDao = userDao;
    }

    @Override
    public User findById(Long id) {
        return userDao.findById(id);
    }

    @Override
    public User findByName(String username) {
        return userDao.findByProperty("userName", username);
    }

    @Override
    public List<User> findPageList(UserQo qo) {
        LoginUser loginUser = UserUtils.getLoginUser();

        if (loginUser != null && loginUser.hasOrg()) {
            qo.setSerialNum(loginUser.getSerialNum());
            if (Strings.isNullOrEmpty(qo.getOrgCode())) {
                qo.setOrgCode(loginUser.getOrgCode());
            }
        }

        return userDao.findPageList(qo);
    }

    @Override
    public Long insert(User user) {
        checkUserName(user.getUserName());
        checkEmail(user.getEmail());
        checkMobilePhone(user.getMobileNum());
        user.setPassword(UserUtils.encode("123456"));
        return userDao.insert(user);
    }

    @Override
    public void update(User user) {

        User oldUser = userDao.findById(user.getUserId());

        if (!Objects.equals(oldUser.getUserName(), user.getUserName())) {
            checkUserName(user.getUserName());
        }
        if (!Objects.equals(oldUser.getEmail(), user.getEmail())) {
            checkEmail(user.getEmail());
        }
        if (!Objects.equals(oldUser.getMobileNum(), user.getMobileNum())) {
            checkMobilePhone(user.getMobileNum());
        }

        userDao.update(user);
    }

    private void checkUserName(String userName) {

        if (Strings.isNullOrEmpty(userName)) {
            return;
        }
        int count = userDao.getSelect().where(User::getUserName).equalsTo(userName).count();
        if (count >= 1) {
            throw new AppException("该用户名被使用");
        }
    }

    private void checkEmail(String email) {
        if (Strings.isNullOrEmpty(email)) {
            return;
        }
        int count = userDao.getSelect().where(User::getEmail).equalsTo(email).count();
        if (count >= 1) {
            throw new AppException("该邮箱已被使用");
        }
    }

    private void checkMobilePhone(String mobileNum) {
        if (Strings.isNullOrEmpty(mobileNum)) {
            return;
        }
        int count = userDao.getSelect().where(User::getMobileNum).equalsTo(mobileNum).count();
        if (count >= 1) {
            throw new AppException("该手机号已被使用");
        }
    }

    @Override
    public void delete(Long[] ids) {
        userDao.deleteByIds(ids);
    }


}
