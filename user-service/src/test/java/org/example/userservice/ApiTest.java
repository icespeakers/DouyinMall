package org.example.userservice;


import com.alibaba.fastjson2.JSON;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.annotation.DubboReference;

import org.example.userservice.proto.LoginReq;
import org.example.userservice.proto.LoginResp;
import org.example.userservice.proto.RegisterReq;
import org.example.userservice.proto.RegisterResp;
import org.example.userservice.service.UserService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest
public class ApiTest {

    @DubboReference(version = "1.0")
    private UserService userService;

//    @Test
//    public void test_rpc_register() {
//        RegisterReq req = RegisterReq.newBuilder()
//                .setEmail("12@qq.com")
//                .setPassword("123456")
//                .setConfirmPassword("123456")
//                .build();
//        // 调用 Dubbo 服务
//        RegisterResp resp = userService.register(req);
//
//        // 输出结果
//        log.info("Success register: " + resp.getUserId());
////        log.info(JSON.toJSONString(resp));
//
//    }
    @Test
    public void test_rpc_login() {
        LoginReq req = LoginReq.newBuilder()
                .setEmail("12@qq.com")
                .setPassword("123456")
                .build();
        // 调用 Dubbo 服务
        LoginResp resp = userService.login(req);

        // 输出结果

        log.info("Success login: " + resp.getUserId());
    //        log.info(JSON.toJSONString(resp));

    }

}
