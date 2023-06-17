package com.ezreal.autobi.domain.user.impl;

import cn.hutool.core.util.RandomUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ezreal.autobi.common.BaseResponse;
import com.ezreal.autobi.common.Code;
import com.ezreal.autobi.common.RKey;
import com.ezreal.autobi.common.ResultsUtils;
import com.ezreal.autobi.domain.user.UserService;
import com.ezreal.autobi.domain.user.model.entity.User;
import com.ezreal.autobi.domain.user.model.resp.UserLoginResp;
import com.ezreal.autobi.domain.user.model.resp.UserRegisterResp;
import com.ezreal.autobi.mapper.UserMapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.crypto.hash.Md5Hash;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Ezreal
 * @description 针对表【user(用户表)】的数据库操作Service实现
 * @createDate 2023-06-17 12:09:38
 */
@Service
@Slf4j
public class UserServiceImpl extends ServiceImpl<UserMapper, User>
        implements UserService {

    @Autowired
    private UserMapper userMapper;

    @Override
    public BaseResponse<UserLoginResp> userLogin(String userAccount, String userPassword, Boolean autoLogin, HttpServletRequest servletRequest) {

        Subject subject = SecurityUtils.getSubject();

        UsernamePasswordToken usernamePasswordToken = new UsernamePasswordToken();
        usernamePasswordToken.setPassword(userPassword.toCharArray());
        usernamePasswordToken.setUsername(userAccount);

        if (autoLogin != null) {
            usernamePasswordToken.setRememberMe(autoLogin);
        }

        try {
            subject.login(usernamePasswordToken);
            log.info("UserController|userLogin|用户登录成功, userAccount: {}", userAccount);

            User user = userMapper.selectUserByAccount(userAccount);

            // 添加 session
            String sessionKey = RKey.UserKey.USER_SESSION.getKey() + user.getId();
            servletRequest.getSession().setAttribute(sessionKey, user.getId());

            UserLoginResp userLoginResp = new UserLoginResp();
            BeanUtils.copyProperties(user, userLoginResp);

            return ResultsUtils.success(Code.UserCode.USER_LOGIN_SUCCESS.getCode(),
                    Code.UserCode.USER_LOGIN_SUCCESS.getMessage(), userLoginResp);
        } catch (AuthenticationException authenticationException) {
            log.warn("UserController|userLogin|用户登录失败, userAccount: {}", userAccount);
            return ResultsUtils.success(Code.UserCode.USER_LOGIN_FAIL.getCode(),
                    Code.UserCode.USER_LOGIN_FAIL.getMessage(), null);
        }
    }

    @Override
    public BaseResponse<UserRegisterResp> userRegister(String userAccount, String userPassword, String userName) {
        User user = new User();
        user.setUserAccount(userAccount);


        // 加密密码
        Md5Hash md5Hash = new Md5Hash(userPassword, Code.ShiroCode.SALT, Code.ShiroCode.HASH_ITERATIONS);
        String hexPassword = md5Hash.toHex();
        user.setUserPassword(hexPassword);

        if (StrUtil.isEmpty(userName)) {
            userName = RandomUtil.randomNumbers(16);
        }
        user.setUserName(userName);

        UserRegisterResp userRegisterResp = new UserRegisterResp()
                .setUserAccount(userAccount);

        try {

            userMapper.insert(user);
            userRegisterResp.setId(user.getId());
            userRegisterResp.setUserName(user.getUserName());

            log.info("UserController|userRegister|用户注册成功, userAccount: {}", userAccount);
            return ResultsUtils.success(Code.UserCode.USER_REGISTER_SUCCESS.getCode(),
                    Code.UserCode.USER_REGISTER_SUCCESS.getMessage(), userRegisterResp);

        } catch (DuplicateKeyException e) {
            log.warn("UserController|userRegister|用户账号已经存在, userAccount: {}", userAccount, e);
            return ResultsUtils.success(Code.UserCode.USER_REGISTER_FAIL.getCode(),
                    Code.UserCode.USER_REGISTER_FAIL.getMessage(), userRegisterResp);
        }
    }
}




