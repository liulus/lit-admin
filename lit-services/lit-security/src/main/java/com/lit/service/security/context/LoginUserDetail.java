package com.lit.service.security.context;

import com.lit.service.core.model.LoginUser;
import com.lit.service.security.model.Role;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.util.CollectionUtils;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * User : liulu
 * Date : 2017/10/24 10:26
 * version $Id: LitUserDetail.java, v 0.1 Exp $
 */

@Getter
@Setter
@ToString
public class LoginUserDetail extends LoginUser implements UserDetails {

    private static final long serialVersionUID = 6501124930968849319L;

    private List<Role> roles;

    private List<GrantedAuthority> authorities;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        if (CollectionUtils.isEmpty(getAuths())) {
            return Collections.emptyList();
        }
        if (CollectionUtils.isEmpty(authorities)) {
            authorities = getAuths().stream()
                    .map(SimpleGrantedAuthority::new)
                    .collect(Collectors.toList());
        }
        return authorities;
    }

    @Override
    public String getUsername() {
        return getUserName();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return !getLock();
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
