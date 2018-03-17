package com.github.lit.user.service.impl;

import com.github.lit.commons.bean.BeanUtils;
import com.github.lit.jdbc.JdbcTools;
import com.github.lit.jdbc.enums.JoinType;
import com.github.lit.jdbc.statement.select.Select;
import com.github.lit.plugin.exception.AppException;
import com.github.lit.user.model.*;
import com.github.lit.user.service.UserService;
import com.github.lit.user.util.UserUtils;
import com.google.common.base.Strings;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.io.Serializable;
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


    @Resource
    private JdbcTools jdbcTools;

    @Override
    public UserVo findById(Long id) {

        User user = jdbcTools.get(User.class, id);
        if (user == null) {
            return null;
        }

        UserVo userVo = BeanUtils.convert(user, new UserVo());

        if (user.getOrgId() != null) {
            Organization organization = jdbcTools.get(Organization.class, user.getOrgId());
            BeanUtils.convert(userVo, organization);
        }

        return userVo;
    }

    @Override
    public User findByName(String username) {
        return jdbcTools.select(User.class).where("userName").equalsTo(username)
                .or("mobilePhone").equalsTo(username)
                .single();
    }

    @Override
    public List<UserVo> findPageList(UserQo qo) {
        LoginUser loginUser = UserUtils.getLoginUser();

        if (loginUser != null && loginUser.hasOrg()) {
            qo.setSerialNum(loginUser.getSerialNum());
            if (Strings.isNullOrEmpty(qo.getOrgCode())) {
                qo.setOrgCode(loginUser.getOrgCode());
            }
        }

        List<UserVo> userVos = buildSelect(qo).page(qo).list(UserVo.class);

        return userVos;
    }

    @Override
    public void insert(UserVo userVo) {
        checkUserName(userVo.getUserName());
        checkEmail(userVo.getEmail());
        checkMobilePhone(userVo.getMobilePhone());
        userVo.setPassword(UserUtils.encode("123456"));
        jdbcTools.insert(BeanUtils.convert(new User(), userVo));
    }

    private void checkUserName(String userName) {

        if (Strings.isNullOrEmpty(userName)) {
            return;
        }
        int count = jdbcTools.select(User.class).where("userName").equalsTo(userName).count();
        if (count >= 1) {
            throw new AppException("改用户名被使用 !");
        }
    }

    private void checkEmail(String email) {
        if (Strings.isNullOrEmpty(email)) {
            return;
        }
        int count = jdbcTools.select(User.class).where("email").equalsTo(email).count();
        if (count >= 1) {
            throw new AppException("改邮箱已被使用 !");
        }
    }

    private void checkMobilePhone(String mobilePhone) {
        if (Strings.isNullOrEmpty(mobilePhone)) {
            return;
        }
        int count = jdbcTools.select(User.class).where("mobilePhone").equalsTo(mobilePhone).count();
        if (count >= 1) {
            throw new AppException("改手机号已被使用 !");
        }
    }

    @Override
    public void update(UserVo userVo) {

        User oldUser = jdbcTools.get(User.class, userVo.getUserId());

        if (!Objects.equals(oldUser.getUserName(), userVo.getUserCode())) {
            checkUserName(userVo.getUserName());
        }
        if (!Objects.equals(oldUser.getEmail(), userVo.getEmail())) {
            checkEmail(userVo.getEmail());
        }
        if (!Objects.equals(oldUser.getMobilePhone(), userVo.getMobilePhone())) {
            checkMobilePhone(userVo.getMobilePhone());
        }
        userVo.setUserType(null);
        userVo.setUserStatus(null);

        jdbcTools.update(BeanUtils.convert(new User(), userVo));
    }

    @Override
    public void delete(Serializable[] ids) {
        jdbcTools.deleteByIds(User.class, ids);
    }


    private Select<User> buildSelect(UserQo qo) {

        Select<User> select = jdbcTools.select(User.class);

        if (qo.getUserId() != null) {
            select.and("userId").equalsTo(qo.getUserId());

            if (!Strings.isNullOrEmpty(qo.getSerialNum())) {
                select.join(JoinType.LEFT, Organization.class)
                        .on(User.class, "orgId").equalsTo(Organization.class, "orgId")
                        .additionalField(Organization.class, "orgCode", "orgName")
                        .and(Organization.class, "serialNum").like(qo.getSerialNum() + "%");
            }
        } else if (qo.getOrgId() != null) {
            select.and("orgId").equalsTo(qo.getOrgId());
        }

        select.or("orgId").isNull().desc("orgId");

        return select;
    }

}
