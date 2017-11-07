package net.skeyurt.lit.security.context;

import net.skeyurt.lit.user.service.UserService;
import net.skeyurt.lit.user.vo.UserVo;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * User : liulu
 * Date : 2017/10/24 10:19
 * version $Id: LitUserDetailService.java, v 0.1 Exp $
 */
public class LitUserDetailService implements UserDetailsService {

    @Resource
    private UserService userService;


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        UserVo userVo = userService.findByName(username);
        if (userVo == null) {
            throw new UsernameNotFoundException("用户 " + username + "不存在!");
        }

        List<GrantedAuthority> authorities = new ArrayList<>();

        authorities.add(new SimpleGrantedAuthority("ROLE_" + "USER_MANAGER"));
        authorities.add(new SimpleGrantedAuthority("ROLE_" + "DICTIONARY_MANAGER"));


        User user = new User(userVo.getUserName(), userVo.getPassword(), authorities);


        return user;
    }
}
