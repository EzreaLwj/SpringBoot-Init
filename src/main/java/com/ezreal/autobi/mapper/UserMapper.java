package com.ezreal.autobi.mapper;

import com.ezreal.autobi.model.entity.User;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
* @author Ezreal
* @description 针对表【user(用户表)】的数据库操作Mapper
* @createDate 2023-06-17 12:09:38
* @Entity com.ezreal.autobi.model/entity.User
*/
@Mapper
public interface UserMapper extends BaseMapper<User> {

}




