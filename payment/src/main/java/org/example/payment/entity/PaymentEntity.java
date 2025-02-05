package org.example.payment.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PaymentEntity {
    private int transactionId;
    private int orderId;
    private float amount;
    private int userId;
    private String creditCardNumber;
    private int creditCardCvv;
    private int creditCardExpirationYear;
    private int creditCardExpirationMonth;
}
