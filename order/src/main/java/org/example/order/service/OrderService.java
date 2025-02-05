package org.example.order.service;


import org.example.order.proto.*;

public interface OrderService {
    PlaceOrderResp placeOrder(PlaceOrderReq placeOrderReq);
    ListOrderResp listOrder(ListOrderReq listOrderReq);
    MarkOrderPaidResp markOrderPaid(MarkOrderPaidReq markOrderPaidReq);
}
