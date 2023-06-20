package com.ezreal.autobi.controller;

import cn.hutool.core.util.StrUtil;
import com.ezreal.autobi.common.BaseResponse;
import com.ezreal.autobi.common.Code;
import com.ezreal.autobi.common.ResultsUtils;
import com.ezreal.autobi.domain.user.UserService;
import com.ezreal.autobi.domain.user.model.req.UserLoginRequest;
import com.ezreal.autobi.domain.user.model.req.UserOutLoginReq;
import com.ezreal.autobi.domain.user.model.req.UserRegisterReq;
import com.ezreal.autobi.domain.user.model.resp.UserLoginResp;
import com.ezreal.autobi.domain.user.model.resp.UserOutLoginResp;
import com.ezreal.autobi.domain.user.model.resp.UserRegisterResp;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;


@RestController
@RequestMapping("/user")
@Slf4j
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/{id}")
    public BaseResponse<UserLoginResp> getUserInfo(@PathVariable Long id, HttpServletRequest servletRequest) {
        if (id == null) {
            log.warn("UserController|userRegister|参数错误, userId: {}", id);
            return ResultsUtils.fail(Code.PARAMS_ERROR.getCode(), Code.PARAMS_ERROR.getMessage());
        }

        return userService.getUserInfo(id, servletRequest);
    }

    @PostMapping("/login")
    public BaseResponse<UserLoginResp> userLogin(@RequestBody UserLoginRequest userLoginRequest,
                                                 HttpServletRequest servletRequest) {

        String userAccount = userLoginRequest.getUserAccount();
        String userPassword = userLoginRequest.getUserPassword();
        log.info("UserController|userRegister|用户开始登录, userAccount: {}", userAccount);
        if (StrUtil.isEmpty(userPassword) || StrUtil.isEmpty(userAccount)) {
            log.warn("UserController|userRegister|参数错误, userAccount: {}, userPassword: {}", userAccount, userPassword);
            return ResultsUtils.fail(Code.PARAMS_ERROR.getCode(), Code.PARAMS_ERROR.getMessage());
        }

        BaseResponse<UserLoginResp> userLoginRespBaseResponse = userService.userLogin(userAccount, userPassword, userLoginRequest.getAutoLogin(), servletRequest);
        log.info("UserController|userRegister|用户开始登录结束, userAccount: {}", userAccount);
        return userLoginRespBaseResponse;

    }

    @PostMapping("/register")
    public BaseResponse<UserRegisterResp> userRegister(@RequestBody UserRegisterReq userRegisterReq) {

        String userAccount = userRegisterReq.getUserAccount();
        String userPassword = userRegisterReq.getUserPassword();

        log.info("UserController|userRegister|用户开始注册, userAccount: {}", userAccount);

        if (StrUtil.isEmpty(userPassword) || StrUtil.isEmpty(userAccount)) {
            log.warn("UserController|userRegister|参数错误, userAccount: {}, userPassword: {}", userAccount, userPassword);
            return ResultsUtils.fail(Code.PARAMS_ERROR.getCode(), Code.PARAMS_ERROR.getMessage());
        }

        BaseResponse<UserRegisterResp> userRegisterRespBaseResponse = userService.userRegister(userAccount, userPassword, userRegisterReq.getUserName());
        log.info("UserController|userRegister|用户注册结束, userAccount: {}", userAccount);
        return userRegisterRespBaseResponse;
    }


    @PostMapping("/outLogin")
//    @RequiresRoles("admin")
    public BaseResponse<UserOutLoginResp> userOutLogin(@RequestBody UserOutLoginReq userOutLoginReq) {
        log.info("UserController|userRegister|用户开始注销登录, id: {}", userOutLoginReq.getId());
        BaseResponse<UserOutLoginResp> userOutLoginRespBaseResponse = userService.userOutLogin(userOutLoginReq);
        log.info("UserController|userRegister|用户注销登录, id: {}", userOutLoginReq.getId());
        return userOutLoginRespBaseResponse;
    }

}
