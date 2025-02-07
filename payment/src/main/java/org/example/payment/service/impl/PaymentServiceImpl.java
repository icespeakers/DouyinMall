package org.example.payment.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.annotation.DubboReference;
import org.apache.dubbo.config.annotation.DubboService;
import org.example.checkout.service.CheckoutService;
import org.example.payment.dao.IPaymentDao;
import org.example.payment.entity.PaymentEntity;
import org.example.payment.proto.ChargeReq;
import org.example.payment.proto.ChargeResp;
import org.example.payment.proto.CreditCardInfo;
import org.example.payment.service.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController()
@CrossOrigin("${app.config.cross-origin}")
@RequestMapping("/api/${app.config.api-version}/payment/")
@DubboService(version = "1.0")
public class PaymentServiceImpl implements PaymentService {
    @Autowired
    private IPaymentDao paymentDao;
//    @DubboReference(version = "1.0")
//    private CheckoutService checkoutService;

    @RequestMapping(value = "charge", method = RequestMethod.POST)
    @Override
    @Transactional
    public ChargeResp charge(ChargeReq chargeReq) {
        float amount = chargeReq.getAmount();
        if(amount==0){
            throw new IllegalArgumentException("amount is null");
        }
        Integer orderId = Integer.valueOf(chargeReq.getOrderId());
        if(orderId==null){
            throw new IllegalArgumentException("orderId is null");
        }
        int userId = chargeReq.getUserId();
        if(userId==0){
            throw new IllegalArgumentException("userId is null");
        }
        CreditCardInfo creditCard = chargeReq.getCreditCard();
        if(creditCard==null){
            throw new IllegalArgumentException("creditCard is null");
        }
        PaymentEntity payment = PaymentEntity.builder().userId(userId).orderId(orderId).amount(amount).
                creditCardNumber(creditCard.getCreditCardNumber()).
                creditCardCvv(creditCard.getCreditCardCvv()).
                creditCardExpirationMonth(creditCard.getCreditCardExpirationMonth()).
                creditCardExpirationYear(creditCard.getCreditCardExpirationYear()).build();
        Integer transactionId = paymentDao.checkCreditCardInfo(payment);
        if (transactionId==null){
            throw new NullPointerException("用户信息不正确");
        }
        Integer status= paymentDao.checkOrderInfo(userId, orderId, amount);
        if(status==null||status==0){
            throw new NullPointerException("订单信息不正确");
        }
        paymentDao.updateOrderInfo(orderId);
//        paymentDao.charge(amount, userId, orderId, creditCard);
        log.info("charge success");

        return ChargeResp.newBuilder().setTransactionId(String.valueOf(transactionId)).build();
    }
}
