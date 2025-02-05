package org.example.checkout.service.impl;

import lombok.extern.slf4j.Slf4j;

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
        String email = req.getEmail();
        String firstname = req.getFirstname();
        String lastname = req.getLastname();
        Address address = req.getAddress();
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
