package org.example.order.service.impl;

import cn.dev33.satoken.stp.StpUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.dubbo.config.annotation.DubboService;
import org.example.cart.dao.ICartDao;
import org.example.order.dao.IOrderDao;
import org.example.order.entity.AddressEntity;
import org.example.order.entity.OrderEntity;
import org.example.order.entity.OrderItemEntity;
import org.example.order.proto.*;
import org.example.order.service.OrderService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@RestController()
@CrossOrigin("${app.config.cross-origin}")
@RequestMapping("/api/${app.config.api-version}/order/")
@DubboService(version = "1.0")
public class OrderServiceImpl implements OrderService {

    private ICartDao cartDao;
    private IOrderDao orderDao;

    public OrderServiceImpl(ICartDao cartDao, IOrderDao orderDao) {
        this.cartDao = cartDao;
        this.orderDao = orderDao;
    }
    @RequestMapping(value = "placeOrder", method = RequestMethod.POST)
    @Override
    @Transactional
    public PlaceOrderResp placeOrder(PlaceOrderReq placeOrderReq) {
        Address address = placeOrderReq.getAddress();
        if (address==null){
            throw new IllegalArgumentException("address is null");
        }
        String email = placeOrderReq.getEmail();
        if(StringUtils.isBlank(email)||email.length()==0){
            throw new IllegalArgumentException("email is null");
        }
        int userId = placeOrderReq.getUserId();
        Object loginId = StpUtil.getLoginId();
        if(userId!=Integer.valueOf(loginId.toString())){
            log.info("userId:{} loginId:{}",userId,loginId);
            throw new IllegalArgumentException("userId is not loginId");
        }
        if(userId==0){
            throw new IllegalArgumentException("userId is null");
        }
        String userCurrency = placeOrderReq.getUserCurrency();
        if(StringUtils.isBlank(userCurrency)||userCurrency.length()==0){
            throw new IllegalArgumentException("userCurrency is null");
        }
        List<OrderItem> orderItemsList = placeOrderReq.getOrderItemsList();
        if(orderItemsList==null||orderItemsList.size()==0){
            throw new IllegalArgumentException("orderItemsList is null");
        }
        float totalCost=0;
        Order.Builder builder = Order.newBuilder();
        List<CartItem> items=new ArrayList<>();
        for(OrderItem orderItem : orderItemsList){
            CartItem item = orderItem.getItem();
            float cost = orderItem.getCost();
            totalCost+=cost;
            items.add(item);

        }
        OrderEntity order = OrderEntity.builder().userId(userId).userCurrency(userCurrency)
                .address(AddressEntity.builder().streetAddress(address.getStreetAddress()).city(address.getCity()).
                        state(address.getState()).country(address.getCountry()).zipCode(address.getZipCode()).build())
                .email(email).totalCost(totalCost).build();
        orderDao.placeOrder(order);

        int Orderid = order.getId();
        for(OrderItem orderItem : orderItemsList){
            CartItem item = orderItem.getItem();
            float cost = orderItem.getCost();
            totalCost+=cost;
            items.add(item);
            orderDao.insertOrderItem(Orderid,orderItem);
        }
//        Order order = builder.setOrderId(String.valueOf(orderId)).setAddress(address).setEmail(email).setUserId(userId)
//                .setUserCurrency(userCurrency).setCreatedAt((int) System.currentTimeMillis()).build();
        return PlaceOrderResp.newBuilder().setOrder(OrderResult.newBuilder().setOrderId(String.valueOf(Orderid)).build()).build();



    }
    @RequestMapping(value = "listOrder", method = RequestMethod.POST)
    @Override
    @Transactional
    public ListOrderResp listOrder(ListOrderReq listOrderReq) {
        int userId = listOrderReq.getUserId();
        Object loginId = StpUtil.getLoginId();
        if(userId!=Integer.valueOf(loginId.toString())){
            log.info("userId:{} loginId:{}",userId,loginId);
            throw new IllegalArgumentException("userId is not loginId");
        }
        if(userId==0){
            throw new IllegalArgumentException("userId is null");
        }
        List<OrderEntity> orderEntities = orderDao.listOrder(userId);
        List<Order> orders=new ArrayList<>();
        for(OrderEntity orderEntity : orderEntities){

            orders.add(exchange(orderEntity));

        }
//        ListOrderResp.newBuilder().

        return ListOrderResp.newBuilder().addAllOrders(orders).build();
    }
    @RequestMapping(value = "markOrderPaid", method = RequestMethod.POST)
    @Override
//    感觉发事件+定时任务查表更合适
    public MarkOrderPaidResp markOrderPaid(MarkOrderPaidReq markOrderPaidReq) {
        String orderId = markOrderPaidReq.getOrderId();
        if(StringUtils.isBlank(orderId)||orderId.length()==0){
            throw new IllegalArgumentException("orderId is null");
        }
        int userId = markOrderPaidReq.getUserId();
        Object loginId = StpUtil.getLoginId();
        if(userId!=Integer.valueOf(loginId.toString())){
            log.info("userId:{} loginId:{}",userId,loginId);
            throw new IllegalArgumentException("userId is not loginId");
        }
        if(userId==0){
            throw new IllegalArgumentException("userId is null");
        }
        Integer i = orderDao.checkOrderPaid(userId, Integer.parseInt(orderId));
        if(i==null){
            log.info("订单不存在");
        }
        else if(i==0){
            log.info("支付成功");
            orderDao.markOrderPaid(userId, Integer.parseInt(orderId));
        }else{
            log.info("订单已支付");
        }
        return MarkOrderPaidResp.newBuilder().build();
    }
    @Transactional
    public Order exchange(OrderEntity entity){
        AddressEntity address = entity.getAddress();
        Address address1 = Address.newBuilder().setStreetAddress(address.getStreetAddress()).setZipCode(address.getZipCode())
                .setCountry(address.getCountry()).setCity(address.getCity()).setState(address.getState()).build();
        int orderID = entity.getId();
        List<OrderItemEntity> orderItems = orderDao.listOrderItem(orderID);
        List<OrderItem> orderItemsList=new ArrayList<>();
        for(OrderItemEntity orderItem : orderItems){
            orderItemsList.add(OrderItem.newBuilder().setItem(CartItem.newBuilder()
                    .setProductId(orderItem.getItem().getProductId()).setQuantity(orderItem.getItem().getQuantity())
                    .build()).setCost(orderItem.getCost()).build());
        }
        Order.Builder builder = Order.newBuilder().setOrderId(String.valueOf(entity.getId())).setUserCurrency(entity.getUserCurrency())
                .setAddress(address1).setEmail(entity.getEmail()).setUserId(entity.getUserId()).
                setCreatedAt(entity.getCreateAt());
        orderItemsList.stream().forEach(item->builder.addOrderItems(item));
        return builder.build();

    }
}
