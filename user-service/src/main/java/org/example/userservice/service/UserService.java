package org.example.userservice.service;


import org.example.userservice.proto.LoginReq;
import org.example.userservice.proto.LoginResp;
import org.example.userservice.proto.RegisterReq;
import org.example.userservice.proto.RegisterResp;

public interface UserService {
    RegisterResp register(RegisterReq request);
    LoginResp login(LoginReq request);
}