package org.example.checkout.dao;

import org.apache.ibatis.annotations.Mapper;
import org.example.checkout.entity.CheckoutEntity;

@Mapper
public interface ICheckoutDao {
//    checkout
    Integer checkout(CheckoutEntity checkoutEntity);
}
