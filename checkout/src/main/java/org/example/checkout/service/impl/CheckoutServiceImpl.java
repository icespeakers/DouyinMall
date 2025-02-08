package org.example.checkout.service.impl;

import cn.dev33.satoken.stp.StpUtil;
import lombok.extern.slf4j.Slf4j;

import org.apache.commons.lang3.StringUtils;
import org.apache.dubbo.config.annotation.DubboService;
import org.example.checkout.dao.ICheckoutDao;
import org.example.checkout.entity.CheckoutEntity;
import org.example.checkout.proto.Address;
import org.example.checkout.proto.CheckoutReq;
import org.example.checkout.proto.CheckoutResp;
import org.example.checkout.proto.CreditCardInfo;
import org.example.checkout.service.CheckoutService;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController()
@CrossOrigin("${app.config.cross-origin}")
@RequestMapping("/api/${app.config.api-version}/checkout/")
@DubboService(version = "1.0")
public class CheckoutServiceImpl implements CheckoutService {

    private ICheckoutDao checkoutDao;

    public CheckoutServiceImpl(ICheckoutDao checkoutDao) {
        this.checkoutDao = checkoutDao;
    }

    @RequestMapping(value = "checkout", method = RequestMethod.POST)
    @Override
    public CheckoutResp checkout(CheckoutReq req) {
        int userId = req.getUserId();
        Object loginId = StpUtil.getLoginId();
        if(userId!=Integer.valueOf(loginId.toString())){
            log.info("userId:{} loginId:{}",userId,loginId);
            throw new IllegalArgumentException("userId is not loginId");
        }
        if(userId==0){
            throw new IllegalArgumentException("userId is null");
        }
        String email = req.getEmail();
        if(StringUtils.isBlank(email)||email.length()==0){
            throw new IllegalArgumentException("email is null");
        }
        String firstname = req.getFirstname();
        if(StringUtils.isBlank(firstname)||firstname.length()==0){
            throw new IllegalArgumentException("firstname is null");
        }
        String lastname = req.getLastname();
        if(StringUtils.isBlank(lastname)||lastname.length()==0){
            throw new IllegalArgumentException("lastname is null");
        }
        Address address = req.getAddress();
        if(address==null){
            throw new IllegalArgumentException("address is null");
        }
        CreditCardInfo creditCard = req.getCreditCard();
        CheckoutEntity checkout = CheckoutEntity.builder().userId(userId).email(email).firstName(firstname).lastName(lastname).
                streetAddress(address.getStreetAddress()).city(address.getCity()).state(address.getState()).
                country(address.getCountry()).zipCode(Integer.valueOf(address.getZipCode())).creditCardNumber(creditCard.getCreditCardNumber()).
                creditCardCvv(creditCard.getCreditCardCvv()).creditCardExpirationMonth(creditCard.getCreditCardExpirationMonth()).
                creditCardExpirationYear(creditCard.getCreditCardExpirationYear()).build();
        Integer transactionId = checkoutDao.checkout(checkout);
        return CheckoutResp.newBuilder().setTransactionId(String.valueOf(transactionId)).build();

    }


}
