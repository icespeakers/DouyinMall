package org.example.payment.dao;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.example.payment.entity.PaymentEntity;
import org.example.payment.proto.CreditCardInfo;

@Mapper
public interface IPaymentDao {
    Integer checkCreditCardInfo(PaymentEntity paymentEntity);

    Integer checkOrderInfo(@Param("userId") Integer userId, @Param("orderId") float orderId, @Param("amount") float amount);

    void updateOrderInfo(@Param("orderId") float orderId);
}
