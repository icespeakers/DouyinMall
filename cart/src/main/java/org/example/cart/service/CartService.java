package org.example.cart.service;

import org.example.cart.proto.*;
import org.springframework.stereotype.Service;


public interface CartService {
    AddItemResp addItem(AddItemReq addItemReq);

    GetCartResp getCart(GetCartReq getCartReq);

    EmptyCartResp emptyCart(EmptyCartReq emptyCartReq);

}
