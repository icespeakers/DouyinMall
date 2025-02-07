package org.example.auth.service;

import org.example.auth.proto.DeliverTokenReq;
import org.example.auth.proto.DeliveryResp;
import org.example.auth.proto.VerifyResp;
import org.example.auth.proto.VerifyTokenReq;

public interface AuthService {
    DeliveryResp DeliverTokenByRPC(DeliverTokenReq deliverTokenReq);
    VerifyResp VerifyTokenByRPC(VerifyTokenReq verifyTokenReq);
}
