package org.example.payment;

import cn.dev33.satoken.stp.StpUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.annotation.DubboReference;
import org.example.payment.proto.ChargeReq;
import org.example.payment.proto.ChargeResp;
import org.example.payment.proto.CreditCardInfo;
import org.example.payment.service.PaymentService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest
public class PaymentServiceTest {
    @DubboReference(version = "1.0")
    private PaymentService paymentService;

    @Test

    public void test_rpc_charge() {
        ChargeReq req = ChargeReq.newBuilder().setOrderId("17").setUserId(1).setAmount(25).
                setCreditCard(CreditCardInfo.newBuilder().setCreditCardNumber("加密后").setCreditCardCvv(1246).
                        setCreditCardExpirationMonth(10).setCreditCardExpirationYear(2025).build()).build();
        StpUtil.login(1);
        ChargeResp resp = paymentService.charge(req);
        String transactionId = resp.getTransactionId();
        log.info("transactionId:{}", transactionId);


    }

}
