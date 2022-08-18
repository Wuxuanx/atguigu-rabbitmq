package com.atguigu.mybatis.mappers;

import com.atguigu.mybatis.pojo.Emp;
import org.apache.ibatis.annotations.Param;

public interface EmpMapper {

    Emp getEmpByEmpId(@Param("empId")Integer empId);
}
