package com.github.lit.security.context;

import com.github.lit.commons.bean.BeanUtils;
import com.github.lit.security.model.Authority;
import com.github.lit.security.model.Role;
import com.github.lit.security.service.AuthorityService;
import com.github.lit.security.service.RoleService;
import com.github.lit.user.model.User;
import com.github.lit.user.service.UserService;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * User : liulu
 * Date : 2017/10/24 10:19
 * version $Id: LitUserDetailService.java, v 0.1 Exp $
 */
public class LitUserDetailService implements UserDetailsService {

    @Resource
    private UserService userService;

    @Resource
    private RoleService roleService;

    @Resource
    private AuthorityService authorityService;


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        User user = userService.findByName(username);
        if (user == null) {
            throw new UsernameNotFoundException("用户 " + username + "不存在!");
        }
        LitUserDetail userDetail = BeanUtils.convert(user, new LitUserDetail());

        List<Role> roles = roleService.findByUserId(user.getId());

        List<Authority> authorities = new ArrayList<>();
        for (Role role : roles) {
            authorities.addAll(authorityService.findByRoleId(role.getRoleId()));
        }

        List<GrantedAuthority> auths = authorities.stream()
                .distinct()
                .map(authority -> new SimpleGrantedAuthority(authority.getAuthorityCode()))
                .collect(Collectors.toList());

        userDetail.setRoles(roles);
        userDetail.setAuthorities(auths);
        return userDetail;
    }

}
