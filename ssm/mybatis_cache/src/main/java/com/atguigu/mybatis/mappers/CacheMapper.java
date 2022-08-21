package com.atguigu.mybatis.mappers;

import com.atguigu.mybatis.pojo.Emp;
import org.apache.ibatis.annotations.Param;

public interface CacheMapper {
//    根据员工id查询信息
    Emp getEmpById(@Param("empId")Integer empId);
}
