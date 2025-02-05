package org.example.checkout.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CheckoutEntity {
    private int transactionId;
    private int userId;
//    private int orderId;
    private String firstName;
    private String lastName;
    private String email;

    private String streetAddress;
    private String city;
    private String state;
    private String country;
    private int zipCode;

    private String creditCardNumber;
    private int creditCardCvv;
    private int creditCardExpirationYear;
    private int creditCardExpirationMonth;
}
