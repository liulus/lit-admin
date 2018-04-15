package com.github.lit.security.context;

import com.github.lit.commons.bean.BeanUtils;
import com.github.lit.security.model.Authority;
import com.github.lit.security.model.Role;
import com.github.lit.security.service.AuthorityService;
import com.github.lit.security.service.RoleService;
import com.github.lit.user.model.User;
import com.github.lit.user.service.OrganizationService;
import com.github.lit.user.service.UserService;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * User : liulu
 * Date : 2017/10/24 10:19
 * version $Id: LoginUserDetailService.java, v 0.1 Exp $
 */
@Component
public class LoginUserDetailService implements UserDetailsService {

    @Resource
    private UserService userService;

    @Resource
    private OrganizationService organizationService;

    @Resource
    private RoleService roleService;

    @Resource
    private AuthorityService authorityService;

    private static final String[] ADMIN_USER = {"admin", "liulu"};


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        User user = userService.findByName(username);
        if (user == null) {
            throw new UsernameNotFoundException("用户 " + username + "不存在!");
        }
        // 构建 loginUser
        LoginUserDetail userDetail = BeanUtils.convert(user, new LoginUserDetail());
        if (user.getOrgId() != null && user.getOrgId() != 0L) {
            userDetail.setOrg(organizationService.findById(user.getId()));
        }

        // 超级管理员拥有所有权限
        if (Arrays.binarySearch(ADMIN_USER, user.getUserName()) >= 0) {
            List<Authority> authorities = authorityService.findAll();
            userDetail.setAuthorities(authorities.stream()
                    .map(authority -> new SimpleGrantedAuthority(authority.getCode()))
                    .collect(Collectors.toList()));
            return userDetail;
        }

        List<Role> roles = roleService.findByUserId(user.getId());
        if (CollectionUtils.isEmpty(roles)) {
            return userDetail;
        }
        // 查询用户权限
        List<Authority> roleAuthorities = authorityService.findByRoleIds(roles.stream().map(Role::getId).toArray(Long[]::new));

        userDetail.setRoles(roles);
        userDetail.setAuthorities(roleAuthorities.stream()
                .map(authority -> new SimpleGrantedAuthority(authority.getCode()))
                .collect(Collectors.toList()));
        return userDetail;
    }

}
