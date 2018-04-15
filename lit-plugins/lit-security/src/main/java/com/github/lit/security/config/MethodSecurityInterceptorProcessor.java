package com.github.lit.security.config;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.security.access.AccessDecisionManager;
import org.springframework.security.access.AccessDecisionVoter;
import org.springframework.security.access.intercept.aopalliance.MethodSecurityInterceptor;
import org.springframework.security.access.vote.AbstractAccessDecisionManager;
import org.springframework.security.access.vote.RoleVoter;

/**
 * User : liulu
 * Date : 2018/4/14 20:59
 * version $Id: MethodSecurityInterceptorProcessor.java, v 0.1 Exp $
 */
public class MethodSecurityInterceptorProcessor implements BeanPostProcessor {


    @Override
    public Object postProcessBeforeInitialization(Object o, String s) throws BeansException {
        return o;
    }

    @Override
    public Object postProcessAfterInitialization(Object o, String s) throws BeansException {

        if (o instanceof MethodSecurityInterceptor) {
            AccessDecisionManager accessDecisionManager = ((MethodSecurityInterceptor) o).getAccessDecisionManager();
            if (accessDecisionManager instanceof AbstractAccessDecisionManager) {
                for (AccessDecisionVoter<?> voter : ((AbstractAccessDecisionManager) accessDecisionManager).getDecisionVoters()) {
                    if (voter instanceof RoleVoter) {
                        ((RoleVoter) voter).setRolePrefix("");
                    }
                }
            }
        }
        return o;
    }
}
