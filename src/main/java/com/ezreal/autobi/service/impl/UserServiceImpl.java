package com.ezreal.autobi.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ezreal.autobi.model.entity.User;
import com.ezreal.autobi.service.UserService;
import com.ezreal.autobi.mapper.UserMapper;
import org.springframework.stereotype.Service;

/**
* @author Ezreal
* @description 针对表【user(用户表)】的数据库操作Service实现
* @createDate 2023-06-17 12:09:38
*/
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User>
    implements UserService{

}




