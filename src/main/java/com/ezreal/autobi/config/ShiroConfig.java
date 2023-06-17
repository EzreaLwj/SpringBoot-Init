package com.ezreal.autobi.config;


import com.ezreal.autobi.common.Code;
import com.ezreal.autobi.model.realm.LoginRealm;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.spring.web.config.DefaultShiroFilterChainDefinition;
import org.apache.shiro.web.mgt.CookieRememberMeManager;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.apache.shiro.web.servlet.SimpleCookie;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.annotation.Resource;

/**
 * Shiro 配置类
 */
@Configuration
public class ShiroConfig {

    @Resource
    private LoginRealm loginRealm;

    @Bean
    public DefaultWebSecurityManager defaultWebSecurityManager() {
        DefaultWebSecurityManager defaultWebSecurityManager = new DefaultWebSecurityManager();

        // 密码使用 MD5 来校验
        HashedCredentialsMatcher hashedCredentialsMatcher = new HashedCredentialsMatcher();
        hashedCredentialsMatcher.setHashAlgorithmName(Code.ShiroCode.MD5_ALGORITHM);

        hashedCredentialsMatcher.setHashIterations(Code.ShiroCode.HASH_ITERATIONS);
        loginRealm.setCredentialsMatcher(hashedCredentialsMatcher);

        defaultWebSecurityManager.setRememberMeManager(cookieRememberMeManager());
        defaultWebSecurityManager.setRealm(loginRealm);
        SecurityUtils.setSecurityManager(defaultWebSecurityManager);
        return defaultWebSecurityManager;
    }

    private CookieRememberMeManager cookieRememberMeManager() {
        CookieRememberMeManager cookieRememberMeManager = new CookieRememberMeManager();


        SimpleCookie simpleCookie = new SimpleCookie("rememberMe");

        // 设置 cookie 的根路径
        simpleCookie.setPath("/");
        simpleCookie.setHttpOnly(true);
        simpleCookie.setMaxAge(30*24*60*60);

        // 设置 cookie 对象
        cookieRememberMeManager.setCookie(simpleCookie);

        // 加密 remember me 的信息
        cookieRememberMeManager.setCipherKey("1234567890987654".getBytes());
        return cookieRememberMeManager;
    }

    @Bean
    public DefaultShiroFilterChainDefinition shiroFilterChainDefinition() {
        DefaultShiroFilterChainDefinition defaultShiroFilterChainDefinition = new DefaultShiroFilterChainDefinition();

        // anon 不需要鉴权，可以公共访问的资源
        defaultShiroFilterChainDefinition.addPathDefinition("/user/login", "anon");

        // 需要登录鉴权的路径
        defaultShiroFilterChainDefinition.addPathDefinition("/user/**", "authc");
        return defaultShiroFilterChainDefinition;
    }
}
