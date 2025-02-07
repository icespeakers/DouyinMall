package org.example.auth;

import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.annotation.DubboReference;
import org.example.auth.proto.DeliverTokenReq;
import org.example.auth.proto.DeliveryResp;
import org.example.auth.proto.VerifyTokenReq;
import org.example.auth.service.AuthService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest
public class AuthServiceTest {
    @DubboReference(version = "1.0")
    private AuthService authService;
    @Test
    public void test_rpc_deliverToken() {
        DeliveryResp resp = authService.DeliverTokenByRPC(DeliverTokenReq.newBuilder().setUserId(1).build());
        log.info("resp:{}",resp.getToken());


    }
    @Test
    public void test_rpc_verifyToken() {
        VerifyTokenReq req = VerifyTokenReq.newBuilder().setToken("c4ca4238a0b923820dcc509a6f75849b").build();
        boolean res = authService.VerifyTokenByRPC(req).getRes();
        log.info("res:{}",res);
    }

    @Test
    public void test_rpc_null() {
        DeliverTokenReq req = DeliverTokenReq.newBuilder().build();
        log.info("req null:{}",req.getUserId());
    }

}
