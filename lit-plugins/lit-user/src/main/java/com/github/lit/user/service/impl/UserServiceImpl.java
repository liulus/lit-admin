package com.github.lit.user.service.impl;

import com.github.lit.commons.bean.BeanUtils;
import com.github.lit.jdbc.JdbcTools;
import com.github.lit.jdbc.enums.JoinType;
import com.github.lit.jdbc.enums.Logic;
import com.github.lit.jdbc.sta.Select;
import com.github.lit.plugin.exception.AppException;
import com.github.lit.user.context.LoginUser;
import com.github.lit.user.entity.Organization;
import com.github.lit.user.entity.User;
import com.github.lit.user.service.UserService;
import com.github.lit.user.util.UserUtils;
import com.github.lit.user.vo.UserVo;
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

        UserVo userVo = BeanUtils.convert(new UserVo(), user);

        if (user.getOrgId() != null) {
            Organization organization = jdbcTools.get(Organization.class, user.getOrgId());
            BeanUtils.convert(userVo, organization);
        }

        return userVo;
    }

    @Override
    public UserVo findByName(String username) {
        User user = jdbcTools.createSelect(User.class).where("userName", username)
                .or("mobilePhone", username)
                .single();
        if (user == null) {
            return null;
        }

        return BeanUtils.convert(new UserVo(), user);
    }

    @Override
    public List<UserVo> queryPageList(UserVo vo) {
        LoginUser loginUser = UserUtils.getLoginUser();

        if (loginUser != null && loginUser.hasOrg()) {
            vo.setSerialNum(loginUser.getSerialNum());
            if (Strings.isNullOrEmpty(vo.getOrgCode())) {
                vo.setOrgCode(loginUser.getOrgCode());
            }
        }

        List<UserVo> userVos = buildSelect(vo).page(vo).list(UserVo.class);

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
        int count = jdbcTools.createSelect(User.class).where("userName", userName).count();
        if (count >= 1) {
            throw new AppException("改用户名被使用 !");
        }
    }

    private void checkEmail(String email) {
        if (Strings.isNullOrEmpty(email)) {
            return;
        }
        int count = jdbcTools.createSelect(User.class).where("email", email).count();
        if (count >= 1) {
            throw new AppException("改邮箱已被使用 !");
        }
    }

    private void checkMobilePhone(String mobilePhone) {
        if (Strings.isNullOrEmpty(mobilePhone)) {
            return;
        }
        int count = jdbcTools.createSelect(User.class).where("mobilePhone", mobilePhone).count();
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


    private Select<User> buildSelect(UserVo vo) {

        Select<User> select = jdbcTools.createSelect(User.class).where("1", 1);

        if (vo.getUserId() != null) {
            select.and("userId", vo.getUserId());

            if (!Strings.isNullOrEmpty(vo.getSerialNum())) {
                select.join(JoinType.LEFT, Organization.class)
                        .on(User.class, "orgId", Logic.EQ, Organization.class, "orgId")
                        .addField(Organization.class, "orgCode", "orgName")
                        .and(Organization.class, "serialNum", Logic.LIKE, vo.getSerialNum() + "%");
            }
        } else if (vo.getOrgId() != null) {
            select.and("orgId", vo.getOrgId());
        }

        select.or("orgId", null).desc("orgId");

        return select;
    }

}
