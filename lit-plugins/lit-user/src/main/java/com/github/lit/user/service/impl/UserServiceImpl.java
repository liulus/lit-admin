package com.github.lit.user.service.impl;

import com.github.lit.plugin.core.model.LoginUser;
import com.github.lit.support.exception.BizException;
import com.github.lit.support.jdbc.JdbcRepository;
import com.github.lit.support.page.PageResult;
import com.github.lit.support.page.PageUtils;
import com.github.lit.support.util.BeanUtils;
import com.github.lit.user.model.User;
import com.github.lit.user.model.UserQo;
import com.github.lit.user.model.UserVo;
import com.github.lit.user.service.UserService;
import com.github.lit.user.util.UserUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
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
    public Long register(UserVo.Register register) {
        if (!Objects.equals(register.getPassword(), register.getConfirmPassword())) {
            throw new BizException("密码和确认密码不一致");
        }
        if (!Objects.equals(register.getSmsCaptcha(), "1234")) {
            throw new BizException("手机验证码不正确");
        }

        return insert(BeanUtils.convert(register, new UserVo.Add()));
    }

    @Override
    public User findById(Long id) {
        return jdbcRepository.selectById(User.class, id);
    }

    @Override
    public User findByName(String username) {
        return jdbcRepository.selectByProperty(User::getUserName, username);
    }

    @Override
    public PageResult<UserVo.List> findPageList(UserQo qo) {
        LoginUser loginUser = UserUtils.getLoginUser();

        if (loginUser != null && loginUser.hasOrg()) {
            qo.setSerialNum(loginUser.getLevelIndex());
            if (StringUtils.isEmpty(qo.getOrgCode())) {
                qo.setOrgCode(loginUser.getOrgCode());
            }
        }
        PageResult<User> pageList = jdbcRepository.selectPageList(User.class, qo);
        return PageUtils.convert(pageList, UserVo.List.class);
    }

    @Override
    public Long insert(UserVo.Add user) {
        checkUserName(user.getUserName());
        checkEmail(user.getEmail());
        checkMobileNum(user.getMobileNum());

        User addUser = BeanUtils.convert(user, new User());

        // 设置默认值
        addUser.setLock(false);
        addUser.setCreator(UserUtils.getLoginUser().getUserName());
        String password = StringUtils.hasText(user.getPassword()) ? user.getPassword() : "123456";
        addUser.setPassword(UserUtils.encode(password));
        jdbcRepository.insert(addUser);
        return addUser.getId();
    }

    @Override
    public void update(UserVo.Update user) {

        User oldUser = findById(user.getId());

        if (!Objects.equals(oldUser.getUserName(), user.getUserName())) {
            checkUserName(user.getUserName());
        }
        if (!Objects.equals(oldUser.getEmail(), user.getEmail())) {
            checkEmail(user.getEmail());
        }
        if (!Objects.equals(oldUser.getMobileNum(), user.getMobileNum())) {
            checkMobileNum(user.getMobileNum());
        }
        User upUser = BeanUtils.convert(user, new User());
        jdbcRepository.updateSelective(upUser);
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

    private void checkMobileNum(String mobileNum) {
        if (StringUtils.isEmpty(mobileNum)) {
            return;
        }
        int count = jdbcRepository.countByProperty(User::getMobileNum, mobileNum);
        if (count >= 1) {
            throw new BizException("该手机号已被使用");
        }
    }

    @Override
    public void delete(Long id) {
        jdbcRepository.deleteById(User.class, id);
    }


}
