package com.ezreal.autobi.mapper;

import com.ezreal.autobi.domain.user.model.entity.User;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
* @author Ezreal
* @description 针对表【user(用户表)】的数据库操作Mapper
* @createDate 2023-06-17 12:09:38
* @Entity com.ezreal.autobi.model/entity.User
*/
@Mapper
public interface UserMapper extends BaseMapper<User> {

    String getPasswordByAccount(@Param("userAccount") String userAccount);

    User selectUserByAccount(@Param("userAccount") String userAccount);
}




