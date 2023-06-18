package com.ezreal.autobi.domain.user.filter;

import com.ezreal.autobi.utils.JwtUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.web.filter.AccessControlFilter;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
@Slf4j
public class JwtFilter extends AccessControlFilter {

    @Resource
    private JwtUtils jwtUtils;

    @Override
    protected boolean isAccessAllowed(ServletRequest servletRequest, ServletResponse servletResponse, Object o) throws Exception {

        // 直接进入 onAccessDenied 方法
        return false;
    }

    @Override
    public String getLoginUrl() {
        return super.getLoginUrl();
    }

    @Override
    protected boolean onAccessDenied(ServletRequest servletRequest, ServletResponse servletResponse) throws Exception {
        HttpServletRequest httpServletRequest = (HttpServletRequest) servletRequest;
        HttpServletResponse httpServletResponse = (HttpServletResponse) servletResponse;
        String token = httpServletRequest.getHeader("Authorization");

        if (token == null) {
            log.warn("JwtFilter|onAccessDenied|token为空, 拒绝访问");
            return false;
        }

        try {
            jwtUtils.verify(token);
            log.warn("JwtFilter|onAccessDenied|token 验证成功");
            return true;
        } catch (Exception e) {
            log.warn("JwtFilter|onAccessDenied|token 解析失败, message: {}", e.getMessage());
            httpServletResponse.sendRedirect("/jwt/error");
            return false;
        }
    }
}
