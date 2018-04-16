package com.github.lit.security.config;

import com.github.lit.plugin.core.constant.PluginConst;
import com.github.lit.security.context.LoginUserDetail;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * User : liulu
 * Date : 2018/4/15 22:24
 * version $Id: Handler.java, v 0.1 Exp $
 */
public class SessionAuthenticationSuccessHandler extends SavedRequestAwareAuthenticationSuccessHandler {

    public SessionAuthenticationSuccessHandler() {
        setDefaultTargetUrl("/user/pass");
        setAlwaysUseDefaultTargetUrl(true);
    }

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws ServletException, IOException {
        if (authentication.getPrincipal() instanceof LoginUserDetail) {
            request.getSession().setAttribute(PluginConst.LOGIN_USER, authentication.getPrincipal());
        }
        super.onAuthenticationSuccess(request, response, authentication);
    }
}
