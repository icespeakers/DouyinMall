package org.example.userservice.dao;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;


@Mapper
public interface IUserDao {
    Integer queryUserByEmail(String email);
    Integer queryUserByEmailAndPassword(@Param("email") String email, @Param("password") String password);
    Integer insert(@Param("email") String email, @Param("password") String password);
}
