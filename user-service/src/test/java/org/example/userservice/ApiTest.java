package org.example.userservice;


import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.annotation.DubboReference;

import org.example.userservice.proto.*;
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
    @Test
    public void test_rpc_logout() {
        LogoutReq req = LogoutReq.newBuilder().setUserId(5).build();
        // 调用 Dubbo 服务
        LogoutResp resp = userService.logout(req);
        // 输出结果

        log.info("Success logout: " + resp.getRes());
        //        log.info(JSON.toJSONString(resp));

    }
    @Test
    public void test_rpc_addBlackList() {
        AddBlackListReq req = AddBlackListReq.newBuilder().setUserId(5).build();
        // 调用 Dubbo 服务
        AddBlackListResp resp = userService.addBlackList(req);
        // 输出结果
        log.info("Success logout: " + resp.getRes());
        //        log.info(JSON.toJSONString(resp));

    }
    @Test
    public void test_rpc_delete() {
        DeleteReq req = DeleteReq.newBuilder().setUserId(2).build();
        // 调用 Dubbo 服务
        DeleteResp resp = userService.delete(req);
        // 输出结果
        log.info("Success logout: " + resp.getRes());
        //        log.info(JSON.toJSONString(resp));

    }

}
