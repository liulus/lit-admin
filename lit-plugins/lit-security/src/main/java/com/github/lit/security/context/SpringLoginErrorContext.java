package com.github.lit.security.context;

import com.github.lit.plugin.web.WebUtils;
import com.github.lit.user.context.LoginErrorContext;
import org.springframework.security.authentication.*;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.WebAttributes;
import org.springframework.security.web.authentication.session.SessionAuthenticationException;
import org.springframework.stereotype.Component;

/**
 * User : liulu
 * Date : 2017/12/31 16:32
 * version $Id: SpringLoginErrorContext.java, v 0.1 Exp $
 */
@Component
public class SpringLoginErrorContext implements LoginErrorContext {

    @Override
    public String getLoginError() {

        Exception loginException = (Exception) WebUtils.getSessionAttribute(WebAttributes.AUTHENTICATION_EXCEPTION);
        if (loginException == null) {
            return null;
        }

        if (loginException instanceof UsernameNotFoundException
                || loginException instanceof BadCredentialsException) {
            return "用户名或密码错误!";
        }
        if (loginException instanceof LockedException) {
            return "该用户已被锁定!";
        }
        if (loginException instanceof DisabledException) {
            return "该用户已被禁用!";
        }
        if (loginException instanceof AccountExpiredException) {
            return "该用户已过期!";
        }
        if (loginException instanceof CredentialsExpiredException) {
            return "密码已过期!";
        }
        if (loginException instanceof SessionAuthenticationException) {
            return "该用户已登录!";
        }
        return "未知异常";
    }
}
