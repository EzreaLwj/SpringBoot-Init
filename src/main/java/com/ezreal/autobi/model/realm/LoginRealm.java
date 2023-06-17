package com.ezreal.autobi.model.realm;

import com.ezreal.autobi.mapper.UserMapper;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

@Component
public class LoginRealm extends AuthorizingRealm {

    @Resource
    private UserMapper userMapper;

    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        return null;
    }

    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        // 用户唯一凭证
//        String principal = authenticationToken.getPrincipal().toString();
//        String password = mallUserMapper.getPassword(principal);
//
//        if (StrUtil.isNotEmpty(password)) {
//            // 自定义信息验证类，具体验证过程由 shiro 底层实现，我们只需要提供正确的数据即可
//            AuthenticationInfo authenticationInfo = new SimpleAuthenticationInfo(
//                    authenticationToken.getPrincipal(),
//                    password,
//                    ByteSource.Util.bytes(Code.ShiroCode.SALT),  // 添加盐值
//                    authenticationToken.getPrincipal().toString()
//            );
//            return authenticationInfo;
//        }
        return null;
    }
}
