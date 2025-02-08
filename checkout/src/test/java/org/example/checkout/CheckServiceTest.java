package org.example.checkout;

import cn.dev33.satoken.stp.StpUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.annotation.DubboReference;
import org.example.checkout.proto.Address;
import org.example.checkout.proto.CheckoutReq;
import org.example.checkout.proto.CheckoutResp;
import org.example.checkout.proto.CreditCardInfo;
import org.example.checkout.service.CheckoutService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest
public class CheckServiceTest {
    @DubboReference(version = "1.0")
    private CheckoutService checkoutService;
    @Test
    public void testCheckout(){
        Address address = Address.newBuilder().setCity("北京").setStreetAddress("长安街").setState("北京").
                setCountry("中国").setZipCode("10001").build();
        CreditCardInfo cardInfo = CreditCardInfo.newBuilder().setCreditCardNumber("加密后").setCreditCardCvv(1246).
                setCreditCardExpirationMonth(10).setCreditCardExpirationYear(2025).build();
        CheckoutReq req = CheckoutReq.newBuilder().setUserId(1).setAddress(address).setEmail("testuser").setCreditCard(cardInfo).
                setFirstname("二").setLastname("王").build();
        StpUtil.login(1);
        CheckoutResp checkout = checkoutService.checkout(req);
        log.info("success");
        log.info("transactionId:{},orderId:{}",checkout.getTransactionId());

    }

}
