package org.example.auth.dao;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface IAuthDao {

    void deliverToken(@Param("userId") int userId, @Param("token") String token);

    Integer verifyToken(String token);
}
