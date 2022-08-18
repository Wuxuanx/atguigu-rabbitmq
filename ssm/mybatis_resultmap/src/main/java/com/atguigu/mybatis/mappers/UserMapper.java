package com.atguigu.mybatis.mappers;



import com.atguigu.mybatis.pojo.User;
import org.apache.ibatis.annotations.Param;

import java.util.Map;

public interface UserMapper {

    User getUserByName(String username);

    User checkLogin(String username, String password);

    User checkLogMap(Map<String, Object> map);

    int insertUser(User user);

    User checkLoginByParam(@Param("username") String username, @Param("password") String password);
}
