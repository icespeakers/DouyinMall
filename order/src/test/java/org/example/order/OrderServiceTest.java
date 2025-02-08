package org.example.order;

import cn.dev33.satoken.stp.StpUtil;
import com.alibaba.fastjson2.JSON;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.annotation.DubboReference;
import org.example.order.proto.*;
import org.example.order.service.OrderService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest
public class OrderServiceTest {
    @DubboReference(version = "1.0")
    private OrderService orderService;
    @Test
    public void test_rpc_placeOrder() {
        Address address = Address.newBuilder().setCity("北京").setStreetAddress("长安街").setState("北京").setCountry("中国").setZipCode(10001).build();
        StpUtil.login(1);
        OrderItem orderItem01 = OrderItem.newBuilder().setCost(10).setItem(CartItem.newBuilder().setProductId(2).setQuantity(1).build()).build();
        OrderItem orderItem02 = OrderItem.newBuilder().setCost(15).setItem(CartItem.newBuilder().setProductId(3).setQuantity(3).build()).build();
        List<OrderItem> orderItems = new ArrayList<>();
        orderItems.add(orderItem01);
        orderItems.add(orderItem02);
        PlaceOrderReq req = PlaceOrderReq.newBuilder().setAddress(address).setUserId(1).setUserCurrency("CN").setEmail("test@example.com").addOrderItems(orderItem01).addOrderItems(orderItem02).build();
        PlaceOrderResp placeOrderResp = orderService.placeOrder(req);
        // 输出结果

        log.info(JSON.toJSONString(placeOrderResp.getOrder().getOrderId()));
//        productsList.stream().forEach(product -> log.info(JSON.toJSONString(product.getName())));
//        log.info(JSON.toJSONString(resp));
    }

    @Test
    public void test_rpc_listOrders() {
        ListOrderReq req = ListOrderReq.newBuilder().setUserId(1).build();
        StpUtil.login(1);
        ListOrderResp listOrderResp = orderService.listOrder(req);
        // 输出结果

        for(Order order : listOrderResp.getOrdersList()){
            log.info("orderId:{}",JSON.toJSONString(order.getOrderId()));
            log.info("getEmail:{}",JSON.toJSONString(order.getEmail()));
            log.info("getAddress:{}",JSON.toJSONString(order.getAddress().getCity()));
            log.info("getCreatedAt:{}",JSON.toJSONString(order.getCreatedAt()));
            log.info("getUserCurrency:{}",JSON.toJSONString(order.getUserCurrency()));
            for(OrderItem orderItem : order.getOrderItemsList()){
                log.info("getProductId:{}",JSON.toJSONString(orderItem.getItem().getProductId()));
                log.info("getQuantity:{}",JSON.toJSONString(orderItem.getItem().getQuantity()));
            }

        }
        log.info("success");
        ;
//        productsList.stream().forEach(product -> log.info(JSON.toJSONString(product.getName())));
//        log.info(JSON.toJSONString(resp));
    }

    @Test
    public void test_rpc_markOrderPaid() {
        MarkOrderPaidReq req = MarkOrderPaidReq.newBuilder().setOrderId("15").setUserId(1).build();
        StpUtil.login(1);
        MarkOrderPaidResp markOrderPaidResp = orderService.markOrderPaid(req);
        // 输出结果

        log.info("success");

    }
}
