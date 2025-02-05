package org.example.checkout.service;

import org.example.checkout.proto.CheckoutReq;
import org.example.checkout.proto.CheckoutResp;

public interface CheckoutService {
    CheckoutResp checkout(CheckoutReq req);
}
