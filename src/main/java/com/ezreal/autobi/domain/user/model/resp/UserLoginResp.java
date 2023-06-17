package com.ezreal.autobi.domain.user.model.resp;

import lombok.Data;

@Data
public class UserLoginResp {

    /**
     * 用户id
     */
    private Long id;

    /**
     * 用户昵称
     */
    private String userName;

    /**
     * 用户账号
     */
    private String userAccount;

    /**
     * 用户角色
     */
    private String userRole;
}
