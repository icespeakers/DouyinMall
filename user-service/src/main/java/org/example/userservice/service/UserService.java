package org.example.userservice.service;


import org.example.userservice.proto.*;

public interface UserService {
    RegisterResp register(RegisterReq request);

    LoginResp login(LoginReq request);

    DeleteResp delete(DeleteReq request);

    LogoutResp logout(LogoutReq request);

    AddBlackListResp addBlackList(AddBlackListReq request);
}