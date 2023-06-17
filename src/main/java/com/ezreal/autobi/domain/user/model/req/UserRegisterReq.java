package com.ezreal.autobi.domain.user.model.req;

import lombok.Data;

@Data
public class UserRegisterReq {

    /**
     * 账号
     */
    private String userAccount;

    /**
     * 密码
     */
    private String userPassword;

    /**
     * 用户名称
     */
    private String userName;
}
