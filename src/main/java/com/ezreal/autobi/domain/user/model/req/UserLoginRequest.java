package com.ezreal.autobi.domain.user.model.req;

import lombok.Data;

@Data
public class UserLoginRequest {

    /**
     * 账号
     */
    private String userAccount;

    /**
     * 密码
     */
    private String userPassword;

    /**
     * remember me
     */
    private Boolean autoLogin;
}
