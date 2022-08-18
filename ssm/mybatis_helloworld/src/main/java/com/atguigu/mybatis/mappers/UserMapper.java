package com.atguigu.mybatis.mappers;

import com.atguigu.mybatis.pojo.User;

import java.util.List;


public interface UserMapper {
    int insertUser();
    void updateUser();
    int deleteUser();
    User getUserbyId();
    List<User> getAllUser();
}
