package org.example.payment.service;

import org.example.payment.proto.ChargeReq;
import org.example.payment.proto.ChargeResp;

public interface PaymentService {
    ChargeResp charge(ChargeReq chargeReq);
}
