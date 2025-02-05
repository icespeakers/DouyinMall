package org.example.cart;

import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.annotation.DubboReference;
import org.example.cart.proto.*;
import org.example.cart.service.CartService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest
public class CartServiceTest {
    @DubboReference(version = "1.0")
    private CartService cartService;

    @Test
    public void test_rpc_addItem() {
        AddItemReq req = AddItemReq.newBuilder().setUserId(1)
                .setItem(CartItem.newBuilder().setProductId(3).setQuantity(1).build())
//                .setItem(CartItem.newBuilder().setProductId(3).setQuantity(2).build())
                .build();
        AddItemResp addItemResp = cartService.addItem(req);
        log.info("add item success ");

    }
    @Test
    public void test_rpc_emptyCart() {
        EmptyCartReq req = EmptyCartReq.newBuilder().setUserId(1).build();

        EmptyCartResp emptyCartResp = cartService.emptyCart(req);
        log.info("empty item success from user {} ",req.getUserId());

    }
    @Test
    public void test_rpc_getCart() {
        GetCartReq req = GetCartReq.newBuilder().setUserId(1).build();

        GetCartResp resp = cartService.getCart(req);
        List<CartItem> itemsList = resp.getCart().getItemsList();
        itemsList.forEach(item->{log.info("productId:{},quantity:{}",item.getProductId(),item.getQuantity());});

        log.info("getCart success from user {} ",req.getUserId());

    }
}
