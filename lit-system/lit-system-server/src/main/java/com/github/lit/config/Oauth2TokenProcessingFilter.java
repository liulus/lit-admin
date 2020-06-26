//package com.github.lit.config;
//
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.security.oauth2.common.OAuth2AccessToken;
//import org.springframework.security.oauth2.provider.authentication.BearerTokenExtractor;
//import org.springframework.security.oauth2.provider.authentication.OAuth2AuthenticationManager;
//import org.springframework.security.oauth2.provider.authentication.OAuth2AuthenticationProcessingFilter;
//import org.springframework.security.oauth2.provider.token.DefaultTokenServices;
//import org.springframework.security.oauth2.provider.token.TokenStore;
//
//import javax.servlet.http.Cookie;
//import javax.servlet.http.HttpServletRequest;
//import java.util.Objects;
//
///**
// * @author liulu
// * @version v1.0
// * date 2019-05-11
// */
//@Slf4j
////@Component
//public class Oauth2TokenProcessingFilter extends OAuth2AuthenticationProcessingFilter {
//
//
//    public Oauth2TokenProcessingFilter(TokenStore tokenStore) {
//        this.init(tokenStore);
//    }
//
//
//    private void init(TokenStore tokenStore) {
//        OAuth2AuthenticationManager authenticationManager = new OAuth2AuthenticationManager();
//        authenticationManager.setResourceId("oauth2-resource");
//        DefaultTokenServices tokenServices = new DefaultTokenServices();
//        tokenServices.setTokenStore(tokenStore);
//        tokenServices.setSupportRefreshToken(true);
//        authenticationManager.setTokenServices(tokenServices);
//
//        setAuthenticationManager(authenticationManager);
//        setTokenExtractor(new CookieBearerTokenExtractor());
//    }
//
//
//    public static class CookieBearerTokenExtractor extends BearerTokenExtractor {
//
//        @Override
//        protected String extractToken(HttpServletRequest request) {
//            // 优先父类解析, 如果还为空, 从cookie中获取
//            String token = super.extractToken(request);
//
//            Cookie[] cookies = request.getCookies();
//            if (cookies == null) {
//                return token;
//            }
//
//            for (Cookie cookie : cookies) {
//                if (Objects.equals(OAuth2AccessToken.ACCESS_TOKEN, cookie.getName())) {
//                    token = cookie.getValue();
//                    break;
//                }
//            }
//            return token;
//        }
//    }
//
//
//
//}
