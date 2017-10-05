package net.skeyurt.lit.user.service.impl;

import com.google.common.base.Strings;
import net.skeyurt.lit.jdbc.JdbcTools;
import net.skeyurt.lit.jdbc.enums.Logic;
import net.skeyurt.lit.jdbc.sta.Select;
import net.skeyurt.lit.user.context.LoginUser;
import net.skeyurt.lit.user.entity.Organization;
import net.skeyurt.lit.user.entity.User;
import net.skeyurt.lit.user.service.UserService;
import net.skeyurt.lit.user.util.UserUtils;
import net.skeyurt.lit.user.vo.UserVo;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

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
    public List<User> queryPageList(UserVo vo) {
        LoginUser loginUser = UserUtils.getLoginUser();

        if (loginUser != null && loginUser.hasOrg()) {
            vo.setSerialNum(loginUser.getSerialNum());
            if (Strings.isNullOrEmpty(vo.getOrgCode())) {
                vo.setOrgCode(loginUser.getOrgCode());
            }
        }

        List<User> users = buildSelect(vo).page(vo).list();

        return users;
    }


    private Select<User> buildSelect(UserVo vo) {

        Select<User> select = jdbcTools.createSelect(User.class);

        if (vo.getUserId() != null) {
            select.and("userId", vo.getUserId());

            if (!Strings.isNullOrEmpty(vo.getSerialNum())) {

                List<Integer> orgIds = jdbcTools.createSelect(Organization.class)
                        .include("orgId")
                        .where("serialNum", Logic.LIKE, vo.getSerialNum() + "%")
                        .list(Integer.class);

                select.and("orgId", Logic.IN, orgIds.toArray());
            }
        } else if (vo.getOrgId() != null) {
            select.and("orgId", vo.getOrgId());
        }

        select.or("orgId", null)
                .desc("orgId");

        return select;
    }

}
