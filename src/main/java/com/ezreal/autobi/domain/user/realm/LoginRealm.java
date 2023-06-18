package com.ezreal.autobi.domain.user.realm;

import cn.hutool.core.util.StrUtil;
import com.ezreal.autobi.common.Code;
import com.ezreal.autobi.domain.user.model.entity.User;
import com.ezreal.autobi.mapper.UserMapper;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;


@Component
public class LoginRealm extends AuthorizingRealm {

    @Resource
    private UserMapper userMapper;

    /**
     * 鉴权
     * @param principalCollection
     * @return
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        String principal =  principalCollection.getPrimaryPrincipal().toString();
        User user = userMapper.selectUserByAccount(principal);
        SimpleAuthorizationInfo simpleAuthorizationInfo = new SimpleAuthorizationInfo ();
        simpleAuthorizationInfo.addRole(user.getUserRole());
        return simpleAuthorizationInfo;
    }

    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        // 用户唯一凭证
        String principal = authenticationToken.getPrincipal().toString();

        String password = userMapper.getPasswordByAccount(principal);
        if (StrUtil.isNotEmpty(password)) {
            // 自定义信息验证类，具体验证过程由 shiro 底层实现，我们只需要提供正确的数据即可
            AuthenticationInfo authenticationInfo = new SimpleAuthenticationInfo(
                    authenticationToken.getPrincipal(),
                    password,
                    ByteSource.Util.bytes(Code.ShiroCode.SALT),  // 添加盐值
                    authenticationToken.getPrincipal().toString()
            );
            return authenticationInfo;
        }
        return null;
    }
}
