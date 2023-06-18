package com.ezreal.autobi.domain.user;

import com.ezreal.autobi.common.BaseResponse;
import com.ezreal.autobi.domain.user.model.entity.User;
import com.baomidou.mybatisplus.extension.service.IService;
import com.ezreal.autobi.domain.user.model.req.UserOutLoginReq;
import com.ezreal.autobi.domain.user.model.resp.UserLoginResp;
import com.ezreal.autobi.domain.user.model.resp.UserOutLoginResp;
import com.ezreal.autobi.domain.user.model.resp.UserRegisterResp;

import javax.servlet.http.HttpServletRequest;

/**
* @author Ezreal
* @description 针对表【user(用户表)】的数据库操作Service
* @createDate 2023-06-17 12:09:38
*/
public interface UserService extends IService<User> {


    BaseResponse<UserLoginResp> userLogin(String userAccount, String userPassword,Boolean autoLogin, HttpServletRequest servletRequest);

    BaseResponse<UserRegisterResp> userRegister(String userAccount, String userPassword, String username);

    BaseResponse<UserLoginResp> getUserInfo(Long id,HttpServletRequest servletRequest);

    BaseResponse<UserOutLoginResp> userOutLogin(UserOutLoginReq userOutLoginReq);
}
