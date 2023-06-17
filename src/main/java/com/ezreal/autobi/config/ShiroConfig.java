package com.ezreal.autobi.config;

import com.ezreal.autobi.common.Code;
import com.ezreal.autobi.domain.user.realm.LoginRealm;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.mgt.DefaultSessionStorageEvaluator;
import org.apache.shiro.mgt.DefaultSubjectDAO;
import org.apache.shiro.mgt.SubjectFactory;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.subject.SubjectContext;
import org.apache.shiro.web.filter.authc.AnonymousFilter;
import org.apache.shiro.web.mgt.CookieRememberMeManager;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.apache.shiro.web.mgt.DefaultWebSubjectFactory;
import org.apache.shiro.web.servlet.SimpleCookie;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.annotation.Resource;
import javax.servlet.Filter;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;


/**
 * shiro 配置
 */
@Configuration
public class ShiroConfig {

    @Resource
    private LoginRealm loginRealm;

    @Bean
    public SubjectFactory subjectFactory() {
        class JwtDefaultSubjectFactory extends DefaultWebSubjectFactory {
            @Override
            public Subject createSubject(SubjectContext context) {
                context.setSessionCreationEnabled(false);
                return super.createSubject(context);
            }
        }
        return new JwtDefaultSubjectFactory();
    }

    @Bean
    public DefaultWebSecurityManager securityManager() {
        DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();

        // 关闭 ShiroDAO 功能
        DefaultSubjectDAO subjectDAO = new DefaultSubjectDAO();
        DefaultSessionStorageEvaluator defaultSessionStorageEvaluator = new DefaultSessionStorageEvaluator();

        // 不需要将 Shiro Session 中的东西存到任何地方（包括 Http Session 中）
        defaultSessionStorageEvaluator.setSessionStorageEnabled(false);
        subjectDAO.setSessionStorageEvaluator(defaultSessionStorageEvaluator);
        securityManager.setSubjectDAO(subjectDAO);

        // 禁止Subject的getSession方法
        securityManager.setSubjectFactory(subjectFactory());

        // 密码使用 MD5 来校验
        HashedCredentialsMatcher hashedCredentialsMatcher = new HashedCredentialsMatcher();
        hashedCredentialsMatcher.setHashAlgorithmName(Code.ShiroCode.MD5_ALGORITHM);
        hashedCredentialsMatcher.setHashIterations(Code.ShiroCode.HASH_ITERATIONS);
        loginRealm.setCredentialsMatcher(hashedCredentialsMatcher);

        securityManager.setRealm(loginRealm);
        securityManager.setRememberMeManager(cookieRememberMeManager());
        SecurityUtils.setSecurityManager(securityManager);

        return securityManager;
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
    public ShiroFilterFactoryBean shiroFilterFactoryBean() {
        ShiroFilterFactoryBean shiroFilter = new ShiroFilterFactoryBean();
        shiroFilter.setSecurityManager(securityManager());
        shiroFilter.setLoginUrl("/unauthenticated");
        shiroFilter.setUnauthorizedUrl("/unauthorized");

        // 添加jwt过滤器
        Map<String, Filter> filterMap = new HashMap<>();

        // 设置过滤器【anon\logout可以不设置】
        filterMap.put("anon", new AnonymousFilter());
//        filterMap.put("userLogin", new UserLoginFilter());
        shiroFilter.setFilters(filterMap);

        // 拦截器，指定方法走哪个拦截器 【login->anon】【logout->logout】【verify->jwt】
        Map<String, String> filterRuleMap = new LinkedHashMap<>();
//        filterRuleMap.put("/user/login", "userLogin");
        shiroFilter.setFilterChainDefinitionMap(filterRuleMap);
        return shiroFilter;
    }

}
