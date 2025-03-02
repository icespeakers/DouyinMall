package org.example.auth.service.impl;

import cn.dev33.satoken.stp.StpUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.dubbo.config.annotation.DubboService;
import org.example.auth.dao.IAuthDao;
import org.example.auth.proto.DeliverTokenReq;
import org.example.auth.proto.DeliveryResp;
import org.example.auth.proto.VerifyResp;
import org.example.auth.proto.VerifyTokenReq;
import org.example.auth.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.util.DigestUtils;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@Slf4j
@RestController()
@CrossOrigin("${app.config.cross-origin}")
@RequestMapping("/api/${app.config.api-version}/auth/")
@DubboService(version = "1.0")
public class AuthServiceImpl implements AuthService {
    @Autowired
    private IAuthDao authDao;

    @RequestMapping(value = "deliverTokenByRPC", method = RequestMethod.POST)
    @Override
    public DeliveryResp DeliverTokenByRPC(DeliverTokenReq deliverTokenReq) {
        int userId = deliverTokenReq.getUserId();
        if (userId == 0) {
            throw new IllegalArgumentException("userId is null");
        }
//        String salt= generateUUID();
//        String token = GenerateToken(String.valueOf(userId)+salt);
        StpUtil.login(userId);
        String token = StpUtil.getTokenValue();
        authDao.deliverToken(userId, token);
        DeliveryResp resp = DeliveryResp.newBuilder().setToken(token).build();
        return resp;
    }

    @RequestMapping(value = "verifyTokenByRPC", method = RequestMethod.POST)
    @Override
    public VerifyResp VerifyTokenByRPC(VerifyTokenReq verifyTokenReq) {
        String token = verifyTokenReq.getToken();
        Object loginIdByToken = StpUtil.getLoginIdByToken(token);
        if (StringUtils.isBlank(token) || token.length() == 0||loginIdByToken==null) {
            throw new IllegalArgumentException("token is null");
        }
        Integer count = authDao.verifyToken(token);
        return count != null ? VerifyResp.newBuilder().setRes(true).build() : VerifyResp.newBuilder().setRes(false).build();

    }


}
