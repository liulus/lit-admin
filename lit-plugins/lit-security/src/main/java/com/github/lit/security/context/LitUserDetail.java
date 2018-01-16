package com.github.lit.security.context;

import com.github.lit.security.model.Authority;
import com.github.lit.security.model.Role;
import com.github.lit.user.model.LoginUser;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

/**
 * User : liulu
 * Date : 2017/10/24 10:26
 * version $Id: LitUserDetail.java, v 0.1 Exp $
 */

@Getter
@Setter
@ToString
public class LitUserDetail extends LoginUser implements UserDetails {

    private static final long serialVersionUID = 6501124930968849319L;

    private List<Role> roles;

    private List<Authority> authorities;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }

    @Override
    public String getUsername() {
        return getUserName();
    }

    @Override
    public boolean isAccountNonExpired() {
        return false;
    }

    @Override
    public boolean isAccountNonLocked() {
        return getLock();
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return false;
    }

    @Override
    public boolean isEnabled() {
        return false;
    }
}
