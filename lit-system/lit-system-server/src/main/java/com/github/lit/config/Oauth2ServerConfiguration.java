//package com.github.lit.config;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.beans.factory.annotation.Qualifier;
//import org.springframework.context.annotation.Bean;
//import org.springframework.security.authentication.AuthenticationManager;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.crypto.password.NoOpPasswordEncoder;
//import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
//import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
//import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
//import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
//import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
//import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;
//import org.springframework.security.oauth2.provider.token.TokenStore;
//import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
//import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;
//
//import javax.annotation.Resource;
//
///**
// * @author liulu
// * @version v1.0
// * date 2019-05-10
// */
////@Configuration
//public class Oauth2ServerConfiguration {
//
//    @Bean
//    public JwtAccessTokenConverter jwtAccessTokenConverter() {
//        JwtAccessTokenConverter converter = new JwtAccessTokenConverter();
//        converter.setSigningKey("lit-admin");
//        return converter;
//    }
//
//    @Bean
//    public TokenStore tokenStore() {
//        return new JwtTokenStore(jwtAccessTokenConverter());
//    }
//
//
////    @Configuration
////    @EnableResourceServer
//    protected static class ResourceServerConfiguration extends ResourceServerConfigurerAdapter {
//
//        @Resource
//        private TokenStore tokenStore;
//
//        @Override
//        public void configure(ResourceServerSecurityConfigurer resources) throws Exception {
//            resources.tokenStore(tokenStore);
//        }
//
//        @Override
//        public void configure(HttpSecurity http) throws Exception {
//
//            // 真实数据请求,
//            http.formLogin().disable()
//                    .logout().disable()
//                    .headers().disable()
//                    .sessionManagement().disable()
//                    .anonymous().disable()
//                    .csrf().disable()
//                    .httpBasic().disable()
//                    .antMatcher("/api/**")
//                    .authorizeRequests()
//                    .anyRequest().authenticated()
//            ;
//        }
//
//    }
//
////    @Configuration
////    @EnableAuthorizationServer
//    protected static class AuthorizationServerConfiguration extends AuthorizationServerConfigurerAdapter {
//
//        @Resource
//        private TokenStore tokenStore;
//
//        @Resource
//        private JwtAccessTokenConverter jwtAccessTokenConverter;
//
//        @Autowired
//        @Qualifier("authenticationManagerBean")
//        private AuthenticationManager authenticationManager;
//
//        @Override
//        public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
//            clients.inMemory()
//                    .withClient("lit")
//                    .secret("lit")
//                    .scopes("read", "write")
//                    .authorizedGrantTypes("authorization_code", "password", "client_credentials", "implicit", "refresh_token")
//                    .accessTokenValiditySeconds(86400)
//                    .refreshTokenValiditySeconds(86400)
//                    .autoApprove("*")
//            ;
//        }
//
//
//        @Override
//        public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
//            endpoints.authenticationManager(authenticationManager)
//                    .reuseRefreshTokens(true)
//                    .tokenStore(tokenStore)
//                    .tokenEnhancer(jwtAccessTokenConverter)
//                    .accessTokenConverter(jwtAccessTokenConverter)
//            ;
//
//        }
//
//        @Override
//        public void configure(AuthorizationServerSecurityConfigurer security) throws Exception {
//            // client 密码验证器
//            security.passwordEncoder(NoOpPasswordEncoder.getInstance())
//            ;
//        }
//
//    }
//
//
//}
