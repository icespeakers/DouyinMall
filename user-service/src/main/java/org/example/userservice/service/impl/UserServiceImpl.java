package org.example.userservice.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.dubbo.config.annotation.DubboService;
import org.example.userservice.dao.IUserDao;

import org.example.userservice.proto.LoginReq;
import org.example.userservice.proto.LoginResp;
import org.example.userservice.proto.RegisterReq;
import org.example.userservice.proto.RegisterResp;
import org.example.userservice.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.DigestUtils;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@Slf4j
@RestController()
@CrossOrigin("${app.config.cross-origin}")
@RequestMapping("/api/${app.config.api-version}/user_service/")
@DubboService(version = "1.0")
public class UserServiceImpl implements UserService {
    @Autowired
    private IUserDao userDao;
    @RequestMapping(value = "register", method = RequestMethod.POST)
    @Override
    public RegisterResp register(RegisterReq request) {
        // 实现注册逻辑

        if(request.getPassword().isEmpty()||request.getConfirmPassword().isEmpty()){
            throw new IllegalArgumentException("参数不能为空!");
        }
        if(request.getPassword()!=request.getConfirmPassword()){
            throw new IllegalArgumentException("两次输入密码不一致!");
        }
        log.info("register user: {}", request.getEmail());
        Integer userId = userDao.queryUserByEmail(request.getEmail());
//        Integer userId=1;
        if(userId!=null){
            log.error("邮箱已存在!");
            throw new IllegalArgumentException("邮箱已存在!");
        }
        userId = userDao.insert(request.getEmail(), MD5(request.getPassword()));
        return RegisterResp.newBuilder().setUserId(userId).build();

    }
    @RequestMapping(value = "login", method = RequestMethod.POST)
    @Override
    public LoginResp login(LoginReq request) {
        if(request.getPassword().isEmpty()){
            throw new IllegalArgumentException("请输入密码!");
        }

        Integer userId = userDao.queryUserByEmailAndPassword(request.getEmail(), MD5(request.getPassword()));
        if(userId==null){
            throw new IllegalArgumentException("该邮箱未注册!");
        }
        return LoginResp.newBuilder().setUserId(userId).build();
    }

    public static String MD5(String key){
        if(StringUtils.isBlank(key)){
            return null;
        }
        return DigestUtils.md5DigestAsHex(key.getBytes());
    }

}
