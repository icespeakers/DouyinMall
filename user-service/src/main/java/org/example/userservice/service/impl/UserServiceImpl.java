package org.example.userservice.service.impl;

import cn.dev33.satoken.stp.StpUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.dubbo.config.annotation.DubboService;
import org.example.userservice.dao.IUserDao;

import org.example.userservice.proto.*;
import org.example.userservice.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.DigestUtils;
import org.springframework.web.bind.annotation.*;

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
//        return RegisterResp.newBuilder().setUserId(userId).build();

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
            throw new IllegalArgumentException("登录失败!");
        }
        return LoginResp.newBuilder().setUserId(userId).build();
    }
    @RequestMapping(value = "delete", method = RequestMethod.POST)
    @Override
    @Transactional
    public DeleteResp delete(DeleteReq request) {
        try {
            int userId = request.getUserId();
            userDao.delete(userId);
            StpUtil.logout(userId);
        }catch (Exception e){
            log.error("登出失败: {}", e.getMessage());
            return DeleteResp.newBuilder().setRes(false).build();
        }finally {

            return DeleteResp.newBuilder().setRes(true).build();
        }
    }
    @RequestMapping(value = "logout", method = RequestMethod.POST)
    @Override
    public LogoutResp logout(LogoutReq request) {
        int userId = request.getUserId();
        try {
            StpUtil.logout(userId);
        }catch (Exception e){
            log.error("登出失败: {}", e.getMessage());
            return LogoutResp.newBuilder().setRes(false).build();
        }finally {

            return LogoutResp.newBuilder().setRes(true).build();
        }

    }
    @RequestMapping(value = "addBlackList", method = RequestMethod.POST)
    @Override
    @Transactional
    public AddBlackListResp addBlackList(AddBlackListReq request) {
        try {
            int userId = request.getUserId();
            userDao.addBlackList(userId);
            StpUtil.logout(userId);
        }catch (Exception e){
            log.error("登出失败: {}", e.getMessage());
            return AddBlackListResp.newBuilder().setRes(false).build();
        }finally {

            return AddBlackListResp.newBuilder().setRes(true).build();
        }
    }

    public static String MD5(String key){
        if(StringUtils.isBlank(key)){
            return null;
        }
        return DigestUtils.md5DigestAsHex(key.getBytes());
    }

}
